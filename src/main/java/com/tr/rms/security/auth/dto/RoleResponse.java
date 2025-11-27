// src/main/java/com/tr/rms/modules/auth/dto/RoleResponse.java
package com.tr.rms.security.auth.dto;

import com.tr.rms.rbac.dto.PermissionResponse;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Set<PermissionResponse> permissions
) {}