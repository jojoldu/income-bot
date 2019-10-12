package com.jojoldu.incomebot.batch.notifier.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@ToString
@Getter
public class TelegramMessage {
    @JsonProperty("chat_id")
    private Long chatId;
    private String text;

    @Builder
    public TelegramMessage(Long chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }
}
