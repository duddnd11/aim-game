package com.aim.form;

import org.springframework.web.multipart.MultipartFile;

import com.aim.annotation.FieldMatch;
import com.aim.annotation.NotEmptyNullable;
import com.aim.domain.MemberRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "{member.password.notmatch}")
@Builder
public class MemberModifyForm {
	@NotBlank(message = "{member.login-id.notblank}")
	private String loginId;
	
	@NotEmptyNullable(message = "{member.current-password.notblank}")
	private String currentPassword;
	
	@Builder.Default
	private boolean currentPasswordDisabled = true;
	
	@NotEmptyNullable(message = "{member.password.notblank}")
	private String password;
	
	@Builder.Default
	private boolean passwordDisabled = true;
	
	@NotEmptyNullable(message = "{member.confirm-password.notblank}")
	private String confirmPassword;
	
	@Builder.Default
	private boolean confirmPasswordDisabled = true;
	
	@NotBlank(message = "{member.nickname.notblank}")
	private String nickname;
	
	@NotBlank(message = "{member.email.notblank}")
	@Email(message = "{member.email.format}")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	private MultipartFile profileImg;
}
