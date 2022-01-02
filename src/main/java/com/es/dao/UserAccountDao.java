package com.es.dao;

import com.es.po.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created on 2021/12/12 20:20
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface UserAccountDao {

    /***********************查询***********************/
    Double fetchAccountBalance(long id);

    Double fetchFreezingMoney(long id);

    /***********************插入***********************/
    int insertUserAccount(@Param("id") long id);

    /***********************修改***********************/
    int updateUserAccount(@Param("id") long id, @Param("balance") double balance, @Param("freezing") double freezing);

    int updateBalance(@Param("id") long id, @Param("balance") double balance);

    int updateFreezing(@Param("id") long id, @Param("freezing") double freezing);

    /***********************删除***********************/
    int clearUserAccount(@Param("id") long id);
}
