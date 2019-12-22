package com.jojoldu.incomebot.admin.web.dto;

import lombok.Getter;

/**
 * Created by jojoldu@gmail.com on 22/12/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

@Getter
public class AdminResponseDto<T> {
    public static final AdminResponseDto<String> OK = new AdminResponseDto(AdminResponseStatus.OK);

    private AdminResponseStatus status;
    private String message;
    private T data;

    public AdminResponseDto(AdminResponseStatus status) {
        this.status = status;
    }

    public AdminResponseDto(AdminResponseStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public AdminResponseDto(AdminResponseStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
