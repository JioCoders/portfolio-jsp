# Architecture & Design Visuals

## 1. Entity Relationship Diagram (ERD) - Expense Management
Visualizes the "Split-wise" style ledger system.

```mermaid
erDiagram
    USERS ||--o{ GROUP_MEMBERS : belongs_to
    GROUPS ||--o{ GROUP_MEMBERS : has
    GROUPS ||--o{ EXPENSES : contains
    EXPENSES ||--o{ EXPENSE_DISTRIBUTIONS : "split_into"
    USERS ||--o{ EXPENSE_DISTRIBUTIONS : "involved_in"
    GROUPS ||--o{ SETTLEMENTS : resolves
    GROUPS ||--o{ AUDIT_LOGS : tracked_in
    USERS ||--o{ AUDIT_LOGS : performed_by

    USERS {
        BIGINT id PK
        VARCHAR username
        VARCHAR full_name
        VARCHAR email
        VARCHAR phone
    }

    EXPENSE_DISTRIBUTIONS {
        BIGINT id PK
        BIGINT expense_id FK
        BIGINT user_id FK
        DECIMAL paid_amount
        DECIMAL share_amount
    }

    AUDIT_LOGS {
        BIGINT id PK
        BIGINT group_id FK
        VARCHAR entity_type
        BIGINT entity_id
        VARCHAR action
        BIGINT performed_by FK
        TEXT details
    }
```

## 2. API Route Mapping
All endpoints are prefixed with `/jiocoders/v1`.

| Category        | Endpoint                       | Method   | Description         |
| :-------------- | :----------------------------- | :------- | :------------------ |
| **Auth**        | `/login`                       | POST     | User Authentication |
| **Groups**      | `/api/groups`                  | POST/GET | Manage Groups       |
| **Expenses**    | `/api/groups/{id}/expenses`    | POST/GET | Add/View Expenses   |
| **Balances**    | `/api/groups/{id}/balances`    | GET      | Net Ledger Per User |
| **Settlements** | `/api/groups/{id}/settlements` | POST     | Record Payments     |

---
*Maintained by Gemini Code Assistant*
