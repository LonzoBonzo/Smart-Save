# Smart-Save

Smart Save is a full-stack personal finance tracker built for the 2026 Spring App Development Program. This milestone now includes a React frontend, a Spring Boot backend, persistent database storage, session-based authentication with Spring Security, and public API integration for exchange-rate snapshots.

## Stack

- Frontend: React, React Router, `useReducer`, Fetch API, Vite
- Backend: Spring Boot, Spring MVC, Spring Data JPA, Spring Security
- Database: H2 file database
- External API: `frankfurter.app` for currency exchange-rate snapshots

## Project Structure

- `SMARTSAVE/smartsave-backend` - Spring Boot API, auth, JPA entities, seed data
- `SMARTSAVE/smartsave-frontend` - React client with dashboard, data display, and form pages

## Features Implemented

- Responsive React app with:
  - Home/Dashboard page
  - Data Display page using backend `GET` endpoints
  - Form page using backend `POST` endpoints
  - Login and registration pages
- Spring Data JPA entities and repositories
- Persistent H2 tables replacing temporary in-memory storage
- Relationship coverage:
  - `AppUser -> SavingsGoal` is `OneToMany`
  - `AppUser -> TransactionRecord` is `OneToMany`
  - `TransactionRecord <-> TransactionTag` is `ManyToMany`
- Input validation with Jakarta Validation annotations
- Spring Security session-based authentication
- Protected API routes and protected frontend routes
- External API ingestion and storage for exchange-rate snapshots
- Seeded demo account and starter finance data

## Run Instructions

### Backend

1. Install Java 17+
2. From `SMARTSAVE/smartsave-backend`, run:

```bash
./mvnw spring-boot:run
```

Backend runs on `http://localhost:8080`

Useful URLs:

- Health: `http://localhost:8080/api/public/health`
- H2 Console: `http://localhost:8080/h2-console`

H2 settings:

- JDBC URL: `jdbc:h2:file:./data/smartsave`
- Username: `sa`
- Password: `password`

### Frontend

1. Install Node.js 18+
2. From `SMARTSAVE/smartsave-frontend`, run:

```bash
npm install
npm run dev
```

Frontend runs on `http://localhost:5173`

## Demo Login

- Email: `demo@smartsave.app`
- Password: `Password123`

## JPA Relationship Diagram

```text
AppUser (1) --------< SavingsGoal
AppUser (1) --------< TransactionRecord
TransactionRecord >--------< TransactionTag

ExchangeRateSnapshot
  stored independently after external API refresh
```

## Database Schema Summary

| Table | Purpose |
| --- | --- |
| `app_users` | Registered users with hashed passwords and roles |
| `savings_goals` | User-owned savings goals |
| `transactions` | User-owned income and expense records |
| `transaction_tags` | Reusable spending/income tags |
| `transaction_record_tags` | Join table for many-to-many transaction tags |
| `exchange_rate_snapshots` | Stored exchange-rate API results |

## Endpoint Table

| Method | Endpoint | Description | Auth Required |
| --- | --- | --- | --- |
| `GET` | `/api/public/health` | Public health check | No |
| `POST` | `/api/auth/register` | Create account | No |
| `POST` | `/api/auth/login` | Login and create session | No |
| `POST` | `/api/auth/logout` | Logout and clear session | Yes |
| `GET` | `/api/auth/me` | Current session user | Yes |
| `GET` | `/api/dashboard` | Dashboard totals and counts | Yes |
| `GET` | `/api/goals` | Fetch current user's goals | Yes |
| `POST` | `/api/goals` | Create a savings goal | Yes |
| `GET` | `/api/transactions` | Fetch current user's transactions | Yes |
| `GET` | `/api/transactions/tags` | Fetch available tags | Yes |
| `POST` | `/api/transactions` | Create a transaction | Yes |
| `GET` | `/api/external/rates` | Retrieve stored exchange-rate snapshots | Yes |
| `POST` | `/api/external/rates/refresh` | Pull and store fresh rates from public API | Yes |

## Example API Responses

### `POST /api/auth/login`

```json
{
  "message": "Login successful",
  "user": {
    "id": 1,
    "fullName": "Demo Saver",
    "email": "demo@smartsave.app",
    "role": "USER"
  }
}
```

### `GET /api/dashboard`

```json
{
  "userName": "Demo Saver",
  "goalsCount": 2,
  "transactionsCount": 2,
  "totalSaved": 870.00,
  "totalIncome": 850.00,
  "totalExpenses": 62.45
}
```

### `POST /api/transactions`

```json
{
  "id": 3,
  "type": "EXPENSE",
  "amount": 18.75,
  "description": "Lunch on campus",
  "transactionDate": "2026-04-20",
  "tags": ["Food", "Campus"]
}
```

### `POST /api/external/rates/refresh`

```json
{
  "status": "success",
  "message": "Rates refreshed successfully",
  "rates": [
    {
      "id": 1,
      "baseCurrency": "USD",
      "targetCurrency": "EUR",
      "rate": 0.91,
      "fetchedAt": "2026-04-20T15:00:00",
      "source": "frankfurter.app"
    }
  ]
}
```

## Secure Data Flow

1. User logs in from React.
2. Spring Security validates credentials and creates an HTTP session.
3. Protected React routes call backend endpoints with session credentials.
4. Controllers validate incoming input.
5. Services persist data through Spring Data JPA repositories.
6. External API data is retrieved by the backend, stored in the database, and displayed in React.

## External API Notes

- Public API used: `https://api.frankfurter.app`
- The backend stores each refresh as `ExchangeRateSnapshot` rows
- If the provider fails or rate limits requests, the backend returns a controlled error message so the frontend can show feedback instead of crashing

## AI Usage Log

| Tool | Purpose | Validation |
| --- | --- | --- |
| ChatGPT / Codex | Generated scaffolding, security wiring, JPA structure, and React routing/layout | Code was reviewed against milestone requirements and connected to the existing repository layout |

## Notes

- The milestone requested React specifically, so the frontend was implemented in React rather than Angular.
- The backend now uses persistent H2 storage instead of temporary lists.
- Session auth was chosen because the rubric allows JWT or session-based authorization.
