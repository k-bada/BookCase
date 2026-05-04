package com.bookcase.bookcase.book.DTO;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookSearchResponseDto {
    private List<BookDto> item;
}