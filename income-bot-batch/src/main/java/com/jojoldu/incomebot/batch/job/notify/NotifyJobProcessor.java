package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.batch.job.notify.parser.LectureParseExecutor;
import com.jojoldu.incomebot.batch.job.notify.parser.ParseResult;
import com.jojoldu.incomebot.batch.job.notify.parser.book.BookParseResult;
import com.jojoldu.incomebot.batch.job.notify.parser.online.OnlineParseResult;
import com.jojoldu.incomebot.batch.telegram.TelegramMessage;
import com.jojoldu.incomebot.batch.telegram.TelegramNotifier;
import com.jojoldu.incomebot.batch.telegram.TelegramResponse;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.Lecture;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@NoArgsConstructor
public class NotifyJobProcessor implements ItemProcessor<Instructor, List<Lecture>> {

    private TelegramNotifier telegramNotifier;
    private LectureParseExecutor lectureParserRestTemplate;

    @Autowired
    public void setTelegramNotifier(TelegramNotifier telegramNotifier) {
        this.telegramNotifier = telegramNotifier;
    }

    @Autowired
    public void setLectureParserRestTemplate(LectureParseExecutor lectureParserRestTemplate) {
        this.lectureParserRestTemplate = lectureParserRestTemplate;
    }

    @Override
    public List<Lecture> process(Instructor item) throws Exception {
        return item.getLectures().stream()
                .map(l -> notify(item.getChatId(), l))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Optional<Lecture> notify(Long chatId, Lecture lecture) {
        return lectureParserRestTemplate.parse(lecture.getUrl(), lecture.getLectureType())
                .filter(parseResult -> lecture.isUpdated(parseResult.getCurrentScore()))
                .map(parseResult -> notify(lecture, parseResult, chatId));
    }

    private Lecture notify(Lecture lecture, ParseResult parseResult, Long chatId) {
        long beforeScore = lecture.getCurrentScore();
        TelegramMessage message = new TelegramMessage(chatId, parseResult.getMessage(beforeScore, lecture.getTitle()));
        TelegramResponse response = telegramNotifier.notify(message);

        if (lecture.isOnline()) {
            return notifyOnline(lecture, parseResult, response);

        } else if (lecture.isBook()) {
            return notifyBook(lecture, parseResult, response);
        }

        return null;
    }

    private Lecture notifyOnline(Lecture lecture, ParseResult parseResult, TelegramResponse response) {
        OnlineParseResult onlineResult = (OnlineParseResult) parseResult;
        lecture.notifyOnline(onlineResult.getCurrentScore(), onlineResult.getCoursePrice(), response.getSendedMessage(), response.getSendTime());
        return lecture;
    }

    private Lecture notifyBook(Lecture lecture, ParseResult parseResult, TelegramResponse response) {
        BookParseResult bookResult = (BookParseResult) parseResult;
        lecture.notifyBook(bookResult.getCurrentScore(), response.getSendedMessage(), response.getSendTime());
        return lecture;
    }
}
