package com.neu.autoparams.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.autoparams.mvc.model.FileMeta;
import org.springframework.jdbc.core.RowMapper;

public class SubmitParams {
    public static final String TASK_ID = "t_id";

    public static final String ALGO_PARAMS = "algo_params";
    public static final String OPT_ALGO_PARAMS = "opt_algo_params";
    public static final String EVA_TYPE = "eva_type";

    public static final String ALGO_ID = "algo_id";
    public static final String ALGO_NAME = "algo_name";
    public static final String ALGO_LIB = "algo_lib";
    public static final String ALGO_TYPE = "algo_type";
    public static final String DEFAULT_PARAMS = "algorithm.default_params";
    public static final String OPT_ALGO_ID = "opt_algo_id";
    public static final String OPT_ALGO_NAME = "opt_algo_name";
    public static final String OPT_DEFAULT_PARAMS = "opt_algorithm.default_params";
    public static final String FilE_ID = "f_id";
    public static final String FilE_PATH = "f_path";
    public static final String FilE_TYPE = "f_type";
    public static final String COL_NAME = "col_Name";
    public static final String CLU_LABEL = "clu_label";
    public static final String ORIGIN_NAME = "origin_name";
    public static final String FILE_SIZE = "f_size";
    public static final String UPLOAD_TIME = "upload_time";

    int taskId;

    int algoId;
    String algoLib;
    String algoName;
    String algoType;
    String algoParams;
    String newAlgoParams;

    String evaType;

    int optAlgoId;
    String optAlgoName;
    String optAlgoParams;
    String newOptAlgoParams;

    String fileInfo;
    String fileTypeValue;



    private static final RowMapper<SubmitParams> rowMapper = (resultSet, i) -> {
        SubmitParams submitParams = new SubmitParams();
        submitParams.setTaskId(resultSet.getInt(TASK_ID));
        submitParams.setAlgoId(resultSet.getInt(ALGO_ID));
        submitParams.setAlgoLib(resultSet.getString(ALGO_LIB));
        submitParams.setAlgoType(resultSet.getString(ALGO_TYPE));
        submitParams.setAlgoName(resultSet.getString(ALGO_NAME));
        submitParams.setAlgoParams(resultSet.getString(DEFAULT_PARAMS));
        submitParams.setNewAlgoParams(resultSet.getString(ALGO_PARAMS));

        submitParams.setEvaType(resultSet.getString(EVA_TYPE));

        submitParams.setOptAlgoId(resultSet.getInt(OPT_ALGO_ID));
        submitParams.setOptAlgoName(resultSet.getString(OPT_ALGO_NAME));
        submitParams.setOptAlgoParams(resultSet.getString(OPT_DEFAULT_PARAMS));
        submitParams.setNewOptAlgoParams(resultSet.getString(OPT_ALGO_PARAMS));


        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileId(resultSet.getInt(FilE_ID));
        fileMeta.setFilePath(resultSet.getString(FilE_PATH));
        fileMeta.setFileType(resultSet.getString(FilE_TYPE));
        fileMeta.setColName(resultSet.getBoolean(COL_NAME));
        fileMeta.setCluLabel(resultSet.getBoolean(CLU_LABEL));
        fileMeta.setOriginName(resultSet.getString(ORIGIN_NAME));
        fileMeta.setFileSize(resultSet.getString(FILE_SIZE));
        fileMeta.setUploadTime(resultSet.getTimestamp(UPLOAD_TIME).getTime());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            submitParams.setFileInfo(objectMapper.writeValueAsString(fileMeta));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        submitParams.setFileTypeValue(resultSet.getString(FilE_TYPE));
        return submitParams;
    };

    public int getAlgoId() {
        return algoId;
    }

    public void setAlgoId(int algoId) {
        this.algoId = algoId;
    }

    public String getAlgoLib() {
        return algoLib;
    }

    public void setAlgoLib(String algoLib) {
        this.algoLib = algoLib;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public String getAlgoParams() {
        return algoParams;
    }

    public void setAlgoParams(String algoParams) {
        this.algoParams = algoParams;
    }

    public String getNewAlgoParams() {
        return newAlgoParams;
    }

    public void setNewAlgoParams(String newAlgoParams) {
        this.newAlgoParams = newAlgoParams;
    }

    public String getEvaType() {
        return evaType;
    }

    public void setEvaType(String evaType) {
        this.evaType = evaType;
    }

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

    public String getOptAlgoParams() {
        return optAlgoParams;
    }

    public void setOptAlgoParams(String optAlgoParams) {
        this.optAlgoParams = optAlgoParams;
    }

    public String getNewOptAlgoParams() {
        return newOptAlgoParams;
    }

    public void setNewOptAlgoParams(String newOptAlgoParams) {
        this.newOptAlgoParams = newOptAlgoParams;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getFileTypeValue() {
        return fileTypeValue;
    }

    public void setFileTypeValue(String fileTypeValue) {
        this.fileTypeValue = fileTypeValue;
    }

    public static RowMapper<SubmitParams> getRowMapper() {
        return rowMapper;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getAlgoType() {
        return algoType;
    }

    public void setAlgoType(String algoType) {
        this.algoType = algoType;
    }
}
