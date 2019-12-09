package com.jojoldu.incomebot.parser.parser.book.kyobo;

import com.jojoldu.incomebot.parser.parser.book.BookParseMessage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 04/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class KyoboParseResultTest {

    @Test
    public void 현재_스코어가_더낮으면_상승() {
        //given
        int currentRank = 3;
        int beforeRank = 5;
        BookParseMessage message = message(currentRank, beforeRank);
        String expected = "상승";

        //when
        String result = message.getRankIncreaseMode();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void 현재_스코어가_더높으면_하강() {
        //given
        int currentRank = 5;
        int beforeRank = 3;
        BookParseMessage message = message(currentRank, beforeRank);
        String expected = "하강";

        //when
        String result = message.getRankIncreaseMode();

        //then
        assertThat(result).isEqualTo(expected);
    }

    private BookParseMessage message(int currentRank, int beforeRank) {
        return new BookParseMessage("", "", 0, 0, beforeRank, currentRank);
    }
}
