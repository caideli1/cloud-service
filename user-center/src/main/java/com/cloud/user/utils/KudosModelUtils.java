package com.cloud.user.utils;

import com.cloud.common.utils.StringUtils;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yoga
 * @Description:
 * @date 2019-06-2010:30
 */
public final class KudosModelUtils {

    private static class KudosModelUtilsInstance {
        private static final KudosModelUtils instance = new KudosModelUtils();
    }

    private KudosModelUtils(){}

    public static KudosModelUtils getInstance() {
        KudosModelUtils instance = KudosModelUtilsInstance.instance;
        if (null == instance.mapper) {
            instance.mapper = new ObjectMapper();
            instance.mapper.registerModule(new JavaTimeModule());
        }
        return instance;
    }

    private ObjectMapper mapper;

    private String fieldKey;

    private RedisUtil redisUtil;

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public <T> List<T> getModelList(String content, Class<T> valueType) throws Exception {
        if (StringUtils.isBlank(fieldKey)) {
            throw new RuntimeException("未设置fieldKey");
        }

        if (StringUtils.isBlank(content)) {
            return new ArrayList<>();
        }

        Object typeObject = new JSONTokener(content).nextValue();
        if (typeObject instanceof JSONArray) {
            List<HashMap<String, Object>> jsonList = mapper.readValue(content, List.class);
            return jsonList.stream()
                    .map(i -> mapToObj((HashMap<String, Object>) i.get(fieldKey), valueType))
                    .collect(Collectors.toList());
        } else if (typeObject instanceof JSONObject) {
            HashMap<String, Object> objectMap = mapper.readValue(content, HashMap.class);
            HashMap<String, Object> valueMap = (HashMap<String, Object>) objectMap.get(fieldKey);
            T res = mapToObj(valueMap, valueType);
            List<T> summaryList = new ArrayList<>();
            summaryList.add(res);
            return summaryList;
        }
        return new ArrayList<>();
    }

    private <T> T mapToObj(HashMap<String, Object> map, Class<T> valueType) {
        try {
            return mapper.readValue(JSONObject.valueToString(map), valueType);
        } catch (IOException e) {
            e.printStackTrace();
            return newTclass(valueType);
        }
    }

    private <T> T newTclass(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public <T> void saveToRedis(String key, List<T> valueList, int sec) {
        try {
            String js = mapper.writeValueAsString(valueList);
            redisUtil.set(key, js, sec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getFromRedis(String key, Class<T> valueType) {
        try {
            String js = redisUtil.get(key);
            if (StringUtils.isBlank(js)) {
                return null;
            }
            List<HashMap<String, Object>> jsonList = mapper.readValue(js, List.class);
            return jsonList.stream()
                    .map(i -> mapToObj(i, valueType))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}