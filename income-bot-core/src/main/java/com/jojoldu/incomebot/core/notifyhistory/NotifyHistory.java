package com.jojoldu.incomebot.core.notifyhistory;

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by jojoldu@gmail.com on 13/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
@Entity
public class NotifyHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long beforeScore;
    private long currentScore;
    private LocalDateTime notifyDateTime;
    private String message;

    @ManyToOne
    @JoinColumn(name = "lecture_id", foreignKey = @ForeignKey(name = "fk_notify_history_lecture"))
    private Lecture lecture;

    @Builder
    public NotifyHistory(long beforeScore, long currentScore, LocalDateTime notifyDateTime, String message) {
        this.beforeScore = beforeScore;
        this.currentScore = currentScore;
        this.notifyDateTime = notifyDateTime;
        this.message = message;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
