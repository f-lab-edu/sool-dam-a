package com.flab.sooldama.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FailResponse {

    private String exception;
    private String message;

    public static FailResponse fail(String exception, String message) {
        return new FailResponse(exception, message);
    }
}
