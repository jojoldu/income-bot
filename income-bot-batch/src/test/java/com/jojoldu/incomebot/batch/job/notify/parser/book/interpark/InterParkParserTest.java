package com.jojoldu.incomebot.batch.job.notify.parser.book.interpark;

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
        String url = "http://book.interpark.com/product/BookDisplay.do?_method=Detail&sc.shopNo=0000400000&dispNo=&sc.prdNo=321276319&sc.saNo=002001005&bkid1=category&bkid2=ct028005&bkid3=c1&bkid4=001";
        InterParkParser parkParser = new InterParkParser();

        //when
        InterParkParseResult result = parkParser.parse(url);

        //then
        System.out.println(result);
        assertThat(result.getSalesPoint()).isGreaterThanOrEqualTo(1L);
    }

}
