# 🏪 API Loja — Sistema de Compras, Vendas e Controle de Estoque

![Java](https://img.shields.io/badge/Java-17+-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

## 📖 Descrição

A **API Loja** é uma aplicação desenvolvida em **Java (Spring Boot)** que simula um sistema de **compra, venda e gerenciamento de estoque** para uma loja física.  
O projeto foi estruturado com **arquitetura em camadas (Controller, Service e Repository)** e utiliza o **PostgreSQL** como banco de dados principal.  
Todas as requisições e testes de endpoints são realizadas via **Postman**, com comunicação em **JSON**.

---

## 🚀 Funcionalidades Principais

- 🧾 **Cadastro de produtos, clientes e vendas**
- 💰 **Registro e controle de transações de compra e venda**
- 📦 **Gerenciamento automático de estoque**
- 🔍 **Consulta e listagem de produtos e vendas**
- ⚙️ **Estrutura modular baseada em camadas**
- 🌐 **API RESTful com integração ao PostgreSQL**

---

## 🧩 Tecnologias Utilizadas

| Categoria | Tecnologia |
|------------|-------------|
| Linguagem | Java 17+ |
| Framework | Spring Boot |
| Persistência | Spring Data JPA |
| Banco de Dados | PostgreSQL |
| Testes de API | Postman |
| Gerenciador de Dependências | Maven |
| Testes Unitários | JUnit & Mockito |

---

## 🧪 Testes Unitários (Em progresso 🚧)
O projeto está sendo desenvolvido seguindo boas práticas de qualidade de software. Atualmente, a camada de Service está passando por um processo de cobertura de testes para garantir a integridade das regras de negócio.

O que está sendo testado:
Cenários de Sucesso: Validação de fluxos onde os dados estão corretos e as persistências (via Mockito) devem ocorrer com sucesso.

Tratamento de Exceções: Uso de assertThrows para garantir que o sistema interrompa operações inválidas (como custos negativos ou estoques abaixo de zero) com as mensagens de erro corretas.

Nota: A cobertura de testes está sendo implementada gradualmente em todas as classes de serviço do projeto. O objetivo é alcançar a cobertura total das principais regras de negócio em breve.

---

## 👨‍💻 Autor

Gustavo Boing
<a href="https://www.linkedin.com/in/gustavo-boing-72a103272/" target="_blank">Linkedin</a>

---

## 📝 Licença

Este projeto está licenciado sob a licença MIT — sinta-se à vontade para usar e modificar conforme necessário.github

---

## 💡 Objetivo

O objetivo principal deste projeto é demonstrar o desenvolvimento de uma API RESTful em Java, aplicando boas práticas de arquitetura, persistência de dados e modularização, simulando o fluxo real de uma loja física — desde o cadastro de produtos até a atualização de estoque após as vendas.
