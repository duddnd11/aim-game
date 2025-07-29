package com.aim.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/uploadFile")
@PropertySource("classpath:file-path.properties")
public class UploadFileRestController {
	
	@Value("${member.img.path}")
	private String profileImgDir;
	
	@GetMapping("/imgView/{fileName}")
	public byte[] getImg(@PathVariable(name="fileName") String fileName) throws IOException {
		log.info("getImg:"+profileImgDir+fileName);
		Path path = Paths.get(profileImgDir+fileName);
		return Files.readAllBytes(path);
	}
}
