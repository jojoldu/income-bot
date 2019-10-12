package com.jojoldu.incomebot.batch.parser.impl;

import com.jojoldu.incomebot.batch.parser.LectureParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class InflearnParser implements LectureParser {
    private static final String DIGIT_REGEX = "\\d+";
    private static final Pattern PATTERN = Pattern.compile(DIGIT_REGEX);

    @Override
    public long parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element section = document.getElementsByClass("student_cnt").get(0);
            String content = section.text();
            Matcher matcher = PATTERN.matcher(content);

            return matcher.find()? parseLong(matcher.group()) : 0;

        } catch (IOException e) {
            log.error("인프런 URL 파싱에 실패하였습니다.");
        }
        return 0;
    }

}
