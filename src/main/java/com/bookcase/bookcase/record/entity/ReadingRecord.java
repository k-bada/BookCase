package com.bookcase.bookcase.record.entity;

import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.global.entity.BaseEntity;
import com.bookcase.bookcase.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class ReadingRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookInfo bookInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;

    private Integer currentPage;

    @Column(nullable = false)
    private boolean bookmarked;

    private LocalDate startDate;
    private LocalDate endDate;

    //별점
    private Integer rating;
}