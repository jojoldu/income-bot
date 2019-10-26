package com.jojoldu.incomebot.batch.job.notify.parser;

import com.jojoldu.incomebot.batch.job.notify.parser.result.ParseResult;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface LectureParser<T extends ParseResult> {
    T parse(String url);
}
