package com.es.config;

import com.es.filter.TokenLoginFilter;
import com.es.enums.UserClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * Created on 2021/12/8 20:28
 * Spring Security认证授权配置类
 * @author Marfack
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 实现SpringSecurity验证密码的类的对象
     */
    @Resource
    UserDetailsService userDetailsService;

    /**
     * 登陆成功处理接口实现类对象
     */
    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 登陆失败处理接口实现类对象
     */
    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 拒绝访问处理接口实现类对象
     */
    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    /**
     *无权限时处理接口实现类对象
     */
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 退出登录处理接口实现类对象
     */
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                // 取消Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/verify/{email}").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/retrieve_verify/{email}").permitAll()
                .antMatchers("/api/retrieve_password").permitAll()
                .antMatchers("/api/career_classes").permitAll()
                .antMatchers("/api/career_list/{classId}").permitAll()
                .antMatchers("/api/staff_list/career{careerId}/page{page}").permitAll()
                .antMatchers("/api/get_provinces").permitAll()
                .antMatchers("/api/get_cities/province{id}").permitAll()
                .antMatchers("/api/staff_list/career{careerId}/city{cityId}/page{page}").permitAll()
                .antMatchers("/chat/{id}").permitAll()
                .antMatchers("/demander/**").hasRole(UserClass.DEMANDER.CLS_STR)
                .antMatchers("/provider/**").hasRole(UserClass.PROVIDER.CLS_STR)
                .anyRequest().hasRole(UserClass.DEFAULT.CLS_STR)
                .and()
                .formLogin().loginProcessingUrl("/api/login").loginProcessingUrl("/api/login").permitAll()
                .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler);

        http
                .addFilterBefore(new TokenLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
