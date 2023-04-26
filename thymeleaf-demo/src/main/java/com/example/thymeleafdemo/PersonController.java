package com.example.thymeleafdemo;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class PersonController {

    @GetMapping("/people")
    String getPeople(Model model){
        model.addAttribute("something", "this is coming from the controller"); // this is a variable we can pass to the html file
        model.addAttribute("people", Arrays.asList(
                new Person("Joe", 30),
                new Person("Jane", 29)
        ));
        return "people";
    }

}
