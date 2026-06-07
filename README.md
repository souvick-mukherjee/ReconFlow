# ReconFlow

An event-driven payments reconciliation platform built with Spring Boot, Kafka, PostgreSQL, and Docker.

ReconFlow simulates a real-world financial transaction processing system where payments are captured, ledger entries are generated, and reconciliation is performed asynchronously using event-driven workflows.

The project demonstrates production-grade distributed systems patterns including:

- Event-Driven Architecture
- Transactional Outbox Pattern
- Saga Orchestration & Compensation
- Idempotent Kafka Consumers
- Retry Handling
- Dead Letter Queue (DLQ)
- Automated Payment Reconciliation
- Tolerance-Based Matching

---

## Architecture

```text
                        +----------------+
                        | Payment API    |
                        +-------+--------+
                                |
                                v
                      +------------------+
                      | PostgreSQL       |
                      | payments         |
                      | outbox_events    |
                      +--------+---------+
                               |
                               v
                      +------------------+
                      | Outbox Publisher |
                      +--------+---------+
                               |
                               v
                          Kafka Topics
                               |
         ------------------------------------------------
         |                                              |
         v                                              v

+-------------------+                    +----------------------+
| Ledger Consumer   |                    | Compensation Flow    |
+-------------------+                    +----------------------+
         |
         v
+-------------------+
| Ledger Entries    |
+-------------------+
         |
         v
  LedgerCreatedEvent
         |
         v
+------------------------+
| Reconciliation Engine  |
+------------------------+
         |
         v
+------------------------+
| Reconciliation Record  |
+------------------------+
```

---

## Key Features

### Payment Processing

- Create and capture payments
- Generate unique payment references
- Store payment lifecycle state

### Ledger Management

- Automatic ledger entry generation
- Double-entry accounting simulation

### Reconciliation Engine

Compares:

- Payment Amount
- Ledger Amount
- Settlement Amount

Possible outcomes:

- MATCHED
- MATCHED_WITH_TOLERANCE
- AMOUNT_MISMATCH
- MISSING_LEDGER
- MISSING_SETTLEMENT

### Tolerance-Based Matching

Example:

```text
Payment Amount:    100.00
Settlement Amount: 99.00

Variance: 1.00
Tolerance: 2.00

Result:
MATCHED_WITH_TOLERANCE
```

---

## Event Flow

### Happy Path

```text
Create Payment
      |
      v
PaymentCreatedEvent
      |
      v
Ledger Consumer
      |
      v
LedgerCreatedEvent
      |
      v
Reconciliation Service
      |
      v
MATCHED
```

### Failure Path

```text
Create Payment
      |
      v
PaymentCreatedEvent
      |
      v
Ledger Processing Failure
      |
      v
PaymentCancelledEvent
      |
      v
Compensation
      |
      v
Payment Status = FAILED
```

---

## Transactional Outbox Pattern

### Problem

```text
Save Payment
    |
    +--> Database Commit Success
    |
    +--> Kafka Publish Failure
```

Result:

```text
Inconsistent System State
```

### Solution

```text
Database Transaction

1. Save Payment
2. Save Outbox Event

COMMIT
```

Background Publisher:

```text
Read Outbox Event
      |
      v
Publish to Kafka
      |
      v
Mark Published
```

Benefits:

- Reliable Event Delivery
- Prevents Lost Events
- Supports Eventual Consistency

---

## Idempotent Consumers

Kafka guarantees:

```text
At-Least-Once Delivery
```

Meaning duplicate events may occur.

ReconFlow prevents duplicate processing using:

```text
processed_events
```

table.

Consumer Flow:

```text
Receive Event
      |
      v
Already Processed?
      |
  +---+---+
  |       |
 YES      NO
  |       |
 Skip   Process
          |
          v
   Mark Processed
```

---

## Retry & Dead Letter Queue

Failed events are retried automatically.

```text
Attempt 1
Attempt 2
Attempt 3
```

If all retries fail:

```text
payments.created.dlq
```

The event is routed to the Dead Letter Queue.

---

## Saga Pattern

ReconFlow implements orchestration-style compensation.

Example:

```text
Payment Created
      |
      v
Ledger Creation Failed
      |
      v
PaymentCancelledEvent
      |
      v
Compensating Action
      |
      v
Payment Status = FAILED
```

Saga States:

- STARTED
- IN_PROGRESS
- COMPLETED
- FAILED
- COMPENSATED

---

## Tech Stack

Backend

- Spring Boot
- Spring Data JPA
- Spring Kafka
- Flyway

Messaging

- Apache Kafka

Database

- PostgreSQL

Infrastructure

- Docker
- Docker Compose

---

## Project Structure

```text
src/main/java/com/reconflow

common/
event/

payment/
ledger/
settlement/
reconciliation/

outbox/
idempotency/
saga/
```

Feature-based packaging is used to support future migration into independent microservices.

---

## Running Locally

### Start Infrastructure

```bash
docker compose up -d
```

### Start Application

```bash
./mvnw spring-boot:run
```

---

## Example Request

### Create Payment

POST /api/payments

```json
{
  "merchantId": "M100",
  "amount": 2500,
  "currency": "USD"
}
```

---

## Future Enhancements

- Debezium CDC for Outbox
- Prometheus Metrics
- Grafana Dashboards
- OpenTelemetry Tracing
- Settlement File Ingestion
- Manual Reconciliation Queue
- Multi-Service Deployment
- Kubernetes

---

## Learning Outcomes

This project was built to explore production-grade distributed systems concepts:

- Event-Driven Design
- Distributed Transactions
- Eventual Consistency
- Reliable Messaging
- Failure Recovery
- Financial Reconciliation Workflows

---