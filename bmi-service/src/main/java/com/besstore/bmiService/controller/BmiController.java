package com.besstore.bmiService.controller;

import com.besstore.bmiService.dto.BmiRequest;
import com.besstore.bmiService.dto.BmiResponse;
import com.besstore.bmiService.service.BMIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bmi")
@RequiredArgsConstructor
@Validated
public class BmiController {

    private final BMIService bmiService;

    @PostMapping("/calculate")
    public ResponseEntity<BmiResponse> calculate(@RequestBody @Valid BmiRequest bmiRequest) {
        BmiResponse bmiResponse = bmiService.calculateBmi(bmiRequest);
        return new ResponseEntity<>(bmiResponse, HttpStatus.OK);
    }


}