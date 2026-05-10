package com.bookcase.bookcase.record.repository;

import com.bookcase.bookcase.record.entity.ReadingRecord;
import com.bookcase.bookcase.record.entity.RecordBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordBlockRepository extends JpaRepository<RecordBlock, Long> {

    List<RecordBlock> findByRecordOrderByOrderIndexAsc(ReadingRecord record);

    Optional<RecordBlock> findByBlockIdAndRecord(Long blockId, ReadingRecord record);
}
