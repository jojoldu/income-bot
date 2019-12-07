package com.jojoldu.incomebot.parser.parser.book;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.parser.parser.LectureParser;
import com.jojoldu.incomebot.parser.parser.ParseResult;
import com.jojoldu.incomebot.parser.parser.book.aladin.AladinParser;
import com.jojoldu.incomebot.parser.parser.book.bandinlunis.BandinlunisParser;
import com.jojoldu.incomebot.parser.parser.book.interpark.InterParkParser;
import com.jojoldu.incomebot.parser.parser.book.kyobo.KyoboParser;
import com.jojoldu.incomebot.parser.parser.book.yes24.Yes24Parser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public enum BookLectureParsers implements LectureParser {
    YES24(BookLectureStoreType.YES24, new Yes24Parser()),
    INTERPARK(BookLectureStoreType.INTERPARK, new InterParkParser()),
    ALADIN(BookLectureStoreType.ALADIN, new AladinParser()),
    BANDINLUNIS(BookLectureStoreType.BANDINLUNIS, new BandinlunisParser()),
    KYOBO(BookLectureStoreType.KYOBO, new KyoboParser());

    private final BookLectureStoreType lectureType;
    private final LectureParser parser;

    @Override
    public ParseResult parse(String url) {
        return this.parser.parse(url);
    }

    public boolean is(BookLectureStoreType lectureType) {
        return this.lectureType == lectureType;
    }
}
