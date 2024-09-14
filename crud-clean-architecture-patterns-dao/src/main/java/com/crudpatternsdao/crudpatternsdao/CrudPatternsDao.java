 package com.crudpatternsdao.crudpatternsdao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CrudPatternsDao {

	public static void main(String[] args) {
		SpringApplication.run(CrudPatternsDao.class, args);
		System.out.println("Iniciado");
	}
}
 