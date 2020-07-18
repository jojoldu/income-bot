package com.jojoldu.incomebot.parser.parser.book.bandinlunis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 27/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class BandinlunisParserTest {

    private BandinlunisParser parser;

    @BeforeEach
    public void setUp() throws Exception {
        parser = new BandinlunisParser();
    }

    @Test
    public void 랭킹이있으면_가져온다() {
        //given
        String url = "http://www.bandinlunis.com/front/product/detailProduct.do?prodId=4256148";

        //when
        BandinlunisParseResult parseResult = parser.parse(url);

        //then
        System.out.println(parseResult);
        assertThat(parseResult.getCurrentScore()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void 메세지포맷_확인() {
        //given
        String url = "http://www.bandinlunis.com/front/product/detailProduct.do?prodId=4296899";

        //when
        BandinlunisParseResult parseResult = parser.parse(url);

        //then
        System.out.println(parseResult.getMessage(640, 0, "스프링 부트와 AWS로 혼자 구현하는 웹 서비스"));
    }

    @Test
    public void 랭킹이없으면_0이반환된다() {
        //given
        String url = "http://www.bandinlunis.com/front/product/detailProduct.do?prodId=4233553";

        //when
        BandinlunisParseResult parseResult = parser.parse(url);

        //then
        System.out.println(parseResult);
        assertThat(parseResult.getCurrentRank()).isEqualTo(0);
    }
}
