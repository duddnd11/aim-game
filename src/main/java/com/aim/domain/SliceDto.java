package com.aim.domain;

import java.util.List;

import lombok.Data;

@Data
public class SliceDto<T> {
	private List<T> content; // 현재 페이지의 데이터
    private boolean hasNext; // 다음 페이지 여부
    private boolean hasPrevious; // 이전 페이지 여부

    public SliceDto(List<T> content, boolean hasNext, boolean hasPrevious) {
        this.content = content;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }
}
