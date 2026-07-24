package com.rafael.agendadortarefas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"jwt.secret=chave-falsa-para-testes-no-ci-nao-usar-em-producao",
		"spring.data.mongodb.uri=mongodb://localhost:27017/db_agendador"
})
class AgendadorTarefasApplicationTests {

	@Test
	void contextLoads() {
	}

}