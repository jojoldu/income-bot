package com.jojoldu.incomebot;

import com.jojoldu.incomebot.batch.job.JobChunkSize;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jojoldu@gmail.com on 13/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class TestBatchConfig {

    @Bean
    @ConditionalOnMissingBean(JobChunkSize.class)
    public JobChunkSize jobChunkSize() {
        return new JobChunkSize();
    }
}
