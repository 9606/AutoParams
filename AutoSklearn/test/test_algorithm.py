# !/usr/bin/env python
# -*- coding:utf-8 -*-

import ConfigParser
import logging
import os
import platform
import subprocess
import json
import warnings
warnings.filterwarnings("ignore")

algor_label = "21001"
other_params = "{\"fileType\":\"csv\", \"cluLabel\":false, \"naSymbol\":[\"NA\",\"?\"]}"

opt_algo_params = '[{"name":"bo_loop","val":20,"placeholder":"迭代次数","isNumber":true},{"name":"bo_random_choice","val":5,"placeholder":"先验个数","isNumber":true},' \
                  '{"name":"bo_count","val":1,"placeholder":"迭代选择个数","isNumber":true},{"name":"bo_ac_func","val":"expected_improvement","placeholder":"获取函数","isNumber":true}]'
file_path = '/Users/enbo/IdeaProjects/AutoParams/AutoSklearn/src/data/classfication/adult.csv'
file_path_prefix = '/Users/enbo/IdeaProjects/AutoParams/AutoSklearn/src/data/'
sys_str = platform.system()
if sys_str == "Windows":
    file_path_prefix = "C:\\Users\\z00456052\\IdeaProjects\\AutoParams\\AutoSklearn\\src\\data\\"
else:
    file_path_prefix = "/Users/enbo/IdeaProjects/AutoParams/AutoSklearn/src/data/"

if algor_label[1] == '1':
    if json.loads(other_params)["fileType"] == "csv":
        file_path = file_path_prefix + "classfication/adult.csv"
    else:
        file_path = file_path_prefix + "classfication/news20.dat"
elif algor_label[1] == '2':
    file_path = file_path_prefix + "clustering/sales_no_label.csv"
if algor_label[1] == '3':
    file_path = file_path_prefix + "regression/feature.csv"

os.chdir("../../AutoParamsWeb/src/main/resources")
# print os.getcwd()
cf = ConfigParser.ConfigParser()
cf.read("algor_params.ini")
algorithm_type = cf.get("algor", "automodel.algor." + algor_label).split(':')[0]
algor_params_str = cf.get("params", "automodel.params." + algor_label)

log = logging.getLogger("Core.Analysis.Processing")

INTERPRETER = "python"
if not os.path.exists(INTERPRETER):
    log.error("Cannot find INTERPRETER at path \"%s\"." % INTERPRETER)

os.chdir("../../../../AutoSklearn/src")
# print os.getcwd()
processor = "main.py"

other_params_json = json.loads(other_params)
other_params_json['filePath'] = file_path
if sys_str == "Windows":
    # pargs = [INTERPRETER, processor, algorithm_type, algor_params_str.replace('"', '"""'), json.dumps(other_params_json), 'Bayesian_Optimization', opt_algo_params.replace('"', '"""')]
    pargs = [INTERPRETER, processor, algorithm_type, algor_params_str, json.dumps(other_params_json), 'Bayesian_Optimization', opt_algo_params]
else:
    pargs = [INTERPRETER, processor, algorithm_type, algor_params_str, json.dumps(other_params_json), 'Bayesian_Optimization', opt_algo_params]
# pargs.extend(["--input=inputMd5s"])
subprocess.Popen(pargs).wait()
# subprocess.PIPE

