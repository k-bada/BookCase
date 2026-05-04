package com.bookcase.bookcase.book.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDto {
    private String title;
    private String author;
    private String cover;
    private String isbn;
    private String publisher;
    private String pubDate;
    private Integer itemPage;
    private String categoryName;
}
