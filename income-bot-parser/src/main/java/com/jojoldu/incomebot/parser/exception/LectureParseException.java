package com.jojoldu.incomebot.parser.exception;

/**
 * Created by jojoldu@gmail.com on 27/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class LectureParseException extends RuntimeException {

    public LectureParseException(String message) {
        super(message);
    }

    public LectureParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
