package com.jojoldu.incomebot.parser.util;

import org.springframework.util.StringUtils;

import java.text.NumberFormat;

import static java.lang.Long.parseLong;

/**
 * Created by jojoldu@gmail.com on 28/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class NumberUtils {

    public static Long extractDigit(String content) {
        if (StringUtils.isEmpty(content)) {
            return 0L;
        }

        return parseLong(content.replaceAll("\\D+", ""));
    }

    public static String toAbsCommaNumber(Integer number) {
        return toCommaNumber(toAbs(number));
    }

    public static String toAbsCommaNumber(Long number) {
        return toCommaNumber(toAbs(number));
    }

    public static Integer toAbs(Integer number) {
        return Math.abs(number);
    }

    public static Long toAbs(Long number) {
        return Math.abs(number);
    }

    public static String toCommaNumber(Integer number) {
        return NumberFormat.getInstance().format(number);
    }

    public static String toCommaNumber(Long number) {
        return NumberFormat.getInstance().format(number);
    }


}
