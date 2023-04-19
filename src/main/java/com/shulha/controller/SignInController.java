package com.shulha.controller;

import com.shulha.service.EmailService;
import com.shulha.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class SignInController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @GetMapping("sign-in")
    public String getSignInPage() {
        return "sign-in";
    }

    @PostMapping("sign-in")
    public ModelAndView signIn(final ModelAndView modelAndView) {
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getHome(final ModelAndView modelAndView, final Authentication authentication) {
        modelAndView.addObject("name", authentication.getName());
        modelAndView.setViewName("main");
        LOGGER.info("{} with email {} was signed in", authentication.getAuthorities(), authentication.getName());
        return modelAndView;
    }
}
