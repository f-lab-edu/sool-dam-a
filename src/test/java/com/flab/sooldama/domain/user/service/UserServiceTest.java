package com.flab.sooldama.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.sooldama.domain.user.dao.UserMapper;
import com.flab.sooldama.domain.user.domain.User;
import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.dto.response.JoinUserResponse;
import com.flab.sooldama.domain.user.exception.DuplicateEmailExistsException;
import com.flab.sooldama.domain.user.exception.NoSuchUserException;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/*
 * @ExtendWith 어노테이션은 테스트에서 사용할 클래스를 명시합니다. @ExtendWith(MockitoExtension.class)를 사용함으로써
 * openMocks 등의 메소드를 생략할 수 있습니다.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("사용자가 회원가입하면 DB에 회원정보가 추가되나")
    public void userInfoAddedOnDB() {
        // 테스트 데이터 및 동작 정의
        JoinUserRequest request = JoinUserRequest.builder()
                .email("sehoon@fmail.com")
                .password("abracadabra")
                .name("sehoon gim")
                .phoneNumber("010-1010-1010")
                .nickname("sesoon")
                .isAdult(true)
                .build();
        User user = User.builder()
                .id(1L)
                .email("sehoon@fmail.com")
                .password("abracadabra")
                .name("sehoon gim")
                .phoneNumber("010-1010-1010")
                .nickname("sesoon")
                .isAdult(true)
                .createdAt(LocalDateTime.now())
                .build();

        when(userMapper.insertUser(any(User.class))).thenReturn(JoinUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .isAdult(user.isAdult())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build());

        when(userMapper.findUserById(any(Long.TYPE))).thenReturn(user);

        // 실행
        JoinUserResponse response = userService.insertUser(request);
        JoinUserResponse joinedUserResponse = userService.findUserById(response.getId());

        // 행위 검증
        Assertions.assertThat(joinedUserResponse.getId()).isEqualTo(1L);
        verify(userMapper).insertUser(any(User.class));
        verify(userMapper).findUserById(any(Long.TYPE));
    }

    @Test
    @DisplayName("없는 아이디로 사용자를 조회")
    public void findUserWithNonExistingId() {
        // 테스트 데이터 및 동작 정의
        Long wrongId = -1L;
        when(userMapper.findUserById(wrongId)).thenThrow(NoSuchUserException.class);

        // 실행
        assertThrows(NoSuchUserException.class, () -> {
            userService.findUserById(wrongId);
        });

        // 행위 검증
        verify(userMapper).findUserById(wrongId);
    }

    @Test
    @DisplayName("입력된 이메일 주소가 이미 있을 경우")
    public void checkDuplicateEmailExists() {
        // 테스트 데이터 및 동작 정의
        JoinUserRequest request = JoinUserRequest.builder()
                .email("sehoon@fmail.com")
                .password("abracadabra")
                .name("sehoon gim")
                .phoneNumber("010-1010-1010")
                .nickname("sesoon")
                .isAdult(true)
                .build();

        when(userMapper.insertUser(any(User.class))).thenThrow(DuplicateEmailExistsException.class);

        // 실행
        assertThrows(DuplicateEmailExistsException.class, () -> {
            userService.insertUser(request);
        });

        // 행위 검증
        verify(userMapper).insertUser(any(User.class));
        verify(userMapper).findUserByEmail(any(String.class));
    }
}