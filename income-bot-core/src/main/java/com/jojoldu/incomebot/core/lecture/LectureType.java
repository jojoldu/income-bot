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
    YES24 ("예스24") ,
    INFLEARN ("인프런");

    private final String title;

}
