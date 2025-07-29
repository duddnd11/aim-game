package com.aim.domain.member.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.aim.domain.BaseDto;
import com.aim.domain.UploadFileDto;
import com.aim.domain.UploadFileType;
import com.aim.domain.member.entity.AuthUser;
import com.aim.domain.member.entity.Member;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // 비교시 부모 클래스 필드값 체크
public class MemberDto extends BaseDto{
	private long memberId;
	private String loginId;
	private String nickname;
	private String email;
	private List<UploadFileDto> profileImg = new ArrayList<UploadFileDto>();
	private Integer rating;
	
	public MemberDto() {}
	
	public MemberDto(long memberId, String loginId, String nickname,String email) {
		this.memberId=memberId;
		this.loginId=loginId;
		this.nickname=nickname;
		this.email=email;
	}
	
	public MemberDto(Member member) {
		this.memberId=member.getMemberId();
		this.loginId=member.getLoginId();
		this.nickname=member.getNickname();
		this.email=member.getEmail();
		this.setCreatedDate(member.getCreatedDate());
		this.setModifiedDate(member.getModifiedDate());
		this.profileImg = member.getProfileImg().stream()
								.filter(uf -> uf.getType().equals(UploadFileType.PROFILE))
								.map(uf -> new UploadFileDto(uf))
								.collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
						            Collections.reverse(list);
						            return list;
						        }));
		this.rating=member.getRating();
	}
	
	public MemberDto(AuthUser user) {
		this.memberId=user.getMemberId();
		this.loginId=user.getUsername();
		this.nickname=user.getNickname();
		this.email=user.getEmail();
		this.profileImg = user.getProfileImg();
		this.rating=user.getRating();
		this.setCreatedDate(user.getCreatedDate());
	}
}
