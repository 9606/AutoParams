# -*- coding: UTF-8 -*-
import json
import os
import sys
import uuid
import logging

import numpy as np
import pandas as pd
from sklearn import metrics
from sklearn import cluster
from sklearn.preprocessing import LabelEncoder
from sklearn.externals import joblib
from sklearn.model_selection import train_test_split
from sklearn import datasets as ds

from util import MultiColumnLabelEncoder
from util import get_logger
from util import eva_result


class ClusteringEvaluate(object):
    def __init__(self, algor_params, other_params):
        self.file_path = other_params["filePath"]
        self.data_format = other_params["fileType"]
        self.is_labeled = other_params["cluLabel"] is True
        if self.data_format == 'csv':
            self.origin_data = pd.read_csv(self.file_path, sep=',', encoding='utf-8')
            self.data = self.origin_data
            self.na_symbol = other_params["naSymbol"]
            for i in self.na_symbol:
                self.data = self.data.replace(i, np.nan).dropna()
            self.rowNum = len(self.data)
            self.class_name = []

            if self.is_labeled:
                self.featNum = len(self.data.columns) - 1
                x = MultiColumnLabelEncoder().fit_transform(self.data.iloc[:, 0:self.data.shape[1] - 1]).values
                y_origin = self.data.values[:, -1]
                y_fit = LabelEncoder().fit(y_origin)
                self.class_name = y_fit.classes_.tolist()
                y = y_fit.transform(y_origin)
                # self.data.values[:, 0:-1]
                self.x_train, self.x_test, self.y_train, self.y_test = train_test_split(
                    x, y, test_size=0.25, random_state=42)
            else:
                self.featNum = len(self.data.columns)
                self.x_train = MultiColumnLabelEncoder().fit_transform(self.data.iloc[:, 0:self.data.shape[1]]).values
                # self.x_train, self.x_test = train_test_split(
                #     x, test_size=0.25, random_state=42)
        else:
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
        if method_name is cluster.KMeans:
            return 'KMeans'
        else:
            return 'UnknownEvaluateMethod'

    def evaluate(self, param_index, is_save, evaluate_method, first_time, total_time):
        chs = {}
        count = 0
        evaluate_method_name = self.get_evaluate_method_name(evaluate_method)
        for i in param_index:
            evaluate_result = {}
            model_path = ""
            try:
                param = self.origin_params[i]
                kmeans = evaluate_method(**param)

                if self.is_labeled:
                    kmeans_model = kmeans.fit(self.x_train, self.y_train)
                    y_pred = kmeans_model.predict(self.x_test)
                    evaluate_result = clustering_evaluate(self.x_test, self.y_test, y_pred, True, self.x_train, None, self.log)
                else:
                    kmeans_model = kmeans.fit(self.x_train)
                    labels = kmeans_model.predict(self.x_train)
                    evaluate_result = clustering_evaluate(None, None, None, False, self.x_train, labels, self.log)

                chs[i] = evaluate_result['calinski_harabaz_score']
                if is_save:
                    model_path = sys.path[0] + os.sep + "model" + os.sep + str(uuid.uuid1())
                    joblib.dump(kmeans_model, model_path)

            except Exception, e:
                result = eva_result(False, e.message, 'sklearn', evaluate_method_name,
                                    param, evaluate_result, 'clustering', model_path, first_time + count, total_time, self.class_name)
            else:
                result = eva_result(True, '', 'sklearn', evaluate_method_name,
                                    param, evaluate_result, 'clustering', model_path, first_time + count, total_time, self.class_name)

            # print json.dumps(result).decode("unicode-escape")
            print json.dumps(result)
            count += 1

        return chs


class KMeansEvaluate(ClusteringEvaluate):

    def __init__(self, algor_params, other_params):
        super(KMeansEvaluate, self).__init__(algor_params, other_params)
        self.log = get_logger('KMeansEvaluate')

    @staticmethod
    def param_to_num(param_name, param_value):
        if param_name == 'init':
            return 0 if param_value == 'k-means++' else 1
        elif param_name == 'algorithm':
            if param_value == 'auto':
                return 0
            elif param_value == 'full':
                return 1
            else:
                return 2
        elif param_name == 'random_state':  # 暂时无法处理
            return 1
        elif param_name == 'precompute_distances':  # 暂时无法处理
            return 1
        elif param_name == 'copy_x':  # 暂时无法处理
            return 1
        else:
            return param_value

    def evaluate(self, param_index, is_save, first, total):
        return super(KMeansEvaluate, self).evaluate(
            param_index, is_save, cluster.KMeans, first, total)


def clustering_evaluate(test_data, test_true, test_pred, is_labeled, train_data, train_pred, log):
    evaluation = {}
    if is_labeled:
        try:
            evaluation['adjusted_rand_score'] = metrics.adjusted_rand_score(test_true, test_pred)
        except Exception as e:
            log.error('Unable to obtain evaluation : adjusted_rand_score.', exc_info=e)
            evaluation['adjusted_rand_score'] = None
        try:
            evaluation['adjusted_mutual_info_score'] = metrics.adjusted_mutual_info_score(test_true, test_pred)
        except Exception as e:
            log.error('Unable to obtain evaluation : adjusted_mutual_info_score.', exc_info=e)
            evaluation['adjusted_mutual_info_score'] = None
        try:
            evaluation['homogeneity_completeness_v_measure'] = list(metrics.homogeneity_completeness_v_measure(test_true, test_pred))
        except Exception as e:
            log.error('Unable to obtain evaluation : homogeneity_completeness_v_measure.', exc_info=e)
            evaluation['homogeneity_completeness_v_measure'] = None
        try:
            evaluation['fowlkes_mallows_score'] = metrics.fowlkes_mallows_score(test_true, test_pred)
        except Exception as e:
            log.error('Unable to obtain evaluation : fowlkes_mallows_score.', exc_info=e)
            evaluation['fowlkes_mallows_score'] = None
        try:
            evaluation['calinski_harabaz_score'] = metrics.calinski_harabaz_score(test_data, test_true)
        except Exception as e:
            log.error('Unable to obtain evaluation : calinski_harabaz_score.', exc_info=e)
        try:
            evaluation['silhouette_score'] = metrics.silhouette_score(test_data, test_true, metric='euclidean')
        except Exception as e:
            log.error('Unable to obtain evaluation : silhouette_score.', exc_info=e)
            evaluation['silhouette_score'] = None
    else:
        evaluation['adjusted_rand_score'] = None
        evaluation['adjusted_mutual_info_score'] = None
        evaluation['homogeneity_completeness_v_measure'] = None
        evaluation['fowlkes_mallows_score'] = None
        # don't need label
        try:
            evaluation['calinski_harabaz_score'] = metrics.calinski_harabaz_score(train_data, train_pred)
        except Exception as e:
            log.error('Unable to obtain evaluation : calinski_harabaz_score.', exc_info=e)
            evaluation['calinski_harabaz_score'] = None
        try:
            evaluation['silhouette_score'] = metrics.silhouette_score(train_data, train_pred, metric='euclidean')
        except Exception as e:
            log.error('Unable to obtain evaluation : silhouette_score.', exc_info=e)
            evaluation['silhouette_score'] = None
    return evaluation

