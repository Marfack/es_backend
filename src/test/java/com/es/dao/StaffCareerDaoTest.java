package com.es.dao;

import com.es.po.Staff;
import com.es.po.StaffCareer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/12/13 19:17
 *
 * @author Marfack
 */
@SpringBootTest
public class StaffCareerDaoTest {

    @Autowired
    StaffCareerDao staffCareerDao;

    @Autowired
    StaffDao staffDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    CareerInfoDao careerInfoDao;

    @Autowired
    ProviderDao providerDao;

    @Test
    @Transactional
    void test() {
        userInfoDao.insertUserInfo("1", "1");
        Object id = userInfoDao.getUserIdAndPasswordByNo("1").get("user_id");
        long id1 = id instanceof Long ? (Long) id : (Integer) id;
        Staff staff = new Staff();
        staff.setStaffName("a");
        staff.setStaffAge(18);
        staff.setStaffSex(1);
        staff.setUserId(id1);
        staff.setCityId(3);
        staffDao.insertStaffObject(staff);
        Staff staff1 = new Staff();
        staff1.setStaffName("b");
        staff1.setStaffAge(18);
        staff1.setStaffSex(1);
        staff1.setUserId(id1);
        staff1.setCityId(3);
        staffDao.insertStaffObject(staff1);
        long id2 = providerDao.queryStaffs(id1).get(0).getStaffId();
        long id3 = providerDao.queryStaffs(id1).get(1).getStaffId();
        List<StaffCareer> ls = new ArrayList<>();
        StaffCareer sc = new StaffCareer();
        sc.setStaffId(id2);
        sc.setCareerId(10);
        ls.add(sc);
        StaffCareer sc1 = new StaffCareer();
        sc1.setStaffId(id2);
        sc1.setCareerId(13);
        ls.add(sc1);
        StaffCareer sc2 = new StaffCareer();
        sc2.setStaffId(id3);
        sc2.setCareerId(32);
        ls.add(sc2);
        StaffCareer sc3 = new StaffCareer();
        sc3.setStaffId(id3);
        sc3.setCareerId(108);
        ls.add(sc3);
        staffCareerDao.insertStaffCareerObjects(ls);
        System.out.println(staffCareerDao.queryCareersOfStaff(id2));
        System.out.println();
        System.out.println(staffCareerDao.queryStaffsOfCareer(108));
        System.out.println();
        staffCareerDao.removeStaffCareers(ls);
    }
}
