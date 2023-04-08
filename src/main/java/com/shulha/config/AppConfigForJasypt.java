package com.shulha.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EncryptablePropertySource("encrypted.properties")
public class AppConfigForJasypt {
}
