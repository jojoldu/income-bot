package com.jojoldu.incomebot.parser.parser.book.yes24;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class Yes24ParserTest {
    private static Yes24Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Yes24Parser();
    }

    @Test
    public void 예스24_강의에서_수강생이_추출된다() {
        //given
        String url = "http://www.yes24.com/Product/Goods/64584833";

        //when
        long salesPoint = parser.parse(url).getSalesPoint();

        //then
        log.info("salesPoint= " + salesPoint);
        assertThat(salesPoint).isGreaterThanOrEqualTo(1);
    }

}
