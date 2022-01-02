package com.es.dao;

import com.es.enums.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 2021/12/13 20:58
 *
 * @author Marfack
 */
@SpringBootTest
public class TransactionDetailsDaoTest {
    @Autowired
    TransactionDetailsDao transactionDetailsDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    ProviderDao providerDao;

    @Autowired
    TransactionInfoDao transactionInfoDao;

    @Test
    void test() {
//        userInfoDao.insertUserInfo("1", "1");
//        userInfoDao.insertUserInfo("2", "2");
//        Object id1 = userInfoDao.getUserIdAndPasswordByNo("1").get("user_id");
//        Object id2 = userInfoDao.getUserIdAndPasswordByNo("2").get("user_id");
//        long buyer = id1 instanceof Long ? (Long) id1 : (Integer) id1;
//        long seller = id2 instanceof Long ? (Long) id2 : (Integer) id2;
//        transactionInfoDao.insertTransactionInfo(buyer, seller, TransactionStatus.WORKING.status);
//        long id = providerDao.queryTransactionInfo(seller).get(0).getTransactionId();
//        transactionDetailsDao.insertTransactionDetails(id, 999.99);
//        System.out.println(transactionDetailsDao.fetchTransaction(id));
//        System.out.println();
        System.out.println(transactionDetailsDao.fetchTransaction(7));
    }
}
