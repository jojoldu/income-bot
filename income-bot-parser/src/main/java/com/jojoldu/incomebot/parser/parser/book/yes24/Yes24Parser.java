package com.jojoldu.incomebot.parser.parser.book.yes24;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import static com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType.YES24;
import static com.jojoldu.incomebot.parser.util.NumberUtils.extractDigit;
import static java.lang.Math.toIntExact;
import static java.lang.String.format;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class Yes24Parser implements BookParser<Yes24ParseResult> {

    @Override
    public String getIsbnQuery() {
        return "http://www.yes24.com/searchcorner/Search?domain=ALL&Wcode=001_005&query=%s";
    }

    @Override
    public BookLectureStoreType getStore() {
        return YES24;
    }

    @Override
    public Elements extractProductLink(Document document) {
        return document.select(".goodsList tbody tr .goods_name a");
    }

    @Override
    public String appendProductLink(String link) {
        return "http://www.yes24.com" + link;
    }

    @Override
    public Yes24ParseResult parse(String url) {
        Document document = getDocument(url);
        return new Yes24ParseResult(getSalesPoint(document), getRank(document));
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".gd_sellNum").get(0);
        String content = section.text();
        return extractDigit(content);
    }

    private int getRank(Document document) {
        Document rankDocument = parseRank(document);
        Elements rankCheck = rankDocument.select(".gd_best em");
        if (CollectionUtils.isEmpty(rankCheck)) {
            return 0;
        }

        Elements elements = rankDocument.select(".gd_best dd a");
        Element section = elements.get(0);
        String content = section.text();
        return toIntExact(extractDigit(content));
    }

    public Document parseRank(Document document) {
        String productNo = document.baseUri().replace("http://www.yes24.com/Product/Goods/", "");
        String categoryNo = document.select("#dispNo").val();
        String url = format("http://www.yes24.com/Product/addModules/BestSellerRank_Book/%s/?categoryNumber=%s", productNo, categoryNo);
        return getDocument(url);
    }

}
