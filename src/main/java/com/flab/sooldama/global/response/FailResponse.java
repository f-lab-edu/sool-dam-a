package com.flab.sooldama.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FailResponse<T> {

    private T message;
    public static <T> FailResponse<T> fail(T message) {
        return new FailResponse<>(message);
    }
}