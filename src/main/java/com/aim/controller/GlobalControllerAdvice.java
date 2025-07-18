package com.aim.controller;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.aim.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
	
	private final MemberService memberService;

	@ModelAttribute("activeMemberList")
    public List<String> activeMemberList() {
        return memberService.activeMemberList();
    }
	
}
