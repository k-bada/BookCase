package com.bookcase.bookcase.record.DTO;

import com.bookcase.bookcase.record.entity.BookStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadingRecordDetailDto {

    private Long recordNo;

    private Long bookNo;
    private String genre;
    private String coverImageUrl;
    private String title;
    private String author;

    private BookStatus status;
    private Integer currentPage;
    private Integer bookPage;
    private Integer progress;
    private Boolean bookmarked;

    private Integer rating;
    private LocalDate startDate;
    private LocalDate endDate;
}
