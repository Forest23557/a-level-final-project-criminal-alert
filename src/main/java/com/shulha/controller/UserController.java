package com.shulha.controller;

import com.shulha.model.User;
import com.shulha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(@Autowired final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ModelAndView create(@ModelAttribute final User user, final ModelAndView modelAndView) {
        userRepository.save(user);

        return modelAndView;
    }

    @GetMapping
    public ModelAndView getUsers(final ModelAndView modelAndView) {
        modelAndView.addObject("user", userRepository.findAll());
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable final String id, final ModelAndView modelAndView) {
        Objects.requireNonNull(id);

        modelAndView.addObject("user", userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found product with id " + id)));
        modelAndView.setViewName("main");

        return modelAndView;
    }
}
