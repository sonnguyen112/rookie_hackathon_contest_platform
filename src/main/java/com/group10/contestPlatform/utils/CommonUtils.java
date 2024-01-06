package com.group10.contestPlatform.utils;

import org.springframework.web.multipart.MultipartFile;

import com.group10.contestPlatform.common.CustomMultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommonUtils {
    public static MultipartFile base64ToMutipartFile(String base64) {
        MultipartFile multipartFile = new CustomMultipartFile(base64);
        return multipartFile;
    }

    public static LocalDate convertToLocalDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(str, formatter);
        return date;
    }
}
