package com.es.dao;

import com.es.po.CareerClass;
import com.es.po.CareerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 15:12
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface CareerClassDao {
    /***********************查询***********************/
    CareerClass fetchCareerClass(int id);

    List<CareerClass> queryCareerClasses();

    List<CareerInfo> queryCareers(int id);

    /***********************插入***********************/
    @Deprecated
    int insertCareerClass(String name);

    /***********************删除***********************/
    int clearCareerClass(int id);
}
