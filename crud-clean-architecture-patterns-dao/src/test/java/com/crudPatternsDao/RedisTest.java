// package com.crudPatternsDao;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Component;


// @Component
// public class RedisTest implements CommandLineRunner {

//     @Autowired
//     private StringRedisTemplate redisTemplate;

//     @Autowired
//     private RedisConnectionFactory redisConnectionFactory;

//     @Override
//     public void run(String... args) throws Exception {
//         try {
//             redisTemplate.getConnectionFactory().getConnection().ping();
//             System.out.println("Conexão com o Redis bem-sucedida!");
//         } catch (Exception e) {
//             System.err.println("Falha na conexão com o Redis: " + e.getMessage());
//         }
//     }
// }