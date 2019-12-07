package com.jojoldu.incomebot.core.lecture.book.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum BookLectureStoreType {
    YES24("예스24"),
    ALADIN("알라딘"),
    BANDINLUNIS("반디앤루니스"),
    INTERPARK("인터파크"),
    KYOBO("교보문고");

    private final String title;

}
