package com.aim.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindIdForm {
	@NotBlank(message = "{member.email.notblank}")
	@Email(message = "{member.email.format}")
	private String email;
}
