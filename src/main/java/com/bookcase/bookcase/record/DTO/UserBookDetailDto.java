package com.bookcase.bookcase.record.DTO;

import com.bookcase.bookcase.record.entity.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBookDetailDto {
    //책 정보
    private String coverImage;

    private String title;

    private String author;

    private Integer totalPage;

    //나머지
    private BookStatus status;

    private Integer progressRate;

    private Integer rating;

    private LocalDate startDate;



}
