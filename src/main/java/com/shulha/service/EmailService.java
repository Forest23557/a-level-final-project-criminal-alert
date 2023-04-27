package com.shulha.service;

import com.shulha.dto.MessageDTO;
import com.shulha.model.Message;
import com.shulha.repository.EmailRepository;
import com.shulha.types.CrimeTypes;
import com.shulha.types.EmailSubject;
import com.shulha.types.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    @Value("${police.email}")
    private String policeEmailAddress;

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(final JavaMailSender mailSender, final EmailRepository emailRepository) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
    }

    public MessageDTO getMessageDTOById(@NonNull final String id) {
        final MessageDTO messageDtoById = emailRepository.getMessageDtoById(id);

        if (messageDtoById == null) {
            throw new IllegalStateException("Message with ID: " + " is not found!");
        }

        return messageDtoById;
    }

    public void updateAndSendMessageForAdmin(@NonNull final MessageDTO messageDTO, @NonNull final String adminEmail) {
        final MessageStatus messageStatus = messageDTO.getMessageStatus();
        final String id = messageDTO.getId();
        Objects.requireNonNull(messageStatus);
        final Message message = findById(id);
        message.setMessageStatus(messageStatus);
        message.setSubject(messageDTO.getSubject());
        message.setBody(messageDTO.getBody());


        if (messageStatus == MessageStatus.ALLOWED) {
            update(message);
            sendMail(message, adminEmail);
        } else {
            Optional.ofNullable(id)
                            .ifPresent(this::deleteById);
        }

        LOGGER.info("Message with ID: {} was updated and sent!", id);
    }

    public Page<Message> findPaginatedMessages(@NonNull final Pageable pageable, @NonNull final String userId) {
        final List<Message> messages = findByUserId(userId);
        return findPaginated(pageable, messages);
    }

    public Page<Message> findPaginatedMessagesForAdmin(@NonNull final Pageable pageable) {
        final List<Message> messages = emailRepository.findByMessageStatus(MessageStatus.UNMODERATED);
        return findPaginated(pageable, messages);
    }

    public Message chooseSendOrModerate(@NonNull final Message message, @NonNull final String userEmail) {
        final EmailSubject subject = message.getSubject();

        Optional.ofNullable(subject)
                .orElseThrow(() -> new NullPointerException("Subject should not be null!"));

        if (subject == EmailSubject.MESSAGE_TO_RELATIVES) {
            message.setMessageStatus(MessageStatus.ALLOWED);
            sendMail(message, userEmail);
        } else {
            message.setMessageStatus(MessageStatus.UNMODERATED);
            changeMessage(message);
            LOGGER.info("Message was sent on moderation!");
        }

        return message;
    }

    public Message save(@NonNull final Message message) {
        final String id = message.getId();

        if (id != null) {
            throw new IllegalStateException("Message with ID: " + id + " already exists!");
        }

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
                message1 -> LOGGER.info("Message with ID: {} was found successfully!", id),
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

    public List<Message> findByUserId(@NonNull final String userId) {
        final List<Message> messages = emailRepository.findByUserId(userId);
        LOGGER.info("All user messages whose ID: {} were received!", userId);
        return messages;
    }

    private Page<Message> findPaginated(final Pageable pageable, List<Message> messageList) {
        final int pageSize = pageable.getPageSize();
        final int currentPage = pageable.getPageNumber();
        final int startItem = currentPage * pageSize;
        final List<Message> list;

        if (messageList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, messageList.size());
            list = messageList.subList(startItem, toIndex);
        }

        return new PageImpl<Message>(list, PageRequest.of(currentPage, pageSize), messageList.size());
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

        message.setToEmail(policeEmailAddress);

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

    private void update(@NonNull final Message message) {
        emailRepository.save(message);
    }
}
