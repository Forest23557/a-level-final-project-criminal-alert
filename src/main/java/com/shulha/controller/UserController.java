package com.shulha.controller;

import com.shulha.model.Person;
import com.shulha.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    private final PersonRepository personRepository;

    public UserController(@Autowired final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping
    public ModelAndView create(@ModelAttribute final Person person, final ModelAndView modelAndView) {
        personRepository.save(person);

        return modelAndView;
    }

    @GetMapping
    public ModelAndView getUsers(final ModelAndView modelAndView) {
        modelAndView.addObject("user", personRepository.findAll());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable final String id, final ModelAndView modelAndView) {
        Objects.requireNonNull(id);

        modelAndView.addObject("user", personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found product with id " + id)));
        modelAndView.setViewName("user");

        return modelAndView;
    }
}
