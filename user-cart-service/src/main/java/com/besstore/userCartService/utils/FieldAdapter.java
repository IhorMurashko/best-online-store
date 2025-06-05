package com.besstore.userCartService.utils;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class FieldAdapter {

    @Named("priceAdapter")
    public BigDecimal toPrice(String priceString) {
        BigDecimal price = new BigDecimal(priceString);
        return price.setScale(2, RoundingMode.DOWN);
    }
}
