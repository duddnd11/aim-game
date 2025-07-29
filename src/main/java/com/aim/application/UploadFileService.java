package com.aim.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aim.domain.UploadFile;
import com.aim.domain.UploadFileType;
import com.aim.domain.member.entity.Member;
import com.aim.infrastructure.UploadFileRepository;
import com.aim.utils.FileStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadFileService {
	private final UploadFileRepository uploadFileRepository;
	private final FileStore fileStore;
	
	@Transactional
	public void storeFile(MultipartFile multipartFile, Member member, String fileDir, UploadFileType type) {
		UploadFile uploadFile = fileStore.storeFile(multipartFile, member, fileDir, type);
		uploadFileRepository.save(uploadFile);
	}
	
}
