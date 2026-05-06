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

    private String title;

    private String author;

    private String publisher;

    @Column(unique = true)
    private String isbn13;

    private LocalDate publishDate;

    private Integer bookPage;

    // 책 표지 URL (알라딘에서 받은 것)
    private String coverImageUrl;

    @Enumerated(EnumType.STRING)
    private Genre genre;


}
