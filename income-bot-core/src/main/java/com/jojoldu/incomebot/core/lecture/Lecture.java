package com.jojoldu.incomebot.core.lecture;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.instructor.Instructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class Lecture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private LectureType lectureType;
    private long beforeScore;
    private long currentScore;

    @ManyToOne
    @JoinColumn(name = "instructor_id", foreignKey = @ForeignKey(name = "fk_lecture_instructor"))
    private Instructor instructor;

    @Builder(builderClassName = "Signup", builderMethodName = "signup")
    public Lecture(String url, LectureType lectureType) {
        this.url = url;
        this.lectureType = lectureType;
        this.beforeScore = 0;
        this.currentScore = 0;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public boolean isUpdated(long currentScore) {
        return this.currentScore != currentScore;
    }

    public void updateScore(long currentScore) {
        if(isUpdated(currentScore)) {
            this.beforeScore = this.currentScore;
            this.currentScore = currentScore;
        }
    }


}
