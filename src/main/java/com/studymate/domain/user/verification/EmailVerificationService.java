package com.studymate.domain.user.verification;

import com.studymate.domain.user.User;
import com.studymate.domain.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailVerificationService(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(User user) {
        EmailVerificationToken token = new EmailVerificationToken();
        User userWithToken = user.toBuilder().emailVerificationToken(token).build();
        userRepository.save(userWithToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userWithToken.email());
        mailMessage.setSubject("Weryfikacja adresu e-mail");
        mailMessage.setText("Link do weryfikacji: http://localhost:8080/verify-email?token=" + userWithToken.emailVerificationToken().getToken());

        mailSender.send(mailMessage);
    }



    public boolean verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationTokenToken(token).orElseThrow();
        if (!user.emailVerificationToken().isExpired()) {
            User userWithVerifiedEmail = user.toBuilder().emailVerified(true).build();
            userRepository.save(userWithVerifiedEmail);
            return true;
        }

        return false;
    }
}
