package com.example.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    private static final String API_KEY = "api-key";

    private String apiKey = "my-api-key";

    // 모든 feign client 요청은 api-key 를 추가하여 전달
    @Override
    public void apply(RequestTemplate template) {
        template.header(API_KEY, apiKey);
    }
}