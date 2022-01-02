package com.es.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

/**
 * Created on 2021/12/12 0:47
 *
 * @author Marfack
 */
public enum UserClass {
    /**
     * DEFAULT登陆后权限
     * DEMANDER完善信息后权限
     * PROVIDER成为供给者后的权限
     */
    DEFAULT(0, "DEFAULT"), DEMANDER(1, "DEMANDER"), PROVIDER(2, "PROVIDER");

    public final int CLS;

    public final String CLS_STR;

    private static final String PREFIX = "ROLE_";

    public static void getAuthorities(Collection<GrantedAuthority> authorities, int cls) {
        authorities.add(new SimpleGrantedAuthority(PREFIX + UserClass.DEFAULT.CLS_STR));
        if (cls >= UserClass.DEMANDER.CLS) {
            authorities.add(new SimpleGrantedAuthority(PREFIX + UserClass.DEMANDER.CLS_STR));
        }
        if (cls == UserClass.PROVIDER.CLS) {
            authorities.add(new SimpleGrantedAuthority(PREFIX + UserClass.PROVIDER.CLS_STR));
        }
    }

    UserClass(int cls, String clsStr) {
        this.CLS = cls;
        this.CLS_STR = clsStr;
    }
}
