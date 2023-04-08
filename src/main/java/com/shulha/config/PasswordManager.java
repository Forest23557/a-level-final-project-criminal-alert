package com.shulha.config;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class PasswordManager {
    public static String getMainPassword() {
        String password = "";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream("main_password.txt"))
        )) {
            password = bufferedReader.readLine();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return password;
    }
}
