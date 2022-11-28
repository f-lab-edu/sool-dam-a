package com.flab.sooldama.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.sooldama.domain.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * @NoArgsConstructor 어노테이션은 파라미터가 없는 생성자를 만들어줍니다.
 * @AllArgsConstructor 어노테이션은 이 어노테이션이 붙은 클래스 안 모든 필드를 파라미터로 하는 생성자를 만들어줍니다.
 * @Email 어노테이션은 해당 필드의 값이 유효한 이메일 주소인지를 검증합니다. 필드값이 null일 때도 유효하다고 판단해 true를 반환합니다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinUserRequest {

	@NotNull
	@Email(regexp = "^[A-Za-z][A-Za-z0-9.-_]+@[A-Za-z0-9.-_]+.[A-Za-z]+", message = "이메일 형식에 맞지 않습니다")
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String name;
	@NotNull
	private String phoneNumber;
	private String nickname;
	@NotNull @JsonProperty
	private boolean isAdult;

	@JsonProperty("isAdult")
	public boolean getIsAdult() {
		return this.isAdult;
	}

	public User toUser() {
		return User.builder()
			.email(this.getEmail())
			.password(this.getPassword())
			.name(this.getName())
			.phoneNumber(this.getPhoneNumber())
			.nickname(this.getNickname())
			.isAdult(getIsAdult())
			.build();
	}

}
