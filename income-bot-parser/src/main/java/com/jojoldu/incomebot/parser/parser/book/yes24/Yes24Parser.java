package com.jojoldu.incomebot.parser.parser.book.yes24;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.parser.exception.LectureParseException;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import static com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType.YES24;
import static com.jojoldu.incomebot.parser.util.NumberUtils.extractDigit;
import static java.lang.Math.toIntExact;

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
        try {
            Document document = Jsoup.connect(url).get();
            return new Yes24ParseResult(getSalesPoint(document), getRank(document));
        } catch (Exception e) {
            log.error("예스24 URL 파싱에 실패하였습니다.", e);
            throw new LectureParseException(getStore(), e);
        }
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".gd_sellNum").get(0);
        String content = section.text();
        return extractDigit(content);
    }

    private int getRank(Document document) {
        Elements rankCheck = document.select(".gd_best em");
        if (CollectionUtils.isEmpty(rankCheck)) {
            return 0;
        }

        Elements elements = document.select(".gd_best dd a");
        Element section = elements.get(0);
        String content = section.text();
        return toIntExact(extractDigit(content));
    }

}
