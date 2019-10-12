package com.jojoldu.incomebot.core;

import com.jojoldu.incomebot.core.instructor.Instructor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class LombokTest {

    @Test
    public void Getter가_호출된다() {
        //given
        Instructor instructor = new Instructor();

        //when
        String chatId = instructor.getChatId();

        //then
        assertThat(chatId).isNull();
    }

    @Test
    public void 커스텀Builder가_사용된다() {
        //given
        String chatId = "chatId";

        //when
        Instructor instructor = Instructor.signup(chatId);

        //then
        assertThat(instructor.getChatId()).isEqualTo(chatId);
    }
}
