package com.studymate.infrastructure.user.controller.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String username,
        String token
) {
}
