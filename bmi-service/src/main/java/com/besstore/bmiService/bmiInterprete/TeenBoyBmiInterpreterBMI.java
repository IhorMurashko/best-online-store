package com.besstore.bmiService.bmiInterprete;

import com.besstore.bmiService.constatnts.BMICategory;

public class TeenBoyBmiInterpreterBMI implements InterpreterBMI {
    @Override
    public BMICategory interpret(double bmi) {
        if (bmi < 17.5) return BMICategory.UNDERWEIGHT;
        if (bmi < 23.0) return BMICategory.NORMAL_WEIGHT;
        if (bmi < 25.0) return BMICategory.OVERWEIGHT;
        return BMICategory.OBESE;
    }
}
