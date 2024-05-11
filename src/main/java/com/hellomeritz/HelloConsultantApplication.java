package com.hellomeritz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HelloConsultantApplication {
    public static void main(String[] args) {
		SpringApplication.run(HelloConsultantApplication.class, args);
	}

}
