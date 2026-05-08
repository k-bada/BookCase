package com.bookcase.bookcase.book.service;

import com.bookcase.bookcase.book.DTO.BookDetailDto;
import com.bookcase.bookcase.book.DTO.BookDetailResponseDto;
import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.book.entity.Genre;
import com.bookcase.bookcase.book.repository.BookInfoRepository;
import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.member.repository.MemberRepository;
import com.bookcase.bookcase.record.entity.BookStatus;
import com.bookcase.bookcase.record.entity.ReadingRecord;
import com.bookcase.bookcase.record.repository.ReadingRecordRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookInfoService {

    private final BookInfoRepository bookInfoRepository;
    private final AladinBookService aladinBookService;
    private final ReadingRecordRepository readingRecordRepository;
    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Transactional
    public BookInfo addBook(BookDetailDto dto) {

        LoginMemberDto loginMember =
                (LoginMemberDto) session.getAttribute("loginMember");

        if (loginMember == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "로그인이 필요합니다."
            );
        }

        Member member = memberRepository.findById(loginMember.getMemberNo())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        BookInfo bookInfo = bookInfoRepository.findByIsbn13(dto.getIsbn())
                .orElseGet(() -> {

                    BookDetailResponseDto detailResponse =
                            aladinBookService.getBookDetail(dto.getIsbn());

                    BookDetailDto detailDto = detailResponse.getItem();

                    BookInfo newBookInfo = BookInfo.builder()
                            .title(detailDto.getTitle())
                            .author(detailDto.getAuthor())
                            .publisher(detailDto.getPublisher())
                            .isbn13(detailDto.getIsbn())
                            .publishDate(parseDate(detailDto.getPubDate()))
                            .bookPage(detailDto.getItemPage())
                            .coverImageUrl(detailDto.getCover())
                            .genre(convertGenre(detailDto.getCategoryName()))
                            .build();

                    return  bookInfoRepository.save(newBookInfo);
                });

        if (readingRecordRepository.existsByMemberAndBookInfo(member, bookInfo)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "이미 책장에 있는 책입니다."
            );
        }

        //독서록 생성
        ReadingRecord readingRecord =
                ReadingRecord.builder()
                        .bookInfo(bookInfo)
                        .member(member)
                        .status(BookStatus.TO_READ)
                        .bookmarked(false)
                        .build();

        readingRecordRepository.save(readingRecord);

        return bookInfo;

    }

    //String -> LocalDate 변환
    private LocalDate parseDate(String pubDate) {

        if (pubDate == null || pubDate.isBlank()) {
            return null;
        }

        return LocalDate.parse(pubDate);
    }

    //카테고리 매칭
    private Genre convertGenre(String categoryName) {
        // 카테고리 정보가 없으면 기타 처리
        if (categoryName == null || categoryName.isBlank()) {
            return Genre.ETC;
        }

        // 추리 / 미스터리
        if (categoryName.contains("추리")
                || categoryName.contains("미스터리")) {

            return Genre.MYSTERY;
        }

        // 판타지
        if (categoryName.contains("판타지")) {
            return Genre.FANTASY;
        }

        // SF
        if (categoryName.contains("SF")) {
            return Genre.SCIENCE_FICTION;
        }

        // 공포 / 스릴러
        if (categoryName.contains("호러")
                || categoryName.contains("스릴러")
                || categoryName.contains("공포")) {

            return Genre.HORROR_THRILLER;
        }

        // 무협
        if (categoryName.contains("무협")) {
            return Genre.MARTIAL_ARTS;
        }

        // 액션
        if (categoryName.contains("액션")) {
            return Genre.ACTION;
        }

        // 로맨스
        if (categoryName.contains("로맨스")) {
            return Genre.ROMANCE;
        }

        // 시 / 시집
        if (categoryName.contains(">시")
                || categoryName.contains("시집")) {

            return Genre.POETRY;
        }

        // 자기계발
        if (categoryName.contains("자기계발")) {
            return Genre.SELF_DEVELOPMENT;
        }

        // 경제 / 경영
        if (categoryName.contains("경제")
                || categoryName.contains("경영")) {

            return Genre.ECONOMY;
        }

        // 에세이
        if (categoryName.contains("에세이")) {
            return Genre.ESSAY;
        }

        // 만화
        if (categoryName.contains("만화")) {
            return Genre.COMIC;
        }

        // 일반 소설
        if (categoryName.contains("소설")) {
            return Genre.NOVEL;
        }

        // 매칭 안 되면 기타 처리
        return Genre.ETC;
    }



}
