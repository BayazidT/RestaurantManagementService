package com.tr.rms.modules.user.dto;

public record UserRequest(
        String name,
        String username,
        String email,
        String password
) {}
