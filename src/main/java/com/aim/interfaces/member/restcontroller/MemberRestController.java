package com.aim.interfaces.member.restcontroller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.Auth;
import com.aim.application.member.dto.MemberResult;
import com.aim.application.member.service.MemberService;
import com.aim.config.security.UsernameEmailAuthenticationToken;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.member.dto.FindIdForm;
import com.aim.interfaces.member.dto.FindPasswordForm;
import com.aim.interfaces.member.dto.MemberForm;
import com.aim.interfaces.member.dto.MemberModifyForm;
import com.aim.interfaces.member.dto.MemberResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/member")
public class MemberRestController {
	private final MemberService memberService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	
	/**
	 * 회원가입
	 * @param memberForm
	 * @return
	 */
	@PostMapping("/join")
	public ResponseEntity<MemberResponse> joinAction(@Validated MemberForm memberForm) {
		log.info("join");
		
		MemberResult memberResult = memberService.joinMember(memberForm.toCommand());
		return ResponseEntity.ok().body(MemberResponse.from(memberResult));
	}
	
	/**
	 * 아이디 중복 찾기
	 * @param logindId
	 * @return
	 */
	@GetMapping("/id/check")
	public ResponseEntity<MemberResponse> idCheck(String logindId){
		MemberResult memberResult = memberService.idCheck(logindId);
		return ResponseEntity.ok().body(MemberResponse.from(memberResult));
	}
	
	/**
	 * 비밀번호 찾기 (아이디, 이메일 확인) 
	 * @return
	 */
	@GetMapping("/password/modify")
	public ResponseEntity<MemberResponse> passwordModify(@Validated FindPasswordForm findPasswordForm, HttpServletRequest request, HttpServletResponse response) {
		log.info("passwordModify Start");
		MemberResult memberResult = memberService.findByLoginIdAndEmail(findPasswordForm.getLoginId(),findPasswordForm.getEmail());
		
		UsernameEmailAuthenticationToken authenticationToken = new UsernameEmailAuthenticationToken(memberResult.getLoginId(), memberResult.getEmail());
		log.info("authenticationToken:"+authenticationToken);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		log.info("authentication:"+authentication);
		
		// 세션 토큰 저장
		SecurityContext context = securityContextHolderStrategy.createEmptyContext();
		context.setAuthentication(authentication);
		securityContextHolderStrategy.setContext(context);
		securityContextRepository.saveContext(context, request, response);
		log.info("passwordModify End");
		
		return ResponseEntity.ok().body(MemberResponse.from(memberResult));
	}
	
	/**
	 * 비밀번호 변경
	 * @param logindId
	 * @param password
	 * @return
	 */
	@PostMapping("/password/modify")
	public ResponseEntity<MemberResponse> passwordModify(String logindId, String password){
		MemberResult memberResult= memberService.modifyMemberPassword(logindId, password);
		return ResponseEntity.ok().body(MemberResponse.from(memberResult));
	}
	
	/**
	 * 멤버 정보 수정
	 * @param memberForm
	 * @param user
	 * @return
	 */
	@PostMapping("/modify")
	public ResponseEntity<MemberResponse> memberModify(@Validated MemberModifyForm memberModifyForm, @Auth AuthUser user, HttpServletRequest request, HttpServletResponse response){
		log.info("memberModifyRestController:"+memberModifyForm);
		MemberResult memberResult = memberService.memberModify(user.getMemberId(), memberModifyForm.toCommand());
		AuthUser modifyUser = (AuthUser) memberService.loadUserByUsername(memberResult.getLoginId());
		
		// Step 1: SecurityContextHolder 비우기
	    SecurityContextHolder.clearContext();

	    // Step 2: 새로운 UserDetails로 다시 로그인 처리
	    Authentication newAuthentication = new UsernamePasswordAuthenticationToken(modifyUser, modifyUser.getPassword(), modifyUser.getAuthorities());

	    // Step 3: SecurityContextHolder에 새로운 Authentication 설정
	    SecurityContext context = securityContextHolderStrategy.createEmptyContext();
		context.setAuthentication(newAuthentication);
		securityContextHolderStrategy.setContext(context);
		securityContextRepository.saveContext(context, request, response);
		return ResponseEntity.ok().body(MemberResponse.from(memberResult));
	}
	
	/**
	 * 로그인한 사용자 아이디 목록
	 * @return
	 */
	@GetMapping("/login")
	public ResponseEntity<List<String>> loginMember(){
		List<String> activeMemberList =  memberService.activeMemberList();
		
		return ResponseEntity.ok().body(activeMemberList);
	}
	
	/**
	 * 아이디 찾기
	 * @param findIdForm
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@GetMapping("/login-id-action")
	public ResponseEntity<MemberResponse> findLoginIdAction(@Validated FindIdForm findIdForm) {
		log.info("findLoginIdAction Controller");
		
		MemberResult memberResult = memberService.findByEmail(findIdForm.getEmail());
		
		return ResponseEntity.ok().body(MemberResponse.from(memberResult));
	}
	
}
