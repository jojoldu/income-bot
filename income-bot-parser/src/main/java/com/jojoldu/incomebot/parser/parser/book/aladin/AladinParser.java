package com.jojoldu.incomebot.parser.parser.book.aladin;

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
public class AladinParser implements LectureParser<AladinParseResult> {

    @Override
    public AladinParseResult parse(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return new AladinParseResult(getSalesPoint(document));
        } catch (IOException e) {
            log.error("알라딘 URL 파싱에 실패하였습니다.");
        }
        return AladinParseResult.EMPTY;
    }

    private long getSalesPoint(Document document) {
        Element section = document.select("#wa_product_top1_wa_Top_Ranking_pnlRanking .Ere_fs15 strong").get(0);
        String content = section.text();
        return parseLong(content.replace(",", ""));
    }

}
