package com.besstore.bmiService.constatnts;

import lombok.Getter;

@Getter
public enum BMICategory {
    UNDERWEIGHT("Underweight"),
    NORMAL_WEIGHT("Normal"),
    OVERWEIGHT("Overweight"),
    OBESE("Obese");


    private final String category;

    BMICategory(String category) {
        this.category = category;
    }


}
