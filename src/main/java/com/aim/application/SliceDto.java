package com.aim.application;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    
    public <R> SliceDto<R> map(Function<? super T, ? extends R> mapper) {
		List<R> mappedContent = this.content.stream()
			.map(mapper)
			.collect(Collectors.toList());

		return new SliceDto<>(mappedContent, this.hasNext, this.hasPrevious);
	}
}
