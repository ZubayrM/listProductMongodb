package com.zubayr.listProduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ListProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListProductApplication.class, args);
	}

}
