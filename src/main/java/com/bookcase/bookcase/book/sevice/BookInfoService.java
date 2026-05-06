package com.bookcase.bookcase.book.sevice;

import com.bookcase.bookcase.book.DTO.BookDetailDto;
import com.bookcase.bookcase.book.DTO.BookDetailResponseDto;
import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.book.entity.Genre;
import com.bookcase.bookcase.book.repository.BookInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookInfoService {

    private final BookInfoRepository bookInfoRepository;
    private final AladinBookService aladinBookService;

    @Transactional
    public BookInfo addBook(String isbn13) {

        //이미 저장된 책이면 기조 데이터 반환
        return bookInfoRepository.findByIsbn13(isbn13)
                .orElseGet(() -> {

                    //알라딘 책 상세
                    BookDetailResponseDto response =
                            aladinBookService.getBookDetail(isbn13);

                    BookDetailDto dto = response.getItem();

                    //Dto -> Entity 변환
                    BookInfo bookInfo = BookInfo.builder()
                            .title(dto.getTitle())
                            .author(dto.getAuthor())
                            .publisher(dto.getPublisher())
                            .isbn13(dto.getIsbn())
                            .publishDate(parseDate(dto.getPubDate()))
                            .bookPage(dto.getItemPage())
                            .coverImageUrl(dto.getCover())
                            .genre(convertGenre(dto.getCategoryName()))
                            .build();

                    return bookInfoRepository.save(bookInfo);
                });

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
