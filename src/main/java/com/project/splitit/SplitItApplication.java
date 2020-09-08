package com.project.splitit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan(basePackages = {"com/project/splitit/entity/common"})  // scan JPA entities
public class SplitItApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitItApplication.class, args);
	}

}
