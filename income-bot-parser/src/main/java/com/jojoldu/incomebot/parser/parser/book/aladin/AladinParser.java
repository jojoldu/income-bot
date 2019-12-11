package com.jojoldu.incomebot.parser.parser.book.aladin;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType.ALADIN;
import static com.jojoldu.incomebot.parser.util.NumberUtils.extractDigit;
import static java.lang.Math.toIntExact;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class AladinParser implements BookParser<AladinParseResult> {

    @Override
    public String getIsbnQuery() {
        return "https://www.aladin.co.kr/search/wsearchresult.aspx?SearchTarget=All&SearchWord=%s";
    }

    @Override
    public BookLectureStoreType getStore() {
        return ALADIN;
    }

    @Override
    public Elements extractProductLink(Document document) {
        return document.select("#Myform table .ss_book_list .bo3");
    }

    @Override
    public String appendProductLink(String link) {
        return link;
    }

    @Override
    public AladinParseResult parse(String url) {
        Document document = getDocument(url);
        return new AladinParseResult(getSalesPoint(document), getRank(document));
    }

    private long getSalesPoint(Document document) {
        Elements elements = document.select("#wa_product_top1_wa_Top_Ranking_pnlRanking .Ere_fs15 strong");
        Element section = elements.get(elements.size() - 1);
        String content = section.text();
        return extractDigit(content);
    }

    private int getRank(Document document) {
        Elements elements = document.select("#wa_product_top1_wa_Top_Ranking_pnlRanking .Ere_fs15 a");
        Element section = elements.get(0);
        String content = section.text();
        return toIntExact(extractDigit(content));
    }

}
