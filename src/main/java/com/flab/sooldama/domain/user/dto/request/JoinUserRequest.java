package com.flab.sooldama.domain.user.dto.request;

import com.flab.sooldama.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * @NoArgsConstructor 어노테이션은 파라미터가 없는 생성자를 만들어줍니다.
 * @AllArgsConstructor 어노테이션은 이 어노테이션이 붙은 클래스 안 모든 필드를 파라미터로 하는 생성자를 만들어줍니다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinUserRequest {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickname;
    private boolean isAdult;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public User toUser() {
        return User.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .name(this.getName())
                .phoneNumber(this.getPhoneNumber())
                .nickname(this.getNickname())
                .isAdult(this.isAdult())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(this.getDeletedAt())
                .build();
    }

}
