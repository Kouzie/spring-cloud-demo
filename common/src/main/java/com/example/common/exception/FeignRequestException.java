package com.example.common.exception;

import feign.Response;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Getter
@ToString
public class FeignRequestException extends RuntimeException {
    private String reason;

    public FeignRequestException(Response response) {
        try {
            InputStream inputStream = response.body().asInputStream();
            reason = new String(inputStream.readAllBytes());
        } catch (IOException e) {
            log.error("IOException invoked, type:{}, msg:{}", e.getClass().getSimpleName(), e.getMessage());
            reason = e.getMessage();
        }
    }
}
