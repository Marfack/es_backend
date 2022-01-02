package com.es.dto;

import com.es.po.Provider;
import com.es.po.Staff;

import java.util.List;

/**
 * Created on 2021/12/13 14:33
 *
 * @author Marfack
 */
public class ProviderDto extends Provider {

    /**
     * 供给方员工列表
     */
    List<Staff> staffList;
}
