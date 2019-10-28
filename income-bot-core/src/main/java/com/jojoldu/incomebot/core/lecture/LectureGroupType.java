package com.jojoldu.incomebot.core.lecture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.jojoldu.incomebot.core.lecture.LectureType.ALADIN;
import static com.jojoldu.incomebot.core.lecture.LectureType.BANDINLUNIS;
import static com.jojoldu.incomebot.core.lecture.LectureType.INFLEARN;
import static com.jojoldu.incomebot.core.lecture.LectureType.YES24;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum LectureGroupType {
    BOOK("도서", Arrays.asList(YES24, ALADIN, BANDINLUNIS)),
    ONLINE("온라인강좌", Arrays.asList(INFLEARN));

    private final String title;
    private final List<LectureType> lectures;

    public static boolean isBook(LectureType lectureType) {
        return BOOK.lectures.contains(lectureType);
    }

    public static boolean isOnline(LectureType lectureType) {
        return ONLINE.lectures.contains(lectureType);
    }
}
