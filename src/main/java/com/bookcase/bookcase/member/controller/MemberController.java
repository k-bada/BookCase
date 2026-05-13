package com.bookcase.bookcase.member.controller;

import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.DTO.LoginRequestDto;
import com.bookcase.bookcase.member.DTO.SignupRequestDto;
import com.bookcase.bookcase.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto,
                                   HttpSession session) {

        LoginMemberDto loginMemberDto = memberService.login(requestDto);

        session.setAttribute("loginMember", loginMemberDto);

        return ResponseEntity.ok(loginMemberDto);
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "로그아웃되었습니다.";
    }

    @PostMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        LoginMemberDto loginMemberDto =
                (LoginMemberDto) session.getAttribute("loginMember");

        if (loginMemberDto == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다");
        }

        return ResponseEntity.ok(loginMemberDto);
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto dto) {
        memberService.signup(dto);

        return ResponseEntity.ok("회원가입 성공");
    }
}
