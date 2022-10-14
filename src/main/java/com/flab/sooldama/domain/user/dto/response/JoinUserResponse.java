package com.flab.sooldama.domain.user.dto.response;

import com.flab.sooldama.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinUserResponse {

    private User user;

    public static JoinUserResponse getResponse(User user) {
        return JoinUserResponse.builder()
                .user(user)
                .build();
    }
}
