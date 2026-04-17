package com.app.qmaservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    // Injected by Spring from application.yml — do NOT initialise manually
    @Value("${jwt.secret}")
    private String secret;

    // ── Key helper — built lazily so @Value is populated first ────────────
    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ── Extract username / email (subject) from token ─────────────────────
    public String extractSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // ── Validate token (returns false instead of throwing) ────────────────
    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
}
