package com.jojoldu.incomebot.parser.parser;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface ParseResult {

    String getMessage(long beforeScore, String goods);

    long getCurrentScore();
}
