package com.bookcase.bookcase.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String nickname;
    private Genre genre;

    // 프로필 이미지 원본 이름
    private String profileImageName;

    // 저장된 이름
    private String profileImageSavedName;

    // 저장 경로
    private String profileImagePath;
}


