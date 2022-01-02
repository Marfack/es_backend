package com.es.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2021/12/12 20:36
 *
 * @author Marfack
 */
@SpringBootTest
public class UserAccountDaoTest {
    @Autowired
    UserAccountDao userAccountDao;

    @Test
    @Transactional
    void test() {
        System.out.println(userAccountDao.insertUserAccount(1));
        System.out.println(userAccountDao.fetchAccountBalance(1));
        System.out.println(userAccountDao.fetchFreezingMoney(1));
        System.out.println(userAccountDao.updateUserAccount(1, 3, 2));
        System.out.println(userAccountDao.fetchAccountBalance(1));
        System.out.println(userAccountDao.clearUserAccount(1));
    }
}
