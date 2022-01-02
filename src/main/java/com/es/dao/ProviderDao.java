package com.es.dao;

import com.es.po.Provider;
import com.es.po.Staff;
import com.es.po.TransactionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2021/12/13 12:54
 *
 * @author Marfack
 */
@Mapper
@Repository
public interface ProviderDao {
    /***********************查询***********************/
    Provider fetchProvider(long id);

    List<Staff> queryStaffs(long id);

    List<TransactionInfo> queryTransactionInfo(long id);

    /***********************插入***********************/
    int insertProviderObject(Provider provider);

    /***********************修改***********************/
    int updateProviderObject(Provider provider);

    /***********************删除***********************/
    int clearProvider(long id);
}
