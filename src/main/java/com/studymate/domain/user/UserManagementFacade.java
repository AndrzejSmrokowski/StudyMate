package com.studymate.domain.user;

import com.studymate.domain.progresstracking.Progress;
import com.studymate.domain.progresstracking.ProgressNotFoundException;
import com.studymate.domain.progresstracking.ProgressRepository;
import com.studymate.domain.progresstracking.ProgressTrackingFacade;
import com.studymate.domain.user.dto.RegisterUserDto;
import com.studymate.domain.user.dto.RegistrationResultDto;
import com.studymate.domain.user.dto.UserData;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class UserManagementFacade {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProgressTrackingFacade progressTrackingFacade;
    private final ProgressRepository progressRepository;


    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User with the given username not found: " + username));
    }

    public RegistrationResultDto registerUser(RegisterUserDto registerUserDto) {
        Optional<User> existingUser = userRepository.findByUsername(registerUserDto.username());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(registerUserDto.username());
        }
        String encodedPassword = passwordEncoder.encode(registerUserDto.password());
        List<SimpleGrantedAuthority> userAuthorities = List.of(new SimpleGrantedAuthority("USER"));

        User newUser = User.builder()
                .username(registerUserDto.username())
                .password(encodedPassword)
                .authorities(userAuthorities)
                .build();
        User registeredUser = userRepository.save(newUser);
        progressTrackingFacade.initializeProgress(registeredUser.userId());
        return new RegistrationResultDto(registeredUser.userId(), true, registeredUser.username());

    }

    public boolean existsByUsername(String admin) {
        return userRepository.existsByUsername(admin);
    }

    public void updateUserData(User user) {
        userRepository.save(user);
    }

    public UserData getUserData(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Progress progress = progressRepository.getProgressByUserId(userId)
                .orElseThrow(() -> new ProgressNotFoundException(userId));
        return UserData.builder()
                .username(user.username())
                .authorities(user.getAuthorities())
                .progress(progress)
                .build();

    }
}