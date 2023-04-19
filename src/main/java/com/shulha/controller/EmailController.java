package com.shulha.controller;

import com.shulha.model.Message;
import com.shulha.model.User;
import com.shulha.service.EmailService;
import com.shulha.types.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/message")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(final EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ModelAndView getMessagePage(final ModelAndView modelAndView, final Authentication authentication,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        final Page<Message> paginatedMessages =
                emailService.findPaginatedMessagesForAdmin(PageRequest.of(currentPage - 1, pageSize));

        paginatedMessages.get().forEach(System.out::println);
        modelAndView.addObject("messagePage", paginatedMessages);

        final int totalPages = paginatedMessages.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        modelAndView.addObject("name", authentication.getName());
        modelAndView.setViewName("message");
        return modelAndView;
    }
}
