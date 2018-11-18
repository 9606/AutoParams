package com.neu.autoparams.entity;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmParam {

    private String paramName;
    private String paramDesc;

    // 枚举
    private boolean isString;
    private List<String> stringValues;

    private boolean isFloat;
    private float floatMin;
    private boolean floatMinInclusive;
    private float floatDownValue;
    private float floatMax;
    private boolean floatMaxInclusive;
    private float floatUpValue;
    private int floatNum;

    private boolean isInt;
    private int intMin;
    private boolean intMinInclusive;
    private int intDownValue;
    private int intMax;
    private boolean intMaxInclusive;
    private int intUpValue;
    private int intNum;

    private boolean isBool;
    private List<Boolean> boolValues;

    private boolean isList;
    private List<List<Object>> listValues;
    private boolean isNull;

    public AlgorithmParam(String paramName, String paramDesc, List<String> stringValues) {
        this.isString = true;
        this.paramName = paramName;
        this.paramDesc = paramDesc;
        this.stringValues = stringValues;
    }

    public AlgorithmParam(String paramName, String paramDesc, float floatMin, float floatDownValue, float floatMax, float floatUpValue, int floatNum) {
        this.isFloat = true;
        this.paramName = paramName;
        this.paramDesc = paramDesc;
        this.floatMin = floatMin;
        this.floatDownValue = floatDownValue;
        this.floatMax = floatMax;
        this.floatUpValue = floatUpValue;
        this.floatNum = floatNum;
    }

    public AlgorithmParam(String paramName, String paramDesc, int intMin, int intDownValue, int intMax, int intUpValue, int intNum) {
        this.isInt = true;
        this.paramName = paramName;
        this.paramDesc = paramDesc;
        this.intMin = intMin;
        this.intDownValue = intDownValue;
        this.intMax = intMax;
        this.intUpValue = intUpValue;
        this.intNum = intNum;
    }

    public AlgorithmParam(String paramName, String paramDesc, boolean isBool) {
        this.paramName = paramName;
        this.paramDesc = paramDesc;
        this.isBool = isBool;
        this.boolValues = new ArrayList<Boolean>();
        boolValues.add(new Boolean(true));
        boolValues.add(new Boolean(false));
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public boolean getIsString() {
        return isString;
    }

    public void setIsString(boolean string) {
        isString = string;
    }

    public List<String> getStringValues() {
        return stringValues;
    }

    public void setStringValues(List<String> stringValues) {
        this.stringValues = stringValues;
    }

    public boolean getIsFloat() {
        return isFloat;
    }

    public void setIsFloat(boolean aFloat) {
        isFloat = aFloat;
    }

    public float getFloatMin() {
        return floatMin;
    }

    public void setFloatMin(float floatMin) {
        this.floatMin = floatMin;
    }

    public boolean isFloatMinInclusive() {
        return floatMinInclusive;
    }

    public void setFloatMinInclusive(boolean floatMinInclusive) {
        this.floatMinInclusive = floatMinInclusive;
    }

    public float getFloatDownValue() {
        return floatDownValue;
    }

    public void setFloatDownValue(float floatDownValue) {
        this.floatDownValue = floatDownValue;
    }

    public float getFloatMax() {
        return floatMax;
    }

    public void setFloatMax(float floatMax) {
        this.floatMax = floatMax;
    }

    public boolean isFloatMaxInclusive() {
        return floatMaxInclusive;
    }

    public void setFloatMaxInclusive(boolean floatMaxInclusive) {
        this.floatMaxInclusive = floatMaxInclusive;
    }

    public float getFloatUpValue() {
        return floatUpValue;
    }

    public void setFloatUpValue(float floatUpValue) {
        this.floatUpValue = floatUpValue;
    }

    public int getFloatNum() {
        return floatNum;
    }

    public void setFloatNum(int floatNum) {
        this.floatNum = floatNum;
    }

    public boolean getIsInt() {
        return isInt;
    }

    public void setIsInt(boolean anInt) {
        isInt = anInt;
    }

    public int getIntMin() {
        return intMin;
    }

    public void setIntMin(int intMin) {
        this.intMin = intMin;
    }

    public boolean isIntMinInclusive() {
        return intMinInclusive;
    }

    public void setIntMinInclusive(boolean intMinInclusive) {
        this.intMinInclusive = intMinInclusive;
    }

    public int getIntDownValue() {
        return intDownValue;
    }

    public void setIntDownValue(int intDownValue) {
        this.intDownValue = intDownValue;
    }

    public int getIntMax() {
        return intMax;
    }

    public void setIntMax(int intMax) {
        this.intMax = intMax;
    }

    public boolean isIntMaxInclusive() {
        return intMaxInclusive;
    }

    public void setIntMaxInclusive(boolean intMaxInclusive) {
        this.intMaxInclusive = intMaxInclusive;
    }

    public int getIntUpValue() {
        return intUpValue;
    }

    public void setIntUpValue(int intUpValue) {
        this.intUpValue = intUpValue;
    }

    public int getIntNum() {
        return intNum;
    }

    public void setIntNum(int intNum) {
        this.intNum = intNum;
    }

    public boolean getIsBool() {
        return isBool;
    }

    public void setIsBool(boolean bool) {
        isBool = bool;
    }

    public List<Boolean> getBoolValues() {
        return boolValues;
    }

    public void setBoolValues(List<Boolean> boolValues) {
        this.boolValues = boolValues;
    }

    public boolean getIsList() {
        return isList;
    }

    public void setIsList(boolean list) {
        isList = list;
    }

    public List<List<Object>> getListValues() {
        return listValues;
    }

    public void setListValues(List<List<Object>> listValues) {
        this.listValues = listValues;
    }

    public boolean getIsNull() {
        return isNull;
    }

    public void setIsNull(boolean aNull) {
        isNull = aNull;
    }


}