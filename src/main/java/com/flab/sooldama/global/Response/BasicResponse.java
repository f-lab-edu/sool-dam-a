package com.flab.sooldama.global.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BasicResponse<T> {

    private boolean success;
    private T data;

    public static <T> BasicResponse<T> success(T data) {
        return new BasicResponse<>(true, data);
    }
}