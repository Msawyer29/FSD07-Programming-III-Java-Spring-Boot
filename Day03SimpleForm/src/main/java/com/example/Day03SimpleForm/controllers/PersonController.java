package com.example.Day03SimpleForm.controllers;

import com.example.Day03SimpleForm.entities.Person;
import com.example.Day03SimpleForm.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    // Constructor injection
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/")
    String showForm(Model model) {
        model.addAttribute("person", new Person());
        return "people";
    }

    @PostMapping("/register")
    public String registerPerson(@Valid @ModelAttribute("person") Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Validation errors: {}", result.getAllErrors());
            model.addAttribute("person", person);
            return "people";
        }

        // From Spring CRUD document:
        // use the returned instance for further implementation as the save operation may have modified the entity
        person = personRepository.save(person);
        System.out.println("Registered person: " + person.getName() + ", " + person.getAge());
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "success";
    }
}
