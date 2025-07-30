package com.aim.application.game;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.game.dto.GameDto;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.member.entity.AuthUser;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;
import com.aim.interfaces.game.dto.GameForm;
import com.aim.interfaces.game.dto.GameModifyForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
	private final GameRepository gameRepository;
	private final MemberRepository memberRepository;
	
	/**
	 * 게임 등록
	 * @param gameForm
	 * @return
	 */
	@Transactional
	public GameDto makeGame(GameForm gameForm, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		Game game = new Game(gameForm, member);
		return new GameDto(gameRepository.save(game));
	}
	
	/**
	 * 게임 선택
	 * @param gameId
	 * @return
	 */
	public GameDto selectGame(Long gameId) {
		Game game = gameRepository.findById(gameId).orElseThrow();
		return new GameDto(game);
	}
	
	public GameDto selectGame(Long gameId, Long memberId) {
		Game game = gameRepository.findByGameIdAndMember_MemberId(gameId,memberId).orElseThrow();
		return new GameDto(game);
	}
	
	/**
	 * 전체 게임 리스트
	 * @return
	 */
	public List<GameDto> findGameAll(){
		List<Game> gameList = gameRepository.findAll();
		List<GameDto> gameDtoList = gameList.stream()
				.map(g -> new GameDto(g))
				.collect(Collectors.toList());
		
		return gameDtoList;
	}
	
	/**
	 * 게임 수정
	 * @param gameModifyForm
	 * @return
	 */
	@Transactional
	public GameDto gameModify(GameModifyForm gameModifyForm) {
		Game game = gameRepository.findById(gameModifyForm.getGameId()).orElseThrow();
		game.gameUpdate(gameModifyForm);
		return new GameDto(game);
	}
	
	/**
	 * 사용자 생성 게임 목록
	 * @param memberId
	 * @return
	 */
	public List<GameDto> findByUser(Long memberId){
		List<GameDto> gameDtoList = gameRepository.findByMember(memberId);
		
		return gameDtoList;
	}
	
	/**
	 * 관리자 생성 게임 리스트 (메인 페이지)
	 * @return
	 */
	public List<GameDto> findMainGame(AuthUser user){
		List<GameDto> gameDtoList;
		if(user == null) {
			List<Game> gameList = gameRepository.findByAdmin();
			gameDtoList = gameList.stream()
					.map(g -> new GameDto(g))
					.collect(Collectors.toList());
		}else {
			gameDtoList = gameRepository.findMainGame(user.getMemberId());
		}
		return gameDtoList;
	}
	
	/**
	 * 게임 삭제 (상태 변경)
	 * @param gameId
	 * @param memberId
	 */
	@Transactional
	public void deleteGame(Long gameId, Long memberId) {
		Game game = gameRepository.findByGameIdAndMember_MemberId(gameId, memberId).orElseThrow();
		
		game.deleteGame();
	}
	
	/**
	 * 즐겨찾기 게임 찾기
	 * @param memberId
	 * @return
	 */
	public List<GameDto> findByHeart(Long memberId){
		List<GameDto> heartGameList = gameRepository.findByHeartGame(memberId);
		
		return heartGameList;
	}

}
