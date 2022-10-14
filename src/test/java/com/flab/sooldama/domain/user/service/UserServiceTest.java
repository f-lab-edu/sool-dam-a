package com.flab.sooldama.domain.user.service;

import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

   @BeforeEach
   public void setUp() {

   }

    @Test
    public void 사용자가_회원가입하면_DB에_회원정보가_추가되나() {
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
        Assertions.assertThat(userService.findUserById(request1.getId()).getUser().getId())
                .isEqualTo(1L);
    }

}