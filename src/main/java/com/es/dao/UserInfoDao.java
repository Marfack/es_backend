package com.es.dao;

import com.es.po.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created on 2021/12/12 1:05
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface UserInfoDao {

    /*****************查询****************/
    UserInfo fetchUserInfoById(long id);

    Map<String, Object> getUserIdAndPasswordByNo(String userNo);

    int fetchUserClass(long id);

    UserInfo fetchUserByNo(String no);

    /*****************插入****************/

    int insertUserInfo(@Param("userNo") String userNo, @Param("password") String password);

    /*****************修改****************/

    int updatePassword(@Param("id") long id, @Param("password") String password);

    int updateUserStatus(@Param("id") long id, @Param("status") int status);

    int updateLoginTime(@Param("id") long id, @Param("time") Timestamp time);

    /*****************删除****************/

    int clearUserInfo(long id);
}
