package com.aim.domain.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aim.domain.file.dto.UploadFileDto;
import com.aim.domain.file.enums.UploadFileType;
import com.aim.domain.member.enums.MemberRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthUser implements UserDetails{
	private long memberId;
	private String loginId;
	private String password;
	private String nickname;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	private List<UploadFileDto> profileImg = new ArrayList<UploadFileDto>();
	private Integer rating;
	private LocalDateTime createdDate;
	
	public AuthUser(Member member) {
		this.memberId=member.getMemberId();
		this.loginId=member.getLoginId();
		this.password=member.getPassword();
		this.nickname=member.getNickname();
		this.email=member.getEmail();
		this.role=member.getRole();
		this.profileImg = member.getProfileImg().stream()
								.filter(uf -> uf.getType().equals(UploadFileType.PROFILE))
								.map(uf -> new UploadFileDto(uf))
								.collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
						            Collections.reverse(list);
						            return list;
						        }));
		this.rating=member.getRating();
		this.createdDate=member.getCreatedDate();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(role.toString()));
		return auth;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.loginId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
