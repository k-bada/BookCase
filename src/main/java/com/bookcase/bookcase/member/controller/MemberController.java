package com.bookcase.bookcase.member.controller;

import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.DTO.LoginRequestDto;
import com.bookcase.bookcase.member.sevice.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto requestDto, HttpSession session){

        LoginMemberDto loginMemberDto = memberService.login(requestDto);

        session.setAttribute("loginMember", loginMemberDto);

        return "로그인 되셨습니다.";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "로그아웃되었습니다.";
    }

    @PostMapping("/me")
    public LoginMemberDto  me(HttpSession session) {
        LoginMemberDto loginMemberDto =
                (LoginMemberDto) session.getAttribute("loginMember");

        if (loginMemberDto == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return loginMemberDto;
    }

}
