package org.example.secureqabackend.service.auth;

import org.example.secureqabackend.dto.AuthRequest;
import org.example.secureqabackend.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    String signup(AuthRequest authRequest);
}
