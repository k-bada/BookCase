package com.bookcase.bookcase.dashboard.sevice;

import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.dashboard.DTO.DashboardResponseDto;
import com.bookcase.bookcase.dashboard.DTO.RecentBookDto;
import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.member.repository.MemberRepository;
import com.bookcase.bookcase.record.entity.BookStatus;
import com.bookcase.bookcase.record.entity.ReadingRecord;
import com.bookcase.bookcase.record.repository.ReadingRecordRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ReadingRecordRepository readingRecordRepository;
    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Transactional(readOnly = true)
    public DashboardResponseDto getDashboard() {

        LoginMemberDto loginMember =
                (LoginMemberDto) session.getAttribute("loginMember");

        if (loginMember == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "로그인이 필요합니다."
            );
        }

        Member member = memberRepository.findById(loginMember.getMemberNo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "회원 정보를 찾을 수 없습니다."
                ));

        LocalDate now = LocalDate.now();

        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd = now.withDayOfMonth(now.lengthOfMonth());

        //이번달 읽은 권수
        Long monthlyReadingCount =
                readingRecordRepository.countByMemberAndStartDateBetween(
                        member,
                        monthStart,
                        monthEnd
                );

        //이번달 완독 도서 수
        Long monthlyCompletedCount =
                readingRecordRepository.countByMemberAndStatusAndEndDateBetween(
                        member,
                        BookStatus.COMPLETED,
                        monthStart,
                        monthEnd
                );

        //독서록 총 개수
        Long totalRecordCount =
                readingRecordRepository.countByMember(member);

        //최근 읽은 책 3개
        List<ReadingRecord> recentRecords =
                readingRecordRepository.findTop3ByMemberOrderByModDateDesc(member);

        List<RecentBookDto> recentBooks = recentRecords.stream()
                .map(record -> {
                    BookInfo book = record.getBookInfo();

                    return RecentBookDto.builder()
                            .recordNo(record.getRecordNo())
                            .bookNo(book.getBno())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .coverImageUrl(book.getCoverImageUrl())
                            .status(record.getStatus())
                            .startDate(record.getStartDate())
                            .endDate(record.getEndDate())
                            .build();
                })
                .toList();

        //가장 최근에 읽은 책 제목
        String latestBookTitle =
                readingRecordRepository.findTop1ByMemberOrderByModDateDesc(member)
                        .map(record -> record.getBookInfo().getTitle())
                        .orElse(null);

        return DashboardResponseDto.builder()
                .monthlyReadCount(monthlyReadingCount)
                .monthlyCompletedCount(monthlyCompletedCount)
                .totalRecordCount(totalRecordCount)
                .latestBookTitle(latestBookTitle)
                .recentBooks(recentBooks)
                .build();
    }

}
