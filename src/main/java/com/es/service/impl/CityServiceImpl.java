package com.es.service.impl;

import com.es.dao.ProvinceDao;
import com.es.enums.ResponseCode;
import com.es.po.City;
import com.es.po.Province;
import com.es.service.CityService;
import com.es.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created on 2021/12/26 20:53
 *
 * @author Marfack
 */
@Async
@Service
public class CityServiceImpl implements CityService {

    @Resource
    private ProvinceDao provinceDao;

    @Override
    public Future<String> getProvinces() throws JsonProcessingException {
        List<Province> provinces = provinceDao.queryProvinces();
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", provinces)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Override
    public Future<String> getCities(int id) throws JsonProcessingException {
        Province p = provinceDao.fetchProvince(id);
        if (p == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        List<City> cities = provinceDao.queryCities(id);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", cities)
                .returnJson();
        return new AsyncResult<>(res);
    }
}
