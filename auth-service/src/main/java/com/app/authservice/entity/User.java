//package com.app.authservice.entity;
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Builder
//@Entity
//@Table(name = "users")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String username;
//
//    private String password;
//
//    private String role;
//
//    @Column(unique = true)
//    private String email;
//
//    // 'LOCAL' for email signup, 'GOOGLE' for OAuth
//    private String provider;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        if (role == null) role = "USER";
//        if (provider == null) provider = "LOCAL";
//    }
//
//	public static Object builder() {
//		return null;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//	
//	public String getRole() {
//		return role;
//	}
//	
//	public String getPassword() {
//		return password;
//	}
//
//	public Object getProvider() {
//		return provider;
//	}
//
//	public Object getUsername() {
//		return username;
//	}
//}

package com.app.authservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;
    private String email;
    private String provider;

    private LocalDateTime createdAt;

    public User() {}

    // getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getProvider() { return provider; }

    // setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    public void setProvider(String provider) { this.provider = provider; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (role == null) role = "USER";
        if (provider == null) provider = "LOCAL";
    }
}
