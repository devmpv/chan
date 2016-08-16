package com.devmpv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ChanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChanApplication.class, args);
	}
}
