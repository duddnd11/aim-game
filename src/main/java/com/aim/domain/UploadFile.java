package com.aim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class UploadFile extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uploadFileId;
	private String tempFileName;
	private String fileName;
	private String fileDirectory;
	
	@Enumerated(EnumType.STRING)
	private UploadFileType type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	public UploadFile() {}
	public UploadFile(String tempFileName, String fileName, String fileDirectory, UploadFileType type, Member member) {
		this.tempFileName = tempFileName;
		this.fileName = fileName;
		this.fileDirectory = fileDirectory;
		this.type = type;
		this.member = member;
	}
}
