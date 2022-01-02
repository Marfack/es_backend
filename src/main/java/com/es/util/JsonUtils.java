package com.es.util;


/**
 * Created on 2021/12/17 12:17
 * 用于接收数据并返回Json字符串
 * @author Marfack
 */
public final class JsonUtils {

    /**
     * 使用JsonObject接收键值对，生成Json。
     * @see com.es.util.JsonObject com.es.util.JsonObject
     */
    private static final JsonObject JSON_OBJECT = new JsonObject();

    private JsonUtils() {}

    public static JsonObject createJson() {
        return JSON_OBJECT.clear();
    }
}


