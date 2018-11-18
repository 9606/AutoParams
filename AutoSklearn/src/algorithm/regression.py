# -*- coding: UTF-8 -*-

import json
import math
import os
import sys
import uuid
import numpy as np
import pandas as pd
import logging

from sklearn import datasets as ds
from sklearn import metrics
from sklearn import linear_model
from sklearn import ensemble
from sklearn import svm
from sklearn.externals import joblib
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import LabelEncoder

from util import MultiColumnLabelEncoder
from util import get_logger
from util import eva_result


class RegressionEvaluate(object):
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
            # self.class_name = y_fit.classes_.tolist()
            y = y_fit.transform(y_origin)
        else:
            # type(x): scipy.sparse.csr.csr_matrix ; type(y):numpy.ndarray
            x, y = ds.load_svmlight_file(self.file_path)
            # self.class_name = list(set(y))
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
            params_range_merge = []  # merge many types of per param into one list
            for k, v in self.params_range.iteritems():
                length = 0
                param_range_merge = []  # merge many types of k param into one list
                for v_values in v:
                    if v_values is not None:
                        length += len(v_values) if len(v_values) != 0 else 0
                    else:
                        length += 1
                if length != 0:
                    self.params_name.append(k)
                    for v_values in v:
                        if v_values is not None:
                            param_range_merge.extend(v_values)
                        else:
                            param_range_merge.append(None)
                    params_range_merge.append(param_range_merge)
                    lengths.append(length)
                    row *= length
                    sizes.append(row)

            column = len(lengths)
            origin_params_tmp = []
            dis_params_tmp = []
            count = np.zeros(column, dtype=int)
            invalid = 0
            for i in range(0, row):
                origin_params_tmp.append({})
                dis_params_tmp.append([])
                for j in range(0, column):
                    if i % sizes[j] == 0:
                        count[j] = count[j] + 1
                        if count[j] == lengths[j] + 1:
                            count[j] = 1
                    origin_params_tmp[i - invalid][self.params_name[j]] = params_range_merge[j][count[j] - 1]
                    dis_params_tmp[i - invalid].append(
                        self.param_to_num(self.params_name[j], params_range_merge[j][count[j] - 1]))
                if not self.check(origin_params_tmp[i - invalid]):
                    del origin_params_tmp[i - invalid]
                    del dis_params_tmp[i - invalid]
                    invalid += 1

            self._origin_params = origin_params_tmp
            self.origin_converted = True
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
    def check(params_value):
        return True

    @staticmethod
    def get_evaluate_method_name(method_name):
        if method_name is linear_model.LinearRegression:
            return 'LinearRegression'
        elif method_name is linear_model.Ridge:
            return 'Ridge'
        elif method_name is linear_model.RidgeCV:
            return 'RidgeCV'
        elif method_name is ensemble.RandomForestRegressor:
            return 'RandomForestRegressor'
        elif method_name is ensemble.GradientBoostingRegressor:
            return 'GradientBoostingRegressor'
        elif method_name is linear_model.Lasso:
            return 'Lasso'
        elif method_name is linear_model.LassoCV:
            return 'LassoCV'
        elif method_name is linear_model.LassoLars:
            return 'LassoLars'
        elif method_name is linear_model.LassoLarsCV:
            return 'LassoLarsCV'
        elif method_name is svm.SVR:
            return 'SVR'
        elif method_name is svm.LinearSVR:
            return 'LinearSVR'
        else:
            return 'UnknownEvaluateMethod'

    def evaluate(self, param_index, is_save, evaluate_method, first_time, total_time):
        mse = {}
        count = 0
        evaluate_method_name = self.get_evaluate_method_name(evaluate_method)
        model_path = ""
        for i in param_index:
            evaluate_result = {}
            try:
                param = self.origin_params[i]
                # print param
                lr = evaluate_method(**param)

                lr = lr.fit(self.x_train, self.y_train)
                y_pred = lr.predict(self.x_test)
                evaluate_result = regression_evaluate(self.y_test, y_pred, self.log)
                mse[i] = 0 - metrics.mean_squared_error(self.y_test, y_pred)
                if is_save:
                    model_path = sys.path[0] + os.sep + "model" + os.sep + str(uuid.uuid1())
                    joblib.dump(lr, model_path)

            except Exception, e:
                result = eva_result(False, e.message, 'sklearn', evaluate_method_name,
                                    param, evaluate_result, 'regression', model_path, first_time + count, total_time)
            else:
                result = eva_result(True, '', 'sklearn', evaluate_method_name,
                                    param, evaluate_result, 'regression', model_path, first_time + count, total_time)

            # print json.dumps(result).decode("unicode-escape")
            print json.dumps(result)
            count += 1

        return mse


class LinearRegressionEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(LinearRegressionEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('LinearRegressionEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        return 0 if param_value else 1

    def evaluate(self, param_index, is_save, first, total):
        return super(LinearRegressionEvaluate, self).evaluate(
            param_index, is_save, linear_model.LinearRegression, first, total)


class RidgeEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(RidgeEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('RidgeEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'normalize':
            return 0 if param_value else 1
        elif param_name == 'solver':
            if param_value == 'auto':
                return 0
            elif param_value == 'svd':
                return 1
            elif param_value == 'cholesky':
                return 2
            elif param_value == 'lsqr':
                return 3
            elif param_value == 'sparse_cg':
                return 4
            else:
                return 5
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def check(self, params_value):
        if self.data_format == 'libvm' and params_value.get('fit_intercept', default=None) and params_value.get(
                'gcv_mode', default=None) != 'svd':
            return False
        else:
            return True

    def evaluate(self, param_index, is_save, first, total):
        return super(RidgeEvaluate, self).evaluate(
            param_index, is_save, linear_model.Ridge, first, total)


class RidgeCVEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(RidgeCVEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('RidgeCVEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'normalize':
            return 0 if param_value else 1
        elif param_name == 'gcv_mode':
            if param_value == 'auto':
                return 0
            elif param_value == 'svd':
                return 1
            else:
                return 2
        elif param_name == 'scoring':
            if param_value == 'mean_squared_error':
                return 0
            elif param_value == 'r2':
                return 1
            else:
                return 2
        elif param_name == 'cv':
            if param_value is None:
                return 0
            return param_value
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def check(self, params_value):
        if self.data_format == 'libvm' and params_value.get('gcv_mode', None) == 'svd':
            return False
        elif params_value.get('cv', None) is not None and params_value.get('store_cv_values', False):
            return False
        else:
            return True

    def evaluate(self, param_index, is_save, first, total):
        # if self.origin_params[param_index[0]].get('alphas', default=None) is not None:
        for pi in param_index:
            self.origin_params[pi]['alphas'] = get_alphas_range()
        return super(RidgeCVEvaluate, self).evaluate(
            param_index, is_save, linear_model.RidgeCV, first, total)


class LassoEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(LassoEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('LassoEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'normalize':
            return 0 if param_value else 1
        elif param_name == 'warm_start':
            return 0 if param_value else 1
        elif param_name == 'positive':
            return 0 if param_value else 1
        elif param_name == 'selection':
            return 0 if param_value == 'cyclic' else 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(LassoEvaluate, self).evaluate(
            param_index, is_save, linear_model.Lasso, first, total)


class LassoCVEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(LassoCVEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('LassoCVEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'normalize':
            return 0 if param_value else 1
        elif param_name == 'warm_start':
            return 0 if param_value else 1
        elif param_name == 'cv':
            if param_value is None:
                return 3
            return param_value
        elif param_name == 'positive':
            return 0 if param_value else 1
        elif param_name == 'selection':
            return 0 if param_value == 'cyclic' else 1
        elif param_name == 'precompute':  # 暂时无法处理
            return 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        for pi in param_index:
            self.origin_params[pi]['alphas'] = get_alphas_range()
        return super(LassoCVEvaluate, self).evaluate(
            param_index, is_save, linear_model.LassoCV, first, total)


class LassoLarsCVEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(LassoLarsCVEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('LassoLarsCVEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'normalize':
            return 0 if param_value else 1
        elif param_name == 'warm_start':
            return 0 if param_value else 1
        elif param_name == 'cv':
            if param_value is None:
                return 3
            return param_value
        elif param_name == 'positive':
            return 0 if param_value else 1
        elif param_name == 'selection':
            return 0 if param_value == 'cyclic' else 1
        elif param_name == 'precompute':  # 暂时无法处理
            return 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(LassoLarsCVEvaluate, self).evaluate(
            param_index, is_save, linear_model.LassoLarsCV, first, total)


class GradientBoostingRegressorEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(GradientBoostingRegressorEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('GradientBoostingRegressorEvaluate')

    def param_to_num(self, param_name, param_value):
        if param_name == 'criterion':
            return 0 if param_value == 'gini' else 1
        elif param_name == 'splitter':
            return 0 if param_value == 'best' else 1
        elif param_name == 'min_samples_split' or param_name == 'min_samples_leaf':
            return param_value if isinstance(param_value, int) else self.rowNum * param_value
        elif param_name == 'max_features':
            if isinstance(param_value, int):
                return param_value
            elif isinstance(param_value, float):
                return int(param_value * self.featNum)
            elif isinstance(param_value, str) | isinstance(param_value, unicode):
                return int(math.sqrt(self.featNum)) if param_value == "auto" or param_value == "sqrt" else int(
                    math.log(self.featNum, 2))
            elif param_value is None:
                return self.featNum
            return None
        elif param_name == 'bootstrap':
            return 0 if param_value else 1
        elif param_name == 'loss':
            if param_value == 'ls':
                return 0
            elif param_value == 'lad':
                return 1
            elif param_value == 'huber':
                return 2
            else:
                return 3
        elif param_name == 'warm_start':
            return 0 if param_value else 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        elif param_name == 'class_weight':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(GradientBoostingRegressorEvaluate, self).evaluate(
            param_index, is_save, ensemble.GradientBoostingRegressor, first, total)


class RandomForestRegressorEvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(RandomForestRegressorEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('RandomForestRegressorEvaluate')

    def param_to_num(self, param_name, param_value):
        if param_name == 'criterion':
            return 0 if param_value == 'gini' else 1
        elif param_name == 'splitter':
            return 0 if param_value == 'best' else 1
        elif param_name == 'min_samples_split' or param_name == 'min_samples_leaf':
            return param_value if isinstance(param_value, int) else self.rowNum * param_value
        elif param_name == 'max_features':
            if isinstance(param_value, int):
                return param_value
            elif isinstance(param_value, float):
                return int(param_value * self.featNum)
            elif isinstance(param_value, str) | isinstance(param_value, unicode):
                return int(math.sqrt(self.featNum)) if param_value == "auto" or param_value == "sqrt" else int(
                    math.log(self.featNum, 2))
            elif param_value is None:
                return self.featNum
            return None
        elif param_name == 'bootstrap':
            return 0 if param_value else 1
        elif param_name == 'oob_score':
            return 0 if param_value else 1
        elif param_name == 'warm_start':
            return 0 if param_value else 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        elif param_name == 'class_weight':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(RandomForestRegressorEvaluate, self).evaluate(
            param_index, is_save, ensemble.RandomForestRegressor, first, total)


class SVREvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(SVREvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('SVREvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'kernel':
            if param_value == 'linear':
                return 0
            elif param_value == 'poly':
                return 1
            elif param_value == 'rbf':
                return 2
            elif param_value == 'sigmoid':
                return 3
            elif param_value == 'precomputed':
                return 4
            else:
                return 5
        elif param_name == 'shrinking':
            return 0 if param_value else 1
        elif param_name == 'verbose':
            return 0 if param_value else 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(SVREvaluate, self).evaluate(
            param_index, is_save, svm.SVR, first, total)


class LinearSVREvaluate(RegressionEvaluate):
    def __init__(self, algor_params, other_params):
        super(LinearSVREvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('LinearSVREvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'loss':
            return 0 if param_value == 'epsilon_insensitive' else 1
        elif param_name == 'dual':
            return 0 if param_value else 1
        elif param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(LinearSVREvaluate, self).evaluate(
            param_index, is_save, svm.LinearSVR, first, total)


def regression_evaluate(true, pred, log):
    evaluation = {}

    try:
        evaluation['mean_squared_error'] = metrics.mean_squared_error(true, pred)
    except Exception as e:
        log.error('Unable to obtain evaluation : mean_squared_error.', exc_info=e)
        evaluation['mean_squared_error'] = np.nan
    try:
        evaluation['mean_absolute_error'] = metrics.mean_absolute_error(true, pred)
    except Exception as e:
        log.error('Unable to obtain evaluation : mean_absolute_error.', exc_info=e)
        evaluation['mean_absolute_error'] = np.nan
    try:
        evaluation['explained_variance_score'] = metrics.explained_variance_score(true, pred)
    except Exception as e:
        log.error('Unable to obtain evaluation : explained_variance_score.', exc_info=e)
        evaluation['explained_variance_score'] = np.nan
    try:
        evaluation['median_absolute_error'] = metrics.median_absolute_error(true, pred)
    except Exception as e:
        log.error('Unable to obtain evaluation : median_absolute_error.', exc_info=e)
        evaluation['median_absolute_error'] = np.nan
    try:
        evaluation['r2_score'] = metrics.r2_score(true, pred)
    except Exception as e:
        log.error('Unable to obtain evaluation : r2_score.', exc_info=e)
        evaluation['r2_score'] = np.nan

    return evaluation


def get_alphas_range():
    alphas_range = np.array([])
    alphas_range = np.append(alphas_range, np.linspace(1e-10, 1e-9, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-9, 1e-8, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-8, 1e-7, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-7, 1e-6, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-6, 1e-5, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-5, 1e-4, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-4, 1e-3, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-3, 1e-2, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-2, 1e-1, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-1, 1e-0, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e-0, 1e-1, 10, False))
    alphas_range = np.append(alphas_range, np.linspace(1e1, 1e2, 10, False))
    return list(alphas_range)
