package com.jojoldu.incomebot.batch.job.notify.processor;

import com.jojoldu.incomebot.batch.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.core.lecture.book.BookLecture;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStore;
import com.jojoldu.incomebot.parser.parser.LectureParseExecutor;
import com.jojoldu.incomebot.parser.parser.book.BookParseResult;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@NoArgsConstructor
public class BookNotifyJobProcessor implements ItemProcessor<BookLecture, List<BookLectureStore>> {

    private TelegramNotifier telegramNotifier;
    private LectureParseExecutor lectureParserRestTemplate;

    @Autowired
    public void setTelegramNotifier(TelegramNotifier telegramNotifier) {
        this.telegramNotifier = telegramNotifier;
    }

    @Autowired
    public void setLectureParserRestTemplate(LectureParseExecutor lectureParserRestTemplate) {
        this.lectureParserRestTemplate = lectureParserRestTemplate;
    }

    @Override
    public List<BookLectureStore> process(BookLecture item) throws Exception {
        return item.getStores().stream()
                .map(this::notify)
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * store 별로 notify하기
     */
    public Optional<BookLectureStore> notify(BookLectureStore store) {
        return lectureParserRestTemplate.parse(store.getUrl(), store.getStoreType())
                .filter(parseResult -> store.isUpdatable(parseResult.getCurrentScore()))
                .map(parseResult -> notify(store, parseResult));
    }

    private BookLectureStore notify(BookLectureStore store, BookParseResult parseResult) {
        long beforeScore = store.getCurrentScore();
        int beforeRank = store.getCurrentRank();

        TelegramMessage message = new TelegramMessage(store.getChatId(), parseResult.getMessage(beforeScore, beforeRank, store.getTitle()));
        TelegramResponse response = telegramNotifier.notify(message);

        store.refreshScore(parseResult.getCurrentScore(), parseResult.getCurrentRank(), response.getSendedMessage(), response.getSendTime());

        return store;
    }

}
