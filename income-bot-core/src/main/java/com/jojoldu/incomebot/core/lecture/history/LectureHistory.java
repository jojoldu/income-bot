package com.jojoldu.incomebot.core.lecture.history;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import com.jojoldu.incomebot.core.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class LectureHistory extends BaseTimeEntity {

    protected long beforeScore;
    protected long currentScore;
    protected LocalDateTime notifyDateTime;
    protected String message;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LectureHistory(long beforeScore, long currentScore, LocalDateTime notifyDateTime, String message) {
        this.beforeScore = beforeScore;
        this.currentScore = currentScore;
        this.notifyDateTime = notifyDateTime;
        this.message = message;
    }
}