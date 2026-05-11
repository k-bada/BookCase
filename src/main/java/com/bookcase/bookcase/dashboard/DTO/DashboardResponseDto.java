package com.bookcase.bookcase.dashboard.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {

    private Long monthlyReadCount;
    private Long monthlyCompletedCount;
    private Long totalRecordCount;

    //최근에 읽은 도서 1개 제목
    private String latestBookTitle;

    //최근 읽은 도서 3권
    private List<RecentBookDto> recentBooks;
}
