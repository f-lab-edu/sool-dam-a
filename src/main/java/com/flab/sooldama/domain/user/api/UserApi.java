package com.flab.sooldama.domain.user.api;

import com.flab.sooldama.domain.user.dto.request.JoinUserRequest;
import com.flab.sooldama.domain.user.dto.response.JoinUserResponse;
import com.flab.sooldama.domain.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController 어노테이션은 @Controller 와 @ResponseBody 가 결합된 어노테이션으로, 컨트롤러 클래스 하위 메서드에
@ResponseBody 를 붙이지 않아도 문자열과 JSON 등을 전송할 수 있게 해줍니다.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    /*
    @GetMapping 어노테이션은 HTTP GET 요청을 처리하는 메서드를 맵핑(@RequestMapping) 하는 어노테이션 입니다.
    Url 에 따라서 어떤 정보를 보여줄지 결정합니다.
     */

    /*
    @PostMapping 어노테이션은 HTTP POST 요청을 처리하는 메서드를 매핑하는 어노테이션입니다.
    Url에 따라서 어떤 정보를 서버에 저장할지 결정합니다.
    @ResponseStatus 어노테이션으로 메서드 실행 후 반환할 HTTP 상태 코드를 설정할 수 있습니다.
    이 어노테이션이 붙은 메서드는 반환 타입이 void 이더라도 @ResponseStatus 어노테이션에서 설정한
    상태 코드를 반환합니다.
    @RequestBody 어노테이션은 클라이언트(예를 들어 Postman)로부터 전달받은 입력을 Java 객체로 변환해줍니다.
    이 어노테이션을 붙이지 않은 채로 클라이언트에서 JSON 형식 데이터를 전달하면 DTO 객체로 변환되지 않아
    api가 올바르게 작동하지 않습니다.
     */
    @PostMapping(path = "")
    public ResponseEntity<JoinUserResponse> joinUser(@Valid @RequestBody JoinUserRequest request) {
        JoinUserResponse response = userService.insertUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
