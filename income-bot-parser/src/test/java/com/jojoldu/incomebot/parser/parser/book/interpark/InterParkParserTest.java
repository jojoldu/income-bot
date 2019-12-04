package com.jojoldu.incomebot.parser.parser.book.interpark;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 27/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class InterParkParserTest {

    @Test
    public void 판매지수_Element_찾기() throws Exception {
        //given
        String url = "http://book.interpark.com/product/BookDisplay.do?_method=detail&sc.prdNo=321796760";
        InterParkParser parkParser = new InterParkParser();

        //when
        InterParkParseResult result = parkParser.parse(url);

        //then
        System.out.println(result);
        assertThat(result.getCurrentScore()).isGreaterThanOrEqualTo(1L);
    }

}
