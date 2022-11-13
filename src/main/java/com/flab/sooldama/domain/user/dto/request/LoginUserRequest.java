package com.flab.sooldama.domain.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {

	@NotNull
	@Email(regexp = "^[A-Za-z][A-Za-z0-9.-_]+@[A-Za-z0-9.-_]+.[A-Za-z]+", message = "이메일 형식에 맞지 않습니다")
	private String email;
	@NotNull
	private String password;
}
