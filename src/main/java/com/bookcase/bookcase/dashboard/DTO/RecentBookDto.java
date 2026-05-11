package com.bookcase.bookcase.dashboard.DTO;

import com.bookcase.bookcase.record.entity.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecentBookDto {

    private Long recordNo;
    private Long bookNo;

    private String title;
    private String author;
    private String coverImageUrl;

    private BookStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
