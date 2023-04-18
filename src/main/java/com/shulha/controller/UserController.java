package com.shulha.controller;

import com.shulha.model.Message;
import com.shulha.model.User;
import com.shulha.repository.UserRepository;
import com.shulha.service.UserService;
import com.shulha.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public ModelAndView getSignUpForm(final ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("sign-up");
        return modelAndView;
    }

    @PostMapping("/sign-up")
    public ModelAndView setUser(@ModelAttribute final User user,  final ModelAndView modelAndView) {
        userService.save(user);

        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


}
