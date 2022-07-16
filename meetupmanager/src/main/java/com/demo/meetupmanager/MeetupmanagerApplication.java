package com.demo.meetupmanager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MeetupmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetupmanagerApplication.class, args);
	}

    @Bean
    ModelMapper createModelMapper() {
        return new ModelMapper();
    }
	
}
