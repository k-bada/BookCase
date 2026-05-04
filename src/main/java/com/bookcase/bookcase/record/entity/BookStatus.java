package com.bookcase.bookcase.record.entity;

public enum BookStatus {
    WANT_TO_READ,   // 읽고 싶음
    READING,        // 읽는 중
    PAUSED,         // 쉬는 중
    COMPLETED,      // 완독
    DROPPED,        // 중도 포기 (선택)
    TRASH           // 삭제 (휴지통)
}