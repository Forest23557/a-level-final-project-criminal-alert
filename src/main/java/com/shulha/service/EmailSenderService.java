package com.shulha.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);
    private final JavaMailSender mailSender;

    public EmailSenderService(@Autowired final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(final String toEmail,
                          final String subject,
                          final String body,
                          final String userEmail) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(userEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        mailSender.send(mailMessage);

        LOGGER.info("Mail sent successfully");
    }
}
