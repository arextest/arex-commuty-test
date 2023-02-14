package com.arextest.agent.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author daixq
 * @date 2023/02/14
 */
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(object);
        } catch (com.fasterxml.jackson.core.JsonProcessingException ex) {
            logger.error("toJson JsonProcessingException", ex);
            json = "{\"result\":\"JsonProcessingException\"}";
        }
        return json;
    }

    public static <T> T parseJson(String json, Class<T> targetClass) {
        ObjectMapper mapper = new ObjectMapper();
        T object = null;
        try {
            object = mapper.readValue(json, targetClass);
        } catch (com.fasterxml.jackson.core.JsonProcessingException ex) {
            logger.error("parseJson JsonProcessingException", ex);
        }
        return object;
    }
}
