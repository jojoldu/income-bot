package com.jojoldu.incomebot.parser.parser;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreType;
import com.jojoldu.incomebot.parser.parser.book.BookLectureParsers;
import com.jojoldu.incomebot.parser.parser.book.BookParseResult;
import com.jojoldu.incomebot.parser.parser.online.OnlineLectureParsers;
import com.jojoldu.incomebot.parser.parser.online.OnlineParseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * Created by jojoldu@gmail.com on 14/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class LectureParseExecutor {

    public Optional<BookParseResult> parse(String url, BookLectureStoreType type) {
        return stream(BookLectureParsers.values())
                .filter(e -> e.is(type)).findFirst()
                .map(e -> (BookParseResult) e.parse(url));
    }

    public Optional<OnlineParseResult> parse(String url, OnlineLectureStoreType type) {
        return stream(OnlineLectureParsers.values())
                .filter(e -> e.is(type)).findFirst()
                .map(e -> (OnlineParseResult) e.parse(url));
    }
}
