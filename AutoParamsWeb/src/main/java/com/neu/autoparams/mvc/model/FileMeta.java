package com.neu.autoparams.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.jdbc.core.RowMapper;

//ignore "bytes" when return json format
@JsonIgnoreProperties({"bytes"})
public class FileMeta {

    public static final String FilE_ID = "f_id";
    public static final String FilE_PATH = "f_path";
    public static final String FilE_TYPE = "f_type";
    public static final String COL_NAME = "col_Name";
    public static final String CLU_LABEL = "clu_label";
    public static final String ORIGIN_NAME = "origin_name";
    public static final String FILE_SIZE = "f_size";
    public static final String UPLOAD_TIME = "upload_time";

    private int fileId;
    private String filePath;
    private String originName;
    private String fileSize;
    private String fileType;
    private Boolean colName;
    private Boolean cluLabel;
    private long uploadTime;
    private byte[] bytes;


    private static final RowMapper<FileMeta> rowMapper = (resultSet, i) -> {
        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileId(resultSet.getInt(FilE_ID));
        fileMeta.setFilePath(resultSet.getString(FilE_PATH));
        fileMeta.setFileType(resultSet.getString(FilE_TYPE));
        fileMeta.setColName(resultSet.getBoolean(COL_NAME));
        fileMeta.setCluLabel(resultSet.getBoolean(CLU_LABEL));
        fileMeta.setOriginName(resultSet.getString(ORIGIN_NAME));
        fileMeta.setFileSize(resultSet.getString(FILE_SIZE));
        fileMeta.setUploadTime(resultSet.getTimestamp(UPLOAD_TIME).getTime());
        return fileMeta;
    };

    public static RowMapper<FileMeta> getRowMapper() {
        return rowMapper;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Boolean getColName() {
        return colName;
    }

    public void setColName(Boolean colName) {
        this.colName = colName;
    }

    public Boolean getCluLabel() {
        return cluLabel;
    }

    public void setCluLabel(Boolean cluLabel) {
        this.cluLabel = cluLabel;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }
}
