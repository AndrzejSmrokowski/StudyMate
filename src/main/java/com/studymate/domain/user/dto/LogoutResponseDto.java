package com.studymate.domain.user.dto;

public record LogoutResponseDto(
        String username,
        boolean loggedOut
) {
}
