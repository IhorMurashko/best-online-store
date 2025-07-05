package com.bestStore.core.utils;

import java.util.Date;

/**
 * Utility class for date-based operations related to token validity.
 */
public class DateConstructorUtil {

    private DateConstructorUtil() {
    }

    /**
     * Constructs a new {@link Date} object by adding the given number of seconds
     * to the specified base date.
     *
     * @param from                    base date
     * @param validityPeriodInSeconds number of seconds to add
     * @return resulting expiration date
     */
    public static Date dateExpirationGenerator(Date from, long validityPeriodInSeconds) {
        return new Date(from.getTime() + validityPeriodInSeconds * 1000);
    }
}
