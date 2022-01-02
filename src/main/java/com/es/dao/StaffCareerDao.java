package com.es.dao;

import com.es.po.CareerInfo;
import com.es.po.Staff;
import com.es.po.StaffCareer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 18:42
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface StaffCareerDao {
    /***********************查询***********************/
    List<CareerInfo> queryCareersOfStaff(long id);

    List<Staff> queryStaffsOfCareer(int id);

    /***********************插入***********************/
    int insertStaffCareerObject(StaffCareer staffCareer);

    int insertStaffCareerObjects(List<StaffCareer> staffCareers);

    int insertStaffs(@Param("careers") List<Integer> careers, @Param("id") long id);

    /***********************删除***********************/
    int clearStaffCareer(StaffCareer staffCareer);

    int removeStaffCareers(List<StaffCareer> staffCareers);

    int removeCareersOfStaff(long staffId);
}
