# Leaderboard Project

Este projeto consiste em uma aplicação full-stack com backend em Java (Spring Boot) e frontend em Angular.

## Estrutura do Projeto

### Backend (Java/Spring Boot)
```
src/main/java/com/leaderboard/
├── controller/        # Camada Resource (API REST)
├── service/          # Camada de regra de negócio
├── repository/       # Camada de acesso ao banco de dados
├── model/            # Entidades JPA (domínio)
├── dto/              # Objetos de transferência
└── Application.java  # Classe principal da aplicação
```

### Frontend (Angular)
```
src/app/
├── services/         # Faz chamadas HTTP para o backend
├── components/       # Componentes da aplicação
├── models/          # Representa o modelo Leaderboard no front
└── app.component.ts # Componente principal
```

## Como Executar

### Backend
1. Certifique-se de ter Java 17+ instalado
2. Execute: `mvn spring-boot:run`
3. A aplicação estará disponível em: `http://localhost:8080`

### Frontend
1. Certifique-se de ter Node.js instalado
2. Instale as dependências: `npm install`
3. Execute: `npm start`
4. A aplicação estará disponível em: `http://localhost:4200`

## Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Maven

### Frontend
- Angular 17
- TypeScript
- RxJS
- Angular CLI 