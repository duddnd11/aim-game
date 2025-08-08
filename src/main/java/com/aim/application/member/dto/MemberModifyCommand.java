package com.aim.application.member.dto;

import org.springframework.web.multipart.MultipartFile;

import com.aim.domain.member.enums.MemberRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberModifyCommand {
	private String loginId;
	private String currentPassword;
	private boolean currentPasswordDisabled;
	private String password;
	private boolean passwordDisabled;
	private String confirmPassword;
	private boolean confirmPasswordDisabled;
	private String nickname;
	private String email;
	private MemberRole role;
	private MultipartFile profileImg;
}
