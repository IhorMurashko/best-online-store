package com.besstore.bmiService.bmiInterprete;

import com.besstore.bmiService.constatnts.BMICategory;

public class TeenGirlBmiInterpreterBMI implements InterpreterBMI {
    @Override
    public BMICategory interpret(double bmi) {
        if (bmi < 17) return BMICategory.UNDERWEIGHT;
        if (bmi < 22.5) return BMICategory.NORMAL_WEIGHT;
        if (bmi < 24.5) return BMICategory.OVERWEIGHT;
        return BMICategory.OBESE;
    }
}
