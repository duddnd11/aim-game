package com.aim.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CertifyForm extends FindPasswordForm{
	
	@NotBlank(message = "{member.certify.notblank}")
	private String certificationNumber;
}
