package com.bookcase.bookcase.book.repository;

import com.bookcase.bookcase.book.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {
}
