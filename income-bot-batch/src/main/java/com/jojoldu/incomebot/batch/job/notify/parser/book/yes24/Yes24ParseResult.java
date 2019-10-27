package com.jojoldu.incomebot.batch.job.notify.parser.book.yes24;

import com.jojoldu.incomebot.batch.job.notify.parser.book.BookParseResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public class Yes24ParseResult implements BookParseResult {
    public static final Yes24ParseResult EMPTY = new Yes24ParseResult(0);
    private static final String TEXT_FORMAT = "[예스24] \"{goods}\"의 판매지수가 {addScore} 되어 현재 {newScore} 를 달성했습니다.";

    private final long salesPoint;

    @Override
    public String getMessage(long beforeScore, String goods) {
        long changeScore = salesPoint - beforeScore;
        String code = changeScore >= 0 ? "+" : "-";

        return TEXT_FORMAT
                .replaceAll("\\{goods\\}", goods)
                .replaceAll("\\{addScore\\}", code + changeScore)
                .replaceAll("\\{newScore\\}", String.valueOf(salesPoint));
    }
}
