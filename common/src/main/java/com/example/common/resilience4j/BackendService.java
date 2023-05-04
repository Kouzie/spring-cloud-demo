package com.example.common.resilience4j;

import java.time.LocalDateTime;

public class BackendService {
    public String doSomething() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            return "error invoked, msg:" + e.getMessage();
        }
        return "hello " + LocalDateTime.now();
    }
}
