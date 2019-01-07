import numpy as np
from scipy.stats import norm
from algorithm import *
import json
from operator import itemgetter
from sklearn import gaussian_process
from sklearn.gaussian_process.kernels import Matern
import warnings

warnings.filterwarnings("ignore")


def part_of_array(seq, *index):
    return [seq[i] for i in index]


def upper_confidence_bound(param_array, train_dic, greater_is_better=True):
    pred = {}
    train_index = train_dic.keys()
    train_Y = []
    train_X = []
    test_X = []
    test_X_index = []
    for i, ele in enumerate(param_array):
        if i in train_index:
            train_X.append(param_array[i])
            train_Y.append(train_dic[i])
        else:
            test_X.append(param_array[i])
            test_X_index.append(i)

    kernel = Matern(length_scale=1.0, length_scale_bounds=(1e-1, 10.0), nu=2.5)
    gpr = gaussian_process.GaussianProcessRegressor(kernel=kernel).fit(train_X, train_Y)
    avg_pred, std_pred = gpr.predict(test_X, return_std=True, return_cov=False)

    scaling_factor = (-1) ** (not greater_is_better)
    beta = 1.96

    for i, avg_pred_i in enumerate(avg_pred):
        pred[test_X_index[i]] = avg_pred_i + scaling_factor * std_pred[i] * beta
    return pred


def expected_improvement(param_array, train_dic, greater_is_better=True):
    train_index = train_dic.keys()
    train_Y = []
    train_X = []
    test_X = []
    test_X_index = []
    for i, ele in enumerate(param_array):
        if i in train_index:
            train_X.append(param_array[i])
            train_Y.append(train_dic[i])
        else:
            test_X.append(param_array[i])
            test_X_index.append(i)

    kernel = Matern(length_scale=1.0, length_scale_bounds=(1e-1, 10.0), nu=2.5)
    gpr = gaussian_process.GaussianProcessRegressor(kernel=kernel).fit(train_X, train_Y)
    avg_pred, std_pred = gpr.predict(test_X, return_std=True, return_cov=False)

    if greater_is_better:
        loss_optimum = np.max(train_Y)
    else:
        loss_optimum = np.min(train_Y)

    scaling_factor = (-1) ** (not greater_is_better)

    # In case sigma equals zero
    with np.errstate(divide='ignore'):
        Z = scaling_factor * (avg_pred - loss_optimum) / std_pred
        expected_improvement = scaling_factor * (avg_pred - loss_optimum) * norm.cdf(Z) + std_pred * norm.pdf(Z)
        expected_improvement[std_pred == 0.0] == 0.0

    return dict(zip(test_X_index, expected_improvement))


def run(algorithm, loop=50, random_choice=5, count=1, ac_func='expected_improvement'):
    dis_dic = algorithm.dis_params

    if isinstance(algorithm, ClassifierEvaluate) or isinstance(algorithm, XGBClassifierEvaluate):
        greater_is_better = True
    elif isinstance(algorithm, RegressionEvaluate) or isinstance(algorithm, XGBRegressorEvaluate):
        greater_is_better = False
    else:
        greater_is_better = True

    if len(dis_dic) > random_choice + loop * count:
        random_index = np.random.choice(len(dis_dic), random_choice, replace=False)
        total_time = random_choice + loop * count + 1
        scores = algorithm.evaluate(random_index, False, 1, total_time)

        for i in range(0, loop):
            if ac_func == 'upper_confidence_bound':
                pred = upper_confidence_bound(dis_dic, scores, greater_is_better)
                sorted_pred = sorted(pred.iteritems(), key=itemgetter(1), reverse=greater_is_better)
            elif ac_func == 'expected_improvement':
                pred = expected_improvement(dis_dic, scores, greater_is_better)
                sorted_pred = sorted(pred.iteritems(), key=itemgetter(1), reverse=True)

            for j in range(count):
                pred_index = sorted_pred[j][0]
                pred_value = algorithm.evaluate(
                    [sorted_pred[j][0]], False, random_choice + 1 + i * count + j, total_time)[sorted_pred[j][0]]
                scores[pred_index] = pred_value

        sorted_scores = sorted(scores.iteritems(), key=itemgetter(1), reverse=greater_is_better)
        algorithm.evaluate([sorted_scores[0][0]], True, total_time, total_time)

    else:
        total_time = len(dis_dic) + 1
        scores = algorithm.evaluate(range(len(dis_dic)), False, 1, total_time)
        sorted_scores = sorted(scores.iteritems(), key=itemgetter(1), reverse=greater_is_better)
        algorithm.evaluate([sorted_scores[0][0]], True, total_time, total_time)
    return scores


def compare(digit_a, digit_b, compare_method):
    if compare_method:
        return digit_a > digit_b
    else:
        return not digit_a > digit_b


def get_algorithm(algorithm_type, algor_params_str, other_params_str):
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
