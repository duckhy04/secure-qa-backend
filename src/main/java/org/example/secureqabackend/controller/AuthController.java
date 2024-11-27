package org.example.secureqabackend.controller;

import org.example.secureqabackend.dto.ApiResponse;
import org.example.secureqabackend.dto.AuthRequest;
import org.example.secureqabackend.dto.AuthResponse;
import org.example.secureqabackend.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        ApiResponse<Object> loginResponse = new ApiResponse<>("Login successfully", authResponse);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Object>> signUp(@RequestBody AuthRequest authRequest) {
        String notification = authService.signup(authRequest);
        ApiResponse<Object> signUpResponse = new ApiResponse<>(notification, null);
        return ResponseEntity.status(HttpStatus.OK).body(signUpResponse);
    }

}
