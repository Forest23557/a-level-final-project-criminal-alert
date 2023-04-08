package com.shulha;

import com.shulha.config.PasswordManager;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.shulha.repository")
@EntityScan("com.shulha.model")
@SpringBootApplication
@EnableEncryptableProperties
public class CriminalAlert {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", PasswordManager.getMainPassword());
        SpringApplication.run(CriminalAlert.class, args);
    }
}
