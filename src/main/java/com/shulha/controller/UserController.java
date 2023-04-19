package com.shulha.controller;

import com.shulha.model.Message;
import com.shulha.model.User;
import com.shulha.service.EmailService;
import com.shulha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/message")
    public ModelAndView getMessagePage(final ModelAndView modelAndView, final Authentication authentication,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        System.out.println(currentPage);
        int pageSize = size.orElse(5);
        System.out.println(pageSize);
        final User user = userService.getUserByUserNameOrEmail(authentication.getName());
        System.out.println(user.getId());
        final Page<Message> paginatedMessages =
                emailService.findPaginatedMessages(PageRequest.of(currentPage - 1, pageSize), user.getId());

        paginatedMessages.get().forEach(System.out::println);
        modelAndView.addObject("messagePage", paginatedMessages);

        final int totalPages = paginatedMessages.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            System.out.println(pageNumbers);
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        modelAndView.addObject("name", authentication.getName());
        modelAndView.setViewName("user-message");
        return modelAndView;
    }
}
