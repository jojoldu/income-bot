package com.jojoldu.incomebot.batch.job.notify.parser;

import com.jojoldu.incomebot.batch.job.notify.parser.result.ParseResult;
import com.jojoldu.incomebot.core.lecture.LectureType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 14/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class LectureParserRestTemplate {

    public Optional<ParseResult> parse(String url, LectureType type) {
        return Arrays.stream(LectureParsers.values())
                .filter(e -> e.is(type)).findFirst()
                .map(e -> e.parse(url));
    }
}
