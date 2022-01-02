package com.es.service.impl;

import com.es.dao.*;
import com.es.dto.StaffInTransactionDto;
import com.es.dto.TransactionDto;
import com.es.enums.PaymentEnum;
import com.es.enums.ResponseCode;
import com.es.enums.TransactionStatus;
import com.es.po.*;
import com.es.service.TransactionService;
import com.es.util.JsonUtils;
import com.es.vo.StaffRegisterVo;
import com.es.vo.TransactionDataVo;
import com.es.vo.WalletVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created on 2021/12/20 14:46
 * 支付业务的实现类
 *
 * @author Marfack
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    UserAccountDao userAccountDao;

    @Resource
    TransactionInfoDao transactionInfoDao;

    @Resource
    TransactionDetailsDao transactionDetailsDao;

    @Resource
    TransactionStaffDao transactionStaffDao;

    @Resource
    StaffDao staffDao;

    @Resource
    StaffCareerDao staffCareerDao;

    @Resource
    UserDetailsDao userDetailsDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> recharge(long id, double amount, PaymentEnum payment) throws JsonProcessingException {
        double max = 1e8;
        int min = 1;
        if (amount < min || amount > max) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        try {
            accessPaymentInterface(payment);
        } catch (EnumConstantNotPresentException e) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        Double balance = userAccountDao.fetchAccountBalance(id);
        if (balance == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.USER_NOT_FOUND_ERROR));
        }
        balance += amount;
        userAccountDao.updateBalance(id, balance);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("balance", balance)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> withdraw(long id, double amount, PaymentEnum payment) throws JsonProcessingException {
        double min = 1;
        double max = 1e6;
        if (min > amount || max < amount) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.PARAM_OUT_OF_RANGE));
        }
        Double balance = userAccountDao.fetchAccountBalance(id);
        if (balance == null || balance < amount) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        try {
            accessPaymentInterface(payment);
        } catch (EnumConstantNotPresentException e) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.USER_NOT_FOUND_ERROR));
        }
        balance -= amount;
        userAccountDao.updateBalance(id, balance);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("balance", balance)
                .returnJson();
        return new AsyncResult<>(res);
    }

    private void accessPaymentInterface(PaymentEnum paymentEnum) {
        switch (paymentEnum) {
            case ALIPAY:
                // 接入阿里第三方支付接口
                break;
            case WE_CHAT:
                // 接入微信第三方支付接口
                break;
            case ONLINE_BANKING:
                // 接入网银第三方支付接口
                break;
            case PAY_PAL:
                // 接入paypal第三方支付接口
                break;
            default:
                throw new EnumConstantNotPresentException(PaymentEnum.class, paymentEnum.getName());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> pay(long id, List<Long> staffs, double amount) throws JsonProcessingException {
        Double balance = userAccountDao.fetchAccountBalance(id);
        if (balance <= amount) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.ACCOUNT_BALANCE_NOT_ENOUGH));
        }
        transactionInfoDao.insertTransactionInfo(id, TransactionStatus.CREATED.status);
        List<Long> idByBuyer = transactionInfoDao.getIdByBuyer(id);
        if (idByBuyer.isEmpty()) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.SERVER_ERROR));
        }
        Long tid = idByBuyer.get(idByBuyer.size() - 1);
        List<TransactionStaff> ls = new ArrayList<>();
        for (long e : staffs) {
            Staff staff = staffDao.fetchStaff(e);
            if (staff == null || staff.getStaffStatus() != 0) {
                return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.STAFF_BUSY));
            }
            staff.setStaffStatus(1);
            staffDao.updateStaffObject(staff);
            TransactionStaff transactionStaff = new TransactionStaff();
            transactionStaff.setTransactionId(tid);
            transactionStaff.setStaffId(e);
            ls.add(transactionStaff);
        }
        transactionStaffDao.insertTransactionStaffs(ls);
        transactionDetailsDao.insertTransactionDetails(tid, amount);
        balance -= amount;
        Double freezing = userAccountDao.fetchFreezingMoney(id);
        freezing += amount;
        userAccountDao.updateUserAccount(id, balance, freezing);
        TransactionInfo info = transactionInfoDao.fetchTransactionInfo(tid);
        info.setTransactionStatus(TransactionStatus.WORKING.status);
        transactionInfoDao.updateTransactionInfoObject(info);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("balance", balance)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> complete(long userId, long transactionId) throws JsonProcessingException {
        TransactionInfo transactionInfo = transactionInfoDao.fetchTransactionInfo(transactionId);
        AtomicReference<Double> freezing = new AtomicReference<>(userAccountDao.fetchFreezingMoney(userId));
        if (userId != transactionInfo.getBuyerId()) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_FAIL));
        }
        if (freezing.get() == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_FAIL));
        }
        TransactionDto transactionDto = transactionDetailsDao.fetchTransaction(transactionId);
        List<StaffInTransactionDto> staffs = transactionDto.getStaffs();
        staffs.forEach(e -> {
            double balance = userAccountDao.fetchAccountBalance(e.getEnterpriseId());
            double salary = e.getSalary();
            Staff staff = staffDao.fetchStaff(e.getId());
            staff.setStaffStatus(0);
            staffDao.updateStaffObject(staff);
            balance += salary * 0.9;
            userAccountDao.updateBalance(e.getEnterpriseId(), balance);
            freezing.updateAndGet(v -> v - salary);
        });
        userAccountDao.updateFreezing(userId, freezing.get());
        transactionInfo.setTransactionStatus(TransactionStatus.COMPLETED.status);
        transactionInfoDao.updateTransactionInfoObject(transactionInfo);
        return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> refund(long userId, long transactionId, long staffId) throws JsonProcessingException {
        TransactionInfo transactionInfo = transactionInfoDao.fetchTransactionInfo(transactionId);
        if (userId != transactionInfo.getBuyerId()) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_FAIL));
        }
        AtomicReference<Double> freezing = new AtomicReference<>(userAccountDao.fetchFreezingMoney(userId));
        AtomicReference<Double> balance = new AtomicReference<>(userAccountDao.fetchAccountBalance(userId));
        if (freezing.get() == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_FAIL));
        }
        TransactionDto transactionDto = transactionDetailsDao.fetchTransaction(transactionId);
        List<StaffInTransactionDto> staffs = transactionDto.getStaffs();
        AtomicBoolean hasStaff = new AtomicBoolean(false);
        AtomicInteger size = new AtomicInteger(staffs.size());
        List<TransactionStaff> transactionStaffs = new ArrayList<>();
        staffs.forEach(e -> {
            if (staffId == e.getId()) {
                TransactionStaff transactionStaff = new TransactionStaff();
                transactionStaff.setStaffId(e.getId());
                transactionStaff.setTransactionId(transactionId);
                transactionStaffs.add(transactionStaff);
                double salary = e.getSalary();
                freezing.updateAndGet(v -> v - salary);
                balance.updateAndGet(v -> v + salary * 0.85);
                userAccountDao.updateBalance(e.getEnterpriseId(), salary * 0.1);
                transactionDetailsDao.updateAmount(transactionId, transactionDto.getTransactionAmount() - salary);
                size.getAndDecrement();
                hasStaff.set(true);
            }
        });
        if (!hasStaff.get()) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_FAIL));
        }
        Staff staff = staffDao.fetchStaff(staffId);
        staff.setStaffStatus(0);
        staffDao.updateStaffObject(staff);
        transactionStaffDao.removeTransactionStaffs(transactionStaffs);
        userAccountDao.updateUserAccount(userId, balance.get(), freezing.get());
        if (size.get() <= 0) {
            transactionDetailsDao.removeTransactionDetails(transactionId);
            transactionInfoDao.clearTransactionInfo(transactionId);
        }
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("balance", balance.get())
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> checkOrders(long id) throws JsonProcessingException {
        List<TransactionInfo> transactionInfos = transactionInfoDao.queryTransactionByUserId(id);
        List<TransactionDto> transactions = new ArrayList<>();
        transactionInfos.forEach(e -> {
            TransactionDto transactionDto = transactionDetailsDao.fetchTransaction(e.getTransactionId());
            if (transactionDto != null) {
                transactions.add(transactionDto);
            }
        });
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", transactions)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> checkOrderDetails(long id, long transactionId) throws JsonProcessingException {
        if (transactionInfoDao.fetchTransactionInfo(transactionId).getBuyerId() != id) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        TransactionDto transactionDto = transactionDetailsDao.fetchTransaction(transactionId);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", transactionDto)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> provideStaffList(StaffRegisterVo vo, long id) throws JsonProcessingException {
        if (vo == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        staffDao.insertStaffVoAndGetId(id, vo);
        long staffId = vo.getStaffId();
        System.out.println(staffId);
        List<StaffCareer> staffCareers = new ArrayList<>();
        for (int career : vo.getCareers()) {
            StaffCareer staffCareer = new StaffCareer();
            staffCareer.setStaffId(staffId);
            staffCareer.setCareerId(career);
            staffCareers.add(staffCareer);
        }
        staffCareerDao.insertStaffCareerObjects(staffCareers);
        return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.SUCCESS));
    }

    @Override
    public Future<String> getTransactionData(long id) throws JsonProcessingException {
        List<TransactionDataVo> transactionDataVos = transactionInfoDao.queryTransactionDatas(id);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", transactionDataVos)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Override
    public Future<String> getWalletVo(long id) throws JsonProcessingException {
        WalletVo walletVo = new WalletVo();
        Double balance = userAccountDao.fetchAccountBalance(id);
        UserDetails userDetails = userDetailsDao.fetchByUserId(id);
        walletVo.setAmount(balance);
        walletVo.setName(userDetails.getUserName());
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", walletVo)
                .returnJson();
        return new AsyncResult<>(res);
    }
}
