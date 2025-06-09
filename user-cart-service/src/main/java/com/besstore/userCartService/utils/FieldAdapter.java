package com.besstore.userCartService.utils;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class that provides methods for adapting fields during mapping operations.
 * Contains named methods that can be referenced in MapStruct mappers.
 */
@UtilityClass
public class FieldAdapter {

    /**
     * Converts a string representation of a price to a BigDecimal.
     * The resulting BigDecimal has a scale of 2 and uses the DOWN rounding mode.
     * This method is used by MapStruct mappers for price conversions.
     *
     * @param priceString the string representation of the price
     * @return the price as a BigDecimal with scale 2
     */
    @Named("priceAdapter")
    public BigDecimal toPrice(String priceString) {
        BigDecimal price = new BigDecimal(priceString);
        return price.setScale(2, RoundingMode.DOWN);
    }
}
