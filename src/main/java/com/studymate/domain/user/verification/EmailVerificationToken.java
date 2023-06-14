package com.studymate.domain.user.verification;

import java.time.Instant;
import java.util.UUID;

public class EmailVerificationToken {
    private String token;
    private Instant expiryDate;

    public EmailVerificationToken() {
        this.token = UUID.randomUUID().toString();
        this.expiryDate = Instant.now().plusSeconds(24 * 60 * 60);
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
