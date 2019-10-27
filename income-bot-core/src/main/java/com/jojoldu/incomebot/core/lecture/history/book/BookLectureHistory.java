package com.jojoldu.incomebot.core.lecture.history.book;

import com.jojoldu.incomebot.core.lecture.Lecture;
import com.jojoldu.incomebot.core.lecture.history.LectureHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
@Entity
public class BookLectureHistory extends LectureHistory {

    @ManyToOne
    @JoinColumn(name = "lecture_id", foreignKey = @ForeignKey(name = "fk_book_lecture_history_lecture"))
    private Lecture lecture;

    @Builder
    public BookLectureHistory(long beforeScore, long currentScore, LocalDateTime notifyDateTime, String message) {
        super(beforeScore, currentScore, notifyDateTime, message);
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
