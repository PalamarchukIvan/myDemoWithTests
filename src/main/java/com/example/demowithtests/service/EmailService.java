package com.example.demowithtests.service;

public interface EmailService {
    boolean sendTestMail(String to, String subject, String text);
}
