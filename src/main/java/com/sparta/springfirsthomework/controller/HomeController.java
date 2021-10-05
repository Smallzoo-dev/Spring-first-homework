package com.sparta.springfirsthomework.controller;

import com.sparta.springfirsthomework.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

//    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        model.addAttribute("username", userDetails.getUsername());

    @GetMapping("/")
    public String home() {
        return "home";
    }
}