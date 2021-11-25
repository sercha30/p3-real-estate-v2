package com.salesianostriana.dam.RealEstateV2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealEstateV2Application {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateV2Application.class, args);
	}

}
