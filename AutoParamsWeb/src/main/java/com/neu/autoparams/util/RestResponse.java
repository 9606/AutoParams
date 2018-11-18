package com.neu.autoparams.util;

import java.util.HashMap;
import java.util.Map;


public class RestResponse {
    private static final String RESULT_KEY = "result";
    private static final String MESSAGE_KEY = "message";
    private static final String DATA_KEY = "data";

    private GlobalResponseCode responseCode;

    private Map<String, Object> additionalData = new HashMap<>();

    private RestResponse(GlobalResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public static RestResponse create(GlobalResponseCode responseCode) {
        return new RestResponse(responseCode);
    }


    public RestResponse put(String key, Object data) {
        this.additionalData.put(key, data);
        return this;
    }

    public RestResponse putData(Object data) {
        this.additionalData.put(DATA_KEY, data);
        return this;
    }

    public Map<String, Object> build() {
        return new HashMap<String, Object>() {{
            put(RESULT_KEY, responseCode);
            put(MESSAGE_KEY, responseCode.getMessage());
            putAll(additionalData);
        }};
    }

}
