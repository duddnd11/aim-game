package com.aim.domain;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder.Default;
import lombok.Getter;

@Entity
@Getter
@DynamicInsert
public class Certification extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long certificationId;
	private String certificationNumber;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private YnType status;
	
	public Certification() {}
	
	public Certification(String email, String certificationNumber) {
		this.email = email;
		this.certificationNumber = certificationNumber;
	}
	
	public void changeStatusY() {
		this.status = YnType.Y;
	}
}
