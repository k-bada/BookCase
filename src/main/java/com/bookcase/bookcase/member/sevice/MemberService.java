package com.bookcase.bookcase.member.sevice;

import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.DTO.LoginRequestDto;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public LoginMemberDto login(LoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!member.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new LoginMemberDto(
                member.getMemberNo(),
                member.getEmail(),
                member.getNickname()
        );
    }

}
