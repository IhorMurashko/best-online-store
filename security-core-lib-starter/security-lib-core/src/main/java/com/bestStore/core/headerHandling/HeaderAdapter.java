package com.bestStore.core.headerHandling;

import org.springframework.lang.NonNull;
/**
 * A generic contract for converting one type of header representation to another.
 *
 * <p>
 * Intended to be implemented by adapters that transform HTTP header-related data structures
 * between formats (e.g., raw headers to internal user context).
 * </p>
 *
 * @param <T> target type after conversion
 * @param <F> source type before conversion
 */
public interface HeaderAdapter<T, F> {

    T convert(@NonNull F f);

}
