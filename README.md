# 🧾 Sistema de Controle de Restaurantes – Spring Boot

Este projeto consolida o código-fonte do desafio Tech Challenge da Fase 2 do curso de Arquitetura e 
Desenvolvimento Java. O desafio consiste no desenvolvimento de um sistema de gestão compartilhado para 
um grupo de restaurantes, visando otimizar os custos operacionais e unificar a experiência do cliente. 
A entrega será dividida em fases para garantir um desenvolvimento cuidadoso, controlado e que permita 
melhorias contínuas ao longo do processo.
---
## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- MariaDB (configurável)
- Lombok (opcional)
- Maven
- Clean Architecture

---

## ✅ Boas Práticas Aplicadas

- ✅ Princípios **SOLID** (`SRP`, `OCP`, `LSP`, `ISP`, `DIP`)
- ✅ Uso de **DTOs** para entrada e saída de dados
- ✅ Definição de **Enum** para tipos de usuário
- ✅ Herança utilizando `@Inheritance(strategy = JOINED)`
- ✅ Validações condicionais com base no tipo do usuário
- ✅ Tratamento de erro `409 CONFLICT` para campos únicos
- ✅ Separação clara de responsabilidades entre `service`, `controller` e `model`

## ✅ Clean Architectre

A aplicação foi organizada inicialmente em três pacotes principais: application, domain e infra.

O pacote **domain** reúne as camadas de regras de negócio da empresa (Entidades) e regras de negócio da aplicação (Casos de Uso) previstas na Clean Architecture.

O pacote **application** representa a camada de adaptadores de interface, contemplando os controladores usados na aplicação, representados no pacote controller, e as exceções disparadas nesta camada, agrupadas no pacote exception.

Por fim, o pacote **infra** representa a camada de framework e drivers, prevista na arquitetura limpa.

A utilização da Clean Architecture neste projeto é um requisito fundamental para atender às exigências de qualidade de código, escalabilidade e separação de responsabilidades. Ao organizar o código em camadas distintas como Domínio, Aplicação e Infraestrutura, garante-se que o sistema seja mais fácil de manter e evoluir, o que é crucial, visto que o projeto será entregue em fases. Essa abordagem permite que as funcionalidades de cadastro de usuários, restaurantes e cardápios sejam desenvolvidas de forma desacoplada da tecnologia de banco de dados escolhida e de outros detalhes de infraestrutura, facilitando a criação de testes automatizados e garantindo a alta qualidade e organização exigidas nos fatores de avaliação.

## ✅ Collection do Postman
As collections permitem organizar essas requisições em subpastas, que podem representar diferentes funcionalidades ou fluxos de trabalho de uma aplicação.

A collection foi compartilhada na plataforma do Postman na nuvem, podendo ser acessada por meio do 
endereço a seguir:
- http://tiny.cc/postman-fiap

