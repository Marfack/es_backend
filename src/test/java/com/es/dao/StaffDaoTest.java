package com.es.dao;

import com.es.po.Staff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 2021/12/13 13:51
 *
 * @author Marfack
 */
@SpringBootTest
public class StaffDaoTest {
    @Autowired
    StaffDao staffDao;

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
        Staff staff = new Staff();
        staff.setUserId(id1);
        staff.setCityId(1);
        staff.setStaffName("Bob");
        staff.setStaffAge(23);
        staff.setStaffSex(1);
        staffDao.insertStaffObject(staff);
        System.out.println();
        System.out.println(staffDao.fetchStaff(providerDao.queryStaffs(id1).get(0).getStaffId()));
        System.out.println();
        staff.setStaffId(providerDao.queryStaffs(id1).get(0).getStaffId());
        staff.setStaffName("Bobby");
        staffDao.updateStaffObject(staff);
        System.out.println();
        System.out.println(staffDao.fetchStaff(providerDao.queryStaffs(id1).get(0).getStaffId()));
        System.out.println();
        staffDao.clearStaff(providerDao.queryStaffs(id1).get(0).getStaffId());
        userInfoDao.clearUserInfo(id1);
        System.out.println(staffDao.queryStaffOfCareer(2, 0));
        System.out.println(staffDao.fetchStaffDetails(32));
    }
}
