# 🏥 Hospital Claims Processing System – Backend Microservice

This project is a backend-only **Hospital Claims Processing System** developed using **Java 8 + Spring Boot + PostgreSQL**. It simulates real-world hospital workflows such as managing patient records, processing insurance claims, and enforcing approval rules.

All endpoints are tested via **Postman**, and the project is structured for scalability, security, and ease of integration with future frontend systems.

---

## 🔧 Tech Stack

| Layer        | Technology            |
|--------------|------------------------|
| Language     | Java 8                 |
| Framework    | Spring Boot            |
| Database     | PostgreSQL             |
| API Testing  | Postman                |
| Packaging    | Maven                  |
| Build Tool   | Spring Boot CLI        |
| Auth (Optional) | JWT + Spring Security (future scope) |

---

## 📦 Modules & Entities

### 👤 Patients
- Stores patient data like name, DOB, contact, and active status.

### 📄 Claims
- Filed against patients
- Contains claim number, diagnosis code, amount, status (PENDING / APPROVED / REJECTED)
- Can be approved/rejected based on business rules

---

## 📂 Project Structure (Important Files)

<pre lang="markdown"> claims/ ├── src/ │ └── main/ │ ├── java/ │ │ └── com/ │ │ └── claims/ │ │ ├── controller/ # Handles REST API endpoints (ClaimController.java, PatientController.java) │ │ ├── service/ # Contains business logic (ClaimService.java, PatientService.java) │ │ ├── repository/ # Spring Data JPA interfaces (ClaimRepository.java, PatientRepository.java) │ │ └── model/ # Entity models (Claim.java, Patient.java) │ └── resources/ │ └── application.properties # Configuration for DB, port, etc. ├── pom.xml # Maven dependencies and plugins └── README.md # Project documentation </pre>

---

## 🔄 API Endpoints (Tested via Postman)

### 👥 Patients

| Method | Endpoint         | Description            |
|--------|------------------|------------------------|
| GET    | `/api/patients`  | Get all patients       |
| GET    | `/api/patients/{id}` | Get patient by ID    |
| POST   | `/api/patients`  | Create a new patient   |
| PUT    | `/api/patients/{id}` | Update patient info  |
| DELETE | `/api/patients/{id}` | Delete a patient     |

---

### 📄 Claims

| Method | Endpoint          | Description                |
|--------|-------------------|----------------------------|
| GET    | `/api/claims`     | Get all claims             |
| GET    | `/api/claims/{id}`| Get claim by ID            |
| POST   | `/api/claims`     | Create a new claim         |
| PUT    | `/api/claims/{id}`| Update a claim             |
| DELETE | `/api/claims/{id}`| Delete a claim             |

---

## 📜 Business Rules

- A claim must be tied to a valid and active patient
- Claims over a certain threshold (e.g. ₹10,000) may require manual review
- Future scope: Use Spring Security to restrict claim approval to specific roles

---

## 🧪 Postman Testing

- All endpoints are tested via **Postman**
- A `hospital-claims.postman_collection.json` file is available in the repo (if uploaded)
- Supports CRUD flows for patients and claims

---

## 🛠️ How to Run Locally

> Ensure PostgreSQL is installed and running with a database created (e.g., `claimsdb`)

1. Clone the repo:

```bash
git clone https://github.com/RanadeepMahendra2000/hospital-claims-system.git
cd hospital-claims-system

