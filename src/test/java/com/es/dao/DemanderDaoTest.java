package com.es.dao;

import com.es.po.Demander;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2021/12/13 12:15
 *
 * @author Marfack
 */
@SpringBootTest
public class DemanderDaoTest {
    @Autowired
    DemanderDao demanderDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Test
    @Transactional
    void test() {
        userInfoDao.insertUserInfo("1", "1");
        Object id = userInfoDao.getUserIdAndPasswordByNo("1").get("user_id");
        long id1 = id instanceof Long ? (Long) id : (Integer) id;
        Demander demander = new Demander();
        demander.setUserId(id1);
        demanderDao.insertDemanderObject(demander);
        System.out.println(demanderDao.fetchDemander(id1));
        demander.setEnterpriseDescription("This is a Super Enterprise created by Marfack");
        demanderDao.updateDemanderObject(demander);
        System.out.println(demanderDao.fetchDemander(id1));
        demanderDao.clearDemander(id1);
        userInfoDao.clearUserInfo(id1);
    }
}
