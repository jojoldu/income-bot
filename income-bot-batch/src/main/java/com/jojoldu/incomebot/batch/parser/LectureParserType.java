package com.jojoldu.incomebot.batch.parser;

import com.jojoldu.incomebot.batch.parser.impl.InflearnParser;
import com.jojoldu.incomebot.core.lecture.LectureType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum LectureParserType implements LectureParser {
    INFLEARN (LectureType.INFLEARN, new InflearnParser());

    private final LectureType lectureType;
    private final LectureParser parser;

    @Override
    public long parse(String url) {
        return this.parser.parse(url);
    }

    public boolean is (LectureType lectureType) {
        return this.lectureType == lectureType;
    }
}
