# -*- coding: UTF-8 -*-

import json
import math
import os
import sys
import uuid
import numpy as np
import pandas as pd

from sklearn import datasets as ds
from sklearn import metrics
from sklearn import tree
from sklearn import ensemble
from sklearn import linear_model
from sklearn.externals import joblib
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import LabelEncoder

from util import MultiColumnLabelEncoder
from util import get_logger
from util import eva_result
import warnings
warnings.filterwarnings("ignore")


class ClassifierEvaluate(object):
    def __init__(self, algor_params, other_params):
        self.file_path = other_params["filePath"]
        self.data_format = other_params["fileType"]
        if self.data_format == 'csv':
            self.origin_data = pd.read_csv(self.file_path, sep=',', encoding='utf-8')
            # 删除数据的中带有 na_symbol 的行和列
            self.data = self.origin_data
            self.na_symbol = other_params["naSymbol"]
            for i in self.na_symbol:
                self.data = self.data.replace(i, np.nan).dropna()
            self.rowNum = len(self.data)  # 数据的行数
            self.featNum = len(self.data.columns) - 1  # 数据的特征数

            # self.data.values[:, 0:-1]
            # 将数据中非数值类型的列转成数值类型
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
    def get_evaluate_method_name(evaluate_method):
        if evaluate_method is tree.DecisionTreeClassifier:
            return 'DecisionTreeClassifier'
        elif evaluate_method is ensemble.RandomForestClassifier:
            return 'RandomForestClassifier'
        elif evaluate_method is linear_model.LogisticRegression:
            return 'LogisticRegression'
        elif evaluate_method is linear_model.LogisticRegressionCV:
            return 'LogisticRegressionCV'
        else:
            return 'UnknownEvaluateMethod'

    def evaluate(self, param_index, is_save, evaluate_method, first_time, total_time):
        f1 = {}
        accuracy = {}
        count = 0
        for i in param_index:
            evaluate_result = {}
            model_path = ""
            try:
                param = self.origin_params[i]
                clf = evaluate_method(**param)

                clf = clf.fit(self.x_train, self.y_train)
                y_pred = clf.predict(self.x_test)
                evaluate_result = classifier_evaluate(self.y_test, y_pred, self.class_name)
                # f1[i] = metrics.f1_score(self.y_test, y_pred, average='weighted') * 1000
                accuracy[i] = metrics.accuracy_score(self.y_test, y_pred, normalize=True)
                if is_save:
                    model_path = sys.path[0] + os.sep + "model" + os.sep + str(uuid.uuid1())
                    joblib.dump(clf, model_path)

            except Exception, e:
                result = eva_result(False, e.message, 'sklearn', self.get_evaluate_method_name(evaluate_method), param,
                                    evaluate_result, 'classification', model_path, first_time + count, total_time, self.class_name)
            else:
                result = eva_result(True, '', 'sklearn', self.get_evaluate_method_name(evaluate_method), param,
                                    evaluate_result, 'classification', model_path, first_time + count, total_time, self.class_name)
            # print json.dumps(result).decode("unicode-escape")
            print json.dumps(result)
            count += 1

        return accuracy


class DecisionTreeClassifierEvaluate(ClassifierEvaluate):
    def __init__(self, algor_params, other_params):
        super(DecisionTreeClassifierEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('DecisionTreeClassifierEvaluate')

    def get_param_grid(self):
        pass

    def grid_evaluate(self):
        # data = pd.read_csv("data/iris.csv", sep=',', encoding='utf-8')
        # pipe = Pipeline([('mce', MultiColumnLabelEncoder())])
        # pipe.fit_transform(data)
        pass

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
                    math.log2(self.featNum))
            return None
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        elif param_name == 'class_weight':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(DecisionTreeClassifierEvaluate, self).evaluate(
            param_index, is_save, tree.DecisionTreeClassifier, first, total)


class RandomForestClassifierEvaluate(ClassifierEvaluate):
    def __init__(self, algor_params, other_params):
        super(RandomForestClassifierEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('RandomForestClassifierEvaluate')

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
        return super(RandomForestClassifierEvaluate, self).evaluate(
            param_index, is_save, ensemble.RandomForestClassifier, first, total)


class LogisticRegressionEvaluate(ClassifierEvaluate):
    def __init__(self, algor_params, other_params):
        super(LogisticRegressionEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('LogisticRegressionEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'dual':
            return 0 if param_value else 1
        elif param_name == 'penalty':
            return 0 if param_value == 'l1' else 1
        elif param_name == 'solver':
            if param_value == 'newton-cg':
                return 0
            elif param_value == 'libfgs':
                return 1
            elif param_value == 'newton-cg':
                return 2
            elif param_value == 'newton-cg':
                return 3
            else:
                return 4
        elif param_name == 'multi_class':
            # binary problem: ovr, else: multinomial
            # 但是写参数时无法判断是哪种，因此统一是 multinomial，永远不会出现 ovr
            return 0
        elif param_name == 'class_weight':  # 暂时无法处理
            return 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def check(self, params_value):

        penalty_value = params_value.get('penalty', None)
        solver_value = params_value.get('solver', None)
        multi_class_value = params_value.get('multi_class', None)
        # ovr 和 multinomial 在二元逻辑回归时并没有任何区别
        # 区别主要在多元逻辑回归上，ovr 分类效果相对略差，直接滤掉
        if len(self.class_name) > 2 and multi_class_value == 'ovr':
            return False
        elif solver_value == 'liblinear' and multi_class_value == 'multinomial':
            return False
        # elif penalty_value == 'l2' and (solver_value == 'liblinear' or solver_value == 'saga'):
        #     return False
        elif solver_value == 'saga':
            return False
        elif penalty_value == 'l1'and (solver_value == 'newton-cg' or solver_value == 'lbfgs' or solver_value == 'sag'):
            return False
        else:
            return True

    def evaluate(self, param_index, is_save, first, total):
        return super(LogisticRegressionEvaluate, self).evaluate(
            param_index, is_save, linear_model.LogisticRegression, first, total)


class LogisticRegressionCVEvaluate(ClassifierEvaluate):
    def __init__(self, algor_params, other_params):
        super(LogisticRegressionCVEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('LogisticRegressionCVEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'Cs':
            # 这个 param_value 其实会转化成 np.logspace(-4, 4, param_value)
            # 因此其实直接转换距离并不是特别合适
            return param_value
        elif param_name == 'fit_intercept':
            return 0 if param_value else 1
        elif param_name == 'dual':
            return 0 if param_value else 1
        elif param_name == 'penalty':
            return 0 if param_value == 'l1' else 1
        elif param_name == 'scoring':
            if param_value == 'accuracy':
                return 0
            elif param_value == 'recall':
                return 1
            elif param_value == 'f1':
                return 2
            elif param_value == 'precision':
                return 3
            else:
                return 4
        elif param_name == 'solver':
            if param_value == 'newton-cg':
                return 0
            elif param_value == 'libfgs':
                return 1
            elif param_value == 'newton-cg':
                return 2
            elif param_value == 'newton-cg':
                return 3
            else:
                return 4
        elif param_name == 'multi_class':
            # binary problem: ovr, else: multinomial
            # 但是写参数时无法判断是哪种，因此统一是 multinomial，永远不会出现 ovr
            return 0
        elif param_name == 'class_weight':  # 暂时无法处理
            return 1
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        else:
            return param_value

    def check(self, params_value):

        penalty_value = params_value.get('penalty', None)
        solver_value = params_value.get('solver', None)
        multi_class_value = params_value.get('multi_class', None)
        # ovr 和 multinomial 在二元逻辑回归时并没有任何区别
        # 区别主要在多元逻辑回归上，ovr 分类效果相对略差，直接滤掉
        if len(self.class_name) > 2 and multi_class_value == 'ovr':
            return False
        elif solver_value == 'liblinear' and multi_class_value == 'multinomial':
            return False
        # elif penalty_value == 'l2' and (solver_value == 'liblinear' or solver_value == 'saga'):
        #     return False
        elif solver_value == 'saga':
            return False
        elif penalty_value == 'l1'and (solver_value == 'newton-cg' or solver_value == 'lbfgs' or solver_value == 'sag'):
            return False
        else:
            return True

    def evaluate(self, param_index, is_save, first, total):
        return super(LogisticRegressionCVEvaluate, self).evaluate(
            param_index, is_save, linear_model.LogisticRegressionCV, first, total)


def classifier_evaluate(true, pred, class_name):
    evaluation = {}
    try:
        evaluation['accuracy_score'] = metrics.accuracy_score(true, pred, normalize=True)
    except ValueError:
        evaluation['accuracy_score'] = None
    try:
        evaluation['precision_score'] = metrics.precision_score(true, pred, average='weighted')
    except ValueError:
        evaluation['precision_score'] = None
    try:
        evaluation['recall_score'] = metrics.recall_score(true, pred, average='weighted')
    except ValueError:
        evaluation['recall_score'] = None
    try:
        evaluation['f1_score'] = metrics.f1_score(true, pred, average='weighted')
    except ValueError:
        evaluation['f1_score'] = None
    try:
        evaluation['roc_auc_score'] = metrics.roc_auc_score(true, pred, average="weighted")
    except ValueError:
        evaluation['roc_auc_score'] = None
    try:
        evaluation['classification_report'] = metrics.classification_report(true, pred)
    except ValueError:
        evaluation['classification_report'] = None
    try:
        # confusion_matrix_origin = metrics.confusion_matrix(true, pred)
        # confusion_matrix_tmp = {}
        # for i, row_matrix in enumerate(confusion_matrix_origin):
        #     row_matrix_tmp = []
        #     for ele in row_matrix:
        #         row_matrix_tmp.append(int(ele))
        #     confusion_matrix_tmp[class_name[i]] = row_matrix_tmp
        # evaluation['confusion_matrix'] = confusion_matrix_tmp
        evaluation['confusion_matrix'] = metrics.confusion_matrix(true, pred).tolist()
    except ValueError:
        evaluation['confusion_matrix'] = None
    return evaluation
