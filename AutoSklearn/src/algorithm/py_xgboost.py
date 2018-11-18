# -*- coding: UTF-8 -*-

import json
import math
import os
import sys
import uuid
import numpy as np
import pandas as pd
import xgboost as xgb
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

warnings.filterwarnings("ignore")

gbtree_params = ['learning_rate', 'min_split_loss', 'max_depth', 'min_child_weight', 'max_delta_step',
                 'subsample', 'colsample_bytree', 'colsample_bylevel', 'reg_lambda', 'reg_alpha',
                 'tree_method', 'sketch_eps', 'scale_pos_weight', 'updater', 'refresh_leaf',
                 'process_type', 'grow_policy', 'max_leaves', 'max_bin', 'predictor']
dart_params = ['sample_type', 'normalize_type', 'rate_drop', 'one_drop', 'skip_drop']
gblinear_params = ['reg_lambda', 'reg_alpha', 'reg_lambda_bias']
default_value = {
    'n_estimators': 100, 'silent': True, 'objective': 'binary:logistic', 'nthread': -1, 'base_score': 0.5, 'seed': 0,
    'missing': None,
    'learning_rate': 0.1, 'min_split_loss': 0, 'max_depth': 3, 'min_child_weight': 1, 'max_delta_step': 0,
    'subsample': 1, 'colsample_bytree': 1, 'colsample_bylevel': 1, 'lambda': 1, 'alpha': 0,
    'tree_method': 'auto', 'sketch_eps': 0.03, 'scale_pos_weight': 1, 'updater': 'grow_colmaker,prune',
    'refresh_leaf': 1,
    'process_type': 'default', 'grow_policy': 'depthwise', 'max_leaves': 0, 'max_bin': 256,
    'predictor': 'cpu_predictor',
    'sample_type': 'uniform', 'normalize_type': 'tree', 'rate_drop': 0.0, 'one_drop': 0, 'skip_drop': 0.0,
    'reg_lambda': 0, 'reg_alpha': 0, 'reg_lambda_bias': 0
}
params_index = {'gbtree': {}, 'gblinear': {}, 'dart': {}}


class XGBEvaluate(object):
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
            params_range_merge = []  # merge four types of per param into one list
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

            # 注释部分是参数去重的代码，查看 xgboost 官网的 api 可知，其实很多参数组合发挥的作用是相同的
            # 代码目前没问题，但是由于运行的有点慢，所以就先不去重了
            # https://github.com/dmlc/xgboost/blob/master/doc/parameter.md
            # for index, param_name in enumerate(self.params_name):
            #     if param_name in gbtree_params:
            #         params_index['gblinear'][index] = param_name
            #     elif param_name in dart_params:
            #         params_index['gblinear'][index] = param_name
            #         params_index['gbtree'][index] = param_name
            #     elif param_name in gblinear_params:
            #         params_index['gbtree'][index] = param_name
            #         params_index['dart'][index] = param_name

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
                origin_params_tmp.append(op)
                dis_params_tmp.append(dp)
                # op, dp, check = self.convert_origin_dis(op, origin_params_tmp, dp, params_index[op.get('booster', 'gbtree')])
                # if check:
                #     origin_params_tmp.append(op)
                #     dis_params_tmp.append(dp)

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


class XGBClassifierEvaluate(XGBEvaluate):
    def __init__(self, algor_params, other_params):
        super(XGBClassifierEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('XGBClassifierEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        # num_pbuffer 和 num_feature 不用调
        if param_name == 'booster':
            return 0 if param_value == 'gbtree' else 1 if param_value == 'gblinear' else 2
        elif param_name == 'sample_type':
            return 0 if param_value == 'uniform' else 1
        elif param_name == 'normalize_type':
            return 0 if param_value == 'tree' else 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first_time, total_time):
        # 由于目前还没有在参数中指定评分标准，而且分类和回归返回的指标并不一样，
        # 所以 xgboost 的 evaluate 方法没有放在父类中
        f1 = {}
        count = 0
        for i in param_index:
            evaluate_result = {}
            model_path = ""
            try:
                param = self.origin_params[i]
                param['silent'] = True
                clf = xgb.XGBClassifier(**param)
                # rf = clf.fit(self.x_train, self.y_train, eval_set=[(self.x_test, self.y_test)],
                #               eval_metric='mlogloss', verbose=True)
                rf = clf.fit(self.x_train, self.y_train)
                y_pred = rf.predict(self.x_test)
                evaluate_result = classifier_evaluate(self.y_test, y_pred, self.class_name)
                f1[i] = metrics.f1_score(self.y_test, y_pred, average='weighted')
                if is_save:
                    model_path = sys.path[0] + os.sep + str(uuid.uuid1())
                    joblib.dump(rf, model_path)

            except Exception, e:
                result = eva_result(False, e.message, 'xgboost', 'XGBClassifier', param,
                                    evaluate_result, 'classification', model_path, first_time + count, total_time, self.class_name)
            else:
                result = eva_result(True, '', 'xgboost', 'XGBClassifier', param,
                                    evaluate_result, 'classification', model_path, first_time + count, total_time, self.class_name)

            # print json.dumps(result).decode("unicode-escape")
            print json.dumps(result)
            count += 1

        return f1


class XGBRegressorEvaluate(XGBEvaluate):
    def __init__(self, algor_params, other_params):
        super(XGBRegressorEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('XGBClassifierEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        # num_pbuffer 和 num_feature 不用调
        if param_name == 'booster':
            return 0 if param_value == 'gbtree' else 1 if param_value == 'gblinear' else 2
        elif param_name == 'sample_type':
            return 0 if param_value == 'uniform' else 1
        elif param_name == 'normalize_type':
            return 0 if param_value == 'tree' else 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first_time, total_time):
        mse = {}
        count = 0
        for i in param_index:
            model_path = ""
            try:
                param = self.origin_params[i]
                param['silent'] = True
                clf = xgb.XGBRegressor(**param)
                rf = clf.fit(self.x_train, self.y_train)
                y_pred = rf.predict(self.x_test)
                evaluate_result = regression_evaluate(self.y_test, y_pred, self.log)
                mse[i] = 0 - metrics.mean_squared_error(self.y_test, y_pred)
                if is_save:
                    model_path = sys.path[0] + os.sep + "model" + os.sep + str(uuid.uuid1())
                    joblib.dump(rf, model_path)

            except Exception, e:
                result = eva_result(False, e.message, 'xgboost', 'XGBRegressor',
                                    param, evaluate_result, 'regression', model_path, first_time + count, total_time)
            else:
                result = eva_result(True, '', 'xgboost', 'XGBRegressor',
                                    param, evaluate_result, 'regression', model_path, first_time + count, total_time)

            # print json.dumps(result).decode("unicode-escape")
            print json.dumps(result)
            count += 1

        return mse
