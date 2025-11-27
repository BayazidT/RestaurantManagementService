// src/main/java/com/tr/rms/security/auth/dto/UserProfileResponse.java
package com.tr.rms.security.auth.dto;

import java.util.Set;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String username,
        String email,
        String name,
        Set<String> roles,
        Set<String> permissions
) {}