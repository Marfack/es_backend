package com.es.controller;

import com.es.service.UploadService;
import com.es.util.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created on 2021/12/31 2:53
 *
 * @author Marfack
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @Resource
    UploadService uploadService;

    @RequestMapping("/ico")
    public void uploadIco(MultipartFile file, HttpServletRequest request) throws IOException {
        String token = request.getHeader("Token");
        uploadService.uploadIco(JwtUtils.getClaim(token, JwtUtils.ID).asLong(), file);
    }
}
