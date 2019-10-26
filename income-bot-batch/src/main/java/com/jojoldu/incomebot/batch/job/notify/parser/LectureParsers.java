package com.jojoldu.incomebot.batch.job.notify.parser;

import com.jojoldu.incomebot.batch.job.notify.parser.impl.InflearnParser;
import com.jojoldu.incomebot.batch.job.notify.parser.result.ParseResult;
import com.jojoldu.incomebot.core.lecture.LectureType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum LectureParsers implements LectureParser {
    INFLEARN (LectureType.INFLEARN, new InflearnParser());

    private final LectureType lectureType;
    private final LectureParser parser;

    @Override
    public ParseResult parse(String url) {
        return this.parser.parse(url);
    }

    public boolean is (LectureType lectureType) {
        return this.lectureType == lectureType;
    }
}
