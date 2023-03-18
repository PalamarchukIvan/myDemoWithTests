package com.example.demowithtests.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceBean implements EmailService {
    private JavaMailSender sender;

    @Override
    public boolean sendTestMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            sender.send(message);
            return true;
        }catch (MailException e){
            e.printStackTrace();
            return false;
        }
    }
}
