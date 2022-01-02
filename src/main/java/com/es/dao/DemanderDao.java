package com.es.dao;

import com.es.po.Demander;
import com.es.po.TransactionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 11:52
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface DemanderDao {

    /***********************查询***********************/
    Demander fetchDemander(long id);

    List<TransactionInfo> queryTransactionInfo(long id);

    /***********************插入***********************/
    int insertDemanderObject(Demander demander);

    int insertDemander(@Param("id") long id, @Param("description") String description);

    /***********************修改***********************/
    int updateDemanderObject(Demander demander);

    int updateHireTimes(@Param("id") long id, @Param("times") int times);

    int updateDescription(@Param("id") long id, @Param("description") String description);

    /***********************删除***********************/
    int clearDemander(long id);
}
