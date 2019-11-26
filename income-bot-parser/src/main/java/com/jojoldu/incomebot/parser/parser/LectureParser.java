package com.jojoldu.incomebot.parser.parser;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface LectureParser<T extends ParseResult> {
    T parse(String url);
}
