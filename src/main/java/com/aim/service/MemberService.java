package com.aim.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aim.domain.AuthUser;
import com.aim.domain.Member;
import com.aim.domain.UploadFileType;
import com.aim.dto.MemberDto;
import com.aim.form.MemberForm;
import com.aim.form.MemberModifyForm;
import com.aim.repository.MemberRepository;
import com.aim.repository.MemberRepositoryCustom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PropertySource("classpath:file-path.properties")
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final UploadFileService uploadFileService;
	private final EmailService emailServicel;
	private final MessageSource ms;
	private final SessionRegistry sessionRegistry;
	private final MemberRepositoryCustom memberRepositoryCustom;
	
	@Value("${member.img.path}")
	private String profileImgDir;
	
	/**
	 * 회원가입
	 * @param member
	 */
	@Transactional
	public MemberDto joinMember(MemberForm form) {
		boolean idCheck = memberRepository.findByLoginId(form.getLoginId()).isEmpty();
		
		if(idCheck) {
			Member member = new Member(form);
			member.encodePassword(passwordEncoder);
			member = memberRepository.save(member);
			
			MultipartFile profileImg = form.getProfileImg();
			if(profileImg != null && !profileImg.isEmpty()) {
				uploadFileService.storeFile(form.getProfileImg(), member, profileImgDir, UploadFileType.PROFILE);
			}
			return new MemberDto(member);
		}else {
			throw new IllegalArgumentException(ms.getMessage("member.login-id.dupl", null, null));
		}
		
	}
	
	/**
	 * 로그인
	 * @param loginId
	 * @param password
	 * @return
	public UserDetails login(String loginId, String password) {
		// 아이디 비밀번호 체크 loadUserByUsername 실행
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		User user = new User(loginId,password,authentication.getAuthorities());
		return user;
	}
	 */

	/**
	 * 로그인 유저 찾기(아이디)
	 */
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		log.info("loadUserByUsername");
		Member member = memberRepository.findByLoginId(loginId).orElseThrow(IllegalArgumentException::new);
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(member.getRole().toString());
		authorities.add(auth);
		
		AuthUser user = new AuthUser(member);
		return user;
	}
	
	/**
	 * 아이디 찾기 (이메일)
	 * @param email
	 */
	public MemberDto findByEmail(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(ms.getMessage("common.nosuch",null, null)));
		return new MemberDto(member);
	}
	
	/**
	 * 비밀번호 변경 유저 확인(아이디, 이메일) -> 이메일 전송
	 * @param loginId
	 * @param email
	 * @return
	 */
	@Transactional
	public MemberDto findByLoginIdAndEmail(String loginId, String email) {
		log.info("findByLoginIdAndEmail");
		Member member = memberRepository.findByLoginIdAndEmail(loginId, email)
				.orElseThrow(() -> new NoSuchElementException(ms.getMessage("member.id.email.bad-credentials", null, null)));
		
		emailServicel.certifyMailSend(email);

		return new MemberDto(member);
	}
	
	/**
	 * 비밀번호 변경
	 * @param loginId
	 * @return
	 */
	@Transactional
	public MemberDto modifyMemberPassword(String loginId, String password) {
		Member member = memberRepository.findByLoginId(loginId).orElseThrow();
		member.modifyPassword(password, passwordEncoder);
		return new MemberDto(member);
	}
	
	/**
	 * 전체 회원 리스트
	 * @return
	 */
	public List<MemberDto> findMemberAll(){
		List<Member> memberList =memberRepository.findAll();
		List<MemberDto> memberDtoList = memberList.stream()
				.map(m -> new MemberDto(m))
				.collect(Collectors.toList());
		
		return memberDtoList;
	}
	
	/**
	 * 아이디 중복체크
	 * @param logindId
	 * @return
	 */
	public MemberDto idCheck(String logindId) {
		Member member = memberRepository.findByLoginId(logindId).orElseThrow();
		return new MemberDto(member);
	}
	
	/**
	 * 회원 정보 수정
	 * @param memberId
	 * @param memberForm
	 * @return
	 */
	@Transactional
	public MemberDto memberModify(Long memberId, MemberModifyForm memberModifyForm) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		member.memberUpdate(memberModifyForm);
		
		if(memberModifyForm.getProfileImg() != null && !memberModifyForm.getProfileImg().isEmpty()) {
			uploadFileService.storeFile(memberModifyForm.getProfileImg(), member, profileImgDir, UploadFileType.PROFILE);
		}
		
		if(memberModifyForm.getPassword() != null && !memberModifyForm.isCurrentPasswordDisabled() && !memberModifyForm.isPasswordDisabled() 
				&& !memberModifyForm.isConfirmPasswordDisabled()){
			member.modifyPassword(memberModifyForm.getPassword(), passwordEncoder);
		}
		
		return new MemberDto(member);
	}
	
	/**
	 * 로그인한 사용자 아이디 목록
	 * @return
	 */
	public List<String> activeMemberList(){
		return sessionRegistry.getAllPrincipals().stream()
	            .filter(principal -> principal instanceof UserDetails)
	            .map(principal -> ((UserDetails) principal).getUsername())
	            .collect(Collectors.toList());
	}
	
	/**
	 * 회원 찾기 (키 값)
	 * @param memberId
	 * @return
	 */
	public MemberDto findById(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		return new MemberDto(member);
	}

	/**
	 * 멤버 pvp 레이팅 랭킹
	 * @param memberId
	 * @return
	 */
	public int memberPvpRank(Long memberId) {
		return memberRepositoryCustom.memberRatingRank(memberId);
	}
	
	/**
	 * pvp 레이팅 순위 리스트
	 * @param memberId
	 */
	public Page<MemberDto> memberPvpRankList(int page) {
//		List<MemberDto> list = memberRepositoryCustom.memberRatingRankList(page);
		Page<Member> pvpRank = memberRepository.pvpRank(PageRequest.of(page-1, 10));
		Page<MemberDto> pvpRankDto = pvpRank.map(m -> new MemberDto(m));
		
		return pvpRankDto;
	}
	
}
