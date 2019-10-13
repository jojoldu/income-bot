package com.jojoldu.incomebot.core.lecture;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.notifyhistory.NotifyHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@NoArgsConstructor
@Entity
public class Lecture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;

    @Enumerated(EnumType.STRING)
    private LectureType lectureType;
    private long beforeScore;
    private long currentScore;

    @ManyToOne
    @JoinColumn(name = "instructor_id", foreignKey = @ForeignKey(name = "fk_lecture_instructor"))
    private Instructor instructor;

    @OneToMany(mappedBy = "lecture", cascade = ALL)
    private List<NotifyHistory> histories = new ArrayList<>();

    @Builder
    public Lecture(String title, String url, LectureType lectureType, long beforeScore, long currentScore, Instructor instructor) {
        this.title = title;
        this.url = url;
        this.lectureType = lectureType;
        this.beforeScore = beforeScore;
        this.currentScore = currentScore;
        this.instructor = instructor;
    }

    public static Lecture init (String title, String url, LectureType lectureType) {
        return Lecture.builder()
                .title(title)
                .url(url)
                .lectureType(lectureType)
                .beforeScore(0)
                .currentScore(0)
                .build();
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public NotifyHistory notify (long newScore, String message, LocalDateTime notifyDateTime) {
        this.updateScore(newScore);

        NotifyHistory history = NotifyHistory.builder()
                .beforeScore(currentScore)
                .currentScore(newScore)
                .message(message)
                .notifyDateTime(notifyDateTime)
                .build();

        this.addHistory(history);

        return history;
    }

    public void addHistory (NotifyHistory history) {
        this.histories.add(history);
        history.setLecture(this);
    }

    public boolean isNotUpdated(long newScore) {
        return !isUpdated(newScore);
    }

    public boolean isUpdated(long newScore) {
        return this.currentScore != newScore;
    }

    public void updateScore(long currentScore) {
        if(isUpdated(currentScore)) {
            this.beforeScore = this.currentScore;
            this.currentScore = currentScore;
        }
    }


}
