// src/main/java/com/tr/rms/security/auth/AuthenticationController.java
package com.tr.rms.security.auth;

import com.tr.rms.security.auth.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletRequest http) {
        String ip = getClientIp(http);
        String ua = http.getHeader("User-Agent");
        System.out.println(ip);
        return ResponseEntity.ok(authService.login(request, ip, ua));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request, HttpServletRequest http) {
        String ip = getClientIp(http);
        String ua = http.getHeader("User-Agent");
        return ResponseEntity.ok(authService.refresh(request, ip, ua));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me(Authentication auth) {
        return ResponseEntity.ok(authService.getProfile(auth));
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isEmpty()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}