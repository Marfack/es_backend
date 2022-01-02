package com.es.controller;

import com.es.enums.PaymentEnum;
import com.es.service.CareerService;
import com.es.service.TransactionService;
import com.es.service.UserInfoService;
import com.es.util.JwtUtils;
import com.es.vo.PayVo;
import com.es.vo.RechargeOrWithDrawVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * Created on 2021/12/17 13:57
 * 处理demander权限的请求
 *
 * @author Marfack
 */
@RestController
@RequestMapping("/demander")
public class DemanderController {

    @Resource
    private CareerService careerService;

    @Resource
    private TransactionService transactionService;

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/career_details/staff{staffId}")
    public String careerDetails(@PathVariable("staffId") long id)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        return careerService.getStaffDetails(id).get();
    }

    @PostMapping("/recharge")
    public String recharge(RechargeOrWithDrawVo vo, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.recharge(JwtUtils.getClaim(token, JwtUtils.ID).asLong(),
                vo.getAmount(), payment(vo.getPayment())).get();
    }

    @PostMapping("/withdraw")
    public String withdraw(RechargeOrWithDrawVo vo, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.withdraw(JwtUtils.getClaim(token, JwtUtils.ID).asLong(),
                vo.getAmount(), payment(vo.getPayment())).get();
    }

    private PaymentEnum payment(int payment) {
        switch (payment) {
            case 0:
                return PaymentEnum.ALIPAY;
            case 1:
                return PaymentEnum.WE_CHAT;
            case 2:
                return PaymentEnum.ONLINE_BANKING;
            case 3:
                return PaymentEnum.PAY_PAL;
            default:
                throw new EnumConstantNotPresentException(PaymentEnum.class, "Undefined Enum");
        }
    }

    @PostMapping("/pay")
    public String pay(@RequestBody PayVo vo, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.pay(JwtUtils.getClaim(token, JwtUtils.ID).asLong(),
                vo.getStaffs(), vo.getAmount()).get();
    }

    @PostMapping("/complete/transaction{transactionId}")
    public String complete(@PathVariable("transactionId") long transactionId, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.complete(JwtUtils.getClaim(token, JwtUtils.ID).asLong(), transactionId).get();
    }

    @PostMapping("/refund/transaction{transactionId}/staff{staffId}")
    public String refund(@PathVariable("transactionId") long transactionId,
                         @PathVariable("staffId") long staffId, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.refund(JwtUtils.getClaim(token, JwtUtils.ID).asLong(),
                transactionId, staffId).get();
    }

    @PostMapping("/checkOrders")
    public String checkOrders(HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.checkOrders(JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/checkOrderDetails/transaction{transactionId}")
    public String checkOrderDetails(@PathVariable("transactionId") long transactionId, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.checkOrderDetails(JwtUtils.getClaim(token, JwtUtils.ID).asLong(),
                transactionId).get();
    }

    @PostMapping("/becomeProvider")
    public String becomeProvider(HttpServletRequest request) throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return userInfoService.becomeProvider(JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/get_user_info")
    public String getUserInfo(HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return userInfoService.getUserInfo(JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/get_transaction_data")
    public String getTransactionData(HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.getTransactionData(JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/get_wallet_data")
    public String getWalletData(HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        String token = request.getHeader("Token");
        return transactionService.getWalletVo(JwtUtils.getClaim(token, JwtUtils.ID).asLong()).get();
    }

    @PostMapping("/get_name/{id}")
    public String getName(@PathVariable("id") long id)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        return userInfoService.getUserName(id).get();
    }

    @PostMapping("/recommend")
    public String recommend(int classId, int cityId)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        return careerService.recommend(classId, cityId).get();
    }

}
