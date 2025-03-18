package com.ecommerce.orders.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.CacheManager;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager jCacheManager() throws URISyntaxException {
        // If you want to use a custom configuration file, uncomment the following lines:
        URI uri = getClass().getResource("/ehcache.xml").toURI();
        return Caching.getCachingProvider().getCacheManager(uri, getClass().getClassLoader());
        // Otherwise, use the default configuration:
        // CachingProvider provider = Caching.getCachingProvider();
        // return provider.getCacheManager();
    }

    @Bean
    public JCacheCacheManager cacheManager(CacheManager jCacheManager) {
        return new JCacheCacheManager(jCacheManager);
    }
}
