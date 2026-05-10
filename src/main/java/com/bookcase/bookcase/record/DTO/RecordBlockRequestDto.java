package com.bookcase.bookcase.record.DTO;

import com.bookcase.bookcase.record.entity.BlockType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecordBlockRequestDto {

    private BlockType blockType;
    private Long questionId;
    private String content;
    private Integer page;
    private Integer orderIndex;
}
