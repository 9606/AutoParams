# -*- coding: UTF-8 -*-
from bayesian_optimization import get_algorithm
import bayesian_optimization as bo
import sys
import json
import warnings
warnings.filterwarnings("ignore")

def main():
    # with open('C:\\Users\\akbbx\\PycharmProjects\\AutoParams\\algor_params.json', 'r') as load_f:
    # with open('algor_params.json', 'r') as load_f:
    #     json_str = load_f.read()
    #     # 注意这个 utf-8 编码是 json_str 的编码，不是最终的编码，最终的编码的 String 的字符串类型是 Unicode
    #     # json.loads(json_str, encoding='utf-8')
    #     loads_dict = json.loads(json_str)

    # sys.argv[0] is the name of module
    algorithm = get_algorithm(sys.argv[1], sys.argv[2], sys.argv[3])
    if sys.argv[4] == 'Bayesian_Optimization':
        loop = 50
        random_choice = 5
        count = 1
        ac_func = 'upper_confidence_bound'
        for param in json.loads(sys.argv[5]):
            if param['name'] == 'bo_loop':
                loop = param['val']
            elif param['name'] == 'bo_random_choice':
                random_choice = param['val']
            elif param['name'] == 'bo_count':
                count = param['val']
            elif param['name'] == 'bo_ac_func':
                ac_func = param['val']
        bo.run(algorithm, loop, random_choice, count, ac_func)


if __name__ == '__main__':
    main()
