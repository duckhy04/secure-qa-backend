package org.example.secureqabackend.service.auth;

import org.example.secureqabackend.dto.AuthRequest;
import org.example.secureqabackend.dto.AuthResponse;
import org.example.secureqabackend.entity.Role;
import org.example.secureqabackend.entity.User;
import org.example.secureqabackend.exception.AlreadyExistsException;
import org.example.secureqabackend.exception.ResourceNotFoundException;
import org.example.secureqabackend.repository.RoleRepository;
import org.example.secureqabackend.repository.UserRepository;
import org.example.secureqabackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtUtils.generateToken(auth);

        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        return authResponse;
    }

    @Override
    public String signup(AuthRequest authRequest) {
        Optional<User> usernameExists = userRepository.findByUsername(authRequest.getUsername());
        Optional<User> emailExists = userRepository.findByEmail(authRequest.getEmail());

        if (usernameExists.isPresent() || emailExists.isPresent()) {
            throw new AlreadyExistsException("Username or email already exists");
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(userRole);

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setEmail(authRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);

        return "Sign up successful";
    }
}
