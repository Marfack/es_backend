package com.es.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created on 2021/12/12 1:19
 *
 * @author Marfack
 */
@SpringBootTest
public class UserInfoDaoTest {

    @Resource
    UserInfoDao userInfoDao;

    @Test
    @Transactional
    public void test() {
//        System.out.println(userInfoDao.insertUserInfo("1", "1"));
//        System.out.println(userInfoDao.fetchUserInfoById(1L));
        System.out.println(userInfoDao.getUserIdAndPasswordByNo("1"));
//        System.out.println(userInfoDao.clearUserInfo(2));
//        System.out.println(userInfoDao.fetchUserClass(1));
    }
}
