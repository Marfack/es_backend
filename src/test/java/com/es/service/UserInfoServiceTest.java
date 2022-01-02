package com.es.service;

import com.es.vo.RegisterVo;
import com.es.vo.ResetPasswordVo;
import com.es.vo.RetrievePasswordVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

/**
 * Created on 2021/12/17 16:33
 *
 * @author Marfack
 */
@SpringBootTest
public class UserInfoServiceTest {

    @Autowired
    UserInfoService userInfoService;

    @Test
    void mailVerifyTest() {
        System.out.println(userInfoService.mailVerify("marfack@outlook.com"));
    }

    @Test
    void register() throws JsonProcessingException, ExecutionException, InterruptedException {
        RegisterVo vo = new RegisterVo();
        vo.setUsername("Tester");
        vo.setPassword("Tester");
        vo.setTime(System.currentTimeMillis());
        System.out.println(userInfoService.register(vo).get());
    }

    @Test
    void resetPassword() throws JsonProcessingException, ExecutionException, InterruptedException {
        ResetPasswordVo vo = new ResetPasswordVo();
        vo.setOriginPsw("Tester");
        vo.setNewPsw("tester");
        System.out.println(userInfoService.resetPassword(vo, 36, "Tester").get());
    }

    @Test
    void retrievePasswordMailVerify() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(userInfoService.retrievePasswordMailVerify("marfack@outlook.com").get());
    }

    @Test
    void retrievePassword() throws JsonProcessingException, ExecutionException, InterruptedException {
        RetrievePasswordVo vo = new RetrievePasswordVo();
        vo.setEmail("marfack@outlook.com");
        vo.setPassword("test");
        System.out.println(userInfoService.retrievePassword(vo).get());
    }
}
