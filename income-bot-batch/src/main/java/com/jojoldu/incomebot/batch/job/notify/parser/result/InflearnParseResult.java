package com.jojoldu.incomebot.batch.job.notify.parser.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public class InflearnParseResult implements ParseResult {
    public static final InflearnParseResult EMPTY = new InflearnParseResult(0, 0);
    private static final String TEXT_FORMAT = "[인프런] \"{goods}\"의 수강생이 {addScore}명 ({addAmount}원) 되어 현재 {newScore} 명이 수강중입니다.";

    private final long studentCount;
    private final long coursePrice;

    @Override
    public String getMessage(long beforeScore, String goods) {
        long changeScore = studentCount - beforeScore;
        String code = changeScore >= 0 ? "+" : "-";
        long changeAmount = Math.round(changeScore * coursePrice * 0.7 * 0.88);

        return TEXT_FORMAT
                .replaceAll("\\{goods\\}", goods)
                .replaceAll("\\{addScore\\}", code + changeScore)
                .replaceAll("\\{addAmount\\}", code + changeAmount)
                .replaceAll("\\{newScore\\}", String.valueOf(studentCount));
    }

    @Override
    public long getCurrentScore() {
        return this.studentCount;
    }
}
