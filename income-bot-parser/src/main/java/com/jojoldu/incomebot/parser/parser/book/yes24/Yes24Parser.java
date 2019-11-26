package com.jojoldu.incomebot.parser.parser.book.yes24;

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
public class Yes24Parser implements LectureParser<Yes24ParseResult> {

    @Override
    public Yes24ParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new Yes24ParseResult(getSalesPoint(document));
        } catch (IOException e) {
            log.error("예스24 URL 파싱에 실패하였습니다.");
        }
        return Yes24ParseResult.EMPTY;
    }

    private long getSalesPoint(Document document) {
        Element section = document.select(".gd_sellNum").get(0);
        String content = section.text();
        return parseLong(content.replaceAll("\\D+", ""));
    }

}
