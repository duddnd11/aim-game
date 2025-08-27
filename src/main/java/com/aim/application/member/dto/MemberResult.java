package com.aim.application.member.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.aim.application.BaseDto;
import com.aim.domain.file.dto.UploadFileDto;
import com.aim.domain.file.enums.UploadFileType;
import com.aim.domain.member.entity.AuthUser;
import com.aim.domain.member.entity.Member;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true) // 비교시 부모 클래스 필드값 체크
@NoArgsConstructor
public class MemberResult extends BaseDto{
	private long memberId;
	private String loginId;
	private String nickname;
	private String email;
	@Builder.Default
	private List<UploadFileDto> profileImg = new ArrayList<UploadFileDto>();
	private Integer rating;
	
	public static MemberResult from(Member member) {
		return MemberResult.builder()
				.memberId(member.getMemberId())
				.loginId(member.getLoginId())
				.nickname(member.getNickname())
				.email(member.getEmail())
				.createdDate(member.getCreatedDate())
				.modifiedDate(member.getModifiedDate())
				.rating(member.getRating())
				.profileImg(member.getProfileImg().stream()
								.filter(uf -> uf.getType().equals(UploadFileType.PROFILE))
								.map(uf -> new UploadFileDto(uf))
								.collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
						            Collections.reverse(list);
						            return list;
						        })))
				.build();
	}
	
	public static MemberResult of(long memberId, String loginId, String nickname,String email) {
		return MemberResult.builder()
				.memberId(memberId)
				.loginId(loginId)
				.nickname(nickname)
				.email(email)
				.build();
	}
	
	public static MemberResult fromAuth(AuthUser user) {
		return MemberResult.builder()
				.memberId(user.getMemberId())
				.loginId(user.getLoginId())
				.nickname(user.getNickname())
				.email(user.getEmail())
				.profileImg(user.getProfileImg())
				.rating(user.getRating())
				.createdDate(user.getCreatedDate())
				.build();
	}
}
