package com.jojoldu.incomebot.core.lecture.book;

import com.jojoldu.incomebot.core.BaseTimeEntity;
import com.jojoldu.incomebot.core.instructor.Instructor;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ConstraintMode;
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
                @Index(name = "idx_book_lecture_1", columnList = "instructor_id")
        }
)
public class BookLecture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "instructor_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Instructor instructor;

    @OneToMany(mappedBy = "lecture", cascade = ALL)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<BookLectureStore> stores = new ArrayList<>();

    @Builder
    public BookLecture(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void addStore(BookLectureStore store) {
        this.stores.add(store);
        store.setLecture(this);
    }
}
