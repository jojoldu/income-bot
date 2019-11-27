package com.jojoldu.incomebot.parser.parser.book.bandinlunis;

import org.junit.Before;

/**
 * Created by jojoldu@gmail.com on 27/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class BandinlunisParserTest {

    private BandinlunisParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new BandinlunisParser();
    }

//    @Test
//    public void isbn_링크_가져온다() {
//        //given
//        String isbn = "9791162241264";
//
//        //when
//        String result = parser.getProductLinkByISBN(isbn);
//
//        //then
//        assertThat(result).isEqualTo("http://www.bandinlunis.com/front/product/detailProduct.do?prodId=4199501");
//    }
}
