package com.matthew.project.TestService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/hiuser")
    public ResponseEntity<String> aw(){
        return ResponseEntity.ok("hii user");
    }
    @GetMapping("/admin/hiuser")
    public ResponseEntity<String> aww(){
        return ResponseEntity.ok("hii admin");
    }
    @GetMapping("/all/hiall")
    public ResponseEntity<String> awww(){
        return ResponseEntity.ok("hii all");
    }
    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestHeader("X-User-Name") String userId,
                                       @RequestHeader("X-User-Role") String role) {
        return ResponseEntity.ok("User ID: " + userId + ", Role: " + role);
    }
}
