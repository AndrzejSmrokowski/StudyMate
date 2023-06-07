package com.studymate.infrastructure.user.controller;

import com.studymate.domain.user.UserManagementFacade;
import com.studymate.domain.user.dto.RegisterUserDto;
import com.studymate.domain.user.dto.RegistrationResultDto;
import com.studymate.infrastructure.security.jwt.JwtAuthenticatorFacade;
import com.studymate.infrastructure.user.controller.dto.TokenRequestDto;
import com.studymate.infrastructure.user.controller.dto.JwtResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {



    private final UserManagementFacade userManagementFacade;
    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        RegistrationResultDto registrationResult = userManagementFacade.registerUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResult);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticateUser(@RequestBody TokenRequestDto loginRequest) {
        JwtResponseDto jwtResponseDto = jwtAuthenticatorFacade.authenticateAndGenerateToken(loginRequest);
        return ResponseEntity.ok(jwtResponseDto);
    }
}
