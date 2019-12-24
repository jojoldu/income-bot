package com.jojoldu.incomebot.admin.service.dto;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStore;
import lombok.Getter;
import lombok.ToString;

import static com.jojoldu.incomebot.admin.service.dto.BounceRateDirection.is;

/**
 * Created by jojoldu@gmail.com on 22/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@ToString
@Getter
public class BookScoreDto {
    private String name;
    private int rank;
    private int bounceRank;
    private BounceRateDirection bounceRankDirection;
    private long salesPoint;
    private double bounceRate;
    private BounceRateDirection bounceRateDirection;

    public BookScoreDto(BookLectureStore store) {
        this.name = store.getStoreType().getTitle();
        this.rank = store.getCurrentRank();
        this.bounceRank = store.getBounceRank();
        this.bounceRankDirection = is(bounceRank);
        this.salesPoint = store.getCurrentScore();
        this.bounceRate = store.getBounceRate();
        this.bounceRateDirection = is(bounceRate);
    }
}
