package com.neu.autoparams.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Definition {
    authority("/authority.json"),
    level("/level.json");

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String path;

    Definition(String path) {
        this.path = path;
    }

    public List getData() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            return objectMapper.readValue(inputStream, List.class);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public <T> List<T> loadData(Class<T> toValueType) {
        List list = getData();
        List<T> source = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            source.add(objectMapper.convertValue(list.get(i), toValueType));
        }

        return source;
    }

    public Map getDataToMap() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    public Map<String, Object> getData(int id) {
        List items = getData();
        return (Map) (items.get(id));
    }
}
