package com.es.dao;

import com.es.po.UserDetails;
import com.es.vo.UserDetailsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/12 20:51
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface UserDetailsDao {

    /***********************查询***********************/
    UserDetails fetchByUserId(long id);

    List<UserDetails> queryByCityId(int id);

    Long fetchUserIdByEmail(String email);

    String getUsername(long id);

    UserDetailsVo fetchUserDetails(long id);

    /***********************插入***********************/
    int insertUserDetails(@Param("user_id") long userId,
                          @Param("city_id") int cityId,
                          @Param("business_no") String businessNo,
                          @Param("user_name") String userName,
                          @Param("user_phone_no") int userPhoneNo,
                          @Param("user_email") String userEmail);

    int insertUserDetailsObject(UserDetails userDetails);

    /***********************修改***********************/
    int updateUserDetails(UserDetails details);

    /***********************删除***********************/
    int clearUserDetails(long id);
}
