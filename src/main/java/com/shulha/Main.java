package com.shulha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

public class Main {
//    @EnableJpaRepositories("com.shulha.repository")
//    @EntityScan("com.shulha.model")
    @SpringBootApplication
    @EnableEncryptableProperties
    @PropertySource(name="EncryptedProperties", value="classpath:application.properties")
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
