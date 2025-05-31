# Insurance-Service

Microserviço responsável pela simulação e contratação de seguros na jornada de onboarding do cliente.

## Funcionalidades
- Simulação de planos de seguro (Bronze, Prata, Ouro)
- Contratação de seguro para cliente existente
- Persistência em MongoDB
- Integração com Client Service (validação de existência do cliente)
- Swagger

## Como rodar
1. Certifique-se que o MongoDB está rodando em `localhost:27017`.
2. Compile o projeto:
   ```bash
   mvn clean package -DskipTests
   ```
3. Execute o serviço:
   ```bash
   java -jar target/insurance-service-1.0.0.jar
   ```
4. O serviço estará disponível em `http://localhost:8082`.

## Swagger
Acesse a documentação interativa em:
- [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)


## Testes
- Testes unitários e de integração: `mvn test`

## Melhorias (negócio)
- Ajuste do código para que a escolha dos planos não seja apenas por idade, permitindo todas faixas etárias escolherem qualquer um dos planos disponíveis
- Personalização dos planos para atender outras demandas específicas 
- Adição dos dependentes 

## Tecnologias
- Java 17
- Spring Boot 3
- MongoDB
- Swagger
- JUnit

## 

Beatriz Odorcik - 30/05/2025

