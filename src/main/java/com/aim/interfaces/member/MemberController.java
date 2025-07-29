package com.aim.interfaces.member;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aim.annotation.Auth;
import com.aim.annotation.UserEmailAuth;
import com.aim.application.game.ScoreService;
import com.aim.application.member.MemberService;
import com.aim.domain.game.dto.ScoreCountDto;
import com.aim.domain.member.dto.MemberDto;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.member.dto.FindIdForm;
import com.aim.interfaces.member.dto.FindPasswordForm;
import com.aim.interfaces.member.dto.MemberForm;
import com.aim.interfaces.member.dto.MemberModifyForm;
import com.aim.interfaces.member.dto.PasswordModifyForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final ScoreService scoreService;
	
	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		log.info("login Controller");
		return "member/login";
	}
	
	/*
	@PostMapping("/login")
	public String loginAction(String loginId, String password) {
		log.info("loginAction:"+loginId);
		User user = (User) memberService.login(loginId, password);
		return null;
	}
	*/
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String join(Model model, @ModelAttribute("memberForm") MemberForm memberForm) {
		return "member/join";
	}
	
	/**
	 * 회원가입 액션
	 * @param memberForm
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/join")
	public String joinAction(@Validated MemberForm memberForm, BindingResult bindingResult) {
		log.info("memberForm:"+memberForm);
		
		if (bindingResult.hasErrors()) {
			 log.info("errors={}", bindingResult);
			 return "member/join";
		}
		
		memberService.joinMember(memberForm);
		return "redirect:/";
	}
	
	/**
	 * 아이디 찾기 페이지
	 * @return
	 */
	@GetMapping("/login-id")
	public String findLoginId(Model model) {
		log.info("login-id");
		model.addAttribute("findIdForm",new FindIdForm());
		return "member/login-id";
	}
	
	/**
	 * 비밀번호 찾기 페이지
	 * @return
	 */
	@GetMapping("/password")
	public String password(Model model, @ModelAttribute("findPasswordForm") FindPasswordForm findPasswordForm) {
		return "member/password";
	}
	
	/**
	 * 비밀번호 변경 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/password/modify")
	public String passwordModify(Model model, @UserEmailAuth AuthUser user, HttpServletRequest request, HttpServletResponse response) {
		log.info("user:"+user);
		if(user == null) {
			log.info("user null");
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			
			return "redirect:/member/password";
		}
		
		model.addAttribute("passwordModifyForm", new PasswordModifyForm());
		return "member/password-modify";
	}
	
	/**
	 * 비밀번호 변경
	 * @param passwordModifyForm
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/password/modify")
	public String passwordModifyAction(@Validated PasswordModifyForm passwordModifyForm, BindingResult bindingResult, @UserEmailAuth AuthUser user) {
		log.info("passwordModifyAction:"+passwordModifyForm);
		
		if (bindingResult.hasErrors()) {
			log.info("errors={}", bindingResult);
			return "member/password-modify";
		}
		
		memberService.modifyMemberPassword(user.getUsername(), passwordModifyForm.getPassword());
		
		return "member/login";
	}
	
	/**
	 * 멤버 정보 수정 페이지
	 * @param user
	 * @param model
	 * @return
	 */
	@GetMapping("/modify")
	public String memberModify(@Auth AuthUser user, Model model) {
		log.info("memberModify");
		MemberDto memberDto = (MemberDto) model.getAttribute("member");
		MemberModifyForm memberModifyForm = MemberModifyForm.builder()
								.loginId(memberDto.getLoginId())
								.nickname(memberDto.getNickname())
								.email(memberDto.getEmail())
								.build();
		model.addAttribute("memberModifyForm", memberModifyForm);
		
		return "member/member-modify";
	}
	
	/**
	 * 마이페이지
	 * @param user
	 * @param model
	 * @return
	 */
	@GetMapping("/mypage")
	public String mypage(@Auth AuthUser user, Model model) {
		int myRank = memberService.memberPvpRank(user.getMemberId());
		ScoreCountDto scoreCount = scoreService.memberScoreCount(user.getMemberId());
		
		model.addAttribute("scoreCount", scoreCount);
		model.addAttribute("myRank",myRank);
		
		return "member/mypage";
	}
	
	/**
	 * pvp 랭크
	 * @param user
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping("/rank/{page}")
	public String rank(@Auth AuthUser user, Model model, @PathVariable(value="page") int page) {
		Page<MemberDto> rankList = memberService.memberPvpRankList(page);
		model.addAttribute("rankList",rankList);
		return "member/rank";
	}
	
}
