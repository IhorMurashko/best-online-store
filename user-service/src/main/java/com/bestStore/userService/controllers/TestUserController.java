package com.bestStore.userService.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class TestUserController {


    @Tag(name = "test user controller")
    @GetMapping("/hello")
    public String helloUser() {
        return "hello-user";
    }


}
