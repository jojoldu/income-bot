package com.jojoldu.incomebot.core.lecture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.jojoldu.incomebot.core.lecture.LectureGroupType.BOOK;
import static com.jojoldu.incomebot.core.lecture.LectureGroupType.ONLINE;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum LectureType {
    YES24("예스24", BOOK),
    ALADIN("알라딘", BOOK),
    BANDINLUNIS("반디앤루니스", BOOK),
    INTERPARK("인터파크", BOOK),
    KYOBO("교보문고", BOOK),
    INFLEARN("인프런", ONLINE);

    private final String title;
    private final LectureGroupType group;

    public boolean isBook() {
        return this.group == BOOK;
    }

    public boolean isOnline() {
        return this.group == ONLINE;
    }
}
