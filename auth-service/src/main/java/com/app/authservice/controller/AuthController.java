package com.app.authservice.controller;

import com.app.authservice.dto.*;
import com.app.authservice.security.JwtUtil;
import com.app.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Register, Login, Google OAuth2")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Register with email")
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(req));
    }

    @Operation(summary = "Login with email")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @Operation(summary = "Google OAuth2 success — called after redirect")
    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauth2Success(
            @AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name  = oAuth2User.getAttribute("name");
        return ResponseEntity.ok(
                authService.handleGoogleLogin(email, name));
    }

    @Operation(summary = "Validate a JWT token")
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(
            @RequestParam String token) {
        return ResponseEntity.ok(jwtUtil.isValid(token));
    }
}
