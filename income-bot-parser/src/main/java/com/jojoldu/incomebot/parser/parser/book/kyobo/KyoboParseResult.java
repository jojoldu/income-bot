package com.jojoldu.incomebot.parser.parser.book.kyobo;

import com.jojoldu.incomebot.parser.parser.book.BookParseResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.jojoldu.incomebot.parser.util.NumberUtils.toAbsCommaNumber;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@ToString
@Getter
@RequiredArgsConstructor
public class KyoboParseResult implements BookParseResult {
    public static final KyoboParseResult EMPTY = new KyoboParseResult(0);
    private static final String TEXT_FORMAT = "[교보문고] \"{goods}\"의 순위가 {addScore} 만큼 {increaseMode} 하여 {newScore}위 를 달성했습니다.";

    private final long currentScore;

    /**
     * 순위
     * 30 -> 20: 상승
     * 20 -> 30: 하강
     */
    @Override
    public String getMessage(long beforeScore, String goods) {
        long changeScore = beforeScore - currentScore;

        return TEXT_FORMAT
                .replaceAll("\\{goods\\}", goods)
                .replaceAll("\\{addScore\\}", toAbsCommaNumber(changeScore))
                .replaceAll("\\{increaseMode\\}", getIncreaseMode(beforeScore))
                .replaceAll("\\{newScore\\}", toAbsCommaNumber(currentScore));
    }

    public String getIncreaseMode(long beforeScore) {
        return beforeScore - currentScore >= 0 ? "상승" : "하강";
    }
}
