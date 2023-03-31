package com.example.demowithtests.service.EmailService;

public interface EmailService {
    boolean sendTestMail(String to, String subject, String text);
}
