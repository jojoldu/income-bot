package com.jojoldu.incomebot.parser.parser.book;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.parser.exception.LectureParseException;
import com.jojoldu.incomebot.parser.parser.LectureParser;
import com.jojoldu.incomebot.parser.parser.ParseResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by jojoldu@gmail.com on 27/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public interface BookParser<T extends ParseResult> extends LectureParser {
    Logger log = LoggerFactory.getLogger(BookParser.class);

    default String getProductLinkByISBN(String isbn) {
        String store = getStore().getTitle();

        Document document = getIsbnDom(isbn)
                .orElseThrow(() -> new LectureParseException(format("[%s] ISBN 파싱에 실패하였습니다.", store)));

        Elements elements = extractProductLink(document);
        validateProductLink(elements, store, isbn);
        return appendProductLink(elements.get(0).attr("href"));
    }

    default Optional<Document> getIsbnDom(String isbn) {
        String url = format(getIsbnQuery(), isbn);
        try {
            return Optional.of(Jsoup.connect(url).get());
        } catch (IOException e) {
            log.error("ISBN 파싱에 실패하였습니다.", e);
            return Optional.empty();
        }
    }

    default void validateProductLink(Elements elements, String store, String isbn) {
        if (CollectionUtils.isEmpty(elements)) {
            throw new LectureParseException(format("[%s] 해당 상품이 입고되어있지 않습니다. isbn=%s", store, isbn));
        }
    }

    String getIsbnQuery();

    BookLectureStoreType getStore();

    Elements extractProductLink(Document document);

    String appendProductLink(String link);

}
