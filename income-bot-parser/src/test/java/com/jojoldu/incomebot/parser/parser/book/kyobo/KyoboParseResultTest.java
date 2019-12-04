package com.jojoldu.incomebot.parser.parser.book.kyobo;

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
        long currentRank = 3;
        KyoboParseResult parseResult = new KyoboParseResult(currentRank);
        String expected = "상승";

        //when
        String result = parseResult.getIncreaseMode(5);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void 현재_스코어가_더높으면_하강() {
        //given
        long currentRank = 5;
        KyoboParseResult parseResult = new KyoboParseResult(currentRank);
        String expected = "하강";

        //when
        String result = parseResult.getIncreaseMode(3);

        //then
        assertThat(result).isEqualTo(expected);
    }
}
