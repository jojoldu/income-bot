package com.jojoldu.incomebot.parser.exception;

import com.jojoldu.incomebot.core.lecture.LectureType;

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

    public LectureParseException(LectureType lectureType, Throwable cause) {
        this(lectureType.getTitle() + " URL 파싱에 실패하였습니다.", cause);
    }
}
