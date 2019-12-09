package com.jojoldu.incomebot.parser.parser.book;

import com.jojoldu.incomebot.parser.parser.ParseResult;

/**
 * Created by jojoldu@gmail.com on 27/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface BookParseResult extends ParseResult {
    int getCurrentRank();

    String getMessageFormat();

    @Override
    default String getMessage(long beforeScore, int beforeRank, String goods) {
        return new BookParseMessage(
                getMessageFormat(),
                goods,
                beforeScore,
                getCurrentScore(),
                beforeRank,
                getCurrentRank())
                .createMessage();
    }
}
