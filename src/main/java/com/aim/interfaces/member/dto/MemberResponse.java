package com.aim.interfaces.member.dto;

import java.util.ArrayList;
import java.util.List;

import com.aim.application.member.dto.MemberResult;
import com.aim.domain.file.dto.UploadFileDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
	private long memberId;
	private String loginId;
	private String nickname;
	private String email;
	@Builder.Default
	private List<UploadFileDto> profileImg = new ArrayList<UploadFileDto>();
	private Integer rating;
	
	public static MemberResponse from(MemberResult memberResult) {
		return MemberResponse.builder()
				.memberId(memberResult.getMemberId())
				.loginId(memberResult.getLoginId())
				.nickname(memberResult.getNickname())
				.email(memberResult.getEmail())
				.profileImg(memberResult.getProfileImg())
				.rating(memberResult.getRating())
				.build();
	}
}
