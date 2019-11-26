package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.TestBatchConfig;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.instructor.InstructorRepository;
import com.jojoldu.incomebot.core.lecture.Lecture;
import com.jojoldu.incomebot.core.lecture.LectureRepository;
import com.jojoldu.incomebot.core.lecture.LectureType;
import com.jojoldu.incomebot.core.lecture.history.book.BookLectureHistory;
import com.jojoldu.incomebot.core.lecture.history.book.BookLectureHistoryRepository;
import com.jojoldu.incomebot.core.lecture.history.online.OnlineLectureHistory;
import com.jojoldu.incomebot.core.lecture.history.online.OnlineLectureHistoryRepository;
import com.jojoldu.incomebot.parser.parser.LectureParseExecutor;
import com.jojoldu.incomebot.parser.parser.book.yes24.Yes24ParseResult;
import com.jojoldu.incomebot.parser.parser.online.inflearn.InflearnParseResult;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.jojoldu.incomebot.core.lecture.LectureType.INFLEARN;
import static com.jojoldu.incomebot.core.lecture.LectureType.YES24;
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
@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes={NotifyJobConfiguration.class, TestBatchConfig.class})
public class NotifyJobConfigurationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private OnlineLectureHistoryRepository onlineLectureHistoryRepository;

    @Autowired
    private BookLectureHistoryRepository bookLectureHistoryRepository;

    @MockBean
    private TelegramNotifier telegramNotifier;

    @MockBean
    private LectureParseExecutor lectureParserRestTemplate;

    @After
    public void tearDown() throws Exception {
        instructorRepository.deleteAll();
    }

    @Test
    public void 조건에_맞는_Lecture의_변경점이_발송된다() throws Exception {
        //given
        long newScore = 100L;
        given(lectureParserRestTemplate.parse(anyString(), any()))
                .willReturn(of(new InflearnParseResult(newScore, 22_000)));

        given(telegramNotifier.notify(any()))
                .willReturn(new TelegramResponse(true, new TelegramResponse.Result(1570872227, "[인프런] \"IntelliJ 를 시작하시는 분들을 위한 가이드\"의 수강생이 +1명 (+13552원) 되어 현재 824 명이 수강중입니다.")));

        createInstructor(123, "IntelliJ 를 시작하시는 분들을 위한 가이드", "https://www.inflearn.com/course/intellij-guide#", INFLEARN);

        JobParameters jobParameters = new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters())
                .addString("interval", "HOUR_1")
                .addString("executeTime", "20191014123456")
                .toJobParameters();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        List<Lecture> lectures = lectureRepository.findAll();

        assertThat(lectures.size()).isEqualTo(1);
        assertThat(lectures.get(0).getCurrentScore()).isEqualTo(newScore);

        List<OnlineLectureHistory> histories = onlineLectureHistoryRepository.findAll();
        assertThat(histories.size()).isEqualTo(1);
        assertThat(histories.get(0).getCurrentScore()).isEqualTo(newScore);
        assertThat(histories.get(0).getMessage()).contains("인프런");
    }

    @Test
    public void 예스24가_저장된다() throws Exception {
        //given
        long newScore = 100L;
        given(lectureParserRestTemplate.parse(anyString(), any()))
                .willReturn(of(new Yes24ParseResult(newScore)));

        given(telegramNotifier.notify(any()))
                .willReturn(new TelegramResponse(true, new TelegramResponse.Result(1570872227, "[예스24] \"처음 배우는 스프링 부트 2\"의 판매지수가 +100 되어 현재 123를 달성했습니다.")));

        createInstructor(123, "처음 배우는 스프링 부트 2", "http://www.yes24.com/Product/Goods/64584833", YES24);

        JobParameters jobParameters = new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters())
                .addString("interval", "HOUR_1")
                .addString("executeTime", "20191014123456")
                .toJobParameters();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        List<Lecture> lectures = lectureRepository.findAll();

        assertThat(lectures.size()).isEqualTo(1);
        assertThat(lectures.get(0).getCurrentScore()).isEqualTo(newScore);

        List<BookLectureHistory> histories = bookLectureHistoryRepository.findAll();
        assertThat(histories.size()).isEqualTo(1);
        assertThat(histories.get(0).getCurrentScore()).isEqualTo(newScore);
        assertThat(histories.get(0).getMessage()).contains("예스24");
    }

    private void createInstructor(long chatId, String goods, String url, LectureType type) {
        Instructor item = Instructor.signup(chatId);
        item.addLecture(goods, url, type);
        instructorRepository.save(item);
    }
}
