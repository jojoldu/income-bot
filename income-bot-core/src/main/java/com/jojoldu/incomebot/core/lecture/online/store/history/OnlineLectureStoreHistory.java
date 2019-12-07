package com.jojoldu.incomebot.core.lecture.online.store.history;

import com.jojoldu.incomebot.core.lecture.LectureHistory;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by jojoldu@gmail.com on 26/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
@Entity
@Table(
        indexes = {
                @Index(name = "idx_online_lecture_store_history_1", columnList = "store_id")
        }
)
public class OnlineLectureStoreHistory extends LectureHistory {

    private long coursePrice;
    private long increasedPrice;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private OnlineLectureStore store;

    @Builder
    public OnlineLectureStoreHistory(long beforeScore, long currentScore, LocalDateTime notifyDateTime, String message, long coursePrice) {
        super(beforeScore, currentScore, notifyDateTime, message);
        this.coursePrice = coursePrice;
        this.increasedPrice = (currentScore - beforeScore) * coursePrice;
    }

    public void setStore(OnlineLectureStore store) {
        this.store = store;
    }


}
