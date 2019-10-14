package com.jojoldu.incomebot.batch.job.notify.processor;

import com.jojoldu.incomebot.batch.job.notify.NotifyJobProcessor;
import com.jojoldu.incomebot.batch.notifier.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.notifier.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.notifier.telegram.TelegramResponse;
import com.jojoldu.incomebot.batch.notifier.telegram.config.TelegramProperties;
import com.jojoldu.incomebot.batch.parser.LectureParserRestTemplate;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.core.notifyhistory.NotifyHistory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.jojoldu.incomebot.core.lecture.LectureType.INFLEARN;
import static java.lang.String.format;
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
    public void setUp() throws Exception {
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
        List<NotifyHistory> result = processor.process(item);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertHistory(result.get(0), format("[인프런] %s가 %d만큼 증가 하였습니다.", goods, NEW_SCORE));
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
        List<NotifyHistory> result = processor.process(item);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertHistory(result.get(0), format("[인프런] %s가 %d만큼 증가 하였습니다.", goods1, NEW_SCORE));
        assertHistory(result.get(1), format("[인프런] %s가 %d만큼 증가 하였습니다.", goods2, NEW_SCORE));
    }

    private void assertHistory(NotifyHistory history, String expectedMessage) {
        assertThat(history.getMessage()).isEqualTo(expectedMessage);
        assertThat(history.getBeforeScore()).isEqualTo(0);
        assertThat(history.getCurrentScore()).isEqualTo(NEW_SCORE);
    }

    public static class StubTelegramNotifier extends TelegramNotifier {

        public StubTelegramNotifier() {
            super(new RestTemplate(), new TelegramProperties());
        }

        @Override
        public TelegramResponse notify(TelegramMessage message) {
            TelegramResponse.Result result = new TelegramResponse.Result();
            ZoneId zoneId = ZoneId.systemDefault();
            result.setDate(REQUEST_DATE_TIME.atZone(zoneId).toEpochSecond());
            return new TelegramResponse(true, result);
        }
    }

    public static class StubLectureParserRestTemplate extends LectureParserRestTemplate {

        @Override
        public long parse(String url, LectureType type) {
            return NEW_SCORE;
        }
    }
}
