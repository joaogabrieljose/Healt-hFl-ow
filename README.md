# HealthFlow

## 1. Descrição do Projeto

O **HealthFlow** é uma plataforma baseada em microserviços para gestão de processos clínicos, incluindo autenticação, gestão de pacientes, médicos, consultas, triagem, notificações e auditoria.

O sistema foi desenvolvido com uma arquitetura distribuída, onde cada microserviço possui uma responsabilidade específica e comunica com os restantes serviços através de HTTP, API Gateway e eventos assíncronos com RabbitMQ.

---

## 2. Objetivo

O objetivo principal do HealthFlow é simular uma plataforma hospitalar moderna, organizada por serviços independentes, permitindo:

- Registo e gestão de pacientes;
- Registo e gestão de médicos e especialidades;
- Marcação e atualização de consultas;
- Realização de triagens;
- Geração de notificações automáticas;
- Registo de auditoria dos eventos importantes do sistema;
- Comunicação assíncrona entre serviços com RabbitMQ;
- Acesso centralizado através de API Gateway.

---

## 3. Arquitetura Geral

O sistema segue uma arquitetura de microserviços.

Cada serviço possui:

- Responsabilidade própria;
- Base de dados própria;
- Porta própria;
- Configuração independente;
- Possibilidade de execução em container Docker.

Fluxo simplificado:

```text
Cliente / Postman / Frontend
        ↓
API Gateway
        ↓
Microserviços REST
        ↓
Bases de dados PostgreSQL

scheduling-service
        ↓
RabbitMQ
        ↓
notification-service
        ↓
notification-postgres

scheduling-service
        ↓
RabbitMQ
        ↓
audit-service
        ↓
audit-postgres
