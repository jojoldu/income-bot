package com.jojoldu.incomebot.admin.service.dto;

import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 22/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@RequiredArgsConstructor
public enum BounceRateDirection {
    UP,
    DOWN;

    public static BounceRateDirection is(long num) {
        return num >= 0 ? UP : DOWN;
    }

    public static BounceRateDirection is(double num) {
        return num >= 0 ? UP : DOWN;
    }
}
