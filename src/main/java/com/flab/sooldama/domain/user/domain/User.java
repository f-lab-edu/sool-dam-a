package com.flab.sooldama.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class User {

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
}
