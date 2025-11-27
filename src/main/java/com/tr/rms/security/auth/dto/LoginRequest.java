// src/main/java/com/tr/rms/security/auth/dto/LoginRequest.java
package com.tr.rms.security.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {}