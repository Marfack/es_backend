package com.es.po;


import lombok.Data;

/**
 * Created on 2021/12/12 10:24
 *
 * @author Marfack
 */
@Data
public class City {

    // 城市id
    int cityId;

    // 所属省份id
    int provinceId;

    // 城市名
    String cityName;
}
