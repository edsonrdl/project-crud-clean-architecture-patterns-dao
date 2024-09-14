// package com.crudPatternsDao;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.crudpatternsdao.crudpatternsdao.CrudPatternsDao;
// import com.crudpatternsdao.crudpatternsdao.application.usecase.clienteusecase.IClientUseCase;

// @SpringBootTest(classes = CrudPatternsDao.class)
// public class ClientServiceTest {

//     @Autowired
//     private IClientUseCase clientService;

//     @Test
//     public void testResponseTime() {
//         int page = 0;
//         int size = 10;

//         long startTimeNoCache = System.nanoTime();
//         clientService.findAllWithoutCache(page, size); 
//         long endTimeNoCache = System.nanoTime();
//         long durationNoCache = endTimeNoCache - startTimeNoCache;
//         System.out.println("Response time without cache: " + durationNoCache + "ns (" + durationNoCache / 1_000_000.0 + "ms)");

//         long startTimeCache = System.nanoTime();
//         clientService.findAll(page, size); 
//         long endTimeCache = System.nanoTime();
//         long durationCache = endTimeCache - startTimeCache;
//         System.out.println("Response time with cache: " + durationCache + "ns (" + durationCache / 1_000_000.0 + "ms)");

//         long startTimeCacheAgain = System.nanoTime();
//         clientService.findAll(page, size); 
//         long endTimeCacheAgain = System.nanoTime();
//         long durationCacheAgain = endTimeCacheAgain - startTimeCacheAgain;
//         System.out.println("Response time with cache (2nd call): " + durationCacheAgain + "ns (" + durationCacheAgain / 1_000_000.0 + "ms)");
//     }
// }
