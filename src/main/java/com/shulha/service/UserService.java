package com.shulha.service;

import com.shulha.model.User;
import com.shulha.repository.UserRepository;
import com.shulha.types.PersonStatus;
import com.shulha.types.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(@NonNull final User user) {
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password is incorrect");
        }

        if (userRepository.findUserByEmailAddressOrUsername(user.getEmailAddress(), user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setRole(Role.USER);
        user.setRating(10);
        user.setPersonStatus(PersonStatus.ACTIVE);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final User savedUser = userRepository.save(user);
        LOGGER.info("User with ID: {} was saved successfully!", savedUser.getId());
    }

    public User findById(@NonNull final String id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with ID: " + id + " is not found"));

        LOGGER.info("User with ID: " + id + " was found successfully!");
        return user;
    }

    public Iterable<User> findAll() {
        final Iterable<User> users = userRepository.findAll();
        LOGGER.info("All users was received!");
        return users;
    }

    public void deleteById(@NonNull final String id) {
        userRepository.changePersonStatusById(PersonStatus.DELETED, id);
        LOGGER.info("User with ID: {} was deleted", id);
    }

    public void deleteAll() {
        userRepository.changeAllPersonStatuses(PersonStatus.DELETED);
        LOGGER.info("All users were removed!");
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull final String emailAddressOrUsername) throws UsernameNotFoundException {
        return userRepository.findUserByEmailAddressOrUsername(emailAddressOrUsername, emailAddressOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User with emailAddressOrUsername or email " +
                        emailAddressOrUsername + " is not found"));
    }
}
