package com.bookcase.bookcase.book.controller;

import com.bookcase.bookcase.book.DTO.BookDetailDto;
import com.bookcase.bookcase.book.DTO.BookDetailResponseDto;
import com.bookcase.bookcase.book.DTO.BookSearchResponseDto;
import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.book.service.AladinBookService;
import com.bookcase.bookcase.book.service.BookInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookInfoController {

    private final AladinBookService aladinBookService;
    private final BookInfoService bookInfoService;

    //검색
    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam("query") String query) {
        return ResponseEntity.ok(aladinBookService.searchBooks(query));
    }

    //상세조회
    @GetMapping("/{isbn13}")
    public ResponseEntity<BookDetailResponseDto> getBookDetail(
            @PathVariable String isbn13) {

        return ResponseEntity.ok(aladinBookService.getBookDetail(isbn13));
    }

    //책 저장
    @PostMapping("/add")
    public BookInfo addBook(@RequestBody BookDetailDto dto) {

        return bookInfoService.addBook(dto);
    }

    //베스트셀러
    @GetMapping("/bestseller")
    public BookSearchResponseDto getBestSellers(
            @RequestParam(required = false) Integer categoryId
    ) {
        return aladinBookService.getBestSellers(categoryId);
    }
}
