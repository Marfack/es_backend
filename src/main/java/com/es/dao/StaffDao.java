package com.es.dao;

import com.es.dto.StaffInTransactionDto;
import com.es.vo.StaffDetailsVo;
import com.es.vo.StaffInfoVo;
import com.es.po.Staff;
import com.es.vo.StaffRegisterVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 13:37
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface StaffDao {
    /***********************查询***********************/
    Staff fetchStaff(long id);

    List<StaffInfoVo> queryStaffOfCareer(@Param("id") int id, @Param("page") long page);

    List<StaffDetailsVo> queryStaffOfSearch(@Param("rid") int careerId, @Param("tid") int cityId, @Param("p") long page);

    StaffDetailsVo fetchStaffDetails(@Param("id") long id);

    List<StaffInTransactionDto> queryStaffInTransaction(long id);

    List<StaffInfoVo> queryStaffsOfUser(long id);

    List<StaffInfoVo> queryStaffsOfClassAndCity(@Param("classId") int classId, @Param("cityId") int cityId);

    /***********************插入***********************/
    int insertStaffObject(Staff staff);

    List<Long> insertStaffsVo(@Param("id") long id, @Param("staffs") List<StaffRegisterVo> vos);

    long insertStaffVoAndGetId(@Param("userId") long id, @Param("staff") StaffRegisterVo vo);

    /***********************修改***********************/
    int updateStaffObject(Staff staff);

    /***********************删除***********************/
    int clearStaff(long id);
}
