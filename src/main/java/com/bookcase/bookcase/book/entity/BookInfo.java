package com.bookcase.bookcase.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    // 알라딘 책 고유 번호
    @Column(unique = true)
    private Long aladinItemId;

    private String title;

    private String author;

    private String publisher;

    private String isbn13;

    private LocalDate publishDate;

    private Integer bookPage;

    // 책 표지 URL (알라딘에서 받은 것)
    private String coverImageUrl;

    @Enumerated(EnumType.STRING)
    private Genre genre;


}
