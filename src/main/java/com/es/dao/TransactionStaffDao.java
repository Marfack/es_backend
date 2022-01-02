package com.es.dao;

import com.es.dto.TransactionDto;
import com.es.po.Staff;
import com.es.po.TransactionStaff;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 21:51
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface TransactionStaffDao {
    /***********************查询***********************/
    List<TransactionDto> queryTransactionOfStaff(long id);

    List<Staff> queryStaffOfTransaction(long id);

    /***********************插入***********************/
    int insertTransactionStaff(TransactionStaff transactionStaff);

    int insertTransactionStaffs(List<TransactionStaff> transactionStaffs);

    /***********************删除***********************/
    int clearTransactionStaff(TransactionStaff transactionStaff);

    int removeTransactionStaffs(List<TransactionStaff> transactionStaffs);
}
