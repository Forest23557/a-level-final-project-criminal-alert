package com.shulha.config;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@Component
public class PasswordManager {
    public static String getMainPassword() {
        String password = "";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                classLoader.getResourceAsStream("main_password.txt")
                        )
                )
        )) {
            password = bufferedReader.readLine();

        } catch (IOException | NullPointerException exception) {
            exception.printStackTrace();
        }

        return password;
    }
}
