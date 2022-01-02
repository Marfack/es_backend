package com.es.dao;

import com.es.dto.TransactionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created on 2021/12/13 20:48
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface TransactionDetailsDao {
    /***********************查询***********************/
    TransactionDetailsDao fetchTransactionDetails(long id);

    TransactionDto fetchTransaction(long id);

    /***********************插入***********************/
    int insertTransactionDetails(@Param("id") long id, @Param("amount") double amount);

    int updateAmount(@Param("id") long id, @Param("amount") double amount);

    int removeTransactionDetails(@Param("id") long id);
}
