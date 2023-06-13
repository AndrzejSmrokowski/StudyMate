package com.studymate.domain.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record RegisterUserDto(
        @NotEmpty(message = "Username is required")
        @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must only contain letters, numbers and underscores")
        String username,

        @NotEmpty(message = "Password is required")
        @Size(min = 8, max = 64, message = "Password must be at least 8")
        String password

) {
}
