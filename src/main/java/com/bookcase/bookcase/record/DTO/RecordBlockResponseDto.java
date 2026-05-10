package com.bookcase.bookcase.record.DTO;

import com.bookcase.bookcase.record.entity.BlockType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordBlockResponseDto {

    private Long blockId;
    private BlockType blockType;

    private Long questionId;
    private String questionContent;

    private String content;
    private Integer page;
    private Integer orderIndex;
}
