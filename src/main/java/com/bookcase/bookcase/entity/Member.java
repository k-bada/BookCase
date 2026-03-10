package com.bookcase.bookcase.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Member extends BaseEntity {
    @Id
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


