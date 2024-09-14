// package com.crudPatternsDao;

// import org.springframework.cache.CacheManager;
// import org.springframework.cache.annotation.EnableCaching;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.cache.RedisCacheManager;

// @Configuration
// @EnableCaching
// @ComponentScan(basePackages = "com.crudPatternsDao")
// public class TestConfig {

//     @Bean
//     public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//         RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
//             .fromConnectionFactory(redisConnectionFactory);
//         return builder.build();
//     }
// }
