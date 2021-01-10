package com.saint.base.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-10 9:40
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    static {
        ParserConfig.getGlobalInstance().setSafeMode(true);
    }

    /**
     * GSON构建器
     */
    private static final Gson GSON = new Gson();

    /**
     * FastJson在复杂类型Bean转JSONString时会出现一些问题，比如：会出现引用的类型导致JSON出错，需要指定引用。
     * 而Gson完全可以做到将复杂类型的json到bean 或 bean到json的转换。
     * 所以使用Gson将bean转换为JSONString，以确保数据的正确。
     *
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * 首先使用最快的JSON库fastJSON，以获取最快的速度。
     * Gson在反序列化int/long等类型数据时，会将其反序列化为double类型(0 ->0.0)
     *
     * @param json  JSONString
     * @param clazz 类型的class对象
     * @param <T>   Class类型
     * @return
     */
    public static <T> T fromJsonString(String json, Class<T> clazz) {
        try {
            return JSONObject.parseObject(json, clazz);
        } catch (Exception e) {
            return GSON.fromJson(json, clazz);
        }
    }

    /**
     * 将Json字符串转换为指定类型的对象
     */
    public static <T> T fromJsonString(String json, Type type) {
        try {
            return JSONObject.parseObject(json, type);
        } catch (Exception e) {
            return GSON.fromJson(json, type);
        }
    }

    /**
     * 将任意类型的对象转换为Map对象
     */
    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return fromJsonString(toJsonString(obj), Map.class);
    }

    /**
     * Map类型转任意Class类型
     */
    public static <T> T fromMap(Map map, Class<T> clazz) {
        if (null == map) {
            return null;
        }
        return fromJsonString(toJsonString(map), clazz);
    }


}
