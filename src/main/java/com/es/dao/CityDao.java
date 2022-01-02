package com.es.dao;

import com.es.po.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created on 2021/12/12 10:26
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface CityDao {

    String fetchCityName(long id);

    City fetchCity(long id);

    Map<String, Object> fetchCityDetails(long id);

    // 供初始化使用
    @Deprecated
    int insertCities(@Param("cities") String[] cities, @Param("province_id") int id);
}
