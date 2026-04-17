package com.app.authservice.service;

import com.app.authservice.dto.*;
import com.app.authservice.entity.User;
import com.app.authservice.repository.UserRepository;
import com.app.authservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered: " + req.getEmail());
        }

        User user = new User();
        user.setUsername(req.getName());
        user.setEmail(req.getEmail().toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("USER");
        user.setProvider("LOCAL");

        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return toResponse(user, token);
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(), req.getPassword())
        );

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());
        return toResponse(user, token);
    }

    public AuthResponse handleGoogleLogin(String email, String name) {
        User user = userRepo.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(name);
            newUser.setEmail(email.toLowerCase());
            newUser.setRole("USER");
            newUser.setProvider("GOOGLE");
            return userRepo.save(newUser);
        });

        String token = jwtUtil.generateToken(user.getEmail());
        return toResponse(user, token);
    }

    private AuthResponse toResponse(User user, String token) {
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setProvider(user.getProvider());
        response.setExpiresIn(3600000L);
        return response;
    }
}
