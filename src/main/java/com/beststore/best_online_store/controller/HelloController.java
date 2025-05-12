package com.beststore.best_online_store.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @Operation(summary = "Returns a hello message",
            description = "This is a simple GET endpoint for returning a hello message")
    public String hello() {
        return "Hello, Best Online Store!";
    }
}
