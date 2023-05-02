package com.example.app.product.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class SchedulerComponent {
    @Value("${test.value:testValue}")
    private String testValue;

    @Value("${test.name:testName}")
    private String testName;

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        log.info("{} and {}", testValue, testName);
    }
}
