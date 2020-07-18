package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.TestBatchConfig;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.instructor.InstructorRepository;
import com.jojoldu.incomebot.core.lecture.book.BookLecture;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStore;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreRepository;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import com.jojoldu.incomebot.core.lecture.book.store.history.BookLectureStoreHistory;
import com.jojoldu.incomebot.core.lecture.book.store.history.BookLectureStoreHistoryRepository;
import com.jojoldu.incomebot.core.lecture.online.OnlineLecture;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStore;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreRepository;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreType;
import com.jojoldu.incomebot.core.lecture.online.store.history.OnlineLectureStoreHistory;
import com.jojoldu.incomebot.core.lecture.online.store.history.OnlineLectureStoreHistoryRepository;
import com.jojoldu.incomebot.parser.parser.LectureParseExecutor;
import com.jojoldu.incomebot.parser.parser.book.kyobo.KyoboParseResult;
import com.jojoldu.incomebot.parser.parser.book.yes24.Yes24ParseResult;
import com.jojoldu.incomebot.parser.parser.online.inflearn.InflearnParseResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;

import static com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType.YES24;
import static com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreType.INFLEARN;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * Created by jojoldu@gmail.com on 14/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@ExtendWith(SpringExtension.class)
@SpringBatchTest
@SpringBootTest(classes={NotifyJobConfiguration.class, TestBatchConfig.class})
public class NotifyJobConfigurationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private BookLectureStoreRepository bookLectureStoreRepository;

    @Autowired
    private OnlineLectureStoreRepository onlineLectureStoreRepository;

    @Autowired
    private OnlineLectureStoreHistoryRepository onlineLectureHistoryRepository;

    @Autowired
    private BookLectureStoreHistoryRepository bookLectureHistoryRepository;

    @MockBean
    private TelegramNotifier telegramNotifier;

    @MockBean
    private LectureParseExecutor lectureParserRestTemplate;

    private Instructor instructor;

    @BeforeEach
    public void setUp() throws Exception {
        instructor = Instructor.signup(123);
    }

    @AfterEach
    public void tearDown() throws Exception {
        instructorRepository.deleteAll();
    }

    @Test
    public void 조건에_맞는_Lecture의_변경점이_발송된다() throws Exception {
        //given
        long newScore = 100L;
        given(lectureParserRestTemplate.parse(anyString(), any(OnlineLectureStoreType.class))).willReturn(of(new InflearnParseResult(newScore, 22_000)));
        given(telegramNotifier.notify(any())).willReturn(telegramResponse("[인프런] \"IntelliJ 를 시작하시는 분들을 위한 가이드\"의 수강생이 +1명 (+13552원) 되어 현재 824 명이 수강중입니다."));

        OnlineLecture onlineLecture = OnlineLecture.builder()
                .title("IntelliJ 를 시작하시는 분들을 위한 가이드")
                .build();

        onlineLecture.addStore(OnlineLectureStore.builder()
                .storeType(INFLEARN)
                .url("https://www.inflearn.com/course/intellij-guide#")
                .build());
        instructor.addLecture(onlineLecture);
        instructorRepository.save(instructor);

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertOnlineLecture(newScore);

        List<OnlineLectureStoreHistory> histories = onlineLectureHistoryRepository.findAll();
        assertThat(histories.size()).isEqualTo(1);
        assertThat(histories.get(0).getCurrentScore()).isEqualTo(newScore);
        assertThat(histories.get(0).getMessage()).contains("인프런");
    }

    @Test
    public void 예스24가_저장된다() throws Exception {
        //given
        long newScore = 100L;
        given(lectureParserRestTemplate.parse(anyString(), any(BookLectureStoreType.class))).willReturn(of(new Yes24ParseResult(newScore, 0)));
        given(telegramNotifier.notify(any())).willReturn(telegramResponse("[예스24] \"스프링 부트와 AWS로 혼자 구현하는 웹 서비스\"의 판매지수가 +666 되어 현재 7,812 를 달성했습니다."));

        BookLecture bookLecture = BookLecture.builder()
                .title("스프링 부트와 AWS로 혼자 구현하는 웹 서비스")
                .isbn("9788965402602")
                .build();

        bookLecture.addStore(BookLectureStore.init(YES24, "http://www.yes24.com/Product/Goods/83849117"));
        instructor.addLecture(bookLecture);
        instructorRepository.save(instructor);

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertBookLecture(newScore);
        assertBookHistory(newScore, "예스24");
    }

    @Test
    public void 교보문고_기존에_저장된게없어도_갱신된다() throws Exception {
        //given
        long newScore = 100L;
        given(lectureParserRestTemplate.parse(anyString(), any(BookLectureStoreType.class))).willReturn(of(new KyoboParseResult(newScore)));
        given(telegramNotifier.notify(any())).willReturn(telegramResponse("[교보문고] \"스프링 부트와 AWS로 혼자 구현하는 웹 서비스\"의 순위가 5 만큼 상승 하여 3위 를 달성했습니다."));

        BookLecture bookLecture = BookLecture.builder()
                .title("스프링 부트와 AWS로 혼자 구현하는 웹 서비스")
                .isbn("9788965402602")
                .build();

        bookLecture.addStore(BookLectureStore.init(YES24, "http://www.kyobobook.co.kr/product/detailViewKor.laf?barcode=9788965402602"));
        instructor.addLecture(bookLecture);
        instructorRepository.save(instructor);

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertBookLecture(newScore);

        assertBookHistory(newScore, "교보문고");
    }

    private void assertOnlineLecture(long newScore) {
        List<OnlineLectureStore> lectures = onlineLectureStoreRepository.findAll();

        assertThat(lectures.size()).isEqualTo(1);
        assertThat(lectures.get(0).getCurrentScore()).isEqualTo(newScore);
    }

    private void assertBookLecture(long newScore) {
        List<BookLectureStore> lectures = bookLectureStoreRepository.findAll();

        assertThat(lectures.size()).isEqualTo(1);
        assertThat(lectures.get(0).getCurrentScore()).isEqualTo(newScore);
    }

    private void assertBookHistory(long newScore, String expectedMessage) {
        List<BookLectureStoreHistory> histories = bookLectureHistoryRepository.findAll();
        assertThat(histories.size()).isEqualTo(1);
        assertThat(histories.get(0).getCurrentScore()).isEqualTo(newScore);
        assertThat(histories.get(0).getMessage()).contains(expectedMessage);
    }

    private TelegramResponse telegramResponse(String s) {
        return new TelegramResponse(true, new TelegramResponse.Result(1570872227, s));
    }

    private JobParameters getJobParameters() {
        return new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters())
                .addString("interval", "HOUR_1")
                .addString("executeTime", "20191014123456")
                .toJobParameters();
    }
}
