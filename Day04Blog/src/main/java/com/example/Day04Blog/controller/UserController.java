package com.example.Day04Blog.controller;

import com.example.Day04Blog.entity.User;
import com.example.Day04Blog.repository.UserRepository;
import com.example.Day04Blog.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepos;

    public UserController(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/register")
    ModelAndView registerUser(){
        ModelAndView mav = new ModelAndView("register-user-form");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("/register")
    String saveUser(@Valid @ModelAttribute User user, BindingResult result){
        // Check if username exists
        if(customUserDetailsService.usernameExists(user.getUsername())){
            result.addError(new FieldError("user", "username", "Username already exists, please choose another one"));
        }

        // Authenticate username
        String errorMsg = customUserDetailsService.authenticateUsername(user.getUsername());
        if(!errorMsg.isEmpty()){
            result.addError(new FieldError("user", "username", errorMsg));
        }

        // Check if email exists
        if(customUserDetailsService.emailExists(user.getEmail())){
            result.addError(new FieldError("user", "email", "Email already exists, please choose another email"));
        }

        // Authenticate password
        List<String> errorMsgs = customUserDetailsService.authenticatePassword(user.getPassword());
        if(!errorMsgs.isEmpty()){
            for(String msg : errorMsgs) {
                result.addError(new FieldError("user", "password", msg));
            }
        }

        // Check if both passwords are the same
        if(!customUserDetailsService.checkConfirmPassword(user.getPassword(), user.getConfirmPassword())){
            result.addError(new FieldError("user", "confirmPassword", "The 2 passwords don't match."));
        }

        if (result.hasErrors()){
            return "register-user-form";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepos.save(user);
        return "register-success";
    }

    @GetMapping("/users")
    public ModelAndView listUsers() {
        ModelAndView mav = new ModelAndView("list-users");
        mav.addObject("users", userRepos.findAll());
        return mav;
    }
}
