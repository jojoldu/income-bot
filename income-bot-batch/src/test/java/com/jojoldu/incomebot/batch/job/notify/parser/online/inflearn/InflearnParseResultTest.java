package com.jojoldu.incomebot.batch.job.notify.parser.online.inflearn;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class InflearnParseResultTest {

    @Test
    public void 인프런_메세지_생성() {
        //given
        long studentCount = 1;
        long coursePrice = 22000;
        long beforeCount = 0;
        InflearnParseResult result = new InflearnParseResult(studentCount, coursePrice);

        //when
        String message = result.getMessage(beforeCount, "IntelliJ 를 시작하시는 분들을 위한 가이드");

        //then
        assertThat(message).isEqualTo("[인프런] \"IntelliJ 를 시작하시는 분들을 위한 가이드\"의 수강생이 +1명 (+13552원) 되어 현재 1 명이 수강중입니다.");
    }
}
