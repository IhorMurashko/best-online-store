package com.besstore.userCartService.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for setting up the caching mechanism using
 * Caffeine for efficient data caching with custom expiration and size policies.
 *
 * @author Ihor Murashko
 */
@Configuration
public class CacheConfig {

    /**
     * Provides a Caffeine cache builder configured with a 30-minute expiration
     * policy and a maximum size of 1000 entries.
     *
     * @return a configured {@link Caffeine} instance.
     */
    @Bean
    public Caffeine caffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, java.util.concurrent.TimeUnit.MINUTES)
                .maximumSize(1000);
    }


    /**
     * Configures and provides a {@link CacheManager} implementation using Caffeine.
     *
     * @param caffeine the Caffeine configuration used to customize caching behavior.
     * @return a {@link CacheManager} configured with the specified Caffeine settings.
     */
    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
