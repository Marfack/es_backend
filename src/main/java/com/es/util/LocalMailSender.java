package com.es.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created on 2021/12/17 18:02
 *
 * @author Marfack
 */
@Component
public class LocalMailSender {

    private static final String FROM = "1411343583@qq.com";

    private static final String ENTERPRISE_NAME = "企享（ES）";

    @Resource
    private JavaMailSender mailSender;

    public void send(String to, String title, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(ENTERPRISE_NAME + "<" + FROM + ">");
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

}
