package com.besstore.bmiService.bmiInterprete;

import com.besstore.bmiService.constatnts.Gender;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BmiInterpreterFactory {

    public InterpreterBMI getInterpreter(Gender gender, int age) {

        if (age < 20) {
            if (gender == Gender.MALE) {
                return new TeenGirlBmiInterpreterBMI();
            } else {
                return new TeenBoyBmiInterpreterBMI();
            }
        } else if (age < 60) {
            return new AdultBmiInterpreterBMI();
        } else {
            return new SeniorBmiInterpreterBMI();
        }
    }
}
