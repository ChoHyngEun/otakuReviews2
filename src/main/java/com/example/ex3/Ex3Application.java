package com.example.ex3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Ex3Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex3Application.class, args);
	}

}
