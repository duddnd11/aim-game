package com.aim.application.member.service;

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

import com.aim.application.EmailService;
import com.aim.application.UploadFileService;
import com.aim.application.member.dto.MemberCommand;
import com.aim.application.member.dto.MemberModifyCommand;
import com.aim.application.member.dto.MemberResult;
import com.aim.domain.file.enums.UploadFileType;
import com.aim.domain.member.entity.AuthUser;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;

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
	
	@Value("${member.img.path}")
	private String profileImgDir;
	
	/**
	 * 회원가입
	 * @param member
	 */
	@Transactional
	public MemberResult joinMember(MemberCommand memberCommand) {
		boolean idCheck = memberRepository.findByLoginId(memberCommand.getLoginId()).isEmpty();
		
		if(idCheck) {
			Member member = new Member(memberCommand.getLoginId(), memberCommand.getPassword(), memberCommand.getNickname(),
					memberCommand.getEmail(), memberCommand.getRole());
			
			member.encodePassword(passwordEncoder);
			member = memberRepository.save(member);
			
			MultipartFile profileImg = memberCommand.getProfileImg();
			if(profileImg != null && !profileImg.isEmpty()) {
				uploadFileService.storeFile(memberCommand.getProfileImg(), member, profileImgDir, UploadFileType.PROFILE);
			}
			return MemberResult.from(member);
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
	public MemberResult findByEmail(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(ms.getMessage("common.nosuch",null, null)));
		return MemberResult.from(member);
	}
	
	/**
	 * 비밀번호 변경 유저 확인(아이디, 이메일) -> 이메일 전송
	 * @param loginId
	 * @param email
	 * @return
	 */
	@Transactional
	public MemberResult findByLoginIdAndEmail(String loginId, String email) {
		log.info("findByLoginIdAndEmail");
		Member member = memberRepository.findByLoginIdAndEmail(loginId, email)
				.orElseThrow(() -> new NoSuchElementException(ms.getMessage("member.id.email.bad-credentials", null, null)));
		
		emailServicel.certifyMailSend(email);

		return MemberResult.from(member);
	}
	
	/**
	 * 비밀번호 변경
	 * @param loginId
	 * @return
	 */
	@Transactional
	public MemberResult modifyMemberPassword(String loginId, String password) {
		Member member = memberRepository.findByLoginId(loginId).orElseThrow();
		member.modifyPassword(password, passwordEncoder);
		return MemberResult.from(member);
	}
	
	/**
	 * 전체 회원 리스트
	 * @return
	 */
	public List<MemberResult> findMemberAll(){
		List<Member> memberList = memberRepository.findAll();
		List<MemberResult> memberDtoList = memberList.stream()
				.map(m -> MemberResult.from(m))
				.collect(Collectors.toList());
		
		return memberDtoList;
	}
	
	/**
	 * 아이디 중복체크
	 * @param logindId
	 * @return
	 */
	public MemberResult idCheck(String logindId) {
		Member member = memberRepository.findByLoginId(logindId).orElseThrow();
		return MemberResult.from(member);
	}
	
	/**
	 * 회원 정보 수정
	 * @param memberId
	 * @param memberForm
	 * @return
	 */
	@Transactional
	public MemberResult memberModify(Long memberId, MemberModifyCommand memberModifyCommand) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		member.memberUpdate(memberModifyCommand.getNickname(), memberModifyCommand.getEmail(), memberModifyCommand.getRole());
		
		if(memberModifyCommand.getProfileImg() != null && !memberModifyCommand.getProfileImg().isEmpty()) {
			uploadFileService.storeFile(memberModifyCommand.getProfileImg(), member, profileImgDir, UploadFileType.PROFILE);
		}
		
		if(memberModifyCommand.getPassword() != null && !memberModifyCommand.isCurrentPasswordDisabled() && !memberModifyCommand.isPasswordDisabled() 
				&& !memberModifyCommand.isConfirmPasswordDisabled()){
			member.modifyPassword(memberModifyCommand.getPassword(), passwordEncoder);
		}
		
		return MemberResult.from(member);
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
	public MemberResult findById(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		return MemberResult.from(member);
	}

	/**
	 * 멤버 pvp 레이팅 랭킹
	 * @param memberId
	 * @return
	 */
	public int memberPvpRank(Long memberId) {
		return memberRepository.memberRatingRank(memberId);
	}
	
	/**
	 * pvp 레이팅 순위 리스트
	 * @param memberId
	 */
	public Page<MemberResult> memberPvpRankList(int page) {
//		List<MemberDto> list = memberRepositoryCustom.memberRatingRankList(page);
		Page<Member> pvpRank = memberRepository.pvpRank(PageRequest.of(page-1, 10));
		Page<MemberResult> pvpRankDto = pvpRank.map(m -> MemberResult.from(m));
		
		return pvpRankDto;
	}
	
}
