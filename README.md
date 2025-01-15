# Pagamentos API - Microservices Architecture

Este repositório contém a implementação de uma **API de Pagamentos** utilizando a arquitetura de **microserviços** com Spring Boot, Spring Cloud, e Netflix Eureka. A aplicação é projetada para estudos de microserviços e contém as seguintes funcionalidades e práticas:

- **Service Discovery** com **Netflix Eureka**: A API se registra no Eureka Server e se comunica com outros microserviços de forma dinâmica.
- **API Gateway** para centralizar as requisições.
- **Balanceamento de Carga** utilizando Spring Cloud.
- **Comunicação síncrona** entre microserviços com **REST** usando **RestTemplate**.
- **Circuit Breaker** com **Hystrix** e **Fallback** para prevenção de falhas.
- **Banco de dados MySQL** para persistência de dados.

## Funcionalidades

- **Controle de Pagamentos**: Permite registrar, consultar e listar pagamentos.
- **Modelo de Dados**: Utiliza DTOs (Data Transfer Objects) para transferência de dados entre microsserviços.
- **Registro e Descoberta de Serviços**: A API de pagamentos se registra no Eureka Server para permitir descoberta de serviço.
- **API Gateway**: Centraliza todas as requisições em um ponto de entrada único.
- **Resiliência**: Implementação de Circuit Breaker para evitar falhas em caso de problemas com outros microserviços.
  
## Arquitetura

A arquitetura foi projetada com base nos seguintes componentes:

- **Eureka Server** (Service Discovery)
- **API Gateway** (Zuul ou Spring Cloud Gateway)
- **Microserviço de Pagamentos** (Controller, Service, Repository, Model, DTO)
- **Hystrix** (Circuit Breaker e Fallback)
- **MySQL Database** (Persistência)

## Tecnologias Utilizadas

- **Spring Boot** (para criação de microserviços)
- **Spring Cloud** (para integração de microserviços e ferramentas como Eureka e Hystrix)
- **Netflix Eureka** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **Spring Data JPA** (para acesso ao banco de dados MySQL)
- **Hystrix** (Circuit Breaker e Fallback)
- **MySQL** (banco de dados relacional)
- **Docker** (para contêineres de microserviços)
- **Maven** (gerenciador de dependências)
