
package com.sparta.springfirsthomework.service;

import com.sparta.springfirsthomework.dto.SignupRequestDto;
import com.sparta.springfirsthomework.repository.UserNormalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    UserNormalRepository userNormalRepository;

    @Autowired
    UserService userService;


    @Test
    public void 정상가입() {
        //given
        SignupRequestDto userOk = new SignupRequestDto("smallzoo", "joonkyu2!", "smallzoo@gmail.com");

        //when
        userService.registerUser(userOk);

        //then
        assertEquals(userOk.getUsername(), userNormalRepository.findByUsername("smallzoo").get().getUsername());
        assertEquals(userOk.getEmail(), userNormalRepository.findByUsername("smallzoo").get().getEmail());

    }

    @Test
    public void 중복회원() throws Exception {

        //given
        SignupRequestDto userA = new SignupRequestDto("kimky", "rkskk1@", "12312s@gmail.com");
        SignupRequestDto userB = new SignupRequestDto("kimky", "woo1!23", "j12s@gmail.com");
        userService.registerUser(userA);
        //when
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userB));

        //then
        String message = exception.getMessage();
        assertEquals("중복된 사용자 ID 가 존재합니다.", message);


    }

    @Test
    public void 비밀번호_오류1_비밀번호_아이디_같음() throws Exception {
        //given
        SignupRequestDto userjang = new SignupRequestDto("jang", "jang123", "12312s@gmail.com");
        //when
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userjang));
        //when
        String message = exception.getMessage();
        assertEquals("유효하지 않은 패스워드 입니다." +
                "[패스워드에 아이디와 같은 값이 포함 될 수 없습니다.]", message);
    }

    @Test
    public void 비밀번호_오류2_비밀번호_자릿수_부족() throws Exception {
        //given
        SignupRequestDto userWoo= new SignupRequestDto("wo3212o", "23", "1244312s@gmail.com");

        //when
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userWoo));
        //when
        String message = exception.getMessage();
        assertEquals("유효하지 않은 패스워드 입니다." + "[패스워드는 4자 이상 10자 이하이며 영어,숫자,특수문자를 포함해야 합니다.]", message);

    }



}
