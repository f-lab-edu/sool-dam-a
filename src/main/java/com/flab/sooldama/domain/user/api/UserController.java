package com.flab.sooldama.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // Todo: 삭제 예정 (테스트 전용입니다.)
    @GetMapping("/")
    public String Index() {
        return "hello world";
    }
}
