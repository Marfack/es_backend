package com.es.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.Future;

/**
 * Created on 2021/12/26 20:51
 * 地理位置相关业务服务
 * @author Marfack
 */
public interface CityService {

    /**
     * 获取所有省
     * @return 所有省
     * @throws JsonProcessingException Json字符串转换异常
     */
    Future<String> getProvinces() throws JsonProcessingException;

    /**
     * 获取某一个省的所有城市
     * @param id 省id
     * @return 城市
     * @throws JsonProcessingException Json字符串转换异常
     */
    Future<String> getCities(int id) throws JsonProcessingException;
}
