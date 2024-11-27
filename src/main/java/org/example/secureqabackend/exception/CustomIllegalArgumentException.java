package org.example.secureqabackend.exception;

public class CustomIllegalArgumentException extends RuntimeException {
    public CustomIllegalArgumentException(String message) {
        super(message);
    }
}
