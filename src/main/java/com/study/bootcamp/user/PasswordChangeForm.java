package com.study.bootcamp.user;

import jakarta.validation.constraints.NotEmpty;

public record PasswordForm(
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	String password1,

	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	String password2
) {

}
