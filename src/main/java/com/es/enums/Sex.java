package com.es.enums;

/**
 * Created on 2021/12/13 20:11
 *
 * @author Marfack
 */
public enum Sex {
    /**
     * MALE：♂
     * FEMALE：♀
     */
    MALE(1), FEMALE(0);

    public final int sex;

    Sex(int sex) {
        this.sex = sex;
    }
}
