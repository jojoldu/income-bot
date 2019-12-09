package com.jojoldu.incomebot.parser.parser.book.kyobo;

import com.jojoldu.incomebot.parser.parser.book.BookParseResult;
import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.toIntExact;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@ToString
@Getter
public class KyoboParseResult implements BookParseResult {
    public static final KyoboParseResult EMPTY = new KyoboParseResult(0);

    private final long currentScore;
    private final int currentRank;

    public KyoboParseResult(long currentScore) {
        this.currentScore = currentScore;
        this.currentRank = toIntExact(currentScore);
    }

    @Override
    public String getMessageFormat() {
        return "[교보문고] \"{goods}\"의 순위가 {addRank} 만큼 {rankIncreaseMode} 하여 {newRank}위를 달성했습니다.";
    }
}
