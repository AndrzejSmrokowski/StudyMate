package com.studymate.domain.user;

import com.studymate.domain.user.dto.RegisterUserDto;
import com.studymate.domain.user.dto.RegistrationResultDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
@AllArgsConstructor
@Component
public class UserManagementFacade {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User with the given username not found: " + username));
    }

    public RegistrationResultDto registerUser(RegisterUserDto registerUserDto) {
        Optional<User> existingUser = userRepository.findByUsername(registerUserDto.username());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(registerUserDto.username());
        }
        String encodedPassword = passwordEncoder.encode(registerUserDto.password());

        User newUser = User.builder()
                .username(registerUserDto.username())
                .password(encodedPassword)
                .build();
        User registeredUser = userRepository.save(newUser);
        return new RegistrationResultDto(registeredUser.userId(), true, registeredUser.username());

    }
}