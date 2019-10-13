package com.jojoldu.incomebot.core.instructor;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.lecture.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        name = "instructors",
        uniqueConstraints = @UniqueConstraint(name = "uni_instructor_chat_id", columnNames = {"chatId"}),
        indexes = {
                @Index(name = "idx_instructor_interval", columnList = "interval")
        }
)
public class Instructor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    @Enumerated(EnumType.STRING)
    private IntervalType interval;

    @OneToMany(mappedBy = "instructor", cascade = ALL)
    private List<Lecture> lectures = new ArrayList<>();

    @Builder
    Instructor(long chatId, IntervalType interval) {
        this.chatId = chatId;
        this.interval = interval;
    }

    public static Instructor signup(long chatId) {
        return Instructor.builder()
                .chatId(chatId)
                .interval(HOUR_1)
                .build();
    }

    public void addLecture (Lecture lecture) {
        this.lectures.add(lecture);
        lecture.setInstructor(this);
    }

    public void updateInterval(IntervalType interval) {
        this.interval = interval;
    }
}
