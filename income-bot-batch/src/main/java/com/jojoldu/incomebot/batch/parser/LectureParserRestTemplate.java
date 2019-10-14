package com.jojoldu.incomebot.batch.parser;

import com.jojoldu.incomebot.core.lecture.LectureType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by jojoldu@gmail.com on 14/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class LectureParserRestTemplate {

    public long parse (String url, LectureType type) {
        return Arrays.stream(LectureParserType.values())
                .filter(e -> e.is(type)).findFirst()
                .map(e -> e.parse(url))
                .orElse(0L);
    }
}
