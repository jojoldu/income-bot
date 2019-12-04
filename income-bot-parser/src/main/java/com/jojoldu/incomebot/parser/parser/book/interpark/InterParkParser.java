package com.jojoldu.incomebot.parser.parser.book.interpark;

import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.parser.exception.LectureParseException;
import com.jojoldu.incomebot.parser.parser.book.BookParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        return "http://bsearch.interpark.com/dsearch/book.jsp?sch=all&bookblockname=s_main&booklinkname=s_main&bid1=search_auto&bid2=product&bid3=000&bid4=001&query=%s";
    }

    @Override
    public LectureType getStore() {
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
        try {
            Document document = Jsoup.connect(url).get();
            return new InterParkParseResult(getSalesPoint(document));
        } catch (Exception e) {
            log.error("인터파크 URL 파싱에 실패하였습니다.", e);
            throw new LectureParseException(getStore(), e);
        }
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".saleIndexWrap .indexBox span").get(0);
        String content = section.text();
        return parseLong(content.replaceAll("\\D+", ""));
    }

}
