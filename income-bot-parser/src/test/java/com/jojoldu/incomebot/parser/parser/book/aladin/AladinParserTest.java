package com.jojoldu.incomebot.parser.parser.book.aladin;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 27/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class AladinParserTest {

    private AladinParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new AladinParser();
    }

    @Test
    public void isbn_링크_가져온다() {
        //given
        String isbn = "9788965402602";

        //when
        String result = parser.getProductLinkByISBN(isbn);

        //then
        assertThat(result).isEqualTo("http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=218568947");
    }
}
