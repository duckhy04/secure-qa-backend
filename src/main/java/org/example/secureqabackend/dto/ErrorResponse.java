package org.example.secureqabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private long timestamp;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
