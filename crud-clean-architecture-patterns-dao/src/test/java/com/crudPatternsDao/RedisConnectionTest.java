// package com.crudPatternsDao;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.testcontainers.containers.GenericContainer;
// import org.testcontainers.utility.DockerImageName;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

// @SpringBootTest(classes = TestConfig.class)
// public class RedisConnectionTest {

//     @Autowired
//     private StringRedisTemplate redisTemplate;

//     private static GenericContainer<?> redisContainer;

//     @BeforeAll
//     public static void startRedis() {
//         redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
//                 .withExposedPorts(6379);
//         redisContainer.start();
//     }

//     @Test
//     public void testRedisConnection() {
//         assertDoesNotThrow(() -> {
//             redisTemplate.getConnectionFactory().getConnection().ping();
//         }, "A conexão com o Redis falhou!");
//     }

//     @AfterAll
//     public static void stopRedis() {
//         if (redisContainer != null) {
//             redisContainer.stop();
//         }
//     }
// }
