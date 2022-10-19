package com.flab.sooldama.domain.user.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter // 객체의 필드에 대한 기본 getter 를 생성해주는 lombok 의 어노테이션 입니다.
@Builder // 객체에 빌더패턴을 사용할 수 있게 도와주는 lombok 의 어노테이션 입니다.
@AllArgsConstructor // 해당 객체 내에 있는 모든 변수를 인수로 받는 생성자를 만들어주는 lombok 의 어노테이션 입니다.
public class User {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickname;
    private boolean isAdult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
