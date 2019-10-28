package com.jojoldu.incomebot.core.lecture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum LectureType {
    YES24("예스24"),
    ALADIN("알라딘"),
    BANDINLUNIS("반디앤루니스"),
    INFLEARN ("인프런");

    private final String title;

    public boolean isBook() {
        return LectureGroupType.isBook(this);
    }

    public boolean isOnline() {
        return LectureGroupType.isOnline(this);
    }
}
