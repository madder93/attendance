package com.madder.attendance.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangqian
 * @Date 2019-09-12 14:24
 **/

public class ResponseData implements Serializable {

    private static final long serialVersionUID = 7776492774834991218L;

    private String state;
    private String describe;
    private HashMap<String, Object> results = new HashMap();

    public ResponseData(String state, String describe) {
        this.state = state;
        this.describe = describe;
    }

    public static final ResponseData SUCCESS() {
        return new ResponseData("000000", "SUCCESS");
    }

    public static final ResponseData PARAMS_ERROR() {
        return new ResponseData("200001", "请求参数检查错误");
    }

    public static final ResponseData SYSTEM_ERROR() {
        return new ResponseData("999999", "系统错误");
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public HashMap<String, Object> getResults() {
        return results;
    }

    public void setResults(HashMap<String, Object> results) {
        this.results = results;
    }

    public void addResultObject(String resultKey, Object result) {
        this.results.put(resultKey, result);
    }

    public void addResultMap(Map<String, Object> resultMap) {
        this.results.putAll(resultMap);
    }
}
