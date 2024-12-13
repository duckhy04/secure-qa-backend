package com.example.secureqabackend.service.auth;

import com.example.secureqabackend.dto.ApiResponse;
import com.example.secureqabackend.dto.AuthRequest;
import com.example.secureqabackend.dto.AuthResponse;
import com.example.secureqabackend.entity.User;
import com.example.secureqabackend.repository.UserRepository;
import com.example.secureqabackend.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse<AuthResponse> login(AuthRequest authRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            String token = jwtUtil.generateToken(auth);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(token);

            return new ApiResponse<>(
                    "Login successful. Welcome back!",
                    ApiResponse.Status.SUCCESS,
                    authResponse,
                    LocalDateTime.now()
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password.");
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Authentication failed.") {};
        }
    }

    @Override
    public ApiResponse<String> signUp(AuthRequest authRequest) {
        if (userRepository.existsByUsername(authRequest.getUsername())) {
            return new ApiResponse<>(
                    "Username already exists",
                    ApiResponse.Status.ERROR,
                    null,
                    LocalDateTime.now()
            );
        }

        if (userRepository.existsByEmail(authRequest.getEmail())) {
            return new ApiResponse<>(
                    "Email already exists",
                    ApiResponse.Status.ERROR,
                    null,
                    LocalDateTime.now()
            );
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setEmail(authRequest.getEmail());
        userRepository.save(user);

        return new ApiResponse<>(
                "Sign up successfully",
                ApiResponse.Status.SUCCESS,
                null,
                LocalDateTime.now()
        );
    }
}