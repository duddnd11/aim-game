package com.aim.dto;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import com.aim.domain.Game;
import com.aim.domain.PvpResult;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PvpMatchingDto {
	private Long pvpId;
	private GameDto gameDto;
	private long pvpTime;
	private boolean active;
	private MatchType matchType;
	
	private List<PvpMatchingMemberDto> matchingUser;
	private ConcurrentHashMap<String,CircleDto> circleMap;
	
	public PvpMatchingDto(Long pvpId, Game game, List<PvpMatchingMemberDto> matchingUser, MatchType matchType) {
		this.pvpId = pvpId;
		this.gameDto = new GameDto(game);
		this.active = true;
		this.matchType = matchType;
		
		this.matchingUser = matchingUser;
		this.circleMap = new ConcurrentHashMap<String,CircleDto>();
	}
	
	public void pvpEnd() {
		this.active=false;
	}
	
	public void pvpResult() {
		PvpMatchingMemberDto player1 =matchingUser.get(0);
		PvpMatchingMemberDto player2 =matchingUser.get(1);
		ScoreDto pvpUserScore1 = player1.getScore();
		ScoreDto pvpUserScore2 = player2.getScore();
		
		if(pvpUserScore1.getTotalScore() > pvpUserScore2.getTotalScore()) {
			pvpUserScore1.setPvpResult(PvpResult.WIN);
			pvpUserScore2.setPvpResult(PvpResult.LOSS);
		}else if(pvpUserScore1.getTotalScore() < pvpUserScore2.getTotalScore()) {
			pvpUserScore1.setPvpResult(PvpResult.LOSS);
			pvpUserScore2.setPvpResult(PvpResult.WIN);
		}else {
			pvpUserScore1.setPvpResult(PvpResult.DRAW);
			pvpUserScore2.setPvpResult(PvpResult.DRAW);
		}
		
		player1.setRating(calRating(player1.getRating(),player2.getRating(),pvpUserScore1.getPvpResult()));
		player2.setRating(calRating(player2.getRating(),player1.getRating(),pvpUserScore2.getPvpResult()));
	}
	
	public void pvpResultForfeit(Long memberId) {
		for(PvpMatchingMemberDto member : matchingUser) {
			if(member.getMemberId() == memberId) {
				member.getScore().setPvpResult(PvpResult.FORFEIT_LOSS);
			}else {
				member.getScore().setPvpResult(PvpResult.FORFEIT_WIN);
			}
		}
	}
	
	public int calRating(double currentRating, double opponentRating, PvpResult pvpResult) {
		double expectedScore = 1.0 / (1.0 + Math.pow(10, (opponentRating - currentRating) / 400.0));
        int score = pvpResult.equals(PvpResult.WIN) ? 1 : 0; // 승리 시 1, 패배 시 0
        return (int) (currentRating + 24 * (score - expectedScore));
	}
}

