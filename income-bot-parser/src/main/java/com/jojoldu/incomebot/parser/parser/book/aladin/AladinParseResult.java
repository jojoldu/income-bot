package com.jojoldu.incomebot.parser.parser.book.aladin;

import com.jojoldu.incomebot.parser.parser.book.BookParseResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@ToString
@Getter
@RequiredArgsConstructor
public class AladinParseResult implements BookParseResult {
    public static final AladinParseResult EMPTY = new AladinParseResult(0, 0);

    private final long currentScore;
    private final int currentRank;

    @Override
    public String getMessageFormat() {
        return "[알라딘] \"{goods}\"의 Sales Point가 {addScore} 되어 현재 {newScore} 이며 " +
                "순위가 {addRank} 만큼 {rankIncreaseMode} 하여 {newRank}위를 달성했습니다.";
    }

}
