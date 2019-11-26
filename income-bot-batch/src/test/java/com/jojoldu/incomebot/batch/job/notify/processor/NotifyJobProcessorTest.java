package com.jojoldu.incomebot.batch.job.notify.processor;

import com.jojoldu.incomebot.batch.job.notify.NotifyJobProcessor;
import com.jojoldu.incomebot.batch.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.batch.telegram.config.TelegramProperties;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.Lecture;
import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.core.lecture.history.online.OnlineLectureHistory;
import com.jojoldu.incomebot.parser.parser.LectureParseExecutor;
import com.jojoldu.incomebot.parser.parser.ParseResult;
import com.jojoldu.incomebot.parser.parser.online.inflearn.InflearnParseResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.jojoldu.incomebot.core.lecture.LectureType.INFLEARN;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 13/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class NotifyJobProcessorTest {
    private static final LocalDateTime REQUEST_DATE_TIME = LocalDateTime.of(2019,10,14,0,0,0);
    private static final Long NEW_SCORE = 100L;

    private NotifyJobProcessor processor;

    @Before
    public void setUp(){
        processor = new NotifyJobProcessor();
        processor.setTelegramNotifier(new StubTelegramNotifier());
        processor.setLectureParserRestTemplate(new StubLectureParserRestTemplate());
    }

    @Test
    public void Lecture의_History가_저장된다() throws Exception {
        //given
        long chatId = 123;
        Instructor item = Instructor.signup(chatId);
        String goods = "IntelliJ 를 시작하시는 분들을 위한 가이드";
        item.addLecture(goods, "https://www.inflearn.com/course/intellij-guide#", INFLEARN);

        //when
        List<Lecture> result = processor.process(item);

        //then
        assertThat(result.size()).isEqualTo(1);
        OnlineLectureHistory history = result.get(0).getOnlineHistories().get(0);
        assertHistory(history, goods);
    }

    @Test
    public void Lecture별로_History가_저장된다() throws Exception {
        //given
        long chatId = 123;
        Instructor item = Instructor.signup(chatId);
        String goods1 = "IntelliJ 를 시작하시는 분들을 위한 가이드";
        item.addLecture(goods1, "https://www.inflearn.com/course/intellij-guide#", INFLEARN);

        String goods2 = "실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발";
        item.addLecture(goods2, "https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-1#", INFLEARN);

        //when
        List<Lecture> result = processor.process(item);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertHistory(result.get(0).getOnlineHistories().get(0), goods1);
        assertHistory(result.get(1).getOnlineHistories().get(0), goods2);
    }

    private void assertHistory(OnlineLectureHistory history, String expectedMessage) {
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
        public Optional<ParseResult> parse(String url, LectureType type) {
            return Optional.of(new InflearnParseResult(NEW_SCORE, 0));
        }
    }
}
