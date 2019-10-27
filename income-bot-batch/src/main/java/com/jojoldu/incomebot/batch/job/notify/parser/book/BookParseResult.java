package com.jojoldu.incomebot.batch.job.notify.parser.book;

import com.jojoldu.incomebot.batch.job.notify.parser.ParseResult;

/**
 * Created by jojoldu@gmail.com on 27/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface BookParseResult extends ParseResult {
    long getSalesPoint();

    @Override
    default long getCurrentScore() {
        return getSalesPoint();
    }
}
