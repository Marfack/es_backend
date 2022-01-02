package com.es.dao;

import com.es.enums.Sex;
import com.es.enums.TransactionStatus;
import com.es.po.Staff;
import com.es.po.TransactionStaff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/12/13 22:26
 *
 * @author Marfack
 */
@SpringBootTest
public class TransactionStaffDaoTest {

    @Autowired
    TransactionStaffDao transactionStaffDao;

    @Autowired
    TransactionInfoDao transactionInfoDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    StaffDao staffDao;

    @Autowired
    ProviderDao providerDao;

    @Test
    @Transactional
    void test() {
        userInfoDao.insertUserInfo("1", "1");
        userInfoDao.insertUserInfo("2", "2");
        Object id1 = userInfoDao.getUserIdAndPasswordByNo("1").get("user_id");
        Object id2 = userInfoDao.getUserIdAndPasswordByNo("2").get("user_id");
        long buyer = id1 instanceof Long ? (long) id1 : (int) id1;
        long seller = id2 instanceof Long ? (long) id2 : (int) id2;
        transactionInfoDao.insertTransactionInfo(buyer, TransactionStatus.WORKING.status);
        Staff staff = new Staff();
        staff.setStaffName("a");
        staff.setUserId(buyer);
        staff.setCityId(123);
        staff.setStaffAge(Sex.MALE.sex);
        staffDao.insertStaffObject(staff);
        long id = providerDao.queryStaffs(buyer).get(0).getStaffId();
        long id3 = providerDao.queryTransactionInfo(seller).get(0).getTransactionId();
        TransactionStaff transactionStaff = new TransactionStaff();
        transactionStaff.setStaffId(id);
        transactionStaff.setTransactionId(id3);
        transactionStaffDao.insertTransactionStaff(transactionStaff);
        System.out.println(transactionStaffDao.queryStaffOfTransaction(id3));
        System.out.println(transactionStaffDao.queryTransactionOfStaff(id));
        List<TransactionStaff> ls = new ArrayList<>();
        ls.add(transactionStaff);
        transactionStaffDao.removeTransactionStaffs(ls);
        System.out.println(transactionStaffDao.queryTransactionOfStaff(32));
    }
}
