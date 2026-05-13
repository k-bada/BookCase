package com.bookcase.bookcase.member.service;

import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.DTO.LoginRequestDto;
import com.bookcase.bookcase.member.DTO.SignupRequestDto;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginMemberDto login(LoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "존재하지 않는 이메일입니다."
                ));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호가 일치하지 않습니다."
            );
        }

        return new LoginMemberDto(
                member.getMemberNo(),
                member.getEmail(),
                member.getNickname()
        );
    }

    //회원가입
    public void signup(SignupRequestDto dto) {

        //이메일 중복 체크
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "이미 사용 중인 이메일입니다."
            );
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();

        memberRepository.save(member);
    }


}
