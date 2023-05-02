package com.example.app.order.controller;


import com.example.app.order.service.BackendAService;
import com.example.app.order.service.BackendBService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/backend")
public class BackendController {
    private final BackendAService backendAService;
    private final BackendBService backendBService;

    @GetMapping("/a/failure")
    public String failureA() {
        return backendAService.failure();
    }

    @GetMapping("/a/success")
    public String successA() {
        return backendAService.success();
    }

    @GetMapping("/b/failure")
    public String failureB() {
        return backendBService.failure();
    }

    @GetMapping("/b/success")
    public String successB() {
        return backendBService.success();
    }
}
