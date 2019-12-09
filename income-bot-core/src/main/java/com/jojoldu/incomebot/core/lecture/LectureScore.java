package com.jojoldu.incomebot.core.lecture;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Created by jojoldu@gmail.com on 07/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
@Embeddable
public class LectureScore {
    private long beforeScore;
    private long currentScore;

    @Builder
    public LectureScore(long beforeScore, long currentScore) {
        this.beforeScore = beforeScore;
        this.currentScore = currentScore;
    }

    public void refresh(long currentScore) {
        if (isUpdatable(currentScore)) {
            this.beforeScore = this.currentScore;
            this.currentScore = currentScore;
        }
    }

    public boolean isNotUpdatable(long newScore) {
        return !isUpdatable(newScore);
    }

    public boolean isUpdatable(long newScore) {
        return this.currentScore != newScore;
    }
}
