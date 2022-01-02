package com.es.service.impl;

import com.es.dao.*;
import com.es.dto.RecommendDto;
import com.es.po.*;
import com.es.vo.StaffDetailsVo;
import com.es.vo.StaffInfoVo;
import com.es.enums.ResponseCode;
import com.es.service.CareerService;
import com.es.util.JsonUtils;
import com.es.vo.StaffRegisterVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created on 2021/12/18 15:49
 * 查询业务实现类，该类全部方法均为异步查询。
 * @author Marfack
 */
@Async
@Service
public class CareerServiceImpl implements CareerService {

    @Resource
    CareerClassDao careerClassDao;

    @Resource
    CareerInfoDao careerInfoDao;

    @Resource
    StaffDao staffDao;

    @Resource
    StaffCareerDao staffCareerDao;

    @Resource
    ProviderDao providerDao;

    @Override
    public Future<String> getCareerClasses() throws JsonProcessingException {
        List<CareerClass> classes = careerClassDao.queryCareerClasses();
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", classes)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Override
    public Future<String> getCareerList(int classId) throws JsonProcessingException {
        List<CareerInfo> info = careerInfoDao.queryCareerInfo(classId);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", info)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Override
    public Future<String> getStaffList(int careerId, long page) throws JsonProcessingException {
        int tipsPerPage = 10;
        List<StaffInfoVo> staffs = staffDao.queryStaffOfCareer(careerId, (page - 1) * tipsPerPage);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", staffs)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Override
    public Future<String> getStaffDetails(long staffId) throws JsonProcessingException {
        StaffDetailsVo staffDetails = staffDao.fetchStaffDetails(staffId);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", staffDetails)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> getStaffs(long userId) throws JsonProcessingException {
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", staffDao.queryStaffsOfUser(userId))
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> updateStaff(StaffRegisterVo vo, long userId) throws JsonProcessingException {
        Staff staff = new Staff();
        staff.setStaffId(vo.getStaffId());
        staff.setStaffAge(vo.getStaffAge());
        staff.setStaffSex(vo.getStaffSex());
        staff.setStaffName(vo.getStaffName());
        staff.setExpectedSalary(vo.getExpectedSalary());
        staff.setCityId(vo.getCityId());
        staff.setUserId(userId);
        staff.setStaffDescription(vo.getStaffDescription());
        staff.setStaffStatus(vo.getStatus());
        staffDao.updateStaffObject(staff);
        if (vo.getCareers() == null || vo.getCareers().isEmpty()) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.SUCCESS));
        }
        List<CareerInfo> careerInfos = staffCareerDao.queryCareersOfStaff(staff.getStaffId());
        System.out.println(careerInfos);
        List<StaffCareer> ls = new ArrayList<>();
        careerInfos.forEach(e ->  {
            StaffCareer staffCareer = new StaffCareer();
            staffCareer.setStaffId(staff.getStaffId());
            staffCareer.setCareerId(e.getCareerId());
            ls.add(staffCareer);
        });
        staffCareerDao.removeStaffCareers(ls);
        ls.clear();
        vo.getCareers().forEach(e -> {
            StaffCareer staffCareer = new StaffCareer();
            staffCareer.setCareerId(e);
            staffCareer.setStaffId(staff.getStaffId());
            ls.add(staffCareer);
        });
        staffCareerDao.insertStaffCareerObjects(ls);
        return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> removeStaff(long staffId) throws JsonProcessingException {
        Staff staff = staffDao.fetchStaff(staffId);
        staff.setStaffStatus(2);
        staffDao.updateStaffObject(staff);
        return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.SUCCESS));
    }

    @Override
    public Future<String> getStaffList(int careerId, int cityId, long page) throws JsonProcessingException {
        int tipsPerPage = 10;
        List<StaffDetailsVo> staffs = staffDao.queryStaffOfSearch(careerId, cityId, (page - 1) * tipsPerPage);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", staffs)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Override
    public Future<String> recommend(int classId, int cityId) throws JsonProcessingException {
        List<StaffInfoVo> staffInfoVos = staffDao.queryStaffsOfClassAndCity(classId, cityId);
        List<CareerInfo> infos = careerClassDao.queryCareers(classId);
        Map<Integer, StaffInfoVo> staffs = new HashMap<>(infos.size());
        Map<Integer, Double> v = new HashMap<>(infos.size());
        infos.forEach(e -> v.put(e.getCareerId(), 0.0));
        for (StaffInfoVo staffInfoVo : staffInfoVos) {
            List<CareerInfo> careers = staffInfoVo.getCareers();
            Provider provider = providerDao.fetchProvider(staffInfoVo.getEnterpriseId());
            double score = 0;
            if (provider.getProvideTimes() != 0) {
                score = provider.getTotalScore() / provider.getProvideTimes();
            }
            double finalScore = score;
            for (CareerInfo k : careers) {
                if (v.containsKey(k.getCareerId())) {
                    if (staffs.containsKey(k.getCareerId())) {
                        if (finalScore > v.get(k.getCareerId())) {
                            v.put(k.getCareerId(), finalScore);
                            staffs.put(k.getCareerId(), staffInfoVo);
                            break;
                        }
                    } else {
                        if (!staffs.containsKey(k.getCareerId())) {
                            v.put(k.getCareerId(), finalScore);
                            staffs.put(k.getCareerId(), staffInfoVo);
                            break;
                        }
                    }
                }
            }
        }
        List<RecommendDto> finalStaffs = new ArrayList<>();
        for (Map.Entry<Integer, StaffInfoVo> entry : staffs.entrySet()) {
            RecommendDto recommendDto = new RecommendDto(entry.getValue().getStaffId(), entry.getValue().getStaffName(),
                    entry.getValue().getEnterpriseId(), entry.getValue().getEnterpriseName(),
                    entry.getKey(), careerInfoDao.fetchCareerInfo(entry.getKey()).getCareerName(),
                    entry.getValue().getExpectedSalary());
            finalStaffs.add(recommendDto);
        }
        String res = JsonUtils.createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", finalStaffs)
                .returnJson();
        return new AsyncResult<>(res);
    }
}
