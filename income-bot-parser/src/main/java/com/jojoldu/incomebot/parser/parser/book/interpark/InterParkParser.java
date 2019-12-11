package com.jojoldu.incomebot.parser.parser.book.interpark;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType.INTERPARK;
import static com.jojoldu.incomebot.parser.util.NumberUtils.extractDigit;
import static java.lang.Math.toIntExact;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class InterParkParser implements BookParser<InterParkParseResult> {

    @Override
    public String getIsbnQuery() {
        return "http://bsearch.interpark.com/dsearch/book.jsp?sch=all&bookblockname=s_main&booklinkname=s_main&bid1=search_auto&bid2=product&bid3=000&bid4=001&query=%s";
    }

    @Override
    public BookLectureStoreType getStore() {
        return INTERPARK;
    }

    @Override
    public Elements extractProductLink(Document document) {
        return document.select(".list_wrap .tit a");
    }

    @Override
    public String appendProductLink(String link) {
        return link;
    }

    @Override
    public InterParkParseResult parse(String url) {
        Document document = getDocument(url);
        return new InterParkParseResult(getSalesPoint(document), getRank(document));
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".saleIndexWrap .indexBox span").get(0);
        String content = section.text();
        return extractDigit(content);
    }

    private int getRank(Document document) {
        Elements elements = document.select(".weekRankBox .wRankList tbody tr td p span");
        Element section = elements.get(elements.size() - 1);
        String content = section.text();
        return toIntExact(extractDigit(content));
    }

}
