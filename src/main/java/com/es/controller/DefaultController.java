package com.es.controller;

import com.es.po.UserDetails;
import com.es.service.CareerService;
import com.es.service.CityService;
import com.es.service.UserInfoService;
import com.es.util.JwtUtils;
import com.es.vo.RegisterVo;
import com.es.vo.ResetPasswordVo;
import com.es.vo.RetrievePasswordVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * Created on 2021/12/8 22:22
 * 处理default权限请求
 * @author Marfack
 */
@RestController
@RequestMapping("/api")
public class DefaultController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private CareerService careerService;

    @Resource
    private CityService cityService;

    @PostMapping("/verify/{email}")
    public String verify(@PathVariable("email") String email) throws ExecutionException, InterruptedException {
        return userInfoService.mailVerify(email).get();
    }

    @PostMapping("/register")
    public String register(RegisterVo registerVo) throws JsonProcessingException, ExecutionException, InterruptedException {
        return userInfoService.register(registerVo).get();
    }

    @PostMapping("/retrieve_verify/{email}")
    public String retrieveVerify(@PathVariable("email") String email) throws JsonProcessingException, ExecutionException, InterruptedException {
        return userInfoService.retrievePasswordMailVerify(email).get();
    }

    @PostMapping("/retrieve_password")
    public String retrievePassword(RetrievePasswordVo vo) throws JsonProcessingException, ExecutionException, InterruptedException {
        return userInfoService.retrievePassword(vo).get();
    }

    @PostMapping("/career_classes")
    public String careerClasses() throws JsonProcessingException, ExecutionException, InterruptedException {
        return careerService.getCareerClasses().get();
    }

    @PostMapping("/career_list/{classId}")
    public String careerList(@PathVariable("classId") int id) throws JsonProcessingException, ExecutionException, InterruptedException {
        return careerService.getCareerList(id).get();
    }

    @PostMapping("/staff_list/career{careerId}/page{page}")
    public String staffList(@PathVariable("careerId") int id, @PathVariable("page") long page) throws JsonProcessingException, ExecutionException, InterruptedException {
        return careerService.getStaffList(id, page).get();
    }

    @PostMapping("/reset_password")
    public String resetPassword(ResetPasswordVo vo, HttpServletRequest request) throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return userInfoService.resetPassword(vo, JwtUtils.getClaim(token, JwtUtils.ID).asLong(),
                JwtUtils.getClaim(token, JwtUtils.PASSWORD).asString()).get();
    }

    @PostMapping("/complete_info")
    public String completeInfo(UserDetails details, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        details.setUserId(JwtUtils.getClaim(token, JwtUtils.ID).asLong());
        return userInfoService.completeInfo(details).get();
    }

    @PostMapping("/alter_info")
    public String alterInfo(UserDetails details, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        details.setUserId(JwtUtils.getClaim(token, JwtUtils.ID).asLong());
        return userInfoService.alterInfo(details).get();
    }

    @PostMapping("/get_name")
    public String getName(HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return userInfoService.getUserName(JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/verify")
    public String verify(long id, String psw)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        return userInfoService.verify(id, psw).get();
    }

    @PostMapping("/get_provinces")
    public String getProvinces() throws JsonProcessingException, ExecutionException, InterruptedException {
        return cityService.getProvinces().get();
    }

    @PostMapping("/get_cities/province{id}")
    public String getCities(@PathVariable("id") int id)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        return cityService.getCities(id).get();
    }

    @PostMapping("/staff_list/career{careerId}/city{cityId}/page{page}")
    public String staffList(@PathVariable("careerId") int careerId,
                            @PathVariable("cityId") int cityId,
                            @PathVariable("page") long page)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        return careerService.getStaffList(careerId, cityId, page).get();
    }
}
