# Fluxo Assíncrono com RabbitMQ - HealthFlow

## 1. Visão Geral

O HealthFlow utiliza uma arquitetura baseada em microserviços, onde cada serviço possui uma responsabilidade específica. Para evitar acoplamento direto entre os serviços e permitir comunicação assíncrona, foi integrado o RabbitMQ como broker de mensagens.

Neste fluxo, o `scheduling-service` atua como produtor de eventos relacionados com consultas médicas. Os serviços `notification-service` e `audit-service` atuam como consumidores desses eventos.

Quando uma consulta é criada ou quando o seu estado é alterado, o `scheduling-service` publica uma mensagem no RabbitMQ. Essa mensagem é depois encaminhada para as filas correspondentes, permitindo que outros serviços reajam ao evento de forma independente.

---

## 2. Objetivo da Comunicação Assíncrona

A comunicação assíncrona foi utilizada para:

- Reduzir o acoplamento entre microserviços;
- Evitar chamadas síncronas desnecessárias entre serviços;
- Permitir que vários serviços reajam ao mesmo evento;
- Melhorar a escalabilidade do sistema;
- Garantir separação de responsabilidades;
- Permitir evolução independente dos serviços.

Por exemplo, quando uma consulta é criada, o `scheduling-service` não precisa chamar diretamente o `notification-service` nem o `audit-service`. Ele apenas publica o evento no RabbitMQ.

---

## 3. Serviços Envolvidos

### 3.1. scheduling-service

Responsável pela gestão de consultas médicas.

Principais responsabilidades:

- Criar consultas;
- Atualizar estados de consultas;
- Validar paciente e médico;
- Publicar eventos no RabbitMQ.

Eventos publicados:

```text
appointment.created
appointment.status.changed