package com.aim.application.member.dto;

import org.springframework.web.multipart.MultipartFile;

import com.aim.domain.member.enums.MemberRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCommand {
	private String loginId;
	private String password;
	private String nickname;
	private String email;
	private MemberRole role;
	private MultipartFile profileImg;
}
