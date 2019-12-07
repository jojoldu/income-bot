package com.jojoldu.incomebot.batch.job.notify.reader;

import com.jojoldu.incomebot.TestBatchConfig;
import com.jojoldu.incomebot.batch.common.QuerydslPagingItemReader;
import com.jojoldu.incomebot.batch.job.notify.NotifyJobConfiguration;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.instructor.InstructorRepository;
import com.jojoldu.incomebot.core.lecture.online.OnlineLecture;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStore;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.jojoldu.incomebot.core.instructor.IntervalType.DAY_1;
import static com.jojoldu.incomebot.core.instructor.IntervalType.HOUR_1;
import static com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStoreType.INFLEARN;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 13/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes={NotifyJobConfiguration.class, TestBatchConfig.class})
public class NotifyJobConfigurationReaderTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private QuerydslPagingItemReader<OnlineLecture> reader;

    @Autowired
    private InstructorRepository instructorRepository;

    @After
    public void tearDown() throws Exception {
        instructorRepository.deleteAll();
    }

    public StepExecution getStepExecution() {
        JobParameters jobParameters = new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters())
                .addString("interval", "HOUR_1")
                .addString("executeTime", "20191014123400")
                .toJobParameters();

        return MetaDataInstanceFactory.createStepExecution(jobParameters);
    }

    @Test
    public void 해당되는_interval_instructor이_조회된다() throws Exception {
        //given
        Instructor expected = new Instructor(123, HOUR_1);
        String expectedTitle = "IntelliJ 를 시작하시는 분들을 위한 가이드";
        OnlineLecture onlineLecture = new OnlineLecture(expectedTitle);
        onlineLecture.addStore(OnlineLectureStore.init(INFLEARN, "https://www.inflearn.com/course/intellij-guide#"));
        expected.addLecture(onlineLecture);

        Instructor unExpected = new Instructor(567, DAY_1);
        OnlineLecture unExpectedLecture = new OnlineLecture("테스트");
        unExpectedLecture.addStore(OnlineLectureStore.init(INFLEARN, "테스트"));
        unExpected.addLecture(unExpectedLecture);

        instructorRepository.saveAll(Arrays.asList(expected, unExpected));

        reader.open(new ExecutionContext());

        //when
        OnlineLecture result = reader.read();

        //then
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(reader.read()).isNull();
    }
}
