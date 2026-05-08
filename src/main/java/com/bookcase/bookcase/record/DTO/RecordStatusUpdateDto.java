package com.bookcase.bookcase.record.DTO;

import com.bookcase.bookcase.record.entity.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecordStatusUpdateDto {

    private BookStatus status;
}
