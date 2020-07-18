package com.jojoldu.incomebot.core.lecture.book;

import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStore;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreRepository;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType.KYOBO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 07/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookLectureTest {

    @Autowired
    private BookLectureRepository bookLectureRepository;

    @Autowired
    private BookLectureStoreRepository bookLectureStoreRepository;

    @AfterEach
    public void tearDown() throws Exception {
        bookLectureRepository.deleteAll();
    }

    @Test
    public void 연관관계_저장() {
        //given
        String expectedTitle = "a";
        String expectedIsbn = "1";
        BookLectureStoreType expectedType = KYOBO;

        BookLecture lecture = BookLecture.builder()
                .title(expectedTitle)
                .isbn(expectedIsbn)
                .build();
        lecture.addStore(BookLectureStore.init(expectedType, ""));

        //when
        bookLectureRepository.save(lecture);

        //then
        BookLecture result = bookLectureRepository.findAll().get(0);
        assertThat(result.getTitle()).isEqualTo(expectedTitle);
        assertThat(result.getIsbn()).isEqualTo(expectedIsbn);

        BookLectureStore bookLectureStore = bookLectureStoreRepository.findAll().get(0);
        assertThat(bookLectureStore.getStoreType()).isEqualTo(expectedType);
    }
}
