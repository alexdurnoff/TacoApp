package ru.durnov.taco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TacoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoAppApplication.class, args);
	}

}
