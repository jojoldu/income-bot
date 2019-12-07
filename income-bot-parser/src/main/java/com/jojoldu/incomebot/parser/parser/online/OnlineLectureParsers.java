package com.jojoldu.incomebot.parser.parser.online;

import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreType;
import com.jojoldu.incomebot.parser.parser.LectureParser;
import com.jojoldu.incomebot.parser.parser.ParseResult;
import com.jojoldu.incomebot.parser.parser.online.inflearn.InflearnParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum OnlineLectureParsers implements LectureParser {
    INFLEARN(OnlineLectureStoreType.INFLEARN, new InflearnParser());

    private final OnlineLectureStoreType lectureType;
    private final LectureParser parser;

    @Override
    public ParseResult parse(String url) {
        return this.parser.parse(url);
    }

    public boolean is(OnlineLectureStoreType lectureType) {
        return this.lectureType == lectureType;
    }
}
