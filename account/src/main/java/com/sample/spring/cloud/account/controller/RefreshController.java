package com.sample.spring.cloud.account.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class RefreshController {

    @Value("${sample.spring.property}")
    private String sampleSpringProperty;

    @Value("${sample.int.property}")
    private int sampleIntProperty;

    @GetMapping("/show")
    public String showProperty() {
        return sampleSpringProperty + ", " + sampleIntProperty;
    }

}
