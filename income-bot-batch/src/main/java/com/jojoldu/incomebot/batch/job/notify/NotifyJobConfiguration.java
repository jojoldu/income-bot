package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.batch.common.JpaItemListWriter;
import com.jojoldu.incomebot.batch.job.JobChunkSize;
import com.jojoldu.incomebot.batch.job.notify.parameter.NotifyJobParameter;
import com.jojoldu.incomebot.batch.job.notify.processor.BookNotifyJobProcessor;
import com.jojoldu.incomebot.batch.job.notify.processor.OnlineNotifyJobProcessor;
import com.jojoldu.incomebot.core.lecture.book.BookLecture;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStore;
import com.jojoldu.incomebot.core.lecture.online.OnlineLecture;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.querydsl.reader.QuerydslNoOffsetPagingItemReader;
import org.springframework.batch.item.querydsl.reader.expression.Expression;
import org.springframework.batch.item.querydsl.reader.options.QuerydslNoOffsetNumberOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static com.jojoldu.incomebot.core.instructor.QInstructor.instructor;
import static com.jojoldu.incomebot.core.lecture.book.QBookLecture.bookLecture;
import static com.jojoldu.incomebot.core.lecture.online.QOnlineLecture.onlineLecture;

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
    private final JobChunkSize jobChunkSize;

    @Bean(BEAN_PREFIX+"jobParameter")
    @StepScope
    public NotifyJobParameter jobParameter() {
        return new NotifyJobParameter();
    }

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(bookStep())
                .next(onlineStep())
                .preventRestart()
                .build();
    }

    @Bean(BEAN_PREFIX + "bookStep")
    @JobScope
    public Step bookStep() {
        return stepBuilderFactory.get(BEAN_PREFIX + "bookStep")
                .<BookLecture, List<BookLectureStore>>chunk(jobChunkSize.getChunkSize())
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter())
                .build();
    }

    @Bean(BEAN_PREFIX + "bookReader")
    @StepScope
    public QuerydslNoOffsetPagingItemReader<BookLecture> bookReader() {
        // 1. No Offset Option
        QuerydslNoOffsetNumberOptions<BookLecture, Long> options =
                new QuerydslNoOffsetNumberOptions<>(bookLecture.id, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(emf, jobChunkSize.getChunkSize(), options, queryFactory -> queryFactory
                .select(bookLecture)
                .from(bookLecture)
                .join(bookLecture.instructor, instructor)
                .join(bookLecture.stores)
                .where(instructor.intervalType.eq(jobParameter.getInterval()))
        );
    }

    @Bean(BEAN_PREFIX + "bookProcessor")
    @StepScope
    public BookNotifyJobProcessor bookProcessor() {
        return new BookNotifyJobProcessor();
    }

    @Bean(BEAN_PREFIX + "bookWriter")
    public JpaItemListWriter<BookLectureStore> bookWriter() {
        JpaItemWriter<BookLectureStore> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(emf);
        return new JpaItemListWriter<>(itemWriter);
    }

    @Bean(BEAN_PREFIX + "onlineStep")
    @JobScope
    public Step onlineStep() {
        return stepBuilderFactory.get(BEAN_PREFIX + "onlineStep")
                .<OnlineLecture, List<OnlineLectureStore>>chunk(jobChunkSize.getChunkSize())
                .reader(onlineReader())
                .processor(onlineProcessor())
                .writer(onlineWriter())
                .build();
    }

    @Bean(BEAN_PREFIX + "onlineReader")
    @StepScope
    public QuerydslNoOffsetPagingItemReader<OnlineLecture> onlineReader() {
        // 1. No Offset Option
        QuerydslNoOffsetNumberOptions<OnlineLecture, Long> options =
                new QuerydslNoOffsetNumberOptions<>(onlineLecture.id, Expression.ASC);

        return new QuerydslNoOffsetPagingItemReader<>(emf, jobChunkSize.getChunkSize(), options, queryFactory -> queryFactory
                .select(onlineLecture)
                .from(onlineLecture)
                .join(onlineLecture.instructor, instructor)
                .join(onlineLecture.stores)
                .where(instructor.intervalType.eq(jobParameter.getInterval()))
        );
    }

    @Bean(BEAN_PREFIX + "onlineProcessor")
    @StepScope
    public OnlineNotifyJobProcessor onlineProcessor() {
        return new OnlineNotifyJobProcessor();
    }

    @Bean(BEAN_PREFIX + "onlineWriter")
    public JpaItemListWriter<OnlineLectureStore> onlineWriter() {
        JpaItemWriter<OnlineLectureStore> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(emf);
        return new JpaItemListWriter<>(itemWriter);
    }
}
