package com.jojoldu.incomebot.batch.notifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.incomebot.batch.config.AppConfig;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class TelegramResponseTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new AppConfig().objectMapper();
    }

    @Test
    public void 텔레그램_응답이_Dto로_전환된다() throws IOException {

        //given
        String message = "{\"ok\":true,\"result\":{\"message_id\":3,\"from\":{\"id\":1234,\"is_bot\":true,\"first_name\":\"\\ub108\\uc758 \\uc218\\uc785\\uc740\",\"username\":\"IncomeNotifier_bot\"},\"chat\":{\"id\":1234,\"first_name\":\"동욱\",\"last_name\":\"이\",\"type\":\"private\"},\"date\":1570872227,\"text\":\"테스트입니다.\"}}";

        //when
        TelegramResponse response = objectMapper.readValue(message, TelegramResponse.class);

        //then
        assertThat(response.isOk()).isTrue();
        assertThat(response.getResult().getMessageId()).isEqualTo(3);
        assertThat(response.getResult().getText()).isEqualTo("테스트입니다.");
        assertThat(response.getChat().getFirstName()).isEqualTo("동욱");
        assertThat(response.getChat().getLastName()).isEqualTo("이");
    }
}
