package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.entitys.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailPath;
    private JavaMailSender javaMailSender;

    public void sendNotifications(User user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(emailPath);
        mail.setSubject("Registration confirmation.");
        mail.setText("Successfully registered.");

        javaMailSender.send(mail);
    }
}
