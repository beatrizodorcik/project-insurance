#!/bin/bash

set -e

# Função para exibir título
function print_title() {
  echo -e "\n=============================="
  echo -e "$1"
  echo -e "=============================="
}

# Função para fazer requisição e mostrar resposta
function make_request() {
  echo "Request: $1"
  echo "Response:"
  curl -s -X $1 $2 \
    -H "Content-Type: application/json" \
    -H "Authorization: Basic $(echo -n 'admin:admin123' | base64)" \
    -d "$3" | jq '.'
  echo -e "\n"
}

# 0. Listar planos disponíveis
print_title "0. Listando planos disponíveis no Insurance Service"
make_request "GET" "http://localhost:8082/api/insurances/plans" ""

# 1. Criar primeiro usuário
print_title "1. Criando primeiro usuário no Client Service"
make_request "POST" "http://localhost:8081/api/clients" '{"cpf":"12345678901","name":"João Silva","birthDate":"1993-01-01","address":"Rua das Flores, 123","phone":"+5511999999999"}'

# 2. Criar segundo usuário
print_title "2. Criando segundo usuário no Client Service"
make_request "POST" "http://localhost:8081/api/clients" '{"cpf":"98765432109","name":"Maria Santos","birthDate":"1985-05-15","address":"Avenida Principal, 789","phone":"+5511988888888"}'

# 3. Alterar primeiro usuário
print_title "3. Alterando primeiro usuário no Client Service"
make_request "PUT" "http://localhost:8081/api/clients/12345678901" '{"cpf":"12345678901","name":"João da Silva","birthDate":"1993-01-01","address":"Rua das Flores, 456","phone":"+5511999999999"}'

# 4. Simular plano para primeiro usuário
print_title "4. Simulando plano para primeiro usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/simulate" '{"cpf":"12345678901","age":31}'

# 5. Simular plano para segundo usuário
print_title "5. Simulando plano para segundo usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/simulate" '{"cpf":"98765432109","age":39}'

# 6. Contratar plano para primeiro usuário
print_title "6. Contratando plano para primeiro usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/contract?cpf=12345678901&type=Silver" ""

# 7. Contratar plano para segundo usuário
print_title "7. Contratando plano para segundo usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/contract?cpf=98765432109&type=Silver" ""

# 8. Deletar apenas o primeiro usuário
print_title "8. Deletando apenas o primeiro usuário no Client Service"
make_request "DELETE" "http://localhost:8081/api/clients/12345678901" ""

print_title "Testes concluídos!" 