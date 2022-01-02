package com.es.dao;

import com.es.po.CareerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 18:00
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface CareerInfoDao {
    /***********************查询***********************/
    CareerInfo fetchCareerInfo(int id);

    List<CareerInfo> queryCareerInfo(int id);

    List<CareerInfo> queryCareerOfStaff(long id);

    /***********************插入***********************/
    @Deprecated
    int insertCareerInfos(@Param("names") String[] names, @Param("class") int classId);

    /***********************删除***********************/
    int clearCareerInfo(int id);
}
