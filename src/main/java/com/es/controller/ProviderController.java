package com.es.controller;

import com.es.service.CareerService;
import com.es.service.TransactionService;
import com.es.util.JwtUtils;
import com.es.vo.StaffRegisterVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * Created on 2021/12/17 13:58
 * 处理provider权限请求
 * @author Marfack
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    TransactionService transactionService;

    @Resource
    CareerService careerService;

    @PostMapping("/provide_staffs")
    public String provideStaffs(@RequestBody StaffRegisterVo vo, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.provideStaffList(vo, JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/get_staffs")
    public String getStaffs(HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return careerService.getStaffs(JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/update_staff")
    public String updateStaff(@RequestBody StaffRegisterVo vo, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return careerService.updateStaff(vo, JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/remove_staff/staff{id}")
    public String removeStaff(@PathVariable("id") long id) throws JsonProcessingException, ExecutionException, InterruptedException {
        return careerService.removeStaff(id).get();
    }
}
