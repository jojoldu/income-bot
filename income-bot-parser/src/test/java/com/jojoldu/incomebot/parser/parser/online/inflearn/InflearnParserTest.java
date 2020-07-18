package com.jojoldu.incomebot.parser.parser.online.inflearn;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
public class InflearnParserTest {
    private static InflearnParser parser;

    @BeforeEach
    public void setUp() throws Exception {
        parser = new InflearnParser();
    }

    @Test
    public void 인프런_강의에서_수강생이_추출된다() {
        //given
        String url = "https://www.inflearn.com/course/intellij-guide#";

        //when
        long count = parser.parse(url).getStudentCount();

        //then
        log.info("count= "+count);
        assertThat(count).isGreaterThanOrEqualTo(814);
    }

    @Test
    public void 인프런_강의에서_가격이_추출된다() {
        //given
        String url = "https://www.inflearn.com/course/intellij-guide#";

        //when
        long coursePrice = parser.parse(url).getCoursePrice();

        //then
        log.info("coursePrice= " + coursePrice);
        assertThat(coursePrice).isGreaterThanOrEqualTo(1_000);
        assertThat(coursePrice).isLessThan(10_000_000);
    }
}
