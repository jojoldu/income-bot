package com.jojoldu.incomebot.parser.parser.book.bandinlunis;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.parser.exception.LectureParseException;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType.BANDINLUNIS;
import static com.jojoldu.incomebot.parser.util.NumberUtils.extractDigit;
import static java.lang.Math.toIntExact;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class BandinlunisParser implements BookParser<BandinlunisParseResult> {

    @Override
    public String getIsbnQuery() {
        return "http://www.bandinlunis.com/search/search.do?q=%s";
    }

    @Override
    public BookLectureStoreType getStore() {
        return BANDINLUNIS;
    }

    @Override
    public Elements extractProductLink(Document document) {
        return document.select(".view_type ul li a");
    }

    @Override
    public String appendProductLink(String link) {
        return link;
    }

    @Override
    public BandinlunisParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new BandinlunisParseResult(getSalesPoint(document), getRank(document));
        } catch (Exception e) {
            log.error("반디앤루니스 URL 파싱에 실패하였습니다.", e);
            throw new LectureParseException(getStore(), e);
        }
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".section_left ul li strong").get(0);
        String content = section.text();
        return extractDigit(content);
    }

    private int getRank(Document document) {
        Elements elements = document.select(".section_left ul li strong");
        if (elements.size() > 1) {
            return 0;
        }

        Element section = elements.get(elements.size() - 1);
        String content = section.text();
        return toIntExact(extractDigit(content));
    }


}
