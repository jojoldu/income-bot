package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.batch.notifier.telegram.TelegramResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@RequiredArgsConstructor
public class NotifyJobHistory {

    private final TelegramResponse response;
    private final String chatId;

}
