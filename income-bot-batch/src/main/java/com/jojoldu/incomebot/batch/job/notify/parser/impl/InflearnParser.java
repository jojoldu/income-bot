package com.jojoldu.incomebot.batch.job.notify.parser.impl;

import com.jojoldu.incomebot.batch.job.notify.parser.LectureParser;
import com.jojoldu.incomebot.batch.job.notify.parser.result.InflearnParseResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class InflearnParser implements LectureParser<InflearnParseResult> {
    private static final String DIGIT_REGEX = "\\d+";
    private static final Pattern PATTERN = Pattern.compile(DIGIT_REGEX);

    @Override
    public InflearnParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new InflearnParseResult(getStudentCount(document), getCoursePrice(document));
        } catch (IOException e) {
            log.error("인프런 URL 파싱에 실패하였습니다.");
        }
        return InflearnParseResult.EMPTY;
    }

    private long getStudentCount(Document document) {
        Element section = document.getElementsByClass("student_cnt").get(0);
        String content = section.text();
        Matcher matcher = PATTERN.matcher(content);

        return matcher.find() ? parseLong(matcher.group()) : 0;
    }

    private long getCoursePrice(Document document) {
        Element section = document.getElementsByClass("course_price").get(0);
        String content = section.text();
        return parseLong(content
                .replaceAll("원", "")
                .replaceAll(",", ""));
    }
}
