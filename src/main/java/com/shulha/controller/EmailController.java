package com.shulha.controller;

import com.shulha.model.Message;
import com.shulha.service.EmailService;
import com.shulha.types.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mail")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(final EmailService emailService) {
        this.emailService = emailService;
    }
}
