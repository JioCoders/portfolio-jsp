# Architecture & Design Visuals

This document contains text-based diagrams for the Jiocoders Portfolio project. These can be viewed directly on GitHub or via Mermaid-supported Markdown editors.

## 1. Entity Relationship Diagram (ERD)
Visualizes the database schema managed by Liquibase.

```mermaid
erDiagram
    USERS {
        BIGINT id PK
        VARCHAR username "unique"
        VARCHAR password "bcrypt"
        VARCHAR email "nullable"
        VARCHAR role "USER/ADMIN"
        TIMESTAMP created_at
    }
```

## 2. Authentication Workflow
Visualizes the security flow for login and registration.

```mermaid
sequenceDiagram
    participant U as Client
    participant C as LoginController
    participant S as UserService
    participant D as UserDao/DB

    U->>C: POST /login (username, password)
    C->>S: login(username, password)
    S->>D: findByUsername(username)
    D-->>S: UserRecord
    S->>S: Compare Passwords (BCrypt)
    S-->>C: UserDTO / null
    C-->>U: 200 OK / 401 Unauthorized

    Note over U,D: Register requires ROLE_ADMIN
```

## 3. Project Route Mapping
Visualizes the new decoupled path structure.

```mermaid
graph LR
    Root[/] --> Portfolio[Portfolio Home Page]
    Root --> APIPrefix[jio.api.prefix: /jiocoders/v1]
    APIPrefix --> Auth[Auth: /login, /register]
    APIPrefix --> Admin[Admin: /admin/users]
    APIPrefix --> API[Public API: /api/hello]
```

---
*Maintained by Gemini Code Assistant*
