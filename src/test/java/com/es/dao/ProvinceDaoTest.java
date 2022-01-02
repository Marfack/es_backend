package com.es.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created on 2021/12/12 10:16
 *
 * @author Marfack
 */
@SpringBootTest
public class ProvinceDaoTest {

    @Autowired
    ProvinceDao provinceDao;

    @Test
    void test() {
        System.out.println(provinceDao.fetchProvince(1));
        System.out.println(provinceDao.fetchProvinceName(34));
        System.out.println(provinceDao.queryCities(20));
    }


    /**
     * 初始化省份数据表使用
     */
    @Test
    @Transactional
    @Rollback(false)
    void init() {
        String[] provinces = "北京市 广东省 山东省 江苏省 河南省 上海市 河北省 浙江省 香港特别行政区 陕西省 湖南省 重庆市 福建省 天津市 云南省 四川省 广西壮族自治区 安徽省 海南省 江西省 湖北省 山西省 辽宁省 台湾省 黑龙江 内蒙古自治区 澳门特别行政区 贵州省 甘肃省 青海省 新疆维吾尔自治区 西藏区 吉林省 宁夏回族自治区"
                .split("\\s+");
        provinceDao.insertProvinces(provinces);
    }
}
