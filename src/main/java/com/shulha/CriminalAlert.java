package com.shulha;

import com.shulha.config.PasswordManager;
import com.shulha.model.Message;
import com.shulha.model.MessageForRelativesBody;
import com.shulha.service.EmailSenderService;
import com.shulha.types.EmailSubject;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.shulha.repository")
@EntityScan("com.shulha.model")
@SpringBootApplication
@EnableEncryptableProperties
public class CriminalAlert {
    private final EmailSenderService emailSenderService;

    public CriminalAlert(@Autowired final EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", PasswordManager.getMainPassword());
        SpringApplication.run(CriminalAlert.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() {
        emailSenderService.sendMail("example@gmail.com",
                EmailSubject.MESSAGE_TO_RELATIVES.toString(),
                "Hi!",
                "example.com");
    }
}
