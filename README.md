#  KYC Blockchain — Hyperledger Fabric + Spring Boot + React

A full-stack **Know Your Customer (KYC)** platform built on **Hyperledger Fabric** blockchain, providing secure, tamper-proof identity verification for financial institutions.

---

##  Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Roles & Permissions](#roles--permissions)
- [Blockchain Integration](#blockchain-integration)
- [Screenshots](#screenshots)
- [Roadmap](#roadmap)

---

##  Overview

KYC Blockchain is a permissioned blockchain solution that allows users to submit KYC documents once and share verified identity data with multiple institutions — without repeatedly submitting the same documents.

Key benefits:
- **Immutable** — KYC records stored on Hyperledger Fabric cannot be altered
- **Tamper-proof** — SHA-256 document hashing ensures document integrity
- **Privacy-first** — Only document hashes are stored on-chain, not raw files
- **Multi-institution** — Banks and institutions can verify KYC without re-submission

---

##  Features

### User Features
- Register and login with JWT authentication
- Submit KYC with personal details and documents
- Track KYC status in real time (Pending, Approved, Rejected)
- Upload supporting documents (Passport, National ID, Utility Bill, etc.)
- Receive email notifications on KYC status updates

### Admin Features
- Dashboard with KYC statistics
- Review, approve, or reject KYC submissions
- View all KYC records by status

### Institution Features
- Request KYC verification for a user
- Verify KYC authenticity against blockchain hash

### Blockchain Features
- KYC data hash stored on Hyperledger Fabric ledger
- Transaction ID returned for every blockchain operation
- Blockchain verification endpoint to confirm data integrity

---

##  Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 21, Spring Boot 3.4.3 |
| Security | Spring Security, JWT (jjwt 0.12.5) |
| Database | H2 (dev) / PostgreSQL (prod) |
| Blockchain | Hyperledger Fabric 2.x, Fabric Gateway SDK 1.4.0 |
| Chaincode | Go (KYC smart contract) |
| Frontend | React 18, Vite, Tailwind CSS |
| HTTP Client | Axios |
| Routing | React Router DOM v6 |
| Notifications | React Toastify |
| Email | Spring Mail (Gmail SMTP) |
| Build Tool | Maven |

---

##  Architecture

```
[React Frontend :5173]
        |
        | REST API (JWT)
        ▼
[Spring Boot Backend :8080]
        |
   ┌────┴────┐
   |         |
[H2/PostgreSQL]  [Hyperledger Fabric Network]
                        |
                 ┌──────┴──────┐
               [Org1]        [Org2]
              (Bank A)      (Bank B)
                        |
                 [KYC Chaincode]
                 [Ledger - hashes]
```

---

##  Project Structure

```
kyc-blockchain/
├── src/main/java/com/kyc/blockchain/
│   ├── config/               # Security & CORS config
│   ├── controller/           # REST API controllers
│   ├── dto/                  # Data Transfer Objects
│   ├── exception/            # Custom exceptions & global handler
│   ├── fabric/               # Hyperledger Fabric gateway
│   ├── model/                # JPA entity classes
│   ├── repository/           # Spring Data JPA repositories
│   ├── security/             # JWT provider & filter
│   ├── service/              # Business logic
│   └── util/                 # HashUtil, FileUtil, DateUtil
├── src/main/resources/
│   ├── crypto-config/        # Fabric certificates
│   ├── wallet/               # Fabric identity wallet
│   └── application.properties
├── chaincode/
│   └── kyc-chaincode/        # Go smart contract
├── fabric-network/           # Docker compose for Fabric network
├── frontend/                 # React + Vite + Tailwind
│   └── src/
│       ├── components/       # Navbar, ProtectedRoute, StatusBadge
│       ├── context/          # AuthContext (JWT state)
│       ├── pages/            # Login, Register, Dashboard, etc.
│       └── services/         # Axios API service
└── pom.xml
```

---

##  Prerequisites

Make sure you have the following installed:

| Tool | Version | Download |
|------|---------|----------|
| Java JDK | 21 | https://adoptium.net |
| Maven | 3.8+ | https://maven.apache.org |
| Node.js & npm | 20 LTS | https://nodejs.org |
| Docker Desktop | Latest | https://docker.com |
| IntelliJ IDEA | Any | https://jetbrains.com |
| Git | Latest | https://git-scm.com |

---

##  Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/KabeloMashapa/kyc-blockchain.git
cd kyc-blockchain
```

### 2. Configure `application.properties`

Edit `src/main/resources/application.properties`:

```properties
spring.application.name=kyc-blockchain
server.port=8080

# H2 Database (development)
spring.datasource.url=jdbc:h2:mem:kycdb
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT
jwt.secret=your-secret-key-minimum-256-bits-long
jwt.expiration=86400000

# Email
spring.mail.username=youremail@gmail.com
spring.mail.password=your-app-password
```

### 3. Run the Spring Boot Backend

```bash
# From project root
mvn spring-boot:run
```

Backend will start at: `http://localhost:8080`

H2 Console available at: `http://localhost:8080/h2-console`

### 4. Run the React Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend will start at: `http://localhost:5173`

### 5. Open in Browser

```
http://localhost:5173
```

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login & get JWT token |

### KYC
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/kyc/submit` | USER | Submit KYC application |
| GET | `/api/kyc/{kycId}` | ALL | Get KYC by ID |
| GET | `/api/kyc/user/{userId}` | USER/ADMIN | Get user's KYC records |
| GET | `/api/kyc/status/{status}` | ADMIN | Get KYC by status |
| PUT | `/api/kyc/{kycId}/approve` | ADMIN | Approve KYC |
| PUT | `/api/kyc/{kycId}/reject` | ADMIN | Reject KYC |
| GET | `/api/kyc/{kycId}/verify` | ADMIN/INSTITUTION | Verify on blockchain |

### Documents
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/documents/upload/{kycId}` | USER/ADMIN | Upload KYC document |
| GET | `/api/documents/{kycId}` | ALL | Get documents for KYC |
| POST | `/api/documents/verify/{documentId}` | ADMIN | Verify document hash |

### Admin
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/admin/dashboard` | ADMIN | Get dashboard stats |
| GET | `/api/admin/kyc/pending` | ADMIN | Get pending KYC list |
| GET | `/api/admin/kyc/all` | ADMIN | Get all KYC records |

---

##  Roles & Permissions

| Role | Description | Permissions |
|------|-------------|------------|
| `USER` | Regular customer | Submit KYC, view own records, upload documents |
| `ADMIN` | KYC reviewer | Approve/reject KYC, view all records, admin dashboard |
| `INSTITUTION` | Bank or org | Verify KYC on blockchain, request verification |

---

##  Blockchain Integration

### How It Works

1. User submits KYC → Spring Boot hashes KYC data using **SHA-256**
2. Hash is submitted to **Hyperledger Fabric** via the Gateway SDK
3. Fabric returns a **Transaction ID** stored in the database
4. Any institution can verify the hash against the blockchain at any time

### KYC Data Flow

```
User submits KYC
      ↓
Spring Boot hashes data (SHA-256)
      ↓
Hash submitted to Fabric chaincode
      ↓
Transaction ID returned & stored in DB
      ↓
Institution requests verification
      ↓
Hash compared against blockchain ledger
      ↓
 Valid or  Tampered
```

### Supported Document Types
- `PASSPORT`
- `NATIONAL_ID`
- `DRIVERS_LICENSE`
- `UTILITY_BILL`
- `BANK_STATEMENT`
- `SELFIE_WITH_ID`

---

##  Roadmap

- [x] Spring Boot REST API
- [x] JWT Authentication & Authorization
- [x] KYC Submission & Review workflow
- [x] Document upload & SHA-256 hashing
- [x] React frontend with Tailwind CSS
- [x] Admin dashboard
- [ ] Real Hyperledger Fabric network (Docker)
- [ ] Go chaincode deployment
- [ ] Switch to PostgreSQL for production
- [ ] Dockerize full stack
- [ ] IPFS document storage
- [ ] Multi-institution verification portal

---

##  Security

- Passwords hashed with **BCrypt**
- API secured with **JWT Bearer tokens**
- Role-based access control on all endpoints
- Document integrity verified via **SHA-256 hashing**
- KYC data hash stored on **immutable blockchain ledger**
- CORS configured for frontend origin only

---

##  Author

**Masha**
- Project: KYC Blockchain — Hyperledger Fabric + Spring Boot + React
- Institution: SMC

---

##  License

This project is for educational purposes.
