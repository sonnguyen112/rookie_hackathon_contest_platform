package com.group10.contestPlatform.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.group10.contestPlatform.common.ErrorCode;
import com.group10.contestPlatform.dtos.ErrorResponse;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorCode errorCode;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException e) {
        return ResponseEntity.status(e.getCodeStatus()).body(ErrorResponse.builder()
                .message(errorCode.getDictError().get(e.getCodeError())).errCode(e.getCodeError()).build());
    }
}
