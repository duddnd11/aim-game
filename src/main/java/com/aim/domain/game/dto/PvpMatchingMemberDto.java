package com.aim.domain.game.dto;

import com.aim.domain.member.dto.MemberDto;
import com.aim.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PvpMatchingMemberDto extends MemberDto{
	private String socketSessionId;
	private ScoreDto score;
	
	public PvpMatchingMemberDto() {}
	
	public PvpMatchingMemberDto(Member member, String socketSessionId) {
		super(member);
		this.socketSessionId = socketSessionId;
		this.score = new ScoreDto();
	}
}
