package com.example.demo.controller;

import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    private static Bucket rateLimiter = Bucket.builder()
            .addLimit(limit -> limit.capacity(5).refillGreedy(10, Duration.ofMinutes(1)))
            .build();

    @GetMapping("/hello")
    public Map<String, String> hello() {
        if (rateLimiter.tryConsume(1)) { // 尝试消费一个令牌
            Map<String, String> response = new HashMap<>();
            response.put("msg", "hello");
            System.out.println("Request accepted at: " + System.currentTimeMillis());
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("msg", "Too many requests");
            System.out.println("Request rejected at: " + System.currentTimeMillis());
            return response;
        }
    }
}

