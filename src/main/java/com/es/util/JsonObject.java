package com.es.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2021/12/17 13:24
 * 用于接收转化数据，生成Json字符串，并输出到流中
 * @author Marfack
 */
public class JsonObject {

    private final Map<String, Object> json = new HashMap<>();

    /**
     * 使用jackson工具转化Map为Json字符串
     * @see com.fasterxml.jackson.databind.ObjectMapper com.fasterxml.jackson.databind.ObjectMapper
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public JsonObject clear() {
        json.clear();
        return this;
    }

    public JsonObject put(String key, Object val) {
        json.put(key, val);
        return this;
    }

    public void writeJsonTo(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter pw = response.getWriter()) {
            pw.write(MAPPER.writeValueAsString(json));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String returnJson() throws JsonProcessingException {
        return MAPPER.writeValueAsString(json);
    }
}
