package com.easysui.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CHAO on 2017/7/10
 * 常用注解
 * @see: @JsonIgnoreProperties(ignoreUnknown = true)
 * @see: @JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。
 * @see: @JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")。
 * @see: @JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty("name")。
 */
@Slf4j
public final class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //设置NULL值不进行序列化
        //OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //空对象不抛异常 e.g. handler{ }
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //解析JSON时忽略未知属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private JsonUtil() {
    }

    /**
     * @param obj POJO
     * @param <T> 泛型
     * @return JSON
     */
    public static <T> String toJSON(T obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param text JSON
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T fromJSON(String text, Class<T> clzz) {
        try {
            return OBJECT_MAPPER.readValue(text, clzz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode fromJSON(String text) {
        try {
            return OBJECT_MAPPER.readTree(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJSON(String text, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(text, valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param src  JSON字节
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T fromByte(byte[] src, Class<T> clzz) {
        try {
            return OBJECT_MAPPER.readValue(src, OBJECT_MAPPER.constructType(clzz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param text JSON
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T parseObject(String text, Class<T> clzz) {
        try {
            return OBJECT_MAPPER.readValue(text, clzz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param map  Map
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clzz) {
        try {
            return OBJECT_MAPPER.convertValue(map, OBJECT_MAPPER.getTypeFactory().constructType(clzz));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param text JSON
     * @param clzz Class<?>
     * @param <T>  T
     * @return List
     */
    public static <T> List<T> fromJsonToList(String text, Class<T> clzz) {
        try {
            return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clzz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
