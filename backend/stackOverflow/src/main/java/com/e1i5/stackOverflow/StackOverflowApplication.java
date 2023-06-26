package com.e1i5.stackOverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class StackOverflowApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StackOverflowApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StackOverflowApplication.class);
	}
}
