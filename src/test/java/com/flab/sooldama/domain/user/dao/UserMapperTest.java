package com.flab.sooldama.domain.user.dao;

import com.flab.sooldama.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("id로 사용자 검색 시 사용자가 없을 경우 NPE 발생")
    public void findNonExistsUserById() {
        Long wrongId = -1L;

        Assertions.assertThat(userMapper.findUserById(wrongId)).isNull();
    }

    @Test
    @DisplayName("email로 사용자 검색 시 사용자가 없을 경우 NPE 발생")
    public void findNonExistsUserByEmail() {
        String wrongEmail = "wrong@email.com";

        Assertions.assertThat(userMapper.findUserByEmail(wrongEmail)).isNull();
    }

    @Test
    @DisplayName("UserMapper 클래스와 UserMapper.xml의 insertUser 반환 타입 불일치 오류 확인")
    public void insertUserReturnTypeNotMatched() {
        userMapper.deleteAllUsers();

        User user = User.builder()
                .email("sehoon@fmail.com")
                .password("abracadabra")
                .name("sehoon gim")
                .phoneNumber("010-1010-1010")
                .nickname("sesoon")
                .isAdult(true)
                .build();
        userMapper.insertUser(user);
    }
}
