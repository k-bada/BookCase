package com.bookcase.bookcase.member.entity;

import com.bookcase.bookcase.global.entity.BaseEntity;
import com.bookcase.bookcase.book.entity.Genre;
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


}


