package com.jojoldu.incomebot.parser.parser.book.kyobo;

import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.parser.exception.LectureParseException;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.jojoldu.incomebot.core.lecture.LectureType.KYOBO;
import static java.lang.Long.parseLong;

/**
 * Created by jojoldu@gmail.com on 04/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class KyoboParser implements BookParser<KyoboParseResult> {

    @Override
    public String getIsbnQuery() {
        return "https://search.kyobobook.co.kr/web/search?vPstrKeyWord=9788965402602";
    }

    @Override
    public LectureType getStore() {
        return KYOBO;
    }

    @Override
    public Elements extractProductLink(Document document) {
        return document.select(".list_search_result #search_list tr .title a");
    }

    @Override
    public String appendProductLink(String link) {
        return link;
    }

    @Override
    public KyoboParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new KyoboParseResult(getRank(document));
        } catch (Exception e) {
            log.error("교보문고 URL 파싱에 실패하였습니다.", e);
            throw new LectureParseException(getStore(), e);
        }
    }

    private long getRank(Document document) {
        Element section = document.select(".rank a em").get(0);
        String content = section.text();
        return parseLong(content.replaceAll("\\D+", ""));
    }
}
