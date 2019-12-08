package com.jojoldu.incomebot.core.lecture.online.store;

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.lecture.LectureScore;
import com.jojoldu.incomebot.core.lecture.online.OnlineLecture;
import com.jojoldu.incomebot.core.lecture.online.store.history.OnlineLectureStoreHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Created by jojoldu@gmail.com on 07/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Getter
@NoArgsConstructor
@Entity
@Table(
        indexes = {
                @Index(name = "idx_online_store_lecture_1", columnList = "lecture_id")
        }
)
public class OnlineLectureStore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OnlineLectureStoreType storeType;
    private String url;

    @Embedded
    private LectureScore score;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "lecture_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineLecture lecture;

    @OneToMany(mappedBy = "store", cascade = ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<OnlineLectureStoreHistory> histories = new ArrayList<>();

    @Builder
    public OnlineLectureStore(OnlineLectureStoreType storeType, String url, long beforeScore, long currentScore) {
        this.storeType = storeType;
        this.url = url;
        this.score = LectureScore.builder().beforeScore(beforeScore).currentScore(currentScore).build();
    }

    public static OnlineLectureStore init(OnlineLectureStoreType storeType, String url) {
        return OnlineLectureStore.builder()
                .storeType(storeType)
                .url(url)
                .beforeScore(0)
                .currentScore(0)
                .build();
    }

    public void setLecture(OnlineLecture lecture) {
        this.lecture = lecture;
    }

    public void refreshScore(long newScore, String message, LocalDateTime notifyDateTime) {
        this.addHistory(newScore, message, notifyDateTime);
        score.refreshScore(newScore); // 순서 중요 (먼저 실행되면 현재 스코어가 변경됨)
    }

    public void addHistory(long newScore, String message, LocalDateTime notifyDateTime) {
        this.addHistory(OnlineLectureStoreHistory.builder()
                .beforeScore(score.getCurrentScore())
                .currentScore(newScore)
                .message(message)
                .notifyDateTime(notifyDateTime)
                .build());
    }

    public void addHistory(OnlineLectureStoreHistory history) {
        this.histories.add(history);
        history.setStore(this);
    }

    public long getCurrentScore() {
        return score.getCurrentScore();
    }

    public boolean isUpdatable(long currentScore) {
        return score.isUpdatable(currentScore);
    }

    public Long getChatId() {
        return lecture.getInstructor().getChatId();
    }

    public String getTitle() {
        return lecture.getTitle();
    }
}
