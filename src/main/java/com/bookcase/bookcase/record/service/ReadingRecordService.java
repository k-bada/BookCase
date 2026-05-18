package com.bookcase.bookcase.record.service;

import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.member.repository.MemberRepository;
import com.bookcase.bookcase.record.DTO.*;
import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.record.entity.BookStatus;
import com.bookcase.bookcase.record.entity.ReadingRecord;
import com.bookcase.bookcase.record.repository.ReadingRecordRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingRecordService {

    private final HttpSession session;
    private final ReadingRecordRepository readingRecordRepository;
    private final MemberRepository memberRepository;

    //책장 책 디테일 가져오기
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
        if (currentPage == null || totalPage == null || totalPage == 0) {
            return 0;
        }

        return (currentPage * 100) / totalPage;
    }

    //status 변경
    @Transactional
    public void updateStatus(Long recordNo, RecordStatusUpdateDto dto) {

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

        ReadingRecord readingRecord =
                readingRecordRepository.findByRecordNoAndMember(recordNo, member)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "독서록을 찾을 수 없습니다."
                        ));

        BookStatus status = dto.getStatus();

        if (status == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "상태값이 필요합니다."
            );
        }

        readingRecord.updateStatus(status);

        // 읽는 중으로 바뀌면 시작일 자동 저장 + 종료일 제거
        if (status == BookStatus.READING) {

            if (readingRecord.getStartDate() == null) {
                readingRecord.updateStartDate(LocalDate.now());
            }

            readingRecord.updateEndDate(null);
        }

        // 완독으로 바뀌면 종료일 자동 저장
        if (status == BookStatus.COMPLETED || status == BookStatus.DROPPED) {

            if (readingRecord.getEndDate() == null) {
                readingRecord.updateEndDate(LocalDate.now());
            }
        }
    }

    //내 책장(독서록) 목록
    @Transactional(readOnly = true)
    public List<ReadingRecordListDto> getMyRecords(BookStatus status, String keyword) {
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

        List<ReadingRecord> records =
                readingRecordRepository.searchMyRecords(member, status, keyword);

        return  records.stream()
                .map(record -> {
                    BookInfo book = record.getBookInfo();

                    return ReadingRecordListDto.builder()
                            .recordNo(record.getRecordNo())
                            .bookNo(book.getBno())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .coverImageUrl(book.getCoverImageUrl())
                            .status(record.getStatus())
                            .bookmarked(record.isBookmarked())
                            .build();
                })
                .toList();
    }

    //독서록 디테일
    @Transactional(readOnly = true)
    public ReadingRecordDetailDto getMyRecordDetail(Long recordNo) {
        LoginMemberDto loginMember =
                (LoginMemberDto) session.getAttribute("loginMember");

        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        Member member = memberRepository.findById(loginMember.getMemberNo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "회원 정보를 찾을 수 없습니다."
                ));

        ReadingRecord record = readingRecordRepository.findByRecordNoAndMember(recordNo, member)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "독서록을 찾을 수 없습니다."
                ));

        BookInfo book = record.getBookInfo();

        Integer progress = 0;

        if (record.getCurrentPage() != null
            && book.getBookPage() != null
            && book.getBookPage() > 0) {
            progress = record.getCurrentPage() * 100 / book.getBookPage();
        }

        return ReadingRecordDetailDto.builder()
                .recordNo(record.getRecordNo())
                .bookNo(book.getBno())
                .genre(book.getGenre().name())
                .coverImageUrl(book.getCoverImageUrl())
                .title(book.getTitle())
                .author(book.getAuthor())
                .status(record.getStatus())
                .currentPage(record.getCurrentPage())
                .bookPage(book.getBookPage())
                .progress(progress)
                .rating(record.getRating())
                .startDate(record.getStartDate())
                .endDate(record.getEndDate())
                .bookmarked(record.isBookmarked())
                .build();
    }

    //기록 삭제
    @Transactional
    public void deleteMyRecord(Long recordNo) {

        LoginMemberDto loginMember =
                (LoginMemberDto) session.getAttribute("loginMember");

        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        Member member = memberRepository.findById(loginMember.getMemberNo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "회원 정보를 찾을 수 없습니다."
                ));

        ReadingRecord record = readingRecordRepository.findByRecordNoAndMember(recordNo, member)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "독서록을 찾을 수 없습니다."
                ));

        readingRecordRepository.delete(record);
    }

    //독서록 수정
    @Transactional
    public void updateRecord(Long recordNo,
                             ReadingRecordUpdateDto dto) {
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

        ReadingRecord record =
                readingRecordRepository.findByRecordNoAndMember(recordNo, member)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "독서록을 찾을 수 없습니다."
                        ));

        if (dto.getStatus() != null) {
            record.updateStatus(dto.getStatus());
        }

        if (dto.getCurrentPage() != null) {
            record.updateCurrentPage(dto.getCurrentPage());
        }

        if (dto.getRating() != null) {
            record.updateRating(dto.getRating());
        }

        if (dto.getStartDate() != null) {
            record.updateStartDate(dto.getStartDate());
        }

        if (dto.getEndDate() != null) {
            record.updateEndDate(dto.getEndDate());
        }

        if (dto.getBookmarked() != null) {
            record.updateBookmarked(dto.getBookmarked());
        }
    }




}
