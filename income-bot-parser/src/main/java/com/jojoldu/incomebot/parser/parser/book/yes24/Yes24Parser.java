package com.jojoldu.incomebot.parser.parser.book.yes24;

import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.parser.exception.LectureParseException;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.jojoldu.incomebot.core.lecture.LectureType.YES24;
import static java.lang.Long.parseLong;

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
    public LectureType getStore() {
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
            return new Yes24ParseResult(getSalesPoint(document));
        } catch (Exception e) {
            log.error("예스24 URL 파싱에 실패하였습니다.", e);
            throw new LectureParseException(getStore(), e);
        }
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".gd_sellNum").get(0);
        String content = section.text();
        return parseLong(content.replaceAll("\\D+", ""));
    }

}
