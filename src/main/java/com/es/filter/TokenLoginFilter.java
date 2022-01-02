package com.es.filter;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.es.dao.UserInfoDao;
import com.es.util.JwtUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created on 2021/12/16 19:39
 *
 * @author Marfack
 */
@ConditionalOnClass(UserInfoDao.class)
public final class TokenLoginFilter extends BasicAuthenticationFilter {

    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Token");
        response.setContentType("application/json;charset=UTF-8");
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        try {
            if (!JwtUtils.verify(token)) {
                chain.doFilter(request, response);
                return;
            }
        } catch (JWTVerificationException e) {
            logger.warn("Token无效，认证失败:\t" + e.getMessage());
            chain.doFilter(request, response);
            return;
        }
        String username = JwtUtils.getClaim(token, JwtUtils.USERNAME).asString();
        String password = JwtUtils.getClaim(token, JwtUtils.PASSWORD).asString();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        JwtUtils.getClaim(token, JwtUtils.AUTHORITIES).asList(String.class).forEach(e -> authorities.add(new SimpleGrantedAuthority(e)));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        super.doFilterInternal(request, response, chain);
    }
}
