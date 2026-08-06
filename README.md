# ⏰ API Agendador (Core NoSQL & Business Logic)

## 📌 Visão Geral
Microsserviço de alta performance responsável pelo gerenciamento do ciclo de vida das tarefas, persistência orientada a documentos (NoSQL), máquinas de estados operacionais e rotinas temporais autônomas (*schedulers*).

## 🛠️ Stack Tecnológico
* **Java 21** | **Spring Boot 3.4.x**
* **Spring Data MongoDB**
* **Springdoc OpenAPI (Swagger UI)**

## 🚀 Como Executar Localmente
1. Certifique-se de ter o MongoDB rodando na porta `27017`.
2. Execute a aplicação via terminal ou IDE:
   ```bash
   ./gradlew bootRun
   ```

## 🔌 Documentação (Swagger)
Com a aplicação rodando na porta `8081`, acesse a documentação interativa:
🔗 [Swagger UI - API Agendador](http://localhost:8081/swagger-ui/index.html)