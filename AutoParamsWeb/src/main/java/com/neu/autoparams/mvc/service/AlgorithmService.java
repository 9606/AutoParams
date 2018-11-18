package com.neu.autoparams.mvc.service;

import com.neu.autoparams.entity.Algorithm;
import com.neu.autoparams.entity.OptAlgorithm;
import com.neu.autoparams.mvc.dao.AlgorithmDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlgorithmService {

    @Resource
    AlgorithmDao algorithmDao;
    public List<String> getAlgoLibList(){
        return algorithmDao.getAlgoLibList();
    }
    public List<String> getAlgoTypeList(String algoLib){
        return algorithmDao.getAlgoTypeList(algoLib);
    }

    public List<Algorithm> getAlgoNameList(String algoLib, String algoType){
        return algorithmDao.getAlgoNameList(algoLib, algoType);
    }
    public List<String> getEvaTypeList(String algoLib, String algoType){
        return algorithmDao.getEvaTypeList(algoLib, algoType);
    }
    public String getAlgoParams(Integer algoId){
        return algorithmDao.getAlgoParams(algoId);
    }
    public List<OptAlgorithm> getOptAlgoNameList(){
        return algorithmDao.getOptAlgoNameList();
    }
    public String getOptAlgoParams(Integer optAlgoId){
        return algorithmDao.getOptAlgoParams(optAlgoId);
    }

}
