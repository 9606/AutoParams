# -*- coding: UTF-8 -*-

from sklearn.preprocessing import LabelEncoder
import numpy as np
import logging
import os
import sys
import warnings
warnings.filterwarnings("ignore")

def convert_params(dict_params):
    algorithm_params = {}
    for param in dict_params:
        param_name = param['paramName']
        param_value = []
        param_value.append(param['stringValues']) if param['isString'] else param_value.append([])
        param_value.append(
            list(np.linspace(param['intDownValue'], param['intUpValue'],
                             param['intNum'], dtype=int))) if param['isInt'] else param_value.append([])
        param_value.append(
            list(np.linspace(param['floatDownValue'], param['floatUpValue'],
                             param['floatNum'], dtype=float))) if param['isFloat'] else param_value.append([])
        param_value.append(param['boolValues']) if param['isBool'] else param_value.append([])
        if param['isNull']:
            param_value.append(None)
        algorithm_params[param_name] = param_value


    return algorithm_params


class MultiColumnLabelEncoder:
    def __init__(self, columns=None):
        self.columns = columns  # array of column names to encode

    def fit(self, X, y=None):
        return self

    def transform(self, X):
        """
        Transforms columns of X specified in self.columns using
        LabelEncoder(). If no columns specified, transforms all
        columns in X.
        """
        output = X.copy()
        if self.columns is not None:
            for col in self.columns:
                if output[col].dtype != np.int_ and output[col].dtype != np.float_:
                    output[col] = LabelEncoder().fit_transform(output[col])
        else:
            for col_name, col in output.iteritems():
                if output[col_name].dtype != np.int_ and output[col_name].dtype != np.float_:
                    output[col_name] = LabelEncoder().fit_transform(col)
        return output

    def fit_transform(self, X, y=None):
        return self.fit(X, y).transform(X)


def get_logger(file_name):
    if not os.path.exists('logs'):
        os.mkdir('logs')
    logger = logging.getLogger(file_name)
    logger.setLevel(logging.DEBUG)
    fh = logging.FileHandler('logs' + os.sep + file_name + '.log')
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    fh.setFormatter(formatter)
    logger.addHandler(fh)
    return logger


def cov_str(items):
    # 编码问题详见 http://python.jobbole.com/81244/
    reload(sys)
    sys.setdefaultencoding('utf-8')

    result = ''
    if isinstance(items, list):
        for ele in items:
            result += '[' if result == '' else ', '
            result += "'" + cov_str(ele) + "'"
        result += "]"
        return result
    elif isinstance(items, dict):
        for key in items:
            result += '{' if result == '' else ', '
            result += "'" + key + "' : " + str(cov_str(items[key]))
        result += "}"
        return result
    else:
        return items


# print cov_str({u"我还": {u"是一个": 0, u"孩子": 1}, u"我还是": [u"一个", u"孩子"]})


def eva_result(is_ok, exception, library, model, param, performance, algorithm_type, model_path, current_time, total_time, class_name=[]):
    result = {'isok': is_ok, 'exception': exception, 'library': library, 'model': model, 'param': param,
              'performance': performance, 'type': algorithm_type, 'modelPath': model_path,
              'progress': str(current_time) + '/' + str(total_time),
              'className': class_name}
    return result
