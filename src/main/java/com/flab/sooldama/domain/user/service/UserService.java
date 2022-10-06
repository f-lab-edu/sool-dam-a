package com.flab.sooldama.domain.user.service;

import com.flab.sooldama.domain.user.dao.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// @Service 어노테이션은 핵심 비즈니스 로직을 담은 서비스 클래스를 빈으로 등록시켜주기 위한 어노테이션입니다.
@Service
@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션입니다.
public class UserService {
    private final UserMapper userMapper;

    //ToDo : 이 곳에 서비스 로직 입력
}
