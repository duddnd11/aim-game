package com.aim.application.game.dto;

import com.aim.domain.member.entity.Member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PvpMatchingMemberDto{
	private Long memberId;
	private String loginId;
	private String nickname;
	private Integer rating;
	private String socketSessionId;
	private ScoreDto score;
	
	public static PvpMatchingMemberDto from(Member member, String socketSessionId) {
		PvpMatchingMemberDto pvpMatchingMemberDto = new PvpMatchingMemberDto();
		pvpMatchingMemberDto.setMemberId(member.getMemberId());
		pvpMatchingMemberDto.setLoginId(member.getLoginId());
		pvpMatchingMemberDto.setNickname(member.getNickname());
		pvpMatchingMemberDto.setRating(member.getRating());
		pvpMatchingMemberDto.setSocketSessionId(socketSessionId);
		pvpMatchingMemberDto.setScore(new ScoreDto());
		return pvpMatchingMemberDto;
	}
}
