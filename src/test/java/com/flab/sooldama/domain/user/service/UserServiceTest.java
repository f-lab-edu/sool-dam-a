package com.flab.sooldama.domain.user.service;

import com.flab.sooldama.domain.user.domain.User;
import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.dto.response.JoinUserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자가 회원가입하면 DB에 회원정보가 추가되나")
    public void userInfoAddedOnDB() {
        // 회원가입할 사용자 객체 생성
        JoinUserRequest request1 = JoinUserRequest.builder()
                .id(1L)
                .email("sehoon@fmail.com")
                .password("abracadabra")
                .name("sehoon gim")
                .phoneNumber("010-1010-1010")
                .nickname("sesoon")
                .isAdult(true)
                .createdAt("2022-10-07")
                .build();

        // service에서 dao에 회원가입 요청
        userService.insertUser(request1);

        // DB에 가입한 회원 정보가 있는지 확인
        User joinedUser = userService.findUserById(request1.getId()).getUser();
        Assertions.assertThat(joinedUser.getId()).isEqualTo(1L);
    }

}