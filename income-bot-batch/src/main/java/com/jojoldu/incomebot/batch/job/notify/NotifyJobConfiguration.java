package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.batch.common.JpaItemListPersistWriter;
import com.jojoldu.incomebot.batch.common.QuerydslPagingItemReader;
import com.jojoldu.incomebot.batch.job.notify.parameter.NotifyJobParameter;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.instructor.QInstructor;
import com.querydsl.core.types.Projections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.jojoldu.incomebot.core.instructor.QInstructor.instructor;
import static com.querydsl.core.types.dsl.Expressions.constantAs;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NotifyJobConfiguration {
    public static final String JOB_NAME = "notifyJob";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;
    private final NotifyJobParameter jobParameter;

    private int chunkSize;

    @Value("${chunkSize:1000}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean(BEAN_PREFIX+"jobParameter")
    @JobScope
    public NotifyJobParameter jobParameter() {
        return new NotifyJobParameter();
    }

//    @Bean(JOB_NAME)
//    public Job job() {
//        return jobBuilderFactory.get(JOB_NAME)
//                .start(step())
//                .preventRestart()
//                .build();
//    }
//
//    @Bean(BEAN_PREFIX + "step")
//    @JobScope
//    public Step step() {
//        return stepBuilderFactory.get(BEAN_PREFIX + "step")
//                .<Instructor, Instructor>chunk(chunkSize)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
//    @Bean(BEAN_PREFIX + "reader")
//    @StepScope
//    public QuerydslPagingItemReader<Instructor> reader() {
//        return new QuerydslPagingItemReader<>(emf, chunkSize, queryFactory -> queryFactory
//                .selectFrom(instructor)
//                .where(instructor.interval.eq(jobParameter.getInterval()))
//        );
//    }
//
//    @Bean(BEAN_PREFIX + "writer")
//    public JpaItemListPersistWriter<TempTxPayMethodSummary> writer() {
//        return new JpaItemListPersistWriter<>(new JpaItemPersistWriter<>(emf));
//    }


}
