package com.flab.sooldama.domain.user.api;

import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.dto.response.JoinUserResponse;
import com.flab.sooldama.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController 어노테이션은 @Controller 와 @ResponseBody 가 결합된 어노테이션으로, 컨트롤러 클래스 하위 메서드에
@ResponseBody 를 붙이지 않아도 문자열과 JSON 등을 전송할 수 있게 해줍니다.
 */
@RestController
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    /*
    @GetMapping 어노테이션은 HTTP GET 요청을 처리하는 메서드를 맵핑(@RequestMapping) 하는 어노테이션 입니다.
    Url 에 따라서 어떤 정보를 보여줄지 결정합니다.
     */
    @GetMapping("/")
    public String index() { // Todo: 삭제 예정 (테스트 전용입니다.)
        return "hello world";
    }

    /*
    @PostMapping 어노테이션은 HTTP POST 요청을 처리하는 메서드를 매핑하는 어노테이션입니다.
    Url에 따라서 어떤 정보를 서버에 저장할지 결정합니다.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public JoinUserResponse joinUser(JoinUserRequest request) {
        userService.insertUser(request);
        JoinUserResponse response = userService.findUserById(request.getId());
        return response;
    }
}