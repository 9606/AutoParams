import math
import numpy as np
from algorithm import *
import json
from operator import itemgetter
from sklearn import gaussian_process
from sklearn.gaussian_process.kernels import Matern
import warnings
warnings.filterwarnings("ignore")

gamma = 1.0
zeta = 1.0


def divide_matrix(origin_matrix, validation_count):
    row_count = origin_matrix.shape[0]
    random_index = np.ones_like(range(row_count), dtype=bool)
    random_index[np.random.choice(row_count, validation_count, replace=False)] = True
    x = origin_matrix[random_index]
    candidate = origin_matrix[~random_index]
    return [x, candidate]


def cov(x1, x2):
    if len(x1) != len(x2):
        return None
    sum_tmp = 0
    try:
        for i, ele in enumerate(x1):
            if type(ele) == list:
                sum_tmp += np.sqrt(np.sum(np.square(np.array(ele) - np.array(x2[i]))))
            else:
                sum_tmp += math.pow(ele - x2[i], 2)
    except Exception as e:
        print e.message
    return math.exp(- math.sqrt(sum_tmp) / 2.0 * gamma)


def pred_result(param_array, x_result, logger):
    pred = {}
    try:
        # [[1,2],[3,4]][0,1]:Error ; [[1,2],[3,4]][[0,1]]:Error
        # np.array([[1,2],[3,4]])[0,1]:2 ; np.array([[1,2],[3,4]])[0][1]:2
        # np.array([[1, 2], [3, 4]])[[0, 1]]:array([[1, 2],[3, 4]])

        x_index = x_result.keys()
        x_result_values = x_result.values()
        C = []
        for x in x_index:
            C.append(param_array[x])
        # C = param_array[x_index]

        # bool_index = np.ones_like(range(param_array.shape[0]), dtype=bool)
        # bool_index[x_index] = True
        # candidate = param_array[~bool_index]
        part2 = square_matrix(C)
        for i in range(0, len(param_array)):
            if i not in x_index:
                part1 = row_matrix(param_array[i], C)
                part12 = np.dot(part1, part2)
                mean = np.dot(part12, x_result_values)
                variance = 1 - np.dot(part12, np.transpose(part1))
                pred[i] = variance * 1.5 + mean
    except Exception as e:
        logger.error('', exc_info=e)
    return pred


def square_matrix(x):
    k_matrix = np.zeros((len(x), len(x)), dtype=float)

    for i in range(0, len(x)):
        for j in range(0, len(x)):
            if i == j:
                k_matrix[i][j] = cov(x[i], x[j]) + math.pow(zeta, 2) * 1
            else:
                k_matrix[i][j] = cov(x[i], x[j])
    # np.set_printoptions(threshold='nan')
    # print k_matrix
    # print np.linalg.det(k_matrix)
    return np.linalg.inv(k_matrix)


def row_matrix(x1, x2):
    # np.zeros((1, 3)):array([[ 0.,  0.,  0.]]) ; np.zeros((1, 3)):array([[ 0.],[ 0.],[ 0.]])
    # np.zeros(3):array([ 0.,  0.,  0.]) ; np.transpose([0.,  0.,  0.]):array([ 0.,  0.,  0.])
    # np.transpose([[ 0.,  0.,  0.]]):array([[ 0.],[ 0.],[ 0.]])
    row_result = np.zeros((1, len(x2)))
    for i in range(0, len(x2)):
        row_result[0][i] = cov(x1, x2[i])
    return row_result


def sklearn_gpr_pred_result(param_array, train_dic, logger):
    pred = {}
    train_index = train_dic.keys()
    train_Y=[]
    train_X=[]
    test_X=[]
    test_X_index=[]
    for i, ele in enumerate(param_array):
        if i in train_index:
            train_X.append(param_array[i])
            train_Y.append(train_dic[i])
        else:
            test_X.append(param_array[i])
            test_X_index.append(i)

    kernel = Matern(length_scale=1.0, length_scale_bounds=(1e-1, 10.0), nu=2.5)
    gpr = gaussian_process.GaussianProcessRegressor(kernel=kernel).fit(train_X, train_Y)
    # y_pred, cov_pred = gpr.predict(test_X, return_std=False, return_cov=True)
    y_pred, std_pred = gpr.predict(test_X, return_std=True, return_cov=False)

    for i, ele in enumerate(y_pred):
            pred[test_X_index[i]] = ele + std_pred[i] * 1.96
    return pred


def run(algorithm, loop=100, random_choice=5, count=1):
    dis_dic = algorithm.dis_params

    if isinstance(algorithm, ClassifierEvaluate) or isinstance(algorithm, XGBClassifierEvaluate):
        reverse_value = False
    elif isinstance(algorithm, RegressionEvaluate) or isinstance(algorithm, XGBRegressorEvaluate):
        # reverse_value = True
        reverse_value = False
    else:
        reverse_value = False

    if len(dis_dic) > random_choice + loop * count:
        random_index = np.random.choice(len(dis_dic), random_choice, replace=False)
        total_time = random_choice + loop * count + 1
        scores = algorithm.evaluate(random_index, False, 1, total_time)

        for i in range(0, loop):
            # pred = pred_result(dis_dic, scores, algorithm.log)
            pred = sklearn_gpr_pred_result(dis_dic, scores, algorithm.log)
            # lambda expression sorting is too slow
            # sorted_pred = sorted(pred.iteritems(), key=lambda x: x[1], reverse=True)
            # sorted_scores = sorted(scores.iteritems(), key=lambda x: x[1], reverse=reverse_value)
            sorted_pred = sorted(pred.iteritems(), key=itemgetter(1), reverse=True)
            # sorted_scores = sorted(scores.iteritems(), key=itemgetter(1), reverse=reverse_value)
            for j in range(count):
                pred_index = sorted_pred[j][0]
                pred_value = algorithm.evaluate(
                    [sorted_pred[j][0]], False, random_choice + 1 + i * count + j, total_time)[sorted_pred[j][0]]
                # if compare(sorted_scores[0][1], pred_value, reverse_value):
                #     sorted_scores.remove(sorted_scores[0])
                #     sorted_scores.append((pred_index, pred_value))
                #     sorted_scores = sorted(dict(sorted_scores).iteritems(), key=itemgetter(1), reverse=reverse_value)
                scores[pred_index] = pred_value

            # scores = dict(sorted_scores)

        sorted_scores = sorted(scores.iteritems(), key=itemgetter(1), reverse=not reverse_value)
        algorithm.evaluate([sorted_scores[0][0]], True, total_time, total_time)
    else:
        total_time = len(dis_dic) + 1
        scores = algorithm.evaluate(range(len(dis_dic)), False, 1, total_time)
        sorted_scores = sorted(scores.iteritems(), key=itemgetter(1), reverse=not reverse_value)
        algorithm.evaluate([sorted_scores[0][0]], True, total_time, total_time)
    return scores


def compare(digit_a, digit_b, compare_method):
    if compare_method:
        return digit_a > digit_b
    else:
        return not digit_a > digit_b


def get_algorithm(algorithm_type, algor_params_str, other_params_str):
    # pd.read_csv('C:\\Users\\akbbx\\IdeaProjects\\DataSciencePlatform\\src\\main\\python\\data\\classfication\\iris.csv', sep=',', encoding='utf-8')
    algor_params = convert_params(json.loads(algor_params_str))

    if algorithm_type == 'DecisionTreeClassifier':
        return DecisionTreeClassifierEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'LogisticRegression':
        return LogisticRegressionEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'LogisticRegressionCV':
        return LogisticRegressionCVEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'RandomForestClassifier':
        return RandomForestClassifierEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'MLPClassifier':
        return MLPClassifierEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'LinearRegression':
        return LinearRegressionEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'Ridge':
        return RidgeEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'RidgeCV':
        return RidgeCVEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'Lasso':
        return LassoEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'LassoCV':
        return LassoCVEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'LassoLarsCV':
        return LassoLarsCVEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'SVR':
        return SVREvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'LinearSVR':
        return LinearSVREvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'RandomForestRegressor':
        return RandomForestRegressorEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'GradientBoostingRegressor':
        return GradientBoostingRegressorEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'MLPRegressor':
        return MLPRegressorEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'KMeans':
        return KMeansEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'XGBClassifier':
        return XGBClassifierEvaluate(algor_params, json.loads(other_params_str))
    elif algorithm_type == 'XGBRegressor':
        return XGBRegressorEvaluate(algor_params, json.loads(other_params_str))
    else:
        print json.dumps({'isok': False, 'exception': 'No algorithm you enter, please re-enter the algorithm name.',
                          'library': 'sklearn',
                          'model': algorithm_type})
        raise Exception("No algorithm you enter, please re-enter the algorithm name.")
