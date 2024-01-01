package com.group10.contestPlatform.exceptions;

public class ExpiredTokenException extends Exception {
    public ExpiredTokenException() {
        super();
    }

    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredTokenException(Throwable cause) {
        super(cause);
    }
}
