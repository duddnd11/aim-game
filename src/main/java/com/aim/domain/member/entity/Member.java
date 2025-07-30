package com.aim.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aim.domain.board.BaseEntity;
import com.aim.domain.file.entity.UploadFile;
import com.aim.domain.member.enums.MemberRole;
import com.aim.interfaces.member.dto.MemberForm;
import com.aim.interfaces.member.dto.MemberModifyForm;

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
	
	public Member(MemberForm memberForm) {
		this.loginId=memberForm.getLoginId();
		this.password=memberForm.getPassword();
		this.nickname=memberForm.getNickname();
		this.email=memberForm.getEmail();
		
		this.role=memberForm.getRole() == null ? MemberRole.ROLE_USER : memberForm.getRole();
	}
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
	
	public void modifyPassword(String password, PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}
	
	public void memberUpdate(MemberModifyForm memberModifyForm) {
		this.nickname = memberModifyForm.getNickname();
		this.email = memberModifyForm.getEmail();
		this.role = memberModifyForm.getRole() == null ? this.role : memberModifyForm.getRole();
	}
	
	public void ratingUpdate(Integer rating) {
		this.rating = rating;
	}
}
