# JioCoders Portfolio (portfolio-jsp)

[![CI Pipeline](https://github.com/jiocoders/portfolio-jsp/actions/workflows/ci.yml/badge.svg)](https://github.com/jiocoders/portfolio-jsp/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)

## 🚀 Overview

JioCoders Portfolio is a robust Spring Boot application designed to architect and deliver high-performance portfolio management. It combines clean code, scalable architecture, and modern Java best practices.

### Key Features

- **Security**: Comprehensive authentication and authorization using Spring Security.
- **Database Management**: Integrated with H2 and managed via Liquibase migrations.
- **Performance**: Optimized startup and request handling.
- **API Documentation**: Interactive Swagger/OpenAPI UI integration.
- **Containerization**: First-class Docker support for seamless deployment.

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.5.4
- **Languages**: Java 21, JSP, HTML, CSS
- **Database**: H2 (In-memory)
- **Migrations**: Liquibase
- **Security**: Spring Security
- **Mapping**: MapStruct
- **Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Build System**: Maven (with Wrapper)
- **CI/CD**: GitHub Actions

---

## 📈 Performance

The application is optimized for rapid deployment and efficient resource usage.

| Metric                   | Average Value                       |
| ------------------------ | ----------------------------------- |
| **Startup Time**         | ~18-22 seconds                      |
| **API Init (SpringDoc)** | ~900-1100 ms                        |
| **Memory Footprint**     | Optimized for JRE 21                |
| **Test Coverage**        | Unit and Integration tests included |

---

## 📂 Project Structure

```text
jiocoders-portfolio/
├── .github/workflows/    # CI/CD Pipelines
├── env/                  # Environment Variables (.env, .env.prod, etc)
├── src/main/java/        # Application Source Code
│   ├── config/           # App Configuration
│   ├── controllers/      # REST Endpoints
│   ├── models/           # Data Entities
│   ├── services/         # Business Logic
│   └── dto/              # Data Transfer Objects
├── src/main/resources/   # Configs, SQL, and Templates
├── src/test/             # Unit and Integration Tests
├── Dockerfile            # Container definition
└── pom.xml               # Dependency management
```

---

## 🏃 Getting Started

### Prerequisites

- JDK 21
- Maven (or use included `./mvnw`)

### Running Locally

#### 1. Clone the repository

```bash
git clone https://github.com/jiocoders/portfolio-jsp.git
cd portfolio-jsp
```

#### 2. Run in Development Mode

```bash
# Load environment variables and run
export $(grep -v '^#' env/.env.dev | xargs) && ./mvnw spring-boot:run
```

#### 3. Run in Production Mode

```bash
./mvnw clean package
export $(grep -v '^#' env/.env.prod | xargs) && java -jar target/portfolio-jsp-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 4. Run in default Mode

```bash
./mvnw spring-boot:run
```

### Docker

```bash
docker build -t portfolio-jsp .
docker run -p 8080:8080 --env-file env/.env.prod portfolio-jsp
```

---

## 📄 License

This project is licensed under the **Apache License, Version 2.0**. See the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.txt) for details.

---

## 🙎 Maintainer

**JioCoders Team**

- 📍 **Current**: Mumbai | Preferred: Delhi NCR
- 🧑‍💼 **Experience**: 8 Years | 🎓 MCA
- 📍 **Origin**: Prayagraj, Uttar Pradesh

---

_Created with ❤️ by [JioCoders](https://jiocoders.com)_
