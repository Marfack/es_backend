package com.es.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created on 2021/12/31 2:40
 * 上传文件服务
 * @author Marfack
 */
public interface UploadService {

    /**
     * 上传头像业务
     * @param id 用户id
     * @param file 文件
     * @throws IOException IO异常
     */
    void uploadIco(long id, MultipartFile file) throws IOException;
}
