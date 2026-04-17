package com.app.authservice.dto;

public class AuthResponse {

    private String token;
    private String username;
    private String email;
    private String role;
    private String provider;
    private long expiresIn;

    public AuthResponse() {}

    public AuthResponse(String token, String username, String email,
                        String role, String provider, long expiresIn) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.expiresIn = expiresIn;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getProvider() { return provider; }
    public long getExpiresIn() { return expiresIn; }

    public void setToken(String token) { this.token = token; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setProvider(String provider) { this.provider = provider; }
    public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
}
