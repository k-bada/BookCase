package com.bookcase.bookcase.record.entity;

import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.global.entity.BaseEntity;
import com.bookcase.bookcase.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno", nullable = false)
    private BookInfo bookInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
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


    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void updateStatus(BookStatus status) {
        this.status = status;
    }

    public void updateCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void updateRating(Integer rating) {
        this.rating = rating;
    }

    public void updateBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

}