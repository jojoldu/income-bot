package com.jojoldu.incomebot.parser.parser.book.bandinlunis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 27/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class BandinlunisUrlJsoupTest {

    @Test
    public void 판매지수_Element_찾기() throws Exception {
        //given
        String url = "http://www.bandinlunis.com/front/product/detailProduct.do?prodId=4199501";
        Document document = Jsoup.connect(url).get();

        //when
        Elements salesPointElements = document.select(".section_left ul li strong");

        //then
        String text = salesPointElements.get(0).text().replace(",", "");
        System.out.println(text);
        assertThat(Long.parseLong(text)).isGreaterThanOrEqualTo(1L);
    }

}
