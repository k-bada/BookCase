package com.bookcase.bookcase.book.repository;

import com.bookcase.bookcase.book.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {

    Optional<BookInfo> findByIsbn13(String isbn13);
}
