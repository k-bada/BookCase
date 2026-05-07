package com.bookcase.bookcase.record.entity;

public enum BookStatus {
    TO_READ,        // 읽을 예정
    READING,        // 읽는 중
    PAUSED,         // 쉬는 중
    COMPLETED,      // 완독
    DROPPED,        // 하차
    TRASH           // 삭제 (휴지통)
}