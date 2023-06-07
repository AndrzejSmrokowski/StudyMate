package com.studymate.infrastructure.security.jwt;


import com.studymate.domain.user.User;
import com.studymate.domain.user.UserManagementFacade;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final UserManagementFacade userManagementFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        User user = userManagementFacade.findUserByUsername(username);
        return getUser(user);
    }

    private org.springframework.security.core.userdetails.User getUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.username(),
                user.password(),
                Collections.emptyList());
    }
}
