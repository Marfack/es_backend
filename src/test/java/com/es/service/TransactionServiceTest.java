package com.es.service;

import com.es.enums.PaymentEnum;
import com.es.vo.StaffRegisterVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created on 2021/12/21 20:05
 *
 * @author Marfack
 */
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void recharge() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(transactionService.recharge(3, 88, PaymentEnum.ONLINE_BANKING).get());
    }

    @Test
    void withdraw() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(transactionService.withdraw(33, 1.2, PaymentEnum.ALIPAY).get());
    }

    @Test
    void pay() throws JsonProcessingException, ExecutionException, InterruptedException {
        ArrayList<Long> ls = new ArrayList<>();
        ls.add(32L);
        ls.add(33L);
        System.out.println(transactionService.pay(31, ls, 30000).get());
    }

    @Test
    void complete() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(transactionService.complete(31, 16).get());
    }

    @Test
    void refund() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(transactionService.refund(31, 17, 33).get());
    }

    @Test
    void checkOrders() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(transactionService.checkOrders(31).get());
    }

    @Test
    void checkOrderDetails() throws JsonProcessingException, ExecutionException, InterruptedException {
        System.out.println(transactionService.checkOrderDetails(31, 7).get());
    }

    @Test
    void provideStaffList() {
//        StaffRegisterVo vo1 = new StaffRegisterVo();
//        vo1.set
//        transactionService.provideStaffList()
    }
}
