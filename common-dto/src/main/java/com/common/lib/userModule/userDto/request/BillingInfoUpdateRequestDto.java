package com.common.lib.userModule.userDto.request;

import static com.common.lib.userModule.constraint.FieldValidationPatterns.CARD_NUMBER_MESSAGE;
import static com.common.lib.userModule.constraint.FieldValidationPatterns.CARD_NUMBER_PATTERN;
import static com.common.lib.userModule.constraint.FieldValidationPatterns.EXPIRE_DATE_MESSAGE;
import static com.common.lib.userModule.constraint.FieldValidationPatterns.EXPIRE_DATE_PATTERN;
import static com.common.lib.userModule.constraint.FieldValidationPatterns.NAME_ON_CARD_MESSAGE;
import static com.common.lib.userModule.constraint.FieldValidationPatterns.NAME_ON_CARD_PATTERN;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record BillingInfoUpdateRequestDto(
        @Pattern(regexp = NAME_ON_CARD_PATTERN,
                message = NAME_ON_CARD_MESSAGE)
        String nameOnCard,
        @Pattern(regexp = CARD_NUMBER_PATTERN,
                message = CARD_NUMBER_MESSAGE)
        String cardNumber,
        @Pattern(regexp = EXPIRE_DATE_PATTERN,
                message = EXPIRE_DATE_MESSAGE)
        String expireDate
) {
}