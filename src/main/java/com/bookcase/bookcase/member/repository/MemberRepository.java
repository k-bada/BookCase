package com.bookcase.bookcase.member.repository;

import com.bookcase.bookcase.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //로그인
    Optional<Member> findByEmail(String email);

    //회원가입시 중복방지
    boolean existsByEmail(String email);

}