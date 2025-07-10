package com.aim.form;

import com.aim.annotation.FieldMatch;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "{member.password.notmatch}")
public class PasswordModifyForm {
	@NotBlank(message = "{member.password.notblank}")
	private String password;
	
	@NotBlank(message = "{member.confirm-password.notblank}")
	private String confirmPassword;
	
}
