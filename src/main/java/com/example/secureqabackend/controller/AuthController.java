package com.example.secureqabackend.controller;

import com.example.secureqabackend.dto.ApiResponse;
import com.example.secureqabackend.dto.AuthRequest;
import com.example.secureqabackend.dto.AuthResponse;
import com.example.secureqabackend.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        try {
            ApiResponse<AuthResponse> authResponse = authService.login(authRequest);
            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        } catch (BadCredentialsException e) {
            return buildErrorResponse("Invalid username or password. Please try again.", HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
            return buildErrorResponse("Authentication failed. Please check your credentials.", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return buildErrorResponse("An internal error occurred during login. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody AuthRequest authRequest) {
        try {
            ApiResponse<String> apiResponse = authService.signUp(authRequest);
            return ResponseEntity
                    .status(apiResponse.getStatus() == ApiResponse.Status.SUCCESS ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                    .body(apiResponse);
        } catch (Exception e) {
            return buildErrorResponse("An internal error occurred during sign-up. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(String message, HttpStatus status) {
        ApiResponse<T> apiResponse = new ApiResponse<>(
                message,
                ApiResponse.Status.ERROR,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(apiResponse);
    }
}