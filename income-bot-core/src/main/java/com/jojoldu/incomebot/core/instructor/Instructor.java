package com.jojoldu.incomebot.core.instructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.lecture.book.BookLecture;
import com.jojoldu.incomebot.core.lecture.online.OnlineLecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

import static com.jojoldu.incomebot.core.instructor.IntervalType.HOUR_1;
import static javax.persistence.CascadeType.ALL;

@Getter
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(name = "uni_instructor_chat_id", columnNames = {"chatId"}),
        indexes = {
                @Index(name = "idx_instructor_interval", columnList = "intervalType")
        }
)
public class Instructor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    @Enumerated(EnumType.STRING)
    private IntervalType intervalType;

    @OneToMany(mappedBy = "instructor", cascade = ALL)
    @ForeignKey(name = "none")
    private List<BookLecture> bookLectures = new ArrayList<>();

    @OneToMany(mappedBy = "instructor", cascade = ALL)
    @ForeignKey(name = "none")
    private List<OnlineLecture> onlineLectures = new ArrayList<>();

    @Builder
    public Instructor(long chatId, IntervalType intervalType) {
        this.chatId = chatId;
        this.intervalType = intervalType;
    }

    public static Instructor signup(long chatId) {
        return Instructor.builder()
                .chatId(chatId)
                .intervalType(HOUR_1)
                .build();
    }

    public void addLecture(BookLecture lecture) {
        this.bookLectures.add(lecture);
        lecture.setInstructor(this);
    }

    public void addLecture(OnlineLecture lecture) {
        this.onlineLectures.add(lecture);
        lecture.setInstructor(this);
    }

    public void updateInterval(IntervalType interval) {
        this.intervalType = interval;
    }
}
