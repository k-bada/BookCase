package com.bookcase.bookcase.record.repository;

import com.bookcase.bookcase.book.entity.BookInfo;
import com.bookcase.bookcase.member.entity.Member;
import com.bookcase.bookcase.record.entity.BookStatus;
import com.bookcase.bookcase.record.entity.ReadingRecord;
import com.bookcase.bookcase.record.entity.RecordBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Long> {
    boolean existsByMemberAndBookInfo(Member member, BookInfo bookInfo);

    List<ReadingRecord> findByMemberAndStatus(Member member, BookStatus status);

    List<ReadingRecord> findByMember(Member member);

    Optional<ReadingRecord> findByRecordNoAndMember(Long recordNo, Member member);

    // 검색용 쿼리
    @Query("""
    select r
    from ReadingRecord r
    join fetch r.bookInfo b
    where r.member = :member
      and (:status is null or r.status = :status)
      and (:keyword is null or b.title like concat('%', :keyword, '%')
           or b.author like concat('%', :keyword, '%'))
""")
    List<ReadingRecord> searchMyRecords(
            @Param("member") Member member,
            @Param("status") BookStatus status,
            @Param("keyword") String keyword
    );

}
