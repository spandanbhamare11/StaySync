# StaySync - Hotel Reservation Management API

StaySync is a RESTful API for managing hotel rooms and reservations. It is built using Java 21, Spring Boot, Spring Data JPA, Hibernate, and MySQL.

## Features

- Create, retrieve, update, and delete hotel rooms.
- Create, retrieve, update, and cancel reservations.
- Validate reservation check-in and check-out dates.
- Check for overlapping reservations for the same room.
- Persist application data using MySQL and Spring Data JPA.
- Expose REST endpoints using Spring Web.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3.4.4 | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Hibernate | ORM and entity management |
| MySQL 8+ | Relational database |
| Maven Wrapper | Build and dependency management |
| Spring Boot Test | Testing framework |

## Prerequisites

Install the following before running the project:

- JDK 21
- MySQL 8 or later
- Git

Maven does not need to be installed separately because the repository includes the Maven Wrapper.

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/spandanbhamare11/StaySync.git
cd StaySync
```

### 2. Configure MySQL

Start your MySQL server and create the database:

```sql
CREATE DATABASE staysync;
```

The application uses these environment variables:

| Variable | Purpose |
|---|---|
| `DB_URL` | MySQL connection URL |
| `DB_USERNAME` | MySQL username |
| `DB_PASSWORD` | MySQL password |
| `SERVER_PORT` | Application port |

Set the variables in PowerShell before starting the application. Replace the example password with your own local MySQL password:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/staysync?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "YOUR_LOCAL_MYSQL_PASSWORD"
$env:SERVER_PORT = "8081"
```

The application configuration currently defaults to port `8080`. The example above uses `8081` to avoid a conflict if another application is already using port `8080`.

Keep database credentials out of source control.

### 3. Build the project

On Windows, run:

```powershell
.\mvnw.cmd clean package
```

### 4. Start the application

```powershell
.\mvnw.cmd spring-boot:run
```

By default, the API uses port `8080`, unless overridden using `SERVER_PORT`.

Base URL when running on port `8081`:

```text
http://localhost:8081
```

## API Endpoints

### Room Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/rooms` | Retrieve all rooms |
| GET | `/api/rooms/{id}` | Retrieve a room by ID |
| POST | `/api/rooms` | Create a room |
| PUT | `/api/rooms/{id}` | Update a room |
| DELETE | `/api/rooms/{id}` | Delete a room |

### Reservation Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/reservations` | Retrieve all reservations |
| GET | `/api/reservations/{id}` | Retrieve a reservation by ID |
| POST | `/api/reservations` | Create a reservation |
| PUT | `/api/reservations/{id}` | Update a reservation |
| DELETE | `/api/reservations/{id}` | Delete a reservation |

## Example Requests

### Create a Room

**POST** `/api/rooms`

```json
{
  "roomNumber": "101",
  "roomType": "DELUXE",
  "capacity": 2,
  "pricePerNight": 2500.00,
  "available": true
}
```

### Create a Reservation

**POST** `/api/reservations`

```json
{
  "guestName": "Aarav Patil",
  "guestEmail": "aarav@example.com",
  "room": {
    "id": 1
  },
  "checkIn": "2026-11-10",
  "checkOut": "2026-11-12"
}
```

The room ID must refer to an existing room.

## Validation

The application includes checks for reservation date order and overlapping bookings for the same room. Check-out must be after check-in, and a room must exist before a reservation can be created.

## Testing

Run the available Maven tests with:

```powershell
.\mvnw.cmd test
```

Build the application with:

```powershell
.\mvnw.cmd clean package
```

Spring Boot Test is included as a test dependency. Additional automated tests can be added as the project evolves.

## Future Improvements

- Add automated unit and integration tests.
- Add authentication and role-based authorization.
- Add pagination and filtering.
- Provide a Postman collection for API testing.
- Improve centralized exception handling and API documentation.

## Author

**Spandan Bhamare**

GitHub: https://github.com/spandanbhamare11
