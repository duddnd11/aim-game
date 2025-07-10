package com.aim.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
	private int status;
	private String message;
	private String field;
	private String code;
}
