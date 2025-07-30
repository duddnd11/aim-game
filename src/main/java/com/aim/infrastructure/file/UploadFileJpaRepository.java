package com.aim.infrastructure.file;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.file.entity.UploadFile;

public interface UploadFileJpaRepository extends JpaRepository<UploadFile,Long>{

}
