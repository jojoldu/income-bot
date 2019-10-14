package com.jojoldu.incomebot.core;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 14/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class MapTest {

    @Test
    public void map키_테스트() {
        //given
        String key = null;
        Map<String, String> map = new HashMap<>();

        //when
        boolean result = map.containsKey(key);

        //then
        assertThat(result).isFalse();
    }
}
