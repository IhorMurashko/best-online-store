package com.besstore.bmiService.bmiInterprete;

import com.besstore.bmiService.constatnts.BMICategory;

public class SeniorBmiInterpreterBMI implements InterpreterBMI {
    @Override
    public BMICategory interpret(double bmi) {
        if (bmi < 22.0) return BMICategory.UNDERWEIGHT;
        if (bmi < 27.0) return BMICategory.NORMAL_WEIGHT;
        if (bmi < 30.0) return BMICategory.OVERWEIGHT;
        return BMICategory.OBESE;
    }
}
