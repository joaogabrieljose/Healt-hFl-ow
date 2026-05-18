package dev.joaogj.Auth.config;

import java.util.List;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email, List<String> roles) {
    
}
