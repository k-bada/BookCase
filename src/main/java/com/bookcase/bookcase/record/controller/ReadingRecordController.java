package com.bookcase.bookcase.record.controller;

import com.bookcase.bookcase.record.DTO.ReadingRecordDetailDto;
import com.bookcase.bookcase.record.DTO.ReadingRecordListDto;
import com.bookcase.bookcase.record.DTO.ReadingRecordUpdateDto;
import com.bookcase.bookcase.record.DTO.RecordStatusUpdateDto;
import com.bookcase.bookcase.record.entity.BookStatus;
import com.bookcase.bookcase.record.service.ReadingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records")
public class ReadingRecordController {

    private final ReadingRecordService readingRecordService;

    @GetMapping
    public List<ReadingRecordListDto> getMyRecords(
            @RequestParam(required = false) BookStatus status,
            @RequestParam(required = false) String keyword
            ) {
        return readingRecordService.getMyRecords(status, keyword);
    }

    @GetMapping("/{recordNo}")
    public ReadingRecordDetailDto getMyRecordDetail(@PathVariable Long recordNo) {
        return readingRecordService.getMyRecordDetail(recordNo);
    }

    @DeleteMapping("/{recordNo}")
    public ResponseEntity<String> deleteMyRecord(@PathVariable Long recordNo) {
        readingRecordService.deleteMyRecord(recordNo);
        return ResponseEntity.ok("독서록이 삭제되었습니다.");
    }

    @PatchMapping("/{recordNo}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long recordNo,
            @RequestBody RecordStatusUpdateDto dto
            ) {
        readingRecordService.updateStatus(recordNo, dto);

        return ResponseEntity.ok("상태가 변경되었습니다.");
    }

    @PatchMapping("/{recordNo}")
    public ResponseEntity<String> updateRecord(
            @PathVariable Long recordNo,
            @RequestBody ReadingRecordUpdateDto dto
            ) {
        readingRecordService.updateRecord(recordNo, dto);

        return ResponseEntity.ok("독서록이 수정되었습니다.");
    }



}
