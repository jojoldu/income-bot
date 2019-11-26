package com.jojoldu.incomebot.parser.parser.online;

import com.jojoldu.incomebot.parser.parser.ParseResult;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

public interface OnlineParseResult extends ParseResult {

    long getStudentCount();

    long getCoursePrice();

    @Override
    default long getCurrentScore() {
        return getStudentCount();
    }
}
