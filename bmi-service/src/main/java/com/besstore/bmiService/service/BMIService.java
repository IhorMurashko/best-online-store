package com.besstore.bmiService.service;

import com.besstore.bmiService.dto.BmiRequest;
import com.besstore.bmiService.dto.BmiResponse;

public interface BMIService {

    BmiResponse calculateBmi(BmiRequest bmiRequest);


}
