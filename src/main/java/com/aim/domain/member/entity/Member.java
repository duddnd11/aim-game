package com.aim.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aim.domain.board.BaseEntity;
import com.aim.domain.file.entity.UploadFile;
import com.aim.domain.member.enums.MemberRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
@DynamicInsert
public class Member extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	private String loginId;
	private String password;
	private String nickname;
	private String email;
	private Integer rating;
	
	@Enumerated(EnumType.STRING)
	private MemberRole role;
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<UploadFile> profileImg = new ArrayList<UploadFile>();
	
	public Member() {}
	
	public Member(String loginId, String password, String nickname, String email, MemberRole role) {
		this.loginId=loginId;
		this.password=password;
		this.nickname=nickname;
		this.email=email;
		
		this.role=role == null ? MemberRole.ROLE_USER : role;
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
	
	public void modifyPassword(String password, PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}
	
	public void memberUpdate(String nickname, String email, MemberRole role) {
		this.nickname = nickname;
		this.email = email;
		this.role = role == null ? this.role : role;
	}
	
	public void ratingUpdate(Integer rating) {
		this.rating = rating;
	}
}
