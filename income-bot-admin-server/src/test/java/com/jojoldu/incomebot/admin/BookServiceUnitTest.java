package com.jojoldu.incomebot.admin;

import com.jojoldu.incomebot.admin.service.BookService;
import com.jojoldu.incomebot.admin.service.dto.BookScoreDto;
import com.jojoldu.incomebot.core.lecture.book.BookLecture;
import com.jojoldu.incomebot.core.lecture.book.BookLectureRepository;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStore;
import com.jojoldu.incomebot.core.lecture.book.store.BookLectureStoreType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by jojoldu@gmail.com on 23/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class BookServiceUnitTest {
    private BookLectureRepository repository;
    private BookService service;

    @Before
    public void setUp() throws Exception {
        repository = mock(BookLectureRepository.class);
        service = new BookService(repository);
    }

    @Test
    public void BookStore가_BookScoreDto로_변환되어_반환된다() {
        //given
        int expectedRank = 1;
        int expectedSalesPoint = 100;

        BookLecture lecture = new BookLecture("a", "1");
        lecture.addStore(store(expectedRank, expectedSalesPoint));

        given(repository.findById(anyLong())).willReturn(of(lecture));

        //when
        List<BookScoreDto> bookScores = service.getBookScores(1L);

        //then
        assertThat(bookScores.size()).isEqualTo(1);
        assertScoreDto(expectedRank, expectedSalesPoint, bookScores.get(0));
    }

    private void assertScoreDto(int expectedRank, int expectedSalesPoint, BookScoreDto scoreDto) {
        assertThat(scoreDto.getRank()).isEqualTo(expectedRank);
        assertThat(scoreDto.getSalesPoint()).isEqualTo(expectedSalesPoint);
    }

    private BookLectureStore store(int expectedRank, int expectedSalesPoint) {
        return BookLectureStore.builder()
                .beforeRank(0)
                .currentRank(expectedRank)
                .beforeScore(0)
                .currentScore(expectedSalesPoint)
                .storeType(BookLectureStoreType.KYOBO)
                .build();
    }
}
