// src/main/java/com/tr/rms/security/auth/dto/LoginResponse.java
package com.tr.rms.security.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        long expiresIn
) {}