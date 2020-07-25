package com.jojoldu.incomebot.parser.parser.book.yes24;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class Yes24ParserTest {
    private static Yes24Parser parser;

    @BeforeEach
    public void setUp() throws Exception {
        parser = new Yes24Parser();
    }

    @Test
    public void 예스24에서_판매지수가_추출된다() {
        //given
        String url = "http://www.yes24.com/Product/Goods/64584833";

        //when
        long salesPoint = parser.parse(url).getCurrentScore();

        //then
        log.info("salesPoint= " + salesPoint);
        assertThat(salesPoint).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void isbn_링크_가져온다() {
        //given
        String isbn = "9788965402602";

        //when
        String result = parser.getProductLinkByISBN(isbn);

        //then
        assertThat(result).contains("http://www.yes24.com/Product/Goods/83849117");
    }

    @Test
    public void 예스24에서_순위가_추출된다() {
        //given
        String url = "http://www.yes24.com/Product/Goods/83849117";

        //when
        int rank = parser.parse(url).getCurrentRank();

        //then
        log.info("rank= " + rank);
        assertThat(rank).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void 예스24에서_순위밖이면_0이반환된다() {
        //given
        String url = "http://www.yes24.com/Product/Goods/64584833";

        //when
        int rank = parser.parse(url).getCurrentRank();

        //then
        log.info("rank= " + rank);
        assertThat(rank).isEqualTo(0);
    }
}
