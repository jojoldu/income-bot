package com.jojoldu.incomebot.batch.job.notify;

import com.jojoldu.incomebot.batch.job.notify.parser.LectureParserRestTemplate;
import com.jojoldu.incomebot.batch.job.notify.parser.result.ParseResult;
import com.jojoldu.incomebot.batch.job.notify.parser.result.online.InflearnParseResult;
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
    private LectureParserRestTemplate lectureParserRestTemplate;

    @Autowired
    public void setTelegramNotifier(TelegramNotifier telegramNotifier) {
        this.telegramNotifier = telegramNotifier;
    }

    @Autowired
    public void setLectureParserRestTemplate(LectureParserRestTemplate lectureParserRestTemplate) {
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

    private Optional<Lecture> notify(Long chatId, Lecture lecture) {
        Optional<ParseResult> parse = lectureParserRestTemplate.parse(lecture.getUrl(), lecture.getLectureType());
        if (!parse.isPresent()) {
            return Optional.empty();
        }

        ParseResult parseResult = parse.get();

        if (lecture.isNotUpdated(parseResult.getCurrentScore())) {
            return Optional.empty();
        }

        long beforeScore = lecture.getCurrentScore();
        TelegramMessage message = new TelegramMessage(chatId, parseResult.getMessage(beforeScore, lecture.getTitle()));
        TelegramResponse response = telegramNotifier.notify(message);

        if (lecture.isOnline()) {
            InflearnParseResult inflearn = (InflearnParseResult) parseResult;
            lecture.notifyOnline(inflearn.getCurrentScore(), inflearn.getCoursePrice(), message.getText(), response.getSendTime());
        }
        return Optional.of(lecture);
    }
}
