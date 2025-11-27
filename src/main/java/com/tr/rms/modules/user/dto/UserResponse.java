package com.tr.rms.modules.user.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email
) {}
