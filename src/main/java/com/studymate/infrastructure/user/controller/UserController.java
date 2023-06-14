package com.studymate.infrastructure.user.controller;

import com.studymate.domain.user.UserManagementFacade;
import com.studymate.domain.user.dto.LogoutResponseDto;
import com.studymate.domain.user.dto.RegisterUserDto;
import com.studymate.domain.user.dto.RegistrationResultDto;
import com.studymate.domain.user.dto.UserData;
import com.studymate.infrastructure.security.jwt.JwtAuthenticatorFacade;
import com.studymate.infrastructure.security.jwt.JwtBlacklistRepository;
import com.studymate.infrastructure.user.controller.dto.TokenRequestDto;
import com.studymate.infrastructure.user.controller.dto.JwtResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {


    private final UserManagementFacade userManagementFacade;
    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;
    private final JwtBlacklistRepository jwtBlacklistRepository;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        RegistrationResultDto registrationResult = userManagementFacade.registerUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResult);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticateUser(@RequestBody TokenRequestDto loginRequest) {
        JwtResponseDto jwtResponseDto = jwtAuthenticatorFacade.authenticateAndGenerateToken(loginRequest);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logoutUser(@RequestHeader(name = "Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        jwtBlacklistRepository.add(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        LogoutResponseDto logoutResponse = new LogoutResponseDto(currentUserName, true);
        return ResponseEntity.ok(logoutResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserData> getUserData(@PathVariable String userId) {
        UserData userData = userManagementFacade.getUserData(userId);
        return ResponseEntity.ok(userData);
    }

    @PutMapping("/add-email")
    public ResponseEntity<Void> addEmailToUser(@RequestBody @Email @NotBlank String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        userManagementFacade.addEmailToUser(email, currentUserName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Boolean> verifyEmail(@RequestParam String token) {
        boolean isVerified = userManagementFacade.verifyEmail(token);
        return ResponseEntity.ok(isVerified);
    }
}
