package com.bookcase.bookcase.record.DTO;

import com.bookcase.bookcase.record.entity.BookStatus;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadingRecordListDto {

    private Long recordNo;

    private Long bookNo;
    private String title;
    private String author;
    private String coverImageUrl;
    private Boolean bookmarked;

    private BookStatus status;
}
