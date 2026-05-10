package com.bookcase.bookcase.record.controller;

import com.bookcase.bookcase.record.DTO.RecordBlockRequestDto;
import com.bookcase.bookcase.record.DTO.RecordBlockResponseDto;
import com.bookcase.bookcase.record.entity.RecordBlock;
import com.bookcase.bookcase.record.service.RecordBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records/{recordNo}/blocks")
public class RecordBlockController {

    private final RecordBlockService recordBlockService;

    @GetMapping
    public List<RecordBlockResponseDto> getBlocks(@PathVariable Long recordNo) {
        return recordBlockService.getBlock(recordNo);
    }

    @PostMapping
    public RecordBlockResponseDto addBlock(
            @PathVariable Long recordNo,
            @RequestBody RecordBlockRequestDto dto
            ) {
        return recordBlockService.addBlock(recordNo, dto);
    }

    @PatchMapping("/{blockId}")
    public RecordBlockResponseDto updateBlock(
            @PathVariable Long recordNo,
            @PathVariable Long blockId,
            @RequestBody RecordBlockRequestDto dto
    ) {
      return recordBlockService.updateBlock(recordNo, blockId, dto);
    }

    @DeleteMapping("/{blockId}")
    public String deleteBlock(
            @PathVariable Long recordNo,
            @PathVariable Long blockId
    ) {
        recordBlockService.deleteBlock(recordNo, blockId);

        return "블록이 삭제되었습니다.";
    }
}
