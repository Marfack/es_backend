package com.es.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 2021/12/13 18:34
 *
 * @author Marfack
 */
@SpringBootTest
public class CareerInfoDaoTest {
    @Autowired
    CareerInfoDao careerInfoDao;

    @Test
    void test() {
        System.out.println(careerInfoDao.fetchCareerInfo(321));
    }
}
