package com.bookcase.bookcase.book.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailDto {
    private String title;
    private String author;
    private String cover;
    private String isbn;
    private String publisher;
    private String pubDate;
    private Integer itemPage;
    private String categoryName;
    private String description;
}
