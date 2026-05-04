package com.bookcase.bookcase.member.repository;

import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.book.entity.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 저장 테스트")
    void saveMember() {
        // given
        Member member = Member.builder()
                .email("test@test.com")
                .password("1234")
                .nickname("테스터")
                .genre(Genre.NOVEL)
                .build();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getMemberNo()).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void findMember() {
        // given
        Member member = Member.builder()
                .email("find@test.com")
                .password("1234")
                .nickname("조회유저")
                .genre(Genre.NOVEL)
                .build();

        memberRepository.save(member);

        // when
        Member found = memberRepository.findById(member.getMemberNo()).orElseThrow();

        // then
        assertThat(found.getEmail()).isEqualTo("find@test.com");
    }
}