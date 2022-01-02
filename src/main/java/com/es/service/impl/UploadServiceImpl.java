package com.es.service.impl;

import com.es.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created on 2021/12/31 2:45
 *
 * @author Marfack
 */
@PropertySource("classpath:global-value.properties")
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${resource.path}")
    private String icoPath;

    @Override
    public void uploadIco(long id, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String filename = String.valueOf(id);
            String path = icoPath + filename + ".jpg";
            File dest = new File(path);
            file.transferTo(dest);
        }
    }
}
