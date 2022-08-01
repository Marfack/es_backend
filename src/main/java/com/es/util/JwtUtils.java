package com.es.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.es.enums.UserClass;
import com.es.po.UserInfo;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * Created on 2021/12/15 22:23
 * 封装静态Jwt生成工具类，不允许实例化。包含通过user info生成token、token校验和取出token中的值的功能
 * @author Marfack
 */
public final class JwtUtils {

    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String AUTHORITIES = "authorities";

    private static final String ISSUER = "auth0";

    private static final String TOKEN_SECRET = "lkhndqo2hnbro12iaohn143jjiodwajnonompo";

    private JwtUtils() {
    }

    public static String createToken(UserInfo userInfo) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 24);
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserClass.getAuthorities(authorities, userInfo.getUserClass());
        List<String> res = new ArrayList<>();
        authorities.forEach(e -> res.add(e.getAuthority()));
        return JWT.create()
                .withIssuer(ISSUER)
                // 发行对象
                .withAudience(String.valueOf(userInfo.getUserId()))
                // 发行时间
                .withIssuedAt(new Date())
                // 到期时间
                .withExpiresAt(now.getTime())
                .withClaim(ID, userInfo.getUserId())
                .withClaim(USERNAME, userInfo.getUserNo())
                .withClaim(PASSWORD, userInfo.getUserPassword())
                .withClaim(AUTHORITIES, res)
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    public static boolean verify(String token) throws JWTVerificationException {
        if (token == null) {
            return false;
        }
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC256(TOKEN_SECRET))
                .withIssuer(ISSUER)
                .withAudience(String.valueOf(getClaim(token, ID).asLong()))
                .build();
        verifier.verify(token);
        return true;
    }

    public static Claim getClaim(String token, String key) throws JWTDecodeException {
        return JWT.decode(token).getClaim(key);
    }
}
