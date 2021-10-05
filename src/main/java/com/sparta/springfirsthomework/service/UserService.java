package com.sparta.springfirsthomework.service;


import com.sparta.springfirsthomework.domain.model.UserNormal;
import com.sparta.springfirsthomework.dto.SignupRequestDto;
import com.sparta.springfirsthomework.repository.UserNormalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserNormalRepository userNormalRepository;

    @Autowired
    public UserService(UserNormalRepository userNormalRepository, PasswordEncoder passwordEncoder) {
        this.userNormalRepository = userNormalRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<UserNormal> found = userNormalRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드 암호화, 검증
//        if (!isRightPassword(requestDto.getPassword())) {
//            throw new IllegalArgumentException("유효하지 않은 패스워드 입니다. [패스워드는 4자 이상이며, 아이디와 같은 값 포함 될 수 없습니다.]");
//        }
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();


        UserNormal userNormal = new UserNormal(username, password, email);
        userNormalRepository.save(userNormal);
    }

    private boolean isRightPassword(String password) {
        final String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$\n";
        return Pattern.matches(pattern, password);
    }
}