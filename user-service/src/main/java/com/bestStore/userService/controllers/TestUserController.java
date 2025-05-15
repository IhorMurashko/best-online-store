package com.bestStore.userService.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestUserController {

    @GetMapping("/hello-user")
    public String helloUser() {
        return "hello-user";
    }


}
