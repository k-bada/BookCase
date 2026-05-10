package com.bookcase.bookcase.record.entity;

import com.bookcase.bookcase.global.entity.BaseEntity;
import com.bookcase.bookcase.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordBlock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_no")
    private ReadingRecord record;

    @Enumerated(EnumType.STRING)
    private BlockType type;

    // QUESTION일 때 사용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    // TEXT면 자유글, QUESTION이면 답변, QUOTE면 인용구 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // QUOTE일 때 사용
    private Integer page;

    private Integer orderIndex;

    public void updateBlock(BlockType type, Question question, String content, Integer page, Integer orderIndex) {
        this.type = type;
        this.question = question;
        this.content = content;
        this.page = page;
        this.orderIndex = orderIndex;

    }



}
