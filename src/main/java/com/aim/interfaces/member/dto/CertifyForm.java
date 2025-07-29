package com.aim.interfaces.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CertifyForm extends FindPasswordForm{
	
	@NotBlank(message = "{member.certify.notblank}")
	private String certificationNumber;
}
