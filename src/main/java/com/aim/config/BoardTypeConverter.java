package com.aim.config;

import org.springframework.core.convert.converter.Converter;

import com.aim.domain.board.enums.BoardType;

import lombok.extern.slf4j.Slf4j;

/**
 * path 보드 타입 값 대문자로 변경
 */
public class BoardTypeConverter implements Converter<String, BoardType> {

	@Override
	public BoardType convert(String source) {
		try {
            return BoardType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
	}

}
