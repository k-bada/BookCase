package com.bookcase.bookcase.record.DTO;

import com.bookcase.bookcase.record.entity.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadingRecordUpdateDto {

    private BookStatus status;
    private Integer currentPage;
    private Integer rating;
    private Boolean bookmarked;

    private LocalDate startDate;
    private LocalDate endDate;
}
