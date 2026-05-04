package com.bookcase.bookcase.record.repository;


import com.bookcase.bookcase.record.entity.ReadingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Long> {
    //내 책장에 추가


}
