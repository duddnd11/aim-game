package com.aim.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UploadFileDto {

	private Long uploadFileId;
	private String tempFileName;
	private String fileName;
	private String fileDirectory;
	
	@Enumerated(EnumType.STRING)
	private UploadFileType type;
	
	public UploadFileDto() {}
	public UploadFileDto(UploadFile uploadFile) {
		this.uploadFileId = uploadFile.getUploadFileId();
		this.tempFileName = uploadFile.getTempFileName();
		this.fileName = uploadFile.getFileName();
		this.fileDirectory = uploadFile.getFileDirectory();
		this.type = uploadFile.getType();
	}
	
}
