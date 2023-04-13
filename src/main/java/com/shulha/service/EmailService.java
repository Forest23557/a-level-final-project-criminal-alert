package com.shulha.service;

import com.shulha.model.Message;
import com.shulha.repository.EmailRepository;
import com.shulha.types.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(final JavaMailSender mailSender, final EmailRepository emailRepository) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
    }

    public void sendMail(final Message message, final String userEmail) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        final String toEmail = message.getToEmail();
        final String subject = message.getSubject().toString();
        final String messageBody = message.getBody();

        mailMessage.setFrom(userEmail);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        mailSender.send(mailMessage);

        LOGGER.info("Mail {} sent successfully", message.getId());
    }

    public Message save(@NonNull final Message message) {
        Objects.requireNonNull(message);
        return emailRepository.save(message);
    }

    public void deleteById(@NonNull final String id) {
        emailRepository.markAsDeletedById(MessageStatus.DELETED, id);
    }

    public Message findById(@NonNull final String id) {
        return emailRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Email with ID: " + id + " is not found!"));
    }

    public Iterable<Message> findAll() {
        return emailRepository.findAll();
    }
}
