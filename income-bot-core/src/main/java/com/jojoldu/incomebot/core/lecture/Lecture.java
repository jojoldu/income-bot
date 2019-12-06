package com.jojoldu.incomebot.core.lecture;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.history.book.BookLectureHistory;
import com.jojoldu.incomebot.core.lecture.history.online.OnlineLectureHistory;
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
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "instructor_id", foreignKey = @ForeignKey(name = "fk_lecture_instructor"))
    private Instructor instructor;

    @OneToMany(mappedBy = "lecture", cascade = ALL)
    private List<OnlineLectureHistory> onlineHistories = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", cascade = ALL)
    private List<BookLectureHistory> bookHistories = new ArrayList<>();

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

    public void notifyOnline(long newScore, long coursePrice, String message, LocalDateTime notifyDateTime) {
        OnlineLectureHistory history = OnlineLectureHistory.builder()
                .beforeScore(currentScore)
                .currentScore(newScore)
                .coursePrice(coursePrice)
                .message(message)
                .notifyDateTime(notifyDateTime)
                .build();

        this.addOnlineHistory(history);
        this.refreshScore(newScore); // 순서 중요 (먼저 실행되면 현재 스코어가 변경됨)
    }

    public void addOnlineHistory(OnlineLectureHistory history) {
        if (isOnline()) {
            this.onlineHistories.add(history);
            history.setLecture(this);
        }
    }

    public void notifyBook(long newScore, String message, LocalDateTime notifyDateTime) {
        BookLectureHistory history = BookLectureHistory.builder()
                .beforeScore(currentScore)
                .currentScore(newScore)
                .message(message)
                .notifyDateTime(notifyDateTime)
                .build();

        this.addBookHistory(history);
        this.refreshScore(newScore); // 순서 중요 (먼저 실행되면 현재 스코어가 변경됨)
    }

    public void addBookHistory(BookLectureHistory history) {
        if (isBook()) {
            this.bookHistories.add(history);
            history.setLecture(this);
        }
    }

    public boolean isOnline() {
        return this.lectureType.isOnline();
    }

    public boolean isBook() {
        return this.lectureType.isBook();
    }

    public boolean isNotUpdated(long newScore) {
        return !isUpdated(newScore);
    }

    public void refreshScore(long currentScore) {
        if (isUpdated(currentScore)) {
            this.beforeScore = this.currentScore;
            this.currentScore = currentScore;
        }
    }

    public boolean isUpdated(long newScore) {
        return this.currentScore != newScore;
    }


}
