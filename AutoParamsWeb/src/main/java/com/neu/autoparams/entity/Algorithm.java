package com.neu.autoparams.entity;

import org.springframework.jdbc.core.RowMapper;

public class Algorithm {
    public static final String ALGO_ID = "algo_id";
    public static final String ALGO_NAME = "algo_name";
    public static final String ALGO_LIB = "algo_lib";
    public static final String ALGO_TYPE = "algo_type";
    public static final String DEFAULT_PARAMS = "default_params";

    private int algoId;
    private String algoName;
    private String algoLib;
    private String algoType;
    private String defaultParams;


    public static final RowMapper<Algorithm> algoNameRowMapper = (resultSet, i) -> {
        Algorithm algorithm = new Algorithm();
        algorithm.setAlgoId(resultSet.getInt(ALGO_ID));
        algorithm.setAlgoName(resultSet.getString(ALGO_NAME));
        return algorithm;
    };

    public int getAlgoId() {
        return algoId;
    }

    public void setAlgoId(int algoId) {
        this.algoId = algoId;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public String getAlgoLib() {
        return algoLib;
    }

    public void setAlgoLib(String algoLib) {
        this.algoLib = algoLib;
    }

    public String getAlgoType() {
        return algoType;
    }

    public void setAlgoType(String algoType) {
        this.algoType = algoType;
    }

    public String getDefaultParams() {
        return defaultParams;
    }

    public void setDefaultParams(String defaultParams) {
        this.defaultParams = defaultParams;
    }
}
