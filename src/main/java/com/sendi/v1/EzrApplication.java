package com.sendi.v1;

import com.sendi.v1.domain.Deck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EzrApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzrApplication.class, args);
	}
}
