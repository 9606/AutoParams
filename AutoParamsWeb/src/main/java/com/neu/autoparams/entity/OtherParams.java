package com.neu.autoparams.entity;

import java.util.ArrayList;
import java.util.List;

public class OtherParams {
    private String fileType;
    private Boolean cluLabel;
    private Boolean colName;
    private List<String> naSymbol;
    private String filePath;

    public OtherParams() {
        naSymbol = new ArrayList<String>();
        naSymbol.add("?");
        naSymbol.add("NA");
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Boolean getCluLabel() {
        return cluLabel;
    }

    public void setCluLabel(Boolean cluLabel) {
        this.cluLabel = cluLabel;
    }

    public Boolean getColName() {
        return colName;
    }

    public void setColName(Boolean colName) {
        this.colName = colName;
    }

    public List<String> getNaSymbol() {
        return naSymbol;
    }

    public void setNaSymbol(List<String> naSymbol) {
        this.naSymbol = naSymbol;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
