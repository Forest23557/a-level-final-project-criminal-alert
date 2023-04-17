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

    @GetMapping
    public ModelAndView save(final ModelAndView modelAndView) {
        final Message message = new Message();

        message.setMessageStatus(MessageStatus.ALLOWED);
        message.setBody("Some info");

        modelAndView.addObject("mail", emailService.save(message));
        modelAndView.setViewName("main");
        System.out.println(message);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView delete(@PathVariable final String id, final ModelAndView modelAndView) {
        emailService.deleteById(id);

        final Message message = emailService.findById(id);

        modelAndView.addObject("mail", message);
        modelAndView.setViewName("main");
        System.out.println(message);

        return modelAndView;
    }

    @GetMapping("user/{id}")
    public ModelAndView findByUserId(@PathVariable final String id, final ModelAndView modelAndView) {
        emailService.deleteByUserId(id);
        final Iterable<Message> messages = emailService.findByUserId(id);
        System.out.println(messages);

        for (Message message : messages) {
            modelAndView.addObject("mail", message);
            modelAndView.setViewName("main");
            System.out.println(message);
        }

        return modelAndView;
    }
}
