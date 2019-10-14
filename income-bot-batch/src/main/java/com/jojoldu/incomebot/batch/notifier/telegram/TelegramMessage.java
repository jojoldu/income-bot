package com.jojoldu.incomebot.batch.notifier.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jojoldu.incomebot.core.lecture.LectureType;
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

    @Builder(builderClassName = "ByLecture", builderMethodName = "byLecture")
    public TelegramMessage(Long chatId, long beforeScore, long currentScore, LectureType type, String goods) {
        long changeScore = currentScore - beforeScore;
        this.chatId = chatId;
        this.text = createText(changeScore, type, goods, currentScore);
    }

    public static String createText(long changeScore, LectureType lectureType, String goods, Long newScore) {
        final String TEXT_FORMAT = "[{type}] \"{goods}\"가 {addScore} 되어 현재 {newScore} 입니다.";

        String code = changeScore >= 0 ? "+" : "-";
        return TEXT_FORMAT
                .replaceAll("\\{type\\}", lectureType.getTitle())
                .replaceAll("\\{goods\\}", goods)
                .replaceAll("\\{addScore\\}", code + changeScore)
                .replaceAll("\\{newScore\\}", String.valueOf(newScore));
    }
}
