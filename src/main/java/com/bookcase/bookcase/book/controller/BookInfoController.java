package com.bookcase.bookcase.book.controller;

import com.bookcase.bookcase.book.DTO.BookDetailResponseDto;
import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.book.sevice.AladinBookService;
import com.bookcase.bookcase.book.sevice.BookInfoService;
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
    @PostMapping("/add/{isbn13}")
    public BookInfo addBook(@PathVariable String isbn13) {

        return bookInfoService.addBook(isbn13);
    }
}
