package com.aim.interfaces.game.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aim.annotation.Auth;
import com.aim.application.game.dto.GameDto;
import com.aim.application.game.dto.GameResult;
import com.aim.application.game.dto.PvpCommand;
import com.aim.application.game.dto.PvpMatchingMemberDto;
import com.aim.application.game.service.GameService;
import com.aim.application.game.service.PvpScoreService;
import com.aim.application.game.service.PvpService;
import com.aim.domain.game.enums.EndType;
import com.aim.domain.game.enums.MatchType;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.game.dto.PvpClickRequest;
import com.aim.interfaces.game.dto.PvpEndRequest;
import com.aim.interfaces.game.dto.PvpMatchingMemberRequest;
import com.aim.interfaces.game.dto.PvpMatchingRequest;
import com.aim.interfaces.game.dto.PvpRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("pvp")
@RequiredArgsConstructor
public class PvpController {
	private final GameService gameService;
	private final SimpMessagingTemplate messagingTemplate;
	private final PvpService pvpService;
	private final PvpScoreService pvpScoreService;

	
	@GetMapping("")
	public String pvpGameList(Model model, @Auth AuthUser user) {
		List<GameResult> gameList = gameService.findMainGame(user);
		model.addAttribute("gameList", gameList);
		return "game/pvp-list";
	}
	
	/**
	 * pvp 페이지 이동
	 * @param user
	 * @return
	 */
	@GetMapping("/{gameId}")
	public String pvpGame(@PathVariable(value = "gameId") Long gameId, Model model, @Auth AuthUser user) {
		GameResult gameResult = gameService.selectGame(gameId);
		model.addAttribute("game", gameResult);
		return "game/pvp";
	}
	
	/**
	 * pvp 매칭
	 * @param principal
	 * @param headerAccessor
	 * @param game
	 */
	@MessageMapping("/matching")
	public void pvpGameMatching(Principal principal, SimpMessageHeaderAccessor headerAccessor, PvpRequest pvpRequest) {
		log.info("pvpGameMatching");
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		List<PvpMatchingMemberDto> matchingUser = pvpService.pvpMatchingQue(user.getMemberId(), headerAccessor.getSessionId(), pvpRequest.toCommand());
		messagingTemplate.convertAndSendToUser(user.getUsername(),"/pvp/que",true);
		
		if(matchingUser.size() == 2) {
			pvpService.gameStart(pvpRequest.toCommand(), matchingUser, MatchType.NEWMATCH);
		}
	}
	
	/**
	 * pvp 리매치 요청
	 * @param pvpMatchingMember
	 */
	@MessageMapping("/rematching")
	public void pvpRematching(PvpMatchingMemberRequest pvpMatchingMember) {
		log.info("pvpRematching");
		messagingTemplate.convertAndSendToUser(pvpMatchingMember.getLoginId(),"/pvp/rematching",true);
	}
	
	/**
	 * 리매칭 
	 * @param pvpMatching
	 */
	@MessageMapping("/rematching/decision")
	public void rematchingDecision(PvpMatchingRequest pvpMatching, @Header("decision") boolean decision, SimpMessageHeaderAccessor headerAccessor) {
		log.info("rematchingDecision");
		GameDto game = pvpMatching.getGameDto();
		List<PvpMatchingMemberDto> matchingUser = pvpMatching.getMatchingUser();
		String sessionId = headerAccessor.getSessionId();
		if(decision) {
			for(PvpMatchingMemberDto pvpUser: matchingUser) {
				pvpUser.getScore().scoreReset();
			}
			pvpService.gameStart(PvpCommand.of(game.getGameId()), matchingUser, MatchType.REMATCH);
		}else {
			for(int i=0; i<matchingUser.size(); i++) {
				messagingTemplate.convertAndSendToUser(matchingUser.get(i).getLoginId(),"/pvp/rematching/decision",sessionId);
			}
		}
	}
	
	/**
	 * pvp 게임 종료
	 * @param principal
	 * @param pvpMatching
	 */
	@MessageMapping("/pvp/end")
	public void pvpQuit(Principal principal, PvpEndRequest pvpEndRequest, @Header("endType") EndType endType) {
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		pvpScoreService.gameEnd(pvpEndRequest.toCommand(), endType, user.getMemberId());
	}
	
	/**
	 * pvp 마우스 클릭
	 * @param principal
	 * @param pvpClickRequest
	 */
	@MessageMapping("/pvp/click")
	public void click(Principal principal, PvpClickRequest pvpClickRequest, SimpMessageHeaderAccessor headerAccessor) {
		//log.info("click:"+pvpClickRequest);
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		pvpService.targetClick(pvpClickRequest.toCommand(user.getMemberId(), headerAccessor.getSessionId()));
	}
	
	/**
	 * 타겟 상태 전체 업데이트
	 * @param principalㅌ
	 * @param pvpClickRequest
	 * @param headerAccessor
	 */
	@MessageMapping("/pvp/state")
	public void pvpState(Principal principal, PvpClickRequest pvpClickRequest, SimpMessageHeaderAccessor headerAccessor) {
		log.info("pvpState");
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		pvpService.pvpState(pvpClickRequest.toCommand(user.getMemberId(), headerAccessor.getSessionId()));
	}

	/**
	 * 매칭 신청 큐 취소
	 * @param principal
	 * @param gameId
	 * @param headerAccessor
	 */
	@MessageMapping("/pvp/quitQue")
	public void quitQue(Principal principal, Long gameId, SimpMessageHeaderAccessor headerAccessor) {
		log.info("quitQue");
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		pvpService.quitQue(user.getMemberId(), headerAccessor.getSessionId(), gameId);
	}
}
