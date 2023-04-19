package com.shulha.controller;

import com.shulha.dto.MessageDTO;
import com.shulha.model.Message;
import com.shulha.model.User;
import com.shulha.service.EmailService;
import com.shulha.types.MessageStatus;
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
@RequestMapping("/message")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(final EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ModelAndView getMessagePage(final ModelAndView modelAndView,
                                       final Authentication authentication,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {

        setPagination(modelAndView, page, size);

        modelAndView.addObject("name", authentication.getName());
        modelAndView.setViewName("message");
        return modelAndView;
    }

    @PostMapping("/moderate")
    public ModelAndView moderatePage(final ModelAndView modelAndView,
                                     final Authentication authentication,
                                     @ModelAttribute final MessageDTO messageDTO,
                                     @RequestParam("id") Optional<String> id,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        System.out.println(messageDTO);
        final String checkedId = id.orElseThrow(() -> new NullPointerException("Parameter ID should not be null!"));
        emailService.updateAndSendMessageForAdmin(messageDTO, authentication.getName());

        setPagination(modelAndView, page, size);

        modelAndView.addObject("name", authentication.getName());
        modelAndView.setViewName("message");
        return modelAndView;
    }

    @GetMapping("/moderate")
    public ModelAndView getMessageModeratingPage(final ModelAndView modelAndView,
                                                 final Authentication authentication,
                                                 @RequestParam("id") Optional<String> id) {
        final String checkedId = id.orElseThrow(() -> new NullPointerException("Parameter ID should not be null!"));
        final MessageDTO messageDTOById = emailService.getMessageDTOById(checkedId);
        System.out.println(messageDTOById);

        modelAndView.addObject("name", authentication.getName());
        modelAndView.addObject("message", messageDTOById);
        modelAndView.setViewName("message-moderate");
        return modelAndView;
    }

    private void setPagination(final ModelAndView modelAndView,
                               final Optional<Integer> page,
                               final Optional<Integer> size) {

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
    }
}
