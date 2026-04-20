# 🛡️ Fraud Detection System (Microservices)

Sistema distribuído de detecção de fraudes em transações financeiras em tempo real, baseado em arquitetura de microserviços, com foco em escalabilidade, resiliência e boas práticas de mercado.

---

## 🧠 Visão Geral

Este projeto simula um ambiente de fintech, onde transações passam por múltiplos serviços especializados antes de serem aprovadas ou rejeitadas.

A arquitetura foi construída utilizando os padrões:

- Microservices  
- Service Discovery  
- Comunicação Síncrona (com evolução planejada para mensageria assíncrona)

---

## 🏗️ Arquitetura e Estrutura

O projeto segue o modelo **Mono‑Repo**, onde todos os serviços estão organizados dentro de um único repositório para facilitar o estudo e versionamento.

### 📂 Estrutura de Pastas

```bash
fraud-detection-system/
│
├── discovery-server/      # Service Discovery (Eureka)
├── account-service/       # Gestão de Contas e Score Base
├── transaction-service/   # Orquestrador de Transações
├── risk-analysis-service/ # Motor de Regras (Strategy Pattern)
├── gateway-server/ 🚧     # API Gateway (Em desenvolvimento)
└── docker-compose.yml     # Infraestrutura (PostgreSQL)
```

---

## 🔧 Componentes do Sistema

| Serviço                | Função                                             | Porta |
|------------------------|----------------------------------------------------|-------|
| discovery-server       | Service Discovery com Eureka                       | 8761  |
| account-service        | Gerencia contas e score de risco base            | 8081  |
| transaction-service    | Processa transações e toma decisões              | 8082  |
| risk-analysis-service  | Motor de regras de fraude (Strategy Pattern)     | 8083  |
| gateway-server 🚧      | API Gateway e roteamento centralizado            | 8080  |
| infrastructure         | Docker + PostgreSQL com bancos isolados          | -     |

---

## 🔄 Fluxo de Processamento

1. O cliente envia uma solicitação de transação.
2. O `transaction-service`:
   - Consulta o `account-service` para obter o score base da conta.
   - Consulta o `risk-analysis-service` para avaliar o score da transação atual.
3. O sistema calcula o **risco total acumulado**.
4. **Decisão Final**:
   - ✅ `APPROVED`
   - ❌ `REJECTED`

---

## 🛠️ Tecnologias Utilizadas

- Linguagem: **Java 21**
- Framework: **Spring Boot 4.x**
- Cloud: **Spring Cloud** (Eureka, OpenFeign)
- Persistência: **Spring Data JPA & PostgreSQL**
- Containers: **Docker / Docker Compose**
- Build: **Maven**
- Arquitetura: **Microserviços Distribuídos**

---

## 🧩 Padrões e Conceitos Aplicados

- Microservices Architecture  
- Service Discovery (Eureka)  
- API Communication: OpenFeign  
- Design Patterns: **Strategy Pattern** (Motor de Risco)  
- Java Moderno: **DTOs com record**  
- Error Handling: **Global Exception Handler**  
- Resiliência: Tratamento básico de falhas entre serviços  

---

## 📊 Status do Projeto

### ✅ Concluído

- [x] Estrutura de microserviços criada.  
- [x] Comunicação entre serviços via Feign.  
- [x] Service Discovery com Eureka.  
- [x] Banco de dados isolado por serviço.  
- [x] Strategy Pattern implementado.  
- [x] Regras de fraude funcionando.  
- [x] Tratamento de erros global.  

### 🚧 Em andamento

- [ ] API Gateway (Spring Cloud Gateway).  
- [ ] Padronização de rotas.  
- [ ] Melhorias de resiliência.  

---

## 🚀 Roadmap

### 🔹 Fase 4 (Atual)

- [ ] Finalizar API Gateway.  
- [ ] Centralizar acesso via `/api`.  
- [ ] Ajustar roteamento (`StripPrefix`).  

### 🔹 Próximos Passos

- [ ] Circuit Breaker (Resilience4j).  
- [ ] Documentação com Swagger/OpenAPI.  
- [ ] Mensageria com RabbitMQ ou Kafka.  
- [ ] Processamento assíncrono de transações.  

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos

- Java 21+  
- Maven  
- Docker + Docker Compose  

### 1. Subir bancos de dados

```bash
docker-compose up -d
```

### 2. Iniciar Serviços (Ordem recomendada)

```bash
# Terminal 1: Discovery Server
cd discovery-server && mvn spring-boot:run

# Terminal 2: Account Service
cd account-service && mvn spring-boot:run

# Terminal 3: Risk Analysis
cd risk-analysis-service && mvn spring-boot:run

# Terminal 4: Transaction Service
cd transaction-service && mvn spring-boot:run
```

---

## 🧪 Testar API

- Criar conta: `POST /accounts`  
- Criar transação: `POST /transactions`

```json
{
  "accountId": 1,
  "amount": 500.00,
  "type": "PIX"
}
```

---

## 🛡️ Regras de Fraude

Uma transação é rejeitada se apresentar:

- Valor acima do limite configurado.  
- Score de risco total acima do permitido.  

### Regras do Risk Analysis:

- Transações de alto valor.  
- Transações em horário suspeito (madrugada).  

---

## ⚠️ Observações Técnicas Importantes

- O projeto utiliza **Spring Boot 4.x**, que pode apresentar instabilidades com módulos específicos.  
- O **Gateway está em desenvolvimento**.  
- Comunicação atual é síncrona (evolução para assíncrona pendente).  

---

## 💡 Objetivo do Projeto

- Evolução de arquitetura monolítica para microserviços.  
- Simulação de sistemas financeiros reais.  
- Aplicação de boas práticas (Clean Code, SOLID).  

---

## 👩‍💻 Autora

Desenvolvido por **Fabiana Morais**  
Projeto em evolução contínua 🚀
