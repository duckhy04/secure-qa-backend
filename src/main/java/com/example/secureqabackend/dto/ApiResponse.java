package com.example.secureqabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private Status status;
    private T data;
    private LocalDateTime timestamp;

    public enum Status {
        SUCCESS, ERROR
    }
}
