package com.aim.domain.file.repository;

import com.aim.domain.file.entity.UploadFile;

public interface UploadFileRepository {
	UploadFile save(UploadFile uploadFile);
}
