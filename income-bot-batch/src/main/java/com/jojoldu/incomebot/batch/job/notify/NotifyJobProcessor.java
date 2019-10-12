package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.batch.notifier.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.notifier.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.notifier.telegram.TelegramResponse;
import com.jojoldu.incomebot.core.instructor.Instructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
public class NotifyJobProcessor implements ItemProcessor<Instructor, List<NotifyJobHistory>> {

    private TelegramNotifier telegramNotifier;

    @Autowired
    public void setTelegramNotifier(TelegramNotifier telegramNotifier) {
        this.telegramNotifier = telegramNotifier;
    }

    @Override
    public List<NotifyJobHistory> process(Instructor item) throws Exception {
        Long chatId = item.getChatId();
        TelegramMessage message = TelegramMessage.builder()
                .chatId(chatId)
                .build();

        TelegramResponse response = telegramNotifier.notify(message);

        return null;
    }
}
