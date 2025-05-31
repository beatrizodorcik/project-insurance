#!/bin/bash

set -e

function print_title() {
  echo -e "\n=============================="
  echo -e "$1"
  echo -e "=============================="
}

function make_request() {
  echo "Request: $1"
  echo "Response:"
  curl -s -X $1 $2 \
    -H "Content-Type: application/json" \
    -H "Authorization: Basic $(echo -n 'admin:admin123' | base64)" \
    -d "$3" | jq '.'
  echo -e "\n"
}

print_title "Listando planos disponíveis no Insurance Service"
make_request "GET" "http://localhost:8082/api/insurances/plans" ""

print_title "Criando primeiro usuário no Client Service"
make_request "POST" "http://localhost:8081/api/clients" '{"cpf":"12345678901","name":"João Silva","birthDate":"1993-01-01","address":"Rua das Flores, 123","phone":"+5511999999999"}'

print_title "Criando segundo usuário no Client Service"
make_request "POST" "http://localhost:8081/api/clients" '{"cpf":"98765432109","name":"Maria Santos","birthDate":"1985-05-15","address":"Avenida Principal, 789","phone":"+5511988888888"}'

print_title "Alterando primeiro usuário no Client Service"
make_request "PUT" "http://localhost:8081/api/clients/12345678901" '{"cpf":"12345678901","name":"João da Silva","birthDate":"1993-01-01","address":"Rua das Flores, 456","phone":"+5511999999999"}'

print_title "Simulando plano para primeiro usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/simulate" '{"cpf":"12345678901","age":31}'

print_title "Simulando plano para segundo usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/simulate" '{"cpf":"98765432109","age":39}'

print_title "Contratando plano para primeiro usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/contract?cpf=12345678901&type=Silver" ""

print_title "Contratando plano para segundo usuário no Insurance Service"
make_request "POST" "http://localhost:8082/api/insurances/contract?cpf=98765432109&type=Silver" ""

print_title "Deletando apenas o primeiro usuário no Client Service"
make_request "DELETE" "http://localhost:8081/api/clients/12345678901" ""
