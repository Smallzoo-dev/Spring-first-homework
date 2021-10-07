package com.sparta.springfirsthomework.service;


import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.dto.SignupRequestDto;
import com.sparta.springfirsthomework.repository.UserNormalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserNormalRepository userNormalRepository;

    public void registerUser(SignupRequestDto requestDto) {

        //Id, PW 유효성 체크
        isValidateId(requestDto.getUsername(), userNormalRepository.findByUsername(requestDto.getUsername()));
        isValidatePassword(requestDto.getUsername(), requestDto.getPassword());

        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();


        UserNormal userNormal = new UserNormal(requestDto.getUsername(), password, email);
        userNormalRepository.save(userNormal);
    }

    public UserNormal findByUsername(String username) {
        Optional<UserNormal> byUsername = userNormalRepository.findByUsername(username);
        return byUsername.get();
    }

    private void isValidateId(String username, Optional<UserNormal> found) {
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        } else if (username.length() < 3) {
            throw new IllegalArgumentException("아이디는 세 글자 이상 입력해야 합니다.");
        }
    }

    private void isValidatePassword(String username, String password) {
        final int MIN = 4;
        final int MAX = 10;
        final String pattern = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + MIN + "," + MAX + "})$";

        if (password.contains(username)) {
            throw new IllegalArgumentException("유효하지 않은 패스워드 입니다." +
                    "[패스워드에 아이디와 같은 값이 포함 될 수 없습니다.]");
        } else if (!Pattern.matches(pattern, password)) {
            throw new IllegalArgumentException("유효하지 않은 패스워드 입니다." +
                    "[패스워드는 4자 이상 10자 이하이며 영어,숫자,특수문자를 포함해야 합니다.]");
        }

    }
}