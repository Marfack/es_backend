package com.es.dao;

import com.es.po.TransactionInfo;
import com.es.vo.TransactionDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 20:12
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface TransactionInfoDao {
    /***********************查询***********************/
    TransactionInfo fetchTransactionInfo(long id);

    List<Long> getIdByBuyer(long id);

    List<Long> getUncompletedIdByBuyer(long id);

    List<TransactionInfo> queryTransactionByUserId(long id);

    List<TransactionDataVo> queryTransactionDatas(long id);

    /***********************插入***********************/
    int insertTransactionInfo(@Param("buyer") long buyer, @Param("status") int status);

    /***********************修改***********************/
    int updateTransactionInfoObject(TransactionInfo transactionInfo);

    /***********************删除***********************/
    int clearTransactionInfo(long id);
}
