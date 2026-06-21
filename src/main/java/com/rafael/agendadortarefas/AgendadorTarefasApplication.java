package com.rafael.agendadortarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableFeignClients
@SpringBootApplication
@EnableMongoAuditing
public class AgendadorTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendadorTarefasApplication.class, args);
	}

}
