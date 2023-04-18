package com.shulha.controller;

import com.shulha.model.Message;
import com.shulha.model.User;
import com.shulha.service.EmailService;
import com.shulha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    public UserController(final UserService userService, final EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
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

    @GetMapping("/message/create")
    public ModelAndView getMessageCreatingPage(final ModelAndView modelAndView, final Authentication authentication) {
        final Message message = new Message();

        modelAndView.addObject("name", authentication.getName());
        modelAndView.addObject("message", message);
        modelAndView.setViewName("user-message-create");
        return modelAndView;
    }

    @PostMapping("/message/create")
    public ModelAndView createMessage(@ModelAttribute final Message message, final ModelAndView modelAndView,
                                      final Authentication authentication) {

        final User user = userService.getUserByUserNameOrEmail(authentication.getName());
        final Message changedMessage = emailService.chooseSendOrModerate(message, authentication.getName());
        user.getMessages().add(changedMessage);
        userService.update(user);
        modelAndView.addObject("name", authentication.getName());
        modelAndView.addObject("message", message);
        modelAndView.setViewName("main");
        return modelAndView;
    }
}
