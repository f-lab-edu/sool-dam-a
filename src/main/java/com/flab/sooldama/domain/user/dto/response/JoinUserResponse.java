package com.flab.sooldama.domain.user.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinUserResponse {

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
