package com.es.dao;

import com.es.po.City;
import com.es.po.Province;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/12 10:11
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface ProvinceDao {
    String fetchProvinceName(int id);

    List<City> queryCities(int id);

    Province fetchProvince(int id);

    List<Province> queryProvinces();

    // 初始化数据库使用
    @Deprecated
    int insertProvinces(String[] provinces);
}
