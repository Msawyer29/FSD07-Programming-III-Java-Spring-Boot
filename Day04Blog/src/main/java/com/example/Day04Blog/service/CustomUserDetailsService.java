package com.example.Day04Blog.service;

import com.example.Day04Blog.entity.CustomUserDetails;
import com.example.Day04Blog.entity.User;
import com.example.Day04Blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepos;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepos.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

    @Transactional
    public boolean emailExists(String email){
        User findUser = userRepos.findByEmail(email);
        return (findUser != null);
    }

    public String authenticateUsername(String username) {
        String errorMsg = "";
        if (username.length() < 4 || username.length() > 20){
            errorMsg = "Username needs to be between 4-20 characters.";
        }

        return errorMsg;
    }

    @Transactional
    public boolean usernameExists(String username){
        User findUser = userRepos.findByUsername(username);
        return (findUser != null);
    }

    public List<String> authenticatePassword(String password) {
        ArrayList<String> errorMsgs = new ArrayList<>();

        if (password.length() < 6 || password.length() > 100){
            errorMsgs.add("Password length must be 6-100 characters.");
        }

        String upperCasePattern = ".*[A-Z].*";
        if (!password.matches(upperCasePattern)){
            errorMsgs.add("Password must contain at least one uppercase character.");
        }

        String lowerCasePattern = ".*[a-z].*";
        if (!password.matches(lowerCasePattern)){
            errorMsgs.add("Password must contain at least one lowercase character.");
        }

        String numbersSymbolsPattern = ".*[0-9|\\W].*";
        if (!password.matches(numbersSymbolsPattern)){
            errorMsgs.add("Password must contain at least one number or symbol.");
        }

        return errorMsgs;
    }

    public boolean checkConfirmPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }
}