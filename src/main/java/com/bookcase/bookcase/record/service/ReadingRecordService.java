package com.bookcase.bookcase.record.service;

import com.bookcase.bookcase.record.DTO.UserBookDetailDto;
import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.record.entity.BookStatus;
import com.bookcase.bookcase.record.entity.ReadingRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReadingRecordService {

    public UserBookDetailDto toDto(
            ReadingRecord readingRecord
    ) {
        BookInfo book = readingRecord.getBookInfo();

        return UserBookDetailDto.builder()
                .coverImage(book.getCoverImageUrl())
                .title(book.getTitle())
                .author(book.getAuthor())
                .status(readingRecord.getStatus())
                .progressRate(calculateProgressRate(
                        readingRecord.getCurrentPage(),
                        book.getBookPage()
                    )
                )
                .rating(readingRecord.getRating())
                .startDate(readingRecord.getStartDate())
                .totalPage(book.getBookPage())
                .build();
    }

    //진행율 계산
    private Integer calculateProgressRate(
            Integer currentPage,
            Integer totalPage
    ) {
        if (currentPage == null || totalPage == null || totalPage == 0){
            return 0;
        }

        return (currentPage * 100) / totalPage;
    }

    //status 변경
    public void updateStatus(ReadingRecord readingRecord, BookStatus status){
        readingRecord.updateStatus(status);

        //읽기 시작시 시작일 자동 저장
        if (status == BookStatus.READING
            && readingRecord.getStartDate() == null) {

            readingRecord.updateStartDate(LocalDate.now());
        }

        //종료일 자동 처리
        if (status == BookStatus.COMPLETED
                && readingRecord.getEndDate() == null) {

            readingRecord.updateEndDate(LocalDate.now());
        }
    }
}
