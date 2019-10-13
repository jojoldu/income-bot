package com.jojoldu.incomebot.batch.job.notify.reader;

import com.jojoldu.incomebot.TestBatchConfig;
import com.jojoldu.incomebot.batch.common.QuerydslPagingItemReader;
import com.jojoldu.incomebot.batch.job.notify.NotifyJobConfiguration;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.instructor.InstructorRepository;
import com.jojoldu.incomebot.core.instructor.IntervalType;
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

import static com.jojoldu.incomebot.core.instructor.Instructor.signup;
import static com.jojoldu.incomebot.core.instructor.IntervalType.DAY_1;
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

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private QuerydslPagingItemReader<Instructor> reader;

    @Autowired
    private InstructorRepository instructorRepository;

    @After
    public void tearDown() throws Exception {
        instructorRepository.deleteAll();
    }

    public StepExecution getStepExecution() {
        JobParameters jobParameters = new JobParametersBuilder(jobLauncherTestUtils.getUniqueJobParameters())
                .addString("interval", "HOUR_1")
                .toJobParameters();

        return MetaDataInstanceFactory.createStepExecution(jobParameters);
    }

    @Test
    public void 해당되는_interval_instructor이_조회된다() throws Exception {
        //given
        int expected = 123;
        Instructor unExpected = signup(567);
        unExpected.updateInterval(DAY_1);
        instructorRepository.saveAll(Arrays.asList(signup(expected), unExpected));

        reader.open(new ExecutionContext());

        //when
        Instructor instructor = reader.read();

        //then
        assertThat(instructor.getChatId()).isEqualTo(expected);
        assertThat(reader.read()).isNull();
    }
}
