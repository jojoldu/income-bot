package com.jojoldu.incomebot.admin.web;

import com.jojoldu.incomebot.admin.service.BookService;
import com.jojoldu.incomebot.admin.service.dto.BookScoreDto;
import com.jojoldu.incomebot.admin.web.dto.AdminResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.jojoldu.incomebot.admin.web.dto.AdminResponseStatus.OK;

/**
 * Created by jojoldu@gmail.com on 22/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiController {
    private final BookService bookService;

    @GetMapping("/api/v1/books/{id}")
    public AdminResponseDto<List<BookScoreDto>> getBooks(@PathVariable Long id) {
        return new AdminResponseDto<>(OK, bookService.getBookScores(id));
    }
}
