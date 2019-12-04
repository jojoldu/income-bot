package com.jojoldu.incomebot.parser.parser.book.kyobo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 27/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class KyoboParserTest {

    @Test
    public void 판매지수_Element_찾기() throws Exception {
        //given
        String url = "http://www.kyobobook.co.kr/product/detailViewKor.laf?barcode=9788965402602";
        KyoboParser parkParser = new KyoboParser();

        //when
        KyoboParseResult result = parkParser.parse(url);

        //then
        System.out.println(result);
        assertThat(result.getCurrentScore()).isGreaterThanOrEqualTo(1L);
    }

}
