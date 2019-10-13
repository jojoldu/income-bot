package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.batch.notifier.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.notifier.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.notifier.telegram.TelegramResponse;
import com.jojoldu.incomebot.batch.parser.LectureParserType;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.Lecture;
import com.jojoldu.incomebot.core.notifyhistory.NotifyHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
public class NotifyJobProcessor implements ItemProcessor<Instructor, List<NotifyHistory>> {

    private TelegramNotifier telegramNotifier;

    @Autowired
    public void setTelegramNotifier(TelegramNotifier telegramNotifier) {
        this.telegramNotifier = telegramNotifier;
    }

    @Override
    public List<NotifyHistory> process(Instructor item) throws Exception {
        return item.getLectures().stream()
                .map(l -> notify(item.getChatId(), l))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<NotifyHistory> notify(Long chatId, Lecture lecture) {
        long newScore = LectureParserType.parse(lecture.getUrl(), lecture.getLectureType());

        if (lecture.isNotUpdated(newScore)) {
            return Optional.empty();
        }

        long beforeScore = lecture.getCurrentScore();
        TelegramMessage message = TelegramMessage.byLecture()
                .chatId(chatId)
                .beforeScore(beforeScore)
                .currentScore(newScore)
                .type(lecture.getLectureType())
                .goods(lecture.getTitle())
                .build();

        TelegramResponse response = telegramNotifier.notify(message);

        return Optional.of(lecture.notify(newScore, message.getText(), response.getSendTime()));
    }
}
