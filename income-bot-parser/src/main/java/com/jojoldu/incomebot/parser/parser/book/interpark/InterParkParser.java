package com.jojoldu.incomebot.parser.parser.book.interpark;

import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static com.jojoldu.incomebot.core.lecture.LectureType.INTERPARK;
import static java.lang.Long.parseLong;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class InterParkParser implements BookParser<InterParkParseResult> {

    @Override
    public String getIsbnQuery() {
        return null;
    }

    @Override
    public LectureType getStore() {
        return INTERPARK;
    }

    @Override
    public Elements extractProductLink(Document document) {
        return null;
    }

    @Override
    public String appendProductLink(String link) {
        return link;
    }

    @Override
    public InterParkParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new InterParkParseResult(getSalesPoint(document));
        } catch (IOException e) {
            log.error("인터파크 URL 파싱에 실패하였습니다.", e);
        }
        return InterParkParseResult.EMPTY;
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".indexBox").get(0);
        String content = section.text();
        return parseLong(content.replaceAll("\\D+", ""));
    }

}
