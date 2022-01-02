package com.es.dao;

import com.es.po.UserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2021/12/12 21:23
 *
 * @author Marfack
 */
@SpringBootTest
public class UserDetailsDaoTest {
    @Autowired
    UserDetailsDao userDetailsDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Test
    @Transactional
    void test() {
        userInfoDao.insertUserInfo("1", "1");
        UserDetails details = new UserDetails();
        details.setUserEmail("123@123.com");
        Object id = userInfoDao.getUserIdAndPasswordByNo("1").get("user_id");
        long id1;
        if (id instanceof Long) {
            id1 = (Long) id;
        } else {
            id1 = (Integer) id;
        }
        details.setUserId(id1);
        details.setCityId(1);
        details.setBusinessNo("123");
        details.setUserName("abc");
        userDetailsDao.insertUserDetailsObject(details);
        System.out.println(userDetailsDao.fetchByUserId(id1));
        details.setUserEmail("abc@123.com");
        userDetailsDao.updateUserDetails(details);
        System.out.println(userDetailsDao.fetchByUserId(id1));
        userDetailsDao.clearUserDetails(id1);
        userInfoDao.clearUserInfo(id1);
    }
}
