package com.group10.contestPlatform.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class StorageUtils {

    private final String quizImagePath = "upload/images/";

    public String saveFile(String base64, String type) {
        byte[] file = Base64.getDecoder().decode(base64);
        File dir = new File(quizImagePath + type);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = UUID.randomUUID() + ".png";
        File f = new File(dir, fileName);
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(file);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
