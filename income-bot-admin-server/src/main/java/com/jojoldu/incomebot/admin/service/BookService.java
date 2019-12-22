package com.jojoldu.incomebot.admin.service;

import com.jojoldu.incomebot.admin.service.dto.BookScoreDto;
import com.jojoldu.incomebot.core.lecture.book.BookLectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Created by jojoldu@gmail.com on 22/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookLectureRepository bookLectureRepository;

    @Transactional(readOnly = true)
    public List<BookScoreDto> getBookScores(Long bookLectureId) {
        return bookLectureRepository.findById(bookLectureId)
                .map(bookLecture -> bookLecture.getStores().stream().map(BookScoreDto::new).collect(toList()))
                .orElseGet(ArrayList::new);

    }

}
