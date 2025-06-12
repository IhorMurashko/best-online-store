package com.besstore.bmiService.dto;

import com.besstore.bmiService.constatnts.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record BmiRequest(
        @Min(value = 2, message = "Age must be greater than 1 year")
        @Max(value = 120, message = "Age must be less than 120 years")
        int age,
        Gender gender,
        @Min(value = 2, message = "Weight must be greater than 1 kg")
        @Max(value = 250, message = "Weight must be less than 250 kg")
        double weightKg,
        @Min(value = 2, message = "Height must be greater than 1 cm")
        @Max(value = 250, message = "Height must be less than 250 cm")
        double heightCm
) {
}
