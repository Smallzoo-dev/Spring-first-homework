package com.sparta.springfirsthomework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.springfirsthomework.dto.SignupRequestDto;
import com.sparta.springfirsthomework.service.KakaoUserService;
import com.sparta.springfirsthomework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    // 회원 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    // 회원 가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/user/login";
    }

    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}
