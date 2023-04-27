package com.example.Day03SimpleForm.controllers;

import com.example.Day03SimpleForm.entities.Person;
import com.example.Day03SimpleForm.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PersonController {

    // Constructor injection
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/")
    String showForm(Model model) {
        model.addAttribute("person", new Person());
        return "people";
    }

    @PostMapping("/register")
    public String registerPerson(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "people";
        }

        // From Spring CRUD document:
        // use the returned instance for further implementation as the save operation may have modified the entity
        person = personRepository.save(person);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "success";
    }
}
