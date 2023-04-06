package com.shulha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.shulha.repository")
@EntityScan("com.shulha.model")
@SpringBootApplication
public class CriminalAlert {
    public static void main(String[] args) {
        SpringApplication.run(CriminalAlert.class, args);
    }
}
