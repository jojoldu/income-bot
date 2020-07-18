package com.jojoldu.incomebot.parser.parser.book.yes24;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 27/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class Yes24UrlJsoupTest {

    @Test
    public void 판매지수_Element_찾기() throws Exception {
        //given
        String url = "http://www.yes24.com/Product/Goods/64584833";
        Document document = Jsoup.connect(url).get();

        //when
        Elements salesPointElements = document.select(".gd_sellNum");

        //then
        String text = salesPointElements.get(0).text();
        System.out.println(text);
        assertThat(text).contains("판매지수");
    }

    @Test
    public void 판매지수_숫자만_찾기() throws Exception {
        //given
        String url = "http://www.yes24.com/Product/Goods/64584833";
        Document document = Jsoup.connect(url).get();

        //when
        Elements salesPointElements = document.select(".gd_sellNum");

        //then
        String text = salesPointElements.get(0).text().replaceAll("\\D+", "");
        System.out.println(text);
        assertThat(Long.parseLong(text)).isGreaterThanOrEqualTo(1);
    }
}
