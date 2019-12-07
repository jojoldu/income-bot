package com.jojoldu.incomebot.batch.job.notify.processor;

import com.jojoldu.incomebot.batch.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.core.lecture.online.OnlineLecture;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStore;
import com.jojoldu.incomebot.parser.parser.LectureParseExecutor;
import com.jojoldu.incomebot.parser.parser.online.OnlineParseResult;
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
public class OnlineNotifyJobProcessor implements ItemProcessor<OnlineLecture, List<OnlineLectureStore>> {

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
    public List<OnlineLectureStore> process(OnlineLecture item) throws Exception {
        return item.getStores().stream()
                .map(this::notify)
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * store 별로 notify하기
     */
    public Optional<OnlineLectureStore> notify(OnlineLectureStore store) {
        return lectureParserRestTemplate.parse(store.getUrl(), store.getStoreType())
                .filter(parseResult -> store.isUpdatable(parseResult.getCurrentScore()))
                .map(parseResult -> notify(store, parseResult));
    }

    private OnlineLectureStore notify(OnlineLectureStore store, OnlineParseResult parseResult) {
        @SuppressWarnings("DuplicatedCode") long beforeScore = store.getCurrentScore();

        TelegramMessage message = new TelegramMessage(store.getChatId(), parseResult.getMessage(beforeScore, store.getTitle()));
        TelegramResponse response = telegramNotifier.notify(message);

        store.refreshScore(parseResult.getCurrentScore(), response.getSendedMessage(), response.getSendTime());

        return store;
    }

}
