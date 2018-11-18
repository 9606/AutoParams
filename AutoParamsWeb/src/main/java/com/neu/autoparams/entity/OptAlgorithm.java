package com.neu.autoparams.entity;

import org.springframework.jdbc.core.RowMapper;

public class OptAlgorithm {
    public static final String OPT_ALGO_ID = "opt_algo_id";
    public static final String OPT_ALGO_NAME = "opt_algo_name";
    public static final String DEFAULT_PARAMS = "default_params";

    private int optAlgoId;
    private String optAlgoName;
    private String defaultParams;


    public static final RowMapper<OptAlgorithm> optAlgoNameRowMapper = (resultSet, i) -> {
        OptAlgorithm optAlgorithm = new OptAlgorithm();
        optAlgorithm.setOptAlgoId(resultSet.getInt(OPT_ALGO_ID));
        optAlgorithm.setOptAlgoName(resultSet.getString(OPT_ALGO_NAME));
        return optAlgorithm;
    };

    public int getOptAlgoId() {
        return optAlgoId;
    }

    public void setOptAlgoId(int optAlgoId) {
        this.optAlgoId = optAlgoId;
    }

    public String getOptAlgoName() {
        return optAlgoName;
    }

    public void setOptAlgoName(String optAlgoName) {
        this.optAlgoName = optAlgoName;
    }

    public String getDefaultParams() {
        return defaultParams;
    }

    public void setDefaultParams(String defaultParams) {
        this.defaultParams = defaultParams;
    }
}
