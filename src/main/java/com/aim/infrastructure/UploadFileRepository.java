package com.aim.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.UploadFile;

public interface UploadFileRepository extends JpaRepository<UploadFile,Long>{

}
