package com.jojoldu.incomebot.parser.parser.book.aladin;

import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static java.lang.Long.parseLong;

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
    public LectureType getStore() {
        return LectureType.ALADIN;
    }

    @Override
    public Elements extractProductLink(Document document) {
        return document.select("#Myform table .ss_book_list a");
    }

    @Override
    public String appendProductLink(String link) {
        return link;
    }

    @Override
    public AladinParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new AladinParseResult(getSalesPoint(document));
        } catch (IOException e) {
            log.error("알라딘 URL 파싱에 실패하였습니다.", e);
        }
        return AladinParseResult.EMPTY;
    }

    private long getSalesPoint(Document document) {
        Elements elements = document.select("#wa_product_top1_wa_Top_Ranking_pnlRanking .Ere_fs15 strong");
        Element section = elements.get(elements.size() - 1);
        String content = section.text();
        return parseLong(content.replace(",", ""));
    }

}
