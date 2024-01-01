package com.group10.contestPlatform.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
import com.group10.contestPlatform.exceptions.AppException;

public class CustomMultipartFile implements MultipartFile {

    private String base64;
    private byte[] input;

    public CustomMultipartFile(String base64) {
        this.base64 = base64;
        try {
            this.input = Base64.getDecoder().decode(base64.substring(base64.indexOf("base64") + 7));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return UUID.randomUUID().toString() + "." + getContentType().substring(getContentType().indexOf("/") + 1);
    }

    @Override
    public String getContentType() {
        return base64.substring(5, base64.indexOf(";base64"));
    }

    // previous methods
    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(input);
        }
    }
}
