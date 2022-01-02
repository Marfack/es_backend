package com.es.dao;

import com.es.po.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2021/12/13 13:02
 *
 * @author Marfack
 */
@SpringBootTest
public class ProviderDaoTest {
    @Autowired
    ProviderDao providerDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Test
    @Transactional
    void test() {
        userInfoDao.insertUserInfo("1", "1");
        Object id = userInfoDao.getUserIdAndPasswordByNo("1").get("user_id");
        long id1 = id instanceof Long ? (Long) id : (Integer) id;
        Provider provider = new Provider();
        provider.setUserId(id1);
        providerDao.insertProviderObject(provider);
        System.out.println();
        System.out.println(providerDao.fetchProvider(id1));
        System.out.println();
        provider.setProviderDescription("Good Company");
        providerDao.updateProviderObject(provider);
        System.out.println();
        System.out.println(providerDao.fetchProvider(id1));
        System.out.println();
        providerDao.clearProvider(id1);
        userInfoDao.clearUserInfo(id1);
    }
}
