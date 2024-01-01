package com.group10.contestPlatform.utils;

import org.springframework.web.multipart.MultipartFile;

import com.group10.contestPlatform.common.CustomMultipartFile;

public class CommonUtils {
    public static MultipartFile base64ToMutipartFile(String base64) {
        MultipartFile multipartFile = new CustomMultipartFile(base64);
        return multipartFile;
    }
}
