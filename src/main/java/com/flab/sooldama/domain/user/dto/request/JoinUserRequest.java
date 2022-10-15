package com.flab.sooldama.domain.user.dto.request;

import com.flab.sooldama.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
                .id(this.getId())
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
