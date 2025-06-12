package com.besstore.bmiService.service;

import com.besstore.bmiService.bmiInterprete.BmiInterpreterFactory;
import com.besstore.bmiService.bmiInterprete.InterpreterBMI;
import com.besstore.bmiService.constatnts.BMICategory;
import com.besstore.bmiService.dto.BmiRequest;
import com.besstore.bmiService.dto.BmiResponse;
import com.besstore.bmiService.utils.BmiCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BMIServiceImpl implements BMIService {


    private final OpenAiChatModel chatModel;

    @Override
    public BmiResponse calculateBmi(BmiRequest bmiRequest) {

        double bmi = BmiCalculator.calculate(bmiRequest.weightKg(), bmiRequest.heightCm());


        InterpreterBMI interpreter = BmiInterpreterFactory
                .getInterpreter(bmiRequest.gender(), bmiRequest.age());


        BMICategory bmiCategory = interpreter.interpret(bmi);
        log.info("BMI category: {}", bmiCategory);
        List<String> recommendations = getRecommendations(bmiCategory);

        return new BmiResponse(bmi, bmiCategory, null, recommendations);
    }


    private List<String> getRecommendations(BMICategory category) {
        String prompt = switch (category) {
            case UNDERWEIGHT -> "Suggest 5 high-calorie, healthy foods for a person who is underweight.";
            case NORMAL_WEIGHT -> "Suggest 5 balanced, healthy foods for someone with normal BMI.";
            case OVERWEIGHT -> "Suggest 5 light and healthy foods for someone who is overweight.";
            case OBESE -> "Suggest 5 vegetables or low-calorie foods for someone who is obese.";
        };

        String result = chatModel.call(prompt);

        return parseList(result);
    }

    private List<String> parseList(String response) {
        return Arrays.stream(response.split("\n"))
                .map(line -> line.replaceAll("^\\d+[.)]\\s*", "").trim())
                .filter(line -> !line.isBlank())
                .toList();
    }
}