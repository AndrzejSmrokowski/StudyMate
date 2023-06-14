package com.studymate.domain.user;

import com.studymate.domain.user.verification.EmailVerificationToken;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Document
@Builder(toBuilder = true)
public record User(
        @Id String userId,
        @Indexed(unique = true) String username,
        String password,
        Collection<SimpleGrantedAuthority> authorities,
        EmailVerificationToken emailVerificationToken,
        String email,
        boolean emailVerified

) implements UserDetails {
    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
