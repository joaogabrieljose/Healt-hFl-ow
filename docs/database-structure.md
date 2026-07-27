# Database Structure - HealthFlow

## 1. Visão Geral

O HealthFlow segue uma arquitetura de microserviços. Cada microserviço possui a sua própria base de dados PostgreSQL, garantindo separação de responsabilidades e independência entre os serviços.

Esta abordagem evita que um serviço aceda diretamente à base de dados de outro serviço. A comunicação entre serviços é feita através de HTTP, OpenFeign, API Gateway ou eventos assíncronos com RabbitMQ.

---

## 2. Bases de Dados por Serviço

| Serviço       | Base de Dados | Utilizador | Porta Local |
|---------------|---------------|------------|-------------|
| auth-service    | auth        | auth_user | 5438 |
| patient-service | patiente    | post_user | 5434 |
| doctor-service | doctor       | doctor_user | 5435 |
| scheduling-service | scheduling | scheduling_user | 5436 |
| triage-service | triage       | triage_user | 5437 |
| notification-service| notification | notification_user | 5439 |
| audit-service | audit         | audit_user | 5440 |

--------------------------------------------------------------
