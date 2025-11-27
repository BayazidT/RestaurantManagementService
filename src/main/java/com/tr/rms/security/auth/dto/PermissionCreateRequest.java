// src/main/java/com/tr/rms/modules/auth/dto/PermissionCreateRequest.java
package com.tr.rms.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PermissionCreateRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 255) String description,
        @Size(max = 50) String module
) {}