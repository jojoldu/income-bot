package com.jojoldu.incomebot.parser.parser.book.aladin;

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
public class AladinUrlJsoupTest {

    @Test
    public void SalesPoint_Element_찾기() throws Exception {
        //given
        String url = "https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=168752840";
        Document document = Jsoup.connect(url).get();

        //when
        Elements salesPointElements = document.select("#wa_product_top1_wa_Top_Ranking_pnlRanking .Ere_fs15 strong");

        //then
        String text = salesPointElements.get(0).text().replace(",", "");
        System.out.println(text);
        assertThat(Long.parseLong(text)).isGreaterThanOrEqualTo(1L);
    }

}
