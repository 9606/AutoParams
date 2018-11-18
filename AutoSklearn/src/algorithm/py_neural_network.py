# -*- coding: UTF-8 -*-

import json
import math
import os
import sys
import uuid
import numpy as np
import pandas as pd
from sklearn import neural_network
import classification
import regression

from sklearn import datasets as ds
from sklearn import metrics
from sklearn.externals import joblib
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import LabelEncoder

from util import MultiColumnLabelEncoder
from util import get_logger
from util import eva_result
from classification import classifier_evaluate
from regression import regression_evaluate
import warnings

lbfgs_params = []
sgd_params = ['learning_rate', 'shuffle', 'learning_rate_init', 'power_t', 'momentum', 'early_stopping']
adam_params = ['reg_lambda', 'shuffle', 'learning_rate_init', 'early_stopping', 'beta_1', 'beta_2', 'epsilon']

default_value = {
    'hidden_layer_sizes': (100,), 'activation': 'relu', 'solver': 'adam', 'alpha': 0.0001, 'batch_size': 'auto',
    'learning_rate': 'constant', 'max_iter': 200, 'random_state': None, 'shuffle': True, 'tol': 1e-4,
    'power_t': 0.5, 'verbose': False, 'warm_start': False, 'momentum': 0.9, 'nesterovs_momentum': True,
    'early_stopping': False, 'validation_fraction': 0.1, 'beta_1': 0.9, 'beta_2': 0.999,
    'epsilon': 1e-8
}

params_index = {'lbfgs': {}, 'sgd': {}, 'adam': {}}


class NeuralNetworkEvaluate(object):
    def __init__(self, algor_params, other_params):
        self.file_path = other_params["filePath"]
        self.data_format = other_params["fileType"]
        if self.data_format == 'csv':
            self.origin_data = pd.read_csv(self.file_path, sep=',', encoding='utf-8')
            self.data = self.origin_data
            self.na_symbol = other_params["naSymbol"]
            for i in self.na_symbol:
                self.data = self.data.replace(i, np.nan).dropna()
            self.rowNum = len(self.data)
            self.featNum = len(self.data.columns) - 1

            # self.data.values[:, 0:-1]
            x = MultiColumnLabelEncoder().fit_transform(self.data.iloc[:, 0:self.data.shape[1] - 1]).values
            y_origin = self.data.values[:, -1]
            y_fit = LabelEncoder().fit(y_origin)
            self.class_name = y_fit.classes_.tolist()
            y = y_fit.transform(y_origin)
        else:
            # type(x): scipy.sparse.csr.csr_matrix ; type(y):numpy.ndarray
            x, y = ds.load_svmlight_file(self.file_path)
            self.class_name = list(set(y))
            self.rowNum = x.shape[0]
            self.featNum = x.shape[1]

        self.x_train, self.x_test, self.y_train, self.y_test = train_test_split(
            x, y, test_size=0.25, random_state=42)
        self.params_range = algor_params
        self.params_name = []
        self.origin_converted = False
        self._origin_params = []
        self.dis_converted = False
        self._dis_params = []

    @property
    def origin_params(self):
        if not self.origin_converted:
            row = 1  # number of row
            lengths = []  # number of per param's value
            sizes = [1]  # number of row per param
            params_range_merge = []  # merge three types of per param into one list
            for k, v in self.params_range.iteritems():
                length = 0
                param_range_merge = []
                for v_values in v:
                    length += len(v_values) if len(v_values) != 0 else 0
                if length != 0:
                    self.params_name.append(k)
                    for v_values in v:
                        param_range_merge.extend(v_values)
                    params_range_merge.append(param_range_merge)
                    lengths.append(length)
                    row *= length
                    sizes.append(row)

            for index, param_name in enumerate(self.params_name):
                if param_name in lbfgs_params:
                    params_index['lbfgs'][index] = param_name
                elif param_name in sgd_params:
                    params_index['sgd'][index] = param_name
                elif param_name in adam_params:
                    params_index['adam'][index] = param_name

            column = len(lengths)
            origin_params_tmp = []
            dis_params_tmp = []
            count = np.zeros(column, dtype=int)
            for i in range(0, row):
                # print i
                op = {}
                dp = []
                for j in range(0, column):
                    if i % sizes[j] == 0:
                        count[j] = count[j] + 1
                        if count[j] == lengths[j] + 1:
                            count[j] = 1
                    op[self.params_name[j]] = params_range_merge[j][count[j] - 1]
                    dp.append(self.param_to_num(self.params_name[j], params_range_merge[j][count[j] - 1]))
                # origin_params_tmp.append(op)
                # dis_params_tmp.append(dp)
                op, dp, check = self.convert_origin_dis(op, origin_params_tmp, dp, params_index[op.get('solver', 'adam')])
                if check:
                    origin_params_tmp.append(op)
                    dis_params_tmp.append(dp)

            # self._origin_params = reduce(lambda x, y: x if y in x else x + [y], [[], ] + origin_params_tmp)
            self._origin_params = origin_params_tmp
            self.origin_converted = True
            # self._dis_params = reduce(lambda x, y: x if y in x else x + [y], [[], ] + dis_params_tmp)
            self._dis_params = dis_params_tmp
            self.dis_converted = True
        return self._origin_params

    @property
    def dis_params(self):
        if self.dis_converted:
            return self._dis_params
        else:
            self.origin_params
            return self._dis_params

    @staticmethod
    def convert_origin_dis(origin_params, origin_params_tmp, dis_params, origin_params_index):
        for index, param_name in origin_params_index.iteritems():
            origin_params[param_name] = default_value[param_name]
            dis_params[index] = 0
        return origin_params, dis_params, origin_params not in origin_params_tmp

    @staticmethod
    def check(origin_params_tmp, opt):
        if opt in origin_params_tmp:
            return False
        else:
            return True


class MLPRegressorEvaluate(NeuralNetworkEvaluate):
    def __init__(self, algor_params, other_params):
        super(MLPRegressorEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('MLPRegressorEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'activation':
            if param_value == 'identity':
                return 0
            elif param_value == 'logistic':
                return 1
            elif param_value == 'tanh':
                return 2
            else:
                return 3
        elif param_name == 'solver':
            if param_value == 'lbfgs':
                return 0
            elif param_value == 'sgd':
                return 1
            else:
                return 2
        elif param_name == 'learning_rate':
            if param_value == 'constant':
                return 0
            elif param_value == 'invscaling':
                return 1
            else:
                return 2
        elif param_name == 'verbose':
            return 0 if param_value else 1
        elif param_name == 'warm_start':
            return 0 if param_value else 1
        elif param_name == 'nesterovs_momentum':
            return 0 if param_value else 1
        elif param_name == 'early_stopping':
            return 0 if param_value else 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first_time, total_time):
        mse = {}
        count = 0
        for i in param_index:
            model_path = ""
            evaluate_result = {}
            try:
                param = self.origin_params[i]
                hidden_layer_sizes_list = []
                for j in range(param['n_layers'] - 3):
                    hidden_layer_sizes_list.append(param['hidden_layer_size'])
                n_layers = param.pop('n_layers')
                hidden_layer_size = param.pop('hidden_layer_size')
                param['hidden_layer_sizes'] = tuple(hidden_layer_sizes_list)
                clf = neural_network.MLPRegressor(**param)
                rf = clf.fit(self.x_train, self.y_train)
                y_pred = rf.predict(self.x_test)
                evaluate_result = regression_evaluate(self.y_test, y_pred, self.log)
                mse[i] = metrics.mean_squared_error(self.y_test, y_pred)
                if is_save:
                    model_path = sys.path[0] + os.sep + "model" + os.sep + str(uuid.uuid1())
                    joblib.dump(rf, model_path)

            except Exception, e:
                result = eva_result(False, e.message, 'sklearn', 'MLPRegressor',
                                    param, evaluate_result, 'regression', model_path, first_time + count, total_time)
            else:
                result = eva_result(True, '', 'sklearn', 'MLPRegressor',
                                    param, evaluate_result, 'regression', model_path, first_time + count, total_time)

            # print json.dumps(result).decode("unicode-escape")
            print json.dumps(result)
            del param['hidden_layer_sizes']
            param['n_layers'] = n_layers
            param['hidden_layer_size'] = hidden_layer_size
            count += 1

        return mse


class MLPClassifierEvaluate(NeuralNetworkEvaluate):
    def __init__(self, algor_params, other_params):
        super(MLPClassifierEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('MLPClassifierEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'activation':
            if param_value == 'identity':
                return 0
            elif param_value == 'logistic':
                return 1
            elif param_value == 'tanh':
                return 2
            else:
                return 3
        elif param_name == 'solver':
            if param_value == 'lbfgs':
                return 0
            elif param_value == 'sgd':
                return 1
            else:
                return 2
        elif param_name == 'learning_rate':
            if param_value == 'constant':
                return 0
            elif param_value == 'invscaling':
                return 1
            else:
                return 2
        elif param_name == 'verbose':
            return 0 if param_value else 1
        elif param_name == 'warm_start':
            return 0 if param_value else 1
        elif param_name == 'nesterovs_momentum':
            return 0 if param_value else 1
        elif param_name == 'early_stopping':
            return 0 if param_value else 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first_time, total_time):
        f1 = {}
        count = 0
        for i in param_index:
            model_path = ""
            evaluate_result = {}
            try:
                param = self.origin_params[i]
                hidden_layer_sizes_list = []
                for j in range(param['n_layers'] - 3):
                    hidden_layer_sizes_list.append(param['hidden_layer_size'])
                n_layers = param.pop('n_layers')
                hidden_layer_size = param.pop('hidden_layer_size')
                param['hidden_layer_sizes'] = tuple(hidden_layer_sizes_list)
                clf = neural_network.MLPRegressor(**param)
                # rf = clf.fit(self.x_train, self.y_train, eval_set=[(self.x_test, self.y_test)],
                #               eval_metric='mlogloss', verbose=True)
                rf = clf.fit(self.x_train, self.y_train)
                y_pred = rf.predict(self.x_test)
                evaluate_result = classifier_evaluate(self.y_test, y_pred, self.log)
                f1[i] = metrics.f1_score(self.y_test, y_pred, average='weighted')
                if is_save:
                    model_path = sys.path[0] + os.sep + str(uuid.uuid1())
                    joblib.dump(rf, model_path)

            except Exception, e:
                result = eva_result(False, e.message, 'sklearn', 'MLPRegressor', param,
                                    evaluate_result, 'classification', model_path, first_time + count, total_time, self.class_name)
            else:
                result = eva_result(True, '', 'sklearn', 'MLPRegressor', param,
                                    evaluate_result, 'classification', model_path, first_time + count, total_time, self.class_name)

            # print json.dumps(result).decode("unicode-escape")
            print json.dumps(result)
            count += 1

        return f1
