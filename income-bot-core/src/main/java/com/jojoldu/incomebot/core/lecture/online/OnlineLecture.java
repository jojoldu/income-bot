package com.jojoldu.incomebot.core.lecture.online;

/**
 * Created by jojoldu@gmail.com on 12/10/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.online.store.OnlineLectureStore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

@Getter
@NoArgsConstructor
@Entity
@Table(
        indexes = {
                @Index(name = "idx_online_lecture_1", columnList = "instructor_id")
        }
)
public class OnlineLecture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "instructor_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private Instructor instructor;

    @OneToMany(mappedBy = "lecture", cascade = ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<OnlineLectureStore> stores = new ArrayList<>();

    @Builder
    public OnlineLecture(String title) {
        this.title = title;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void addStore(OnlineLectureStore store) {
        this.stores.add(store);
        store.setLecture(this);
    }

}
