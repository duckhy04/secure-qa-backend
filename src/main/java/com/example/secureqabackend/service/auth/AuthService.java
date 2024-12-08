package com.example.secureqabackend.service.auth;

import com.example.secureqabackend.dto.ApiResponse;
import com.example.secureqabackend.dto.AuthRequest;
import com.example.secureqabackend.dto.AuthResponse;

public interface AuthService {
    ApiResponse<AuthResponse> login(AuthRequest authRequest);
    ApiResponse<String> signUp(AuthRequest authRequest);
}
