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
public class LectureRank {
    private int beforeRank;
    private int currentRank;

    @Builder
    public LectureRank(int beforeRank, int currentRank) {
        this.beforeRank = beforeRank;
        this.currentRank = currentRank;
    }

    public void refresh(int currentScore) {
        if (isUpdatable(currentScore)) {
            this.beforeRank = this.currentRank;
            this.currentRank = currentScore;
        }
    }

    public boolean isNotUpdatable(int newRank) {
        return !isUpdatable(newRank);
    }

    public boolean isUpdatable(int newRank) {
        return this.currentRank != newRank;
    }

    public int getBounce() {
        return currentRank - beforeRank;
    }
}
