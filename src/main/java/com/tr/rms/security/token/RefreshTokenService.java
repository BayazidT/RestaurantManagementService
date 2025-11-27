// src/main/java/com/tr/rms/security/token/RefreshTokenService.java
package com.tr.rms.security.token;

import com.tr.rms.modules.user.entity.User;
import com.tr.rms.security.jwt.JwtProperties;
import com.tr.rms.rbac.repository.RefreshTokenRepository;
import com.tr.rms.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Transactional
    public RefreshToken createRefreshToken(User user, String ip, String ua) {
        var token = RefreshToken.builder()
                .user(user)
                .token(jwtService.generateRefreshToken((UserDetails) user))
                .expiresAt(Instant.now().plusMillis(jwtProperties.refreshExpiration()))
                .createdFromIp(ip)
                .createdFromUserAgent(ua)
                .build();

        return refreshTokenRepository.save(token);
    }

    @Transactional
    public void revokeToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(t -> {
                    t.setRevokedAt(Instant.now());
                    refreshTokenRepository.save(t);
                });
    }

    public RefreshToken validateAndGet(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(t -> !t.isRevoked() && !t.isExpired())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }
}