package com.studymate;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;

@Configuration
@Profile("emailVerificationTests")
public class MailTestConfig {

    private GreenMail testSmtp;

    @PostConstruct
    public void startMailServer() {
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
    }

    @PreDestroy
    public void stopMailServer() {
        testSmtp.stop();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        return mailSender;
    }
}
