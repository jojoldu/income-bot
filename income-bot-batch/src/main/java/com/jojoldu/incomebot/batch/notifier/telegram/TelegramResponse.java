package com.jojoldu.incomebot.batch.notifier.telegram;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@ToString
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramResponse {
    private boolean ok;
    private Result result;

    public TelegramResponse(boolean ok, Result result) {
        this.ok = ok;
        this.result = result;
    }

    @JsonIgnore
    public LocalDateTime getSendTime() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(result.date),
                TimeZone.getDefault().toZoneId());
    }

    @JsonIgnore
    public Chat getChat() {
        return result.chat;
    }

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        @JsonProperty("message_id")
        private long messageId;
        private Chat chat;
        private long date;
        private String text;
    }

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Chat {

        private Long id;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

    }
}
