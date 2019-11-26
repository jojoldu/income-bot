package com.jojoldu.incomebot.parser.parser.book.interpark;

import com.jojoldu.incomebot.parser.parser.LectureParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static java.lang.Long.parseLong;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class InterParkParser implements LectureParser<InterParkParseResult> {

    @Override
    public InterParkParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new InterParkParseResult(getSalesPoint(document));
        } catch (IOException e) {
            log.error("인터파크 URL 파싱에 실패하였습니다.");
        }
        return InterParkParseResult.EMPTY;
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".indexBox").get(0);
        String content = section.text();
        return parseLong(content.replaceAll("\\D+", ""));
    }

}
