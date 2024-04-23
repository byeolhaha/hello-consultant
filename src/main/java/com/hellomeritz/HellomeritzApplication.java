package com.hellomeritz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HellomeritzApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellomeritzApplication.class, args);
	}

}
