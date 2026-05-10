package com.bookcase.bookcase.record.service;

import com.bookcase.bookcase.member.DTO.LoginMemberDto;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.member.repository.MemberRepository;
import com.bookcase.bookcase.question.entity.Question;
import com.bookcase.bookcase.question.repository.QuestionRepository;
import com.bookcase.bookcase.record.DTO.RecordBlockRequestDto;
import com.bookcase.bookcase.record.DTO.RecordBlockResponseDto;
import com.bookcase.bookcase.record.entity.ReadingRecord;
import com.bookcase.bookcase.record.entity.RecordBlock;
import com.bookcase.bookcase.record.repository.ReadingRecordRepository;
import com.bookcase.bookcase.record.repository.RecordBlockRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.springframework.cglib.core.Block;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordBlockService {

    private final RecordBlockRepository recordBlockRepository;
    private final ReadingRecordRepository readingRecordRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final HttpSession session;

    //record block 목록 가져오기
    @Transactional
    public List<RecordBlockResponseDto> getBlock(Long recordNo) {
        ReadingRecord record = getMyRecord(recordNo);

        return recordBlockRepository.findByRecordOrderByOrderIndexAsc(record)
                .stream()
                .map(this::toDto)
                .toList();
    }

    //블록 추가하기
    @Transactional
    public RecordBlockResponseDto addBlock(Long recordNo, RecordBlockRequestDto dto) {
        ReadingRecord record = getMyRecord(recordNo);

        Question question = null;

        if (dto.getQuestionId() != null) {
            question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "질문을 찾을 수 없습니다."
                    ));
        }

        RecordBlock block = RecordBlock.builder()
                .record(record)
                .type(dto.getBlockType())
                .content(dto.getContent())
                .question(question)
                .page(dto.getPage())
                .orderIndex(dto.getOrderIndex())
                .build();

        RecordBlock savedBlock = recordBlockRepository.save(block);

        return toDto(savedBlock);
    }

    //block 수정
    @Transactional
    public RecordBlockResponseDto updateBlock(Long recordNo, Long blockId, RecordBlockRequestDto dto) {
        ReadingRecord record = getMyRecord(recordNo);

        RecordBlock block = recordBlockRepository.findByBlockIdAndRecord(blockId, record)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "블록을 찾을 수 없습니다."
                ));

        Question question = null;

        if (dto.getQuestionId() != null) {
            question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "질문을 찾을 수 없습니다."
                    ));
        }

        block.updateBlock(
                dto.getBlockType(),
                question,
                dto.getContent(),
                dto.getPage(),
                dto.getOrderIndex()
        );

        return toDto(block);
    }

    //block 삭제
    @Transactional
    public void deleteBlock(Long recordId, Long blockId) {
        ReadingRecord record = getMyRecord(recordId);

        RecordBlock block = recordBlockRepository.findByBlockIdAndRecord(blockId, record)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "블록을 찾을 수 없습니다."
                ));

        recordBlockRepository.delete(block);
    }


    private ReadingRecord getMyRecord(Long recordNo) {
        LoginMemberDto loginMemberDto =
                (LoginMemberDto) session.getAttribute("loginMember");

        if (loginMemberDto == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "로그인이 필요합니다."
            );
        }

        Member member = memberRepository.findByEmail(loginMemberDto.getEmail())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "회원정보를 찾을 수 없습니다."
                ));

        return readingRecordRepository.findByRecordNoAndMember(recordNo, member)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "독서록을 찾을 수 없습니다."
                ));
    }

    private RecordBlockResponseDto toDto(RecordBlock block) {
        Long questionId = null;
        String questionContent = null;

        if (block.getQuestion() != null) {
            questionId = block.getQuestion().getQuestionId();
            questionContent = block.getQuestion().getContent();
        }

        return RecordBlockResponseDto.builder()
                .blockId(block.getBlockId())
                .blockType(block.getType())
                .questionId(questionId)
                .questionContent(questionContent)
                .content(block.getContent())
                .page(block.getPage())
                .orderIndex(block.getOrderIndex())
                .build();
    }
}
