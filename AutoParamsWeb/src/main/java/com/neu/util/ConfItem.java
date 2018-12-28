package com.neu.util;

import com.neu.autoparams.mvc.entity.AlgorithmParam;
import net.sf.json.JSONArray;

import java.util.ArrayList;

public class ConfItem {
    private String algorithmLabel;
    private String algorithmName;
    private ArrayList<AlgorithmParam> params;

    public ConfItem(String algorithmLabel, String algorithmName) {
        this.algorithmLabel = algorithmLabel;
        this.algorithmName = algorithmName;
        params = new ArrayList<>();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public ArrayList<AlgorithmParam> getParams() {
        return params;
    }

    public void setParams(ArrayList<AlgorithmParam> params) {
        this.params = params;
    }

    public void addParam(AlgorithmParam taskParam) {
        this.params.add(taskParam);
    }

    public String getAlgorithmLabel() {
        return algorithmLabel;
    }

    public void setAlgorithmLabel(String algorithmLabel) {
        this.algorithmLabel = algorithmLabel;
    }

    @Override
    public String toString() {
        return JSONArray.fromObject(params).toString();
    }
}
