package com.neu.autoparams.mvc.dao;

import com.neu.autoparams.mvc.entity.Algorithm;
import com.neu.autoparams.mvc.entity.OptAlgorithm;
import org.ini4j.Ini;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.neu.autoparams.util.ConfigurationUtil;

@Service
public class AlgorithmDao {

    @Resource
    JdbcTemplate jdbcTemplate;
    private static final String findAlgoLibListQuery = "SELECT distinct algo_lib FROM algorithm";
    private static final String findAlgoTypeListQuery = "SELECT distinct algo_type FROM algorithm where algo_lib = ?";
    private static final String findAlgoNameListQuery =  "SELECT * FROM algorithm where algo_lib = ? and algo_type=?";
    private static final String findAlgoParamsQuery =  "SELECT default_params FROM algorithm where algo_id = ?";
    private static final String findOptAlgoNameListQuery =  "SELECT * FROM opt_algorithm";
    private static final String findOptAlgoParamsQuery =  "SELECT default_params FROM opt_algorithm where opt_algo_id = ?";
    private static final String findEvaTypeListQuery =  "SELECT DISTINCT eva_type FROM evaluate WHERE lib=? AND algo_type=?";


    public List<String> getAlgoLibList() {
        List<String> algoLibList = jdbcTemplate.query(findAlgoLibListQuery, (rs, rowNum) -> rs.getString(1));
        return algoLibList;
    }

    public List<String> getAlgoTypeList(String algoLib) {
        List<String> algoLibList = jdbcTemplate.query(findAlgoTypeListQuery, new Object[]{algoLib}, (rs, rowNum) -> rs.getString(1));
        return algoLibList;
    }

    public List<Algorithm> getAlgoNameList(String algoLib, String algoType) {
        List<Algorithm> algorithmList = jdbcTemplate.query(findAlgoNameListQuery, new Object[]{algoLib, algoType}, Algorithm.algoNameRowMapper);
        return algorithmList;
    }

    public String getAlgoParams(Integer algoId) {
        String algoParams = jdbcTemplate.queryForObject(findAlgoParamsQuery, new Object[]{algoId}, (rs, rowNum) -> rs.getString(1));
        return algoParams;
    }

    public List<String> getEvaTypeList(String algoLib, String algoType) {
        List<String> evaTypeList = new ArrayList<>();
        String algoParams = jdbcTemplate.queryForObject(findEvaTypeListQuery, new Object[]{algoLib, algoType}, (rs, rowNum) -> rs.getString(1));
        for (String evaType:algoParams.split(":")){
            evaTypeList.add(evaType);
        }
        return evaTypeList;
    }

    public List<OptAlgorithm> getOptAlgoNameList() {
        List<OptAlgorithm> optAlgorithmList = jdbcTemplate.query(findOptAlgoNameListQuery, OptAlgorithm.optAlgoNameRowMapper);
        return optAlgorithmList;
    }

    public String getOptAlgoParams(Integer optAlgoId) {
        String optAlgoParams = jdbcTemplate.queryForObject(findOptAlgoParamsQuery, new Object[]{optAlgoId}, (rs, rowNum) -> rs.getString(1));
        return optAlgoParams;
    }

    public static void main(String[] args) {
        try {
            Ini ini = new Ini(new File(ConfigurationUtil.INSTANCE.getAlgoParamsPath()));
            Ini.Section algor = ini.get("algor");
            for (String algoInfo : algor.values()) {
                System.out.println(algoInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
