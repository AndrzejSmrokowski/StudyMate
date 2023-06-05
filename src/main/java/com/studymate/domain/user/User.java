package com.studymate.domain.user;

import lombok.Builder;

@Builder
public record User(
        String userId,
        String username,
        String password
) {
}
