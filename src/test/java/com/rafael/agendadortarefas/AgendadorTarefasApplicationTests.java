package com.rafael.agendadortarefas;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class AgendadorApplicationTests {

	@MockitoBean
	private MongoClient mongoClient;

	@Test
	void contextLoads() {
	}
}