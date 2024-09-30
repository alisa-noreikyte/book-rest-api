package com.example.bookapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookapiApplication {

	private static final Logger log = LoggerFactory.getLogger(BookapiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookapiApplication.class, args);
	}

}
