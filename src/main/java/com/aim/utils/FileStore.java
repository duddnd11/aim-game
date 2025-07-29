package com.aim.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aim.domain.UploadFile;
import com.aim.domain.UploadFileType;
import com.aim.domain.member.entity.Member;

@Component
public class FileStore {

	private String fileDir;
	
	public void setDir(String fileDir) {
		File uploadFileDir = new File(fileDir);
		
		if(!uploadFileDir.exists()) {
			uploadFileDir.mkdirs();
		}
		
		this.fileDir = fileDir;
	}
	
	public String getFullPath(String fileName) {
		return fileDir+fileName;
	}
	
	public UploadFile storeFile(MultipartFile multipartFile, Member member, String fileDir, UploadFileType type ) {
		if (multipartFile == null || multipartFile.isEmpty()) {
			return null;
		}

		String originalFilename = multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFilename);

		setDir(fileDir);
		
		try {
			multipartFile.transferTo(new File(getFullPath(storeFileName)));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new UploadFile(storeFileName,originalFilename,fileDir,type,member);
	}
	
	private String createStoreFileName(String originalFilename) {
		String ext = extractExt(originalFilename);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;
	}
	
	private String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}
}
