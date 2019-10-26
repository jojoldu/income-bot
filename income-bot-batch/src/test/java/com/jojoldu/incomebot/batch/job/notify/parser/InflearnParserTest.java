package com.jojoldu.incomebot.batch.job.notify.parser;

import com.jojoldu.incomebot.batch.job.notify.parser.impl.InflearnParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class InflearnParserTest {
    private static InflearnParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new InflearnParser();
    }

    @Test
    public void 인프런_강의에서_수강생만_뽑아낸다() {
        //given
        String url = "https://www.inflearn.com/course/intellij-guide#";

        //when
        long count = parser.parse(url).getStudentCount();

        //then
        log.info("count= "+count);
        assertThat(count).isGreaterThanOrEqualTo(814);
    }
}
