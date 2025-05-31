# Project Insurance

Este projeto é uma aplicação de seguros dividida em dois microserviços: Client Service e Insurance Service. Cada microserviço é responsável por uma parte específica da aplicação e pode ser executado independentemente.

## Estrutura do Projeto

```
project-insurance/
├── client-service/     # Microserviço de Clientes
└── insurance-service/  # Microserviço de Seguros
```

## Microserviços

### Client Service
Responsável pelo gerenciamento de clientes

Para mais detalhes, consulte o [README do Client Service](client-service/README.md)

### Insurance Service
Responsável pelo gerenciamento de seguros

Para mais detalhes, consulte o [README do Insurance Service](insurance-service/README.md)

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.5
- MongoDB
- Maven
- JUnit
- Lombok
- Swagger


## Documentação da API

A documentação da API está disponível através do Swagger UI em cada microserviço:

- Client Service: http://localhost:8080/swagger-ui.html
- Insurance Service: http://localhost:8081/swagger-ui.html


## Testes End-to-End

O projeto inclui testes end-to-end que verificam a integração entre os microserviços. Para executar os testes:

1. Granta que o MongoDB esteja rodando
2. Execute o script de teste:
```bash
./test_end_to_end.sh
```

O script verificar o fluxo completo de:
  - Cadastro de cliente
  - Simulação de seguro
  - Contratação de seguro

##

Beatriz Odorcik
