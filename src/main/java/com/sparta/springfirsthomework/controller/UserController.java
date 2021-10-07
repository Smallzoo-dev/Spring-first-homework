package com.sparta.springfirsthomework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.springfirsthomework.dto.SignupRequestDto;
import com.sparta.springfirsthomework.service.KakaoUserService;
import com.sparta.springfirsthomework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }


    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("HasError", false);
        return "user/signup";
    }


    @PostMapping("/signup")
    public String registerUser(SignupRequestDto requestDto, Model model) {
        try {
            userService.registerUser(requestDto);
            return "redirect:/user/login";
        } catch (IllegalArgumentException e) {
            String eMessage = e.getMessage();
            model.addAttribute("HasError", true);
            model.addAttribute("ErrorMessage", eMessage);
            return "/user/signup";
        }
    }

    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}
