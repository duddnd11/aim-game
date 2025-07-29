package com.aim.interfaces.member;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.aim.application.member.MemberService;

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
