package com.neu.autoparams.mvc.dao;

import com.neu.autoparams.entity.Algorithm;
import com.neu.autoparams.entity.OptAlgorithm;
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

    // public List<String> getAlgoTypeList(String algoLib) {
    //     try {
    //         Ini ini = new Ini(new File(ConfigurationUtil.INSTANCE.getAlgoParamsPath()));
    //         Ini.Section algor = ini.get("algor");
    //         Set<String> algoTypes = new HashSet<>();
    //         for (String algoInfo : algor.values()) {
    //             algoTypes.add(algoInfo.split(":")[2]);
    //         }
    //         return new ArrayList<>(algoTypes);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    public List<Algorithm> getAlgoNameList(String algoLib, String algoType) {
        List<Algorithm> algorithmList = jdbcTemplate.query(findAlgoNameListQuery, new Object[]{algoLib, algoType}, Algorithm.algoNameRowMapper);
        return algorithmList;
    }

    // public List<String> getAlgoNameList(String algoType) {
    //     try {
    //         Ini ini = new Ini(new File(ConfigurationUtil.INSTANCE.getAlgoParamsPath()));
    //         Ini.Section algor = ini.get("algor");
    //         List<String> algoTypeNames = new ArrayList<>();
    //         for (String algoInfo : algor.values()) {
    //             String[] infos = algoInfo.split(":");
    //             if (infos[2].equals(algoType) || algoType.equals("All")) {
    //                 algoTypeNames.add(algoInfo.split(":")[0]);
    //             }
    //         }
    //         return algoTypeNames;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    public String getAlgoParams(Integer algoId) {
        String algoParams = jdbcTemplate.queryForObject(findAlgoParamsQuery, new Object[]{algoId}, (rs, rowNum) -> rs.getString(1));
        return algoParams;
    }

    // public String getAlgoParams(String algoName) {
    //     try {
    //         Ini ini = new Ini(new File(ConfigurationUtil.INSTANCE.getAlgoParamsPath()));
    //         Ini.Section algor = ini.get("algor");
    //         Ini.Section params = ini.get("params");
    //         List<String> algoTypeNames = new ArrayList<>();
    //         for (String algoNo : algor.keySet()) {
    //             if (algor.get(algoNo).split(":")[0].equals(algoName)) {
    //                 String[] infos = algoNo.split("\\.");
    //                 return params.get("automodel.params." + infos[2]);
    //             }
    //         }
    //         return null;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    public List<String> getEvaTypeList(String algoLib, String algoType) {
        List<String> evaTypeList = new ArrayList<>();
        String algoParams = jdbcTemplate.queryForObject(findEvaTypeListQuery, new Object[]{algoLib, algoType}, (rs, rowNum) -> rs.getString(1));
        for (String evaType:algoParams.split(":")){
            evaTypeList.add(evaType);
        }
        return evaTypeList;
    }

    // public List<String> getEvaTypeList(String algoName) {
    //     try {
    //         Ini ini = new Ini(new File(ConfigurationUtil.INSTANCE.getAlgoParamsPath()));
    //         Ini.Section algor = ini.get("algor");
    //         Ini.Section evalu = ini.get("evalu");
    //         List<String> evaTypeList = new ArrayList<>();
    //         for (String algoInfo: algor.values()) {
    //             String iniAlgoName = algoInfo.split(":")[0];
    //             String algoType = algoInfo.split(":")[2];
    //             if (iniAlgoName.equals(algoName)){
    //                 for (String algoNo : evalu.keySet()) {
    //                     String[] infos = algoNo.split("\\.");
    //                     if (infos[3].equals(algoType)){
    //                         for (String evaType:evalu.get(algoNo).split(":")){
    //                             evaTypeList.add(evaType);
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //         return evaTypeList;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    public List<OptAlgorithm> getOptAlgoNameList() {
        List<OptAlgorithm> optAlgorithmList = jdbcTemplate.query(findOptAlgoNameListQuery, OptAlgorithm.optAlgoNameRowMapper);
        return optAlgorithmList;
    }

    // public List<String> getOptAlgoNameList() {
    //     try {
    //         Ini ini = new Ini(new File(ConfigurationUtil.INSTANCE.getAlgoParamsPath()));
    //         Set<String> optAlgoNames = new HashSet<>();
    //         Ini.Section opt = ini.get("opt_algor");
    //         for (String optAlgoName : opt.values()) {
    //             optAlgoNames.add(optAlgoName);
    //         }
    //         return new ArrayList<>(optAlgoNames);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    public String getOptAlgoParams(Integer optAlgoId) {
        String optAlgoParams = jdbcTemplate.queryForObject(findOptAlgoParamsQuery, new Object[]{optAlgoId}, (rs, rowNum) -> rs.getString(1));
        return optAlgoParams;
    }

    // public String getOptAlgoParams(String optAlgoName) {
    //     try {
    //         Ini ini = new Ini(new File(ConfigurationUtil.INSTANCE.getAlgoParamsPath()));
    //         Ini.Section optAlgor = ini.get("opt_algor");
    //         Ini.Section optParams = ini.get("opt_params");
    //         for (String optAlgorKey : optAlgor.keySet()) {
    //             if (optAlgor.get(optAlgorKey).equals(optAlgoName)){
    //                 String[] infos = optAlgorKey.split("\\.");
    //                 return optParams.get("automodel.opt_params." + infos[2]);
    //             }
    //         }
    //         return null;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

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
