package com.blog.boot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Cipher Blog",
				description = "Spring Boot app with Rest APIs documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Babu Gyara",
						email = "gyarababu9@gmail.com"
				)
		)
)
public class BlogBootApplication {

	// add ModelMapper bean
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogBootApplication.class, args);
	}

}
