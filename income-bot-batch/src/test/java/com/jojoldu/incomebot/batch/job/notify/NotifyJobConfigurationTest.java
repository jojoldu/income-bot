package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.TestBatchConfig;
import com.jojoldu.incomebot.batch.job.notify.parser.LectureParserRestTemplate;
import com.jojoldu.incomebot.batch.job.notify.parser.result.InflearnParseResult;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.instructor.InstructorRepository;
import com.jojoldu.incomebot.core.lecture.Lecture;
import com.jojoldu.incomebot.core.lecture.LectureRepository;
import com.jojoldu.incomebot.core.lecture.history.online.OnlineLectureHistory;
import com.jojoldu.incomebot.core.lecture.history.online.OnlineLectureHistoryRepository;
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

    @MockBean
    private TelegramNotifier telegramNotifier;

    @MockBean
    private LectureParserRestTemplate lectureParserRestTemplate;

    @After
    public void tearDown() throws Exception {
        instructorRepository.deleteAll();
    }

    @Test
    public void 조건에_맞는_Lecture의_변경점이_발송된다() throws Exception {
        //given
        given(telegramNotifier.notify(any()))
                .willReturn(new TelegramResponse(true, new TelegramResponse.Result(1570872227)));

        long newScore = 100L;
        given(lectureParserRestTemplate.parse(anyString(), any()))
                .willReturn(new InflearnParseResult(newScore, 22_000));

        createInstructor(123, "IntelliJ 를 시작하시는 분들을 위한 가이드", "https://www.inflearn.com/course/intellij-guide#");

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
        assertThat(histories.get(0).getMessage()).contains("+100");
    }

    private void createInstructor(long chatId, String goods, String url) {
        Instructor item = Instructor.signup(chatId);
        item.addLecture(goods, url, INFLEARN);
        instructorRepository.save(item);
    }
}
