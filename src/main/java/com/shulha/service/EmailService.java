package com.shulha.service;

import com.shulha.model.Message;
import com.shulha.repository.EmailRepository;
import com.shulha.types.CrimeTypes;
import com.shulha.types.EmailSubject;
import com.shulha.types.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private static final String POLICE_EMAIL_ADDRESS = "forest23557@gmail.com";

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(final JavaMailSender mailSender, final EmailRepository emailRepository) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
    }

    public Message chooseSendOrModerate(@NonNull final Message message, @NonNull final String userEmail) {
        final EmailSubject subject = message.getSubject();

        Optional.ofNullable(subject)
                .orElseThrow(() -> new NullPointerException("Subject should not be null!"));

        if (subject == EmailSubject.MESSAGE_TO_RELATIVES) {
            message.setMessageStatus(MessageStatus.ALLOWED);
        } else {
            message.setMessageStatus(MessageStatus.UNMODERATED);
            changeMessage(message);
        }
        sendMail(message, userEmail);

        return message;
    }

    public Message save(@NonNull final Message message) {
        Objects.requireNonNull(message);
        final Message savedMessage = emailRepository.save(message);
        LOGGER.info("Message with ID: {} was saved successfully!", savedMessage.getId());
        return savedMessage;
    }

    public void deleteById(@NonNull final String id) {
        emailRepository.changeMessageStatusById(MessageStatus.DELETED, id);
        LOGGER.info("Message with ID: {} was removed successfully!", id);
    }

    public void deleteByUserId(@NonNull final String userId) {
        emailRepository.changeMessageStatusByUserId(MessageStatus.DELETED, userId);
        LOGGER.info("User messages whose ID: {} were removed successfully!", userId);
    }

    public Message findById(@NonNull final String id) {
        final Optional<Message> message = emailRepository.findById(id);

        message.ifPresentOrElse(
                id1 -> LOGGER.info("Message with ID: {} was found successfully!", id1),
                () -> LOGGER.info("Message with ID: {} is not found!", id)
        );

        return message
                .orElseThrow(() -> new IllegalStateException("Email with ID: " + id + " is not found!"));
    }

    public Iterable<Message> findAll() {
        final Iterable<Message> messages = emailRepository.findAll();
        LOGGER.info("All messages were received!");
        return messages;
    }

    public Iterable<Message> findByUserId(@NonNull final String userId) {
        final Iterable<Message> messages = emailRepository.findByUserId(userId);
        LOGGER.info("All user messages whose ID: {} were received!", userId);
        return messages;
    }

    private void sendMail(final Message message, final String userEmail) {
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

    private Message changeMessage(@NonNull final Message message) {
        final CrimeTypes crimeType = message.getCrimeType();
        final String body = message.getBody();

        if (crimeType == null || body == null) {
            throw new NullPointerException("Fields crimeType and body should not be null!");
        }

        final String string = String.format("Type of a crime: %s. %s", crimeType, body);

        message.setToEmail(POLICE_EMAIL_ADDRESS);

        message.setBody(string);
        LOGGER.info("Message was changed successfully!");

        return message;
    }

    private Message saveAndSend(@NonNull final Message message, @NonNull final String userEmail) {
        final Message saved = save(message);
        sendMail(saved, userEmail);

        return saved;
    }

    private Message saveAndChangeMail(@NonNull final Message message) {
        changeMessage(message);
        final Message saved = save(message);
        return saved;
    }
}
