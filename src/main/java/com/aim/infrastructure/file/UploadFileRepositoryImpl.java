package com.aim.infrastructure.file;

import org.springframework.stereotype.Repository;

import com.aim.domain.file.entity.UploadFile;
import com.aim.domain.file.repository.UploadFileRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UploadFileRepositoryImpl implements UploadFileRepository{
	private final UploadFileJpaRepository uploadJpaRepository;

	@Override
	public UploadFile save(UploadFile uploadFile) {
		return uploadJpaRepository.save(uploadFile);
	}
}
