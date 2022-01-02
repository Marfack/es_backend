package com.es.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

/**
 * Created on 2021/12/18 16:00
 *
 * @author Marfack
 */
@SpringBootTest
public class CareerServiceTest {
    @Autowired
    CareerService careerService;

    @Test
    void getCareerClasses() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(careerService.getCareerClasses().get());
    }

    @Test
    void getCareerInfo() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(careerService.getCareerList(3).get());
    }

    @Test
    void getStaffList() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(careerService.getStaffList(2, 1L).get());
    }

    @Test
    void getStaffDetails() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(careerService.getStaffDetails(32).get());
    }
}
