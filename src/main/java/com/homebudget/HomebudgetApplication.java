package com.homebudget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.homebudget.model")
@SpringBootApplication
public class HomebudgetApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebudgetApplication.class, args);
	}

}
