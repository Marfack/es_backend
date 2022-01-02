package com.es.service;

import com.es.enums.PaymentEnum;
import com.es.vo.StaffRegisterVo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created on 2021/12/20 14:03
 * 交易业务处理接口
 * @author Marfack
 */
public interface TransactionService {

    /**
     * 充值业务
     * @param id 充值用户id
     * @param amount 充值金额
     * @param payment 支付方式PaymentEnum枚举类
     * @return 充值是否成功，若成功返回用户账户当前余额
     * @throws JsonProcessingException Json字符串转化异常
     */
    Future<String> recharge(long id, double amount, PaymentEnum payment) throws JsonProcessingException;

    /**
     * 提现业务
     * @param id 用户id
     * @param amount 提现金额
     * @param payment 提现到账户
     * @return 是否成功，若成功，则返回用户当前账户余额
     * @throws JsonProcessingException Json字符串转化异常
     */
    Future<String> withdraw(long id, double amount, PaymentEnum payment) throws JsonProcessingException;

    /**
     * 需求方签订员工并扣除薪酬
     * @param id 用户id
     * @param staffs 签订员工id列表
     * @param amount 总金额
     * @return 返回是否支付成功
     * @throws JsonProcessingException Json字符串转化异常
     */
    Future<String> pay(long id, List<Long> staffs, double amount) throws JsonProcessingException;

    /**
     * 用户确定订单完成，将冻结资金转到供给方账户
     * @param userId 用户id
     * @param transactionId 交易订单id
     * @return 返回是否成功
     * @throws JsonProcessingException Json字符串转化异常
     */
    Future<String> complete(long userId, long transactionId) throws JsonProcessingException;

    /**
     * 用户退单，将冻结资金一部分返回原账户，剩余清空
     * @param userId 用户id
     * @param transactionId 交易订单id
     * @param staffId 退单员工id
     * @return 返回是否成功，若成功，返回当前余额
     * @throws JsonProcessingException Json字符串转化异常
     */
    Future<String> refund(long userId, long transactionId, long staffId) throws JsonProcessingException;

    /**
     * 查看所有订单
     * @param id 用户id
     * @return 返回状态码以及订单信息
     * @throws JsonProcessingException Json字符串转化异常
     */
    Future<String> checkOrders(long id) throws JsonProcessingException;

    /**
     * 查看订单明细
     * @param id 用户id
     * @param transactionId 交易订单id
     * @return 状态码以及订单明细
     * @throws JsonProcessingException Json字符串转化异常
     */
    Future<String> checkOrderDetails(long id, long transactionId) throws JsonProcessingException;

    /**
     * 接收供给方提供的员工列表
     * @param vo 接收前端表单的数据列表
     * @param id 用户id
     * @return 返回是否成功
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> provideStaffList(StaffRegisterVo vo, long id) throws JsonProcessingException;

    /**
     * 获取交易记录
     * @param id 用户id
     * @return 返回交易记录
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getTransactionData(long id) throws JsonProcessingException;

    /**
     * 获取钱包初始化信息
     * @param id 用户id
     * @return 返回钱包信息
     * @throws JsonProcessingException 由ObjectMapper.writeValueAsString()导致的Json转换错误
     */
    Future<String> getWalletVo(long id) throws JsonProcessingException;
}
