package com.group10.contestPlatform.common;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ErrorCode {
    private HashMap<Integer, String> dictError = new HashMap<>();

    public ErrorCode() {
        dictError.put(0, "Request not validated");
        dictError.put(1, "Sign up failed");
        dictError.put(2, "Login failed");
        dictError.put(3, "Quiz not found");
        dictError.put(4, "Not found register");
        dictError.put(5, "Error when saving file");
        dictError.put(6, "Upload file failed when sending");
        dictError.put(7, "Refresh token is deleted");
        dictError.put(8, "Invalid JWT signature");
        dictError.put(9, "Invalid JWT token");
        dictError.put(10, "Expired JWT token");
        dictError.put(11, "Unsupported JWT token");
        dictError.put(12, "JWT claims string is empty.");
        dictError.put(13, "Not permission");
        dictError.put(14, "Not is the time for this action");
        dictError.put(15, "You can not do quiz twice");


        dictError.put(100, "Something Error");
    }
}
