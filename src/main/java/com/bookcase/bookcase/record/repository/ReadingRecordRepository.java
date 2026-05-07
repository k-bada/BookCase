package com.bookcase.bookcase.record.repository;

import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.record.entity.ReadingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Long> {
    boolean existsByMemberAndBookInfo(Member member, BookInfo bookInfo);
}
