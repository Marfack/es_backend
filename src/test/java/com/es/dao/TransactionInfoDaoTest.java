package com.es.dao;

import com.es.enums.TransactionStatus;
import com.es.po.TransactionInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2021/12/13 20:24
 *
 * @author Marfack
 */
@SpringBootTest
public class TransactionInfoDaoTest {
    @Autowired
    TransactionInfoDao transactionInfoDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    ProviderDao providerDao;

    @Test
    @Transactional
    void test() {
        userInfoDao.insertUserInfo("1", "1");
        userInfoDao.insertUserInfo("2", "2");
        Object id1 = userInfoDao.getUserIdAndPasswordByNo("1").get("user_id");
        Object id2 = userInfoDao.getUserIdAndPasswordByNo("2").get("user_id");
        long buyer = id1 instanceof Long ? (Long) id1 : (Integer) id1;
        long seller = id2 instanceof Long ? (Long) id2 : (Integer) id2;
        transactionInfoDao.insertTransactionInfo(buyer, TransactionStatus.WORKING.status);
        long id = providerDao.queryTransactionInfo(seller).get(0).getTransactionId();
        System.out.println(transactionInfoDao.fetchTransactionInfo(id));
        System.out.println();
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setTransactionId(id);
        transactionInfo.setBuyerId(buyer);
        transactionInfo.setTransactionStatus(TransactionStatus.COMPLETED.status);
        transactionInfoDao.updateTransactionInfoObject(transactionInfo);
        System.out.println(transactionInfoDao.fetchTransactionInfo(id));
        System.out.println();
        transactionInfoDao.clearTransactionInfo(id);
    }
}
