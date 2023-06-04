package com.studymate.domain.user;

import com.studymate.domain.user.dto.RegisterData;
import lombok.AllArgsConstructor;

import java.util.Optional;
@AllArgsConstructor
public class UserManagementFacade {
    private final UserRepository userRepository;



    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User registerUser(RegisterData registerData) {
        Optional<User> existingUser = userRepository.findByUsername(registerData.username());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(registerData.username());
        }

        User newUser = User.builder()
                .username(registerData.username())
                .password(registerData.password())
                .build();
        return userRepository.save(newUser);
    }
}