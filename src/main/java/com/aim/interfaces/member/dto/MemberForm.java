package com.aim.interfaces.member.dto;

import org.springframework.web.multipart.MultipartFile;

import com.aim.annotation.FieldMatch;
import com.aim.domain.member.enums.MemberRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "{member.password.notmatch}")
@Builder
public class MemberForm {
	
	@NotBlank(message = "{member.login-id.notblank}")
	private String loginId;
	
	@NotBlank(message = "{member.password.notblank}")
	private String password;
	
	@NotBlank(message = "{member.confirm-password.notblank}")
	private String confirmPassword;
	
	@NotBlank(message = "{member.nickname.notblank}")
	private String nickname;
	
	@NotBlank(message = "{member.email.notblank}")
	@Email(message = "{member.email.format}")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	private MultipartFile profileImg;
	
}
