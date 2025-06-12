package com.besstore.bmiService.dto;

import com.besstore.bmiService.constatnts.BMICategory;

import java.util.List;

public record BmiResponse(
        double bmi,
        BMICategory category,
        String healthRisk,
        List<String> recomendationsList
) {
}
