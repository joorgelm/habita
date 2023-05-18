package br.com.jorge.habita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HabitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitaApplication.class, args);
	}

}
