package com.app.qmaservice.config;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

/**
 * Prevents Redis cache failures from bubbling up as HTTP 500 errors.
 *
 * Without this, if Redis is temporarily unavailable (connection refused,
 * timeout, etc.) any @Cacheable / @CacheEvict method throws an exception
 * that Spring's DispatcherServlet converts to a 500 Internal Server Error.
 *
 * With this handler, cache errors are swallowed: the method simply executes
 * normally (cache-miss path) and the result is not stored.  Your business
 * logic keeps working even when Redis is down.
 */
@Configuration
public class CacheErrorHandlerConfig implements CachingConfigurer {

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {

            @Override
            public void handleCacheGetError(RuntimeException exception,
                                            Cache cache, Object key) {
                // Log and continue — treat as cache miss
                System.err.println("[Cache] GET error on cache='"
                        + cache.getName() + "' key=" + key
                        + " : " + exception.getMessage());
            }

            @Override
            public void handleCachePutError(RuntimeException exception,
                                            Cache cache, Object key, Object value) {
                System.err.println("[Cache] PUT error on cache='"
                        + cache.getName() + "' key=" + key
                        + " : " + exception.getMessage());
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception,
                                              Cache cache, Object key) {
                System.err.println("[Cache] EVICT error on cache='"
                        + cache.getName() + "' key=" + key
                        + " : " + exception.getMessage());
            }

            @Override
            public void handleCacheClearError(RuntimeException exception,
                                              Cache cache) {
                System.err.println("[Cache] CLEAR error on cache='"
                        + cache.getName() + "' : " + exception.getMessage());
            }
        };
    }
}
