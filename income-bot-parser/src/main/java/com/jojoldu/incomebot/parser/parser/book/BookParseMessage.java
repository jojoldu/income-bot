package com.jojoldu.incomebot.parser.parser.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.jojoldu.incomebot.parser.util.NumberUtils.toAbsCommaNumber;

/**
 * Created by jojoldu@gmail.com on 09/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@ToString
@Getter
@RequiredArgsConstructor
public class BookParseMessage {
    private final String messageFormat;
    private final String goods;
    private final long beforeScore;
    private final long currentScore;
    private final int beforeRank;
    private final int currentRank;

    public String createMessage() {
        return messageFormat
                .replaceAll("\\{goods\\}", goods)
                .replaceAll("\\{addScore\\}", getAddScore())
                .replaceAll("\\{newScore\\}", getNewScore())
                .replaceAll("\\{addRank\\}", getAddRank())
                .replaceAll("\\{rankIncreaseMode\\}", getRankIncreaseMode())
                .replaceAll("\\{newRank\\}", getNewRank());
    }

    public String getAddScore() {
        long changeScore = getChangeScore();
        return getScoreIncreaseMode() + toAbsCommaNumber(changeScore);
    }

    public String getNewScore() {
        return toAbsCommaNumber(currentScore);
    }

    public String getAddRank() {
        int changeRank = getChangeRank();
        return toAbsCommaNumber(changeRank);
    }

    public String getRankIncreaseMode() {
        return getChangeRank() >= 0 ? "상승" : "하강";
    }

    public String getNewRank() {
        return toAbsCommaNumber(currentRank);
    }

    public String getScoreIncreaseMode() {
        long changeScore = getChangeScore();
        return changeScore >= 0 ? "+" : "-";
    }

    private long getChangeScore() {
        return currentScore - beforeScore;
    }

    private int getChangeRank() {
        return beforeRank - currentRank;
    }

}
