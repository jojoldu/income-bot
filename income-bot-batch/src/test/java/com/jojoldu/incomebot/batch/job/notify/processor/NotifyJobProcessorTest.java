package com.jojoldu.incomebot.batch.job.notify.processor;

import com.jojoldu.incomebot.batch.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.batch.telegram.config.TelegramProperties;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.online.OnlineLecture;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStore;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreType;
import com.jojoldu.incomebot.core.lecture.online.store.history.OnlineLectureStoreHistory;
import com.jojoldu.incomebot.parser.parser.LectureParseExecutor;
import com.jojoldu.incomebot.parser.parser.online.OnlineParseResult;
import com.jojoldu.incomebot.parser.parser.online.inflearn.InflearnParseResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreType.INFLEARN;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 13/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class NotifyJobProcessorTest {
    private static final LocalDateTime REQUEST_DATE_TIME = LocalDateTime.of(2019, 10, 14, 0, 0, 0);
    private static final Long NEW_SCORE = 100L;

    private OnlineNotifyJobProcessor processor;
    private Instructor instructor;

    @Before
    public void setUp() {
        processor = new OnlineNotifyJobProcessor();
        processor.setTelegramNotifier(new StubTelegramNotifier());
        processor.setLectureParserRestTemplate(new StubLectureParserRestTemplate());

        instructor = Instructor.signup(123);
    }

    @Test
    public void Lecture의_History가_저장된다() throws Exception {
        //given
        String expectedTitle = "IntelliJ 를 시작하시는 분들을 위한 가이드";
        OnlineLecture item = OnlineLecture.builder()
                .title(expectedTitle)
                .build();

        item.addStore(OnlineLectureStore.builder()
                .storeType(INFLEARN)
                .url("https://www.inflearn.com/course/intellij-guide#")
                .build());
        instructor.addLecture(item);

        //when
        List<OnlineLectureStore> result = processor.process(item);

        //then
        assertThat(result.size()).isEqualTo(1);
        OnlineLectureStoreHistory history = result.get(0).getHistories().get(0);
        assertHistory(history, expectedTitle);
    }

    private void assertHistory(OnlineLectureStoreHistory history, String expectedMessage) {
        assertThat(history.getMessage()).contains(expectedMessage);
        assertThat(history.getBeforeScore()).isEqualTo(0);
        assertThat(history.getCurrentScore()).isEqualTo(NEW_SCORE);
    }

    public static class StubTelegramNotifier extends TelegramNotifier {

        public StubTelegramNotifier() {
            super(new RestTemplate(), new TelegramProperties());
        }

        @Override
        public TelegramResponse notify(TelegramMessage message) {
            ZoneId zoneId = ZoneId.systemDefault();
            long date = REQUEST_DATE_TIME.atZone(zoneId).toEpochSecond();
            TelegramResponse.Result result = new TelegramResponse.Result(date, message.getText());
            return new TelegramResponse(true, result);
        }
    }

    public static class StubLectureParserRestTemplate extends LectureParseExecutor {

        @Override
        public Optional<OnlineParseResult> parse(String url, OnlineLectureStoreType type) {
            return Optional.of(new InflearnParseResult(NEW_SCORE, 0));
        }
    }
}
