# Client-Service

Microserviço responsável pelo cadastro de clientes na jornada de contratação de seguros.

## Funcionalidades
- CRUD completo de clientes (CPF, nome, data de nascimento, telefone, endereço)
- Validação de dados
- Persistência em MongoDB
- Swagger

## Como rodar
1. Certifique-se que o MongoDB está rodando em `localhost:27017`.
2. Compile o projeto:
   ```bash
   mvn clean package -DskipTests
   ```
3. Execute o serviço:
   ```bash
   java -jar target/client-service-1.0.0.jar
   ```
4. O serviço estará disponível em `http://localhost:8081`.

## Swagger
Acesse a documentação interativa em:
- [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

## Testes
- Testes unitários: `mvn test`

## Melhorias (negócio)
- Cadastro de dependentes
- Outras informações de contato

## Tecnologias
- Java 17
- Spring Boot 3
- MongoDB
- Swagger
- JUnit

## 

Beatriz Odorcik - 30/05/2025
