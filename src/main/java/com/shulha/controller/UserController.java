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

    @PostMapping
    public ModelAndView create(@ModelAttribute final User user, final ModelAndView modelAndView) {
//        userService.save(user);

        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ModelAndView getUser(final ModelAndView modelAndView, Principal principal,
                                Authentication authentication) {
        System.out.println(authentication.getName());
        System.out.println(principal.getName());

        final User user = new User();
        user.setAge(5);
        user.setGender(Gender.MALE);
        user.setPassword("agagashg");
        user.setName("sagsgs");
        user.setUsername("Gasdg");
        user.setRating(5);
        user.setRole(Role.USER);
        user.setEmailAddress("fsadggas@gasg.ag");
        user.setMessages(new ArrayList<>());
        final Message message = new Message();
        message.setToEmail("agasg");
        message.setCrimeType(CrimeTypes.ARSON);
        message.setMessageStatus(MessageStatus.ALLOWED);
        message.setSubject(EmailSubject.MESSAGE_TO_RELATIVES);
        message.setBody("aghsdhf");
        final Message message1 = new Message();
        message1.setToEmail("agasg");
        message1.setCrimeType(CrimeTypes.ARSON);
        message1.setMessageStatus(MessageStatus.ALLOWED);
        message1.setSubject(EmailSubject.MESSAGE_TO_RELATIVES);
        message1.setBody("aghsdhf");
        user.getMessages().add(message);
        user.getMessages().add(message1);
//        userService.save(user);
        final UserDetails userDetails = userService.loadUserByUsername(principal.getName());
//        modelAndView.addObject("password", userDetails.getPassword());
        modelAndView.setViewName("main");
        System.out.println(user);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable final String id, final ModelAndView modelAndView) {
        Objects.requireNonNull(id);

//        modelAndView.addObject("user", userService.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Not found product with id " + id)));
        modelAndView.setViewName("main");

        return modelAndView;
    }
}
