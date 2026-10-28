# StaySync — Hotel Reservation Management API

REST API for room and reservation management, built with Java 21, Spring Boot, Spring Data JPA, and MySQL.

## Features
- CRUD operations for rooms and reservations
- Request validation and consistent error responses
- Reservation date validation (`checkOut` must be after `checkIn`)
- Basic prevention of overlapping reservations for the same room
- Controller-based REST endpoints

## Setup
1. Install JDK 21, Maven, and MySQL 8+.
2. Start MySQL. Set `DB_USERNAME` and `DB_PASSWORD` environment variables, or edit `src/main/resources/application.properties` for your local credentials.
3. Run `mvn spring-boot:run`.
4. API base URL: `http://localhost:8080`.

Do not commit real database credentials.

## Endpoints
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/rooms` | List rooms |
| GET | `/api/rooms/{id}` | Get a room |
| POST | `/api/rooms` | Create a room |
| PUT | `/api/rooms/{id}` | Update a room |
| DELETE | `/api/rooms/{id}` | Delete a room |
| GET | `/api/reservations` | List reservations |
| GET | `/api/reservations/{id}` | Get a reservation |
| POST | `/api/reservations` | Create a reservation |
| PUT | `/api/reservations/{id}` | Update a reservation |
| DELETE | `/api/reservations/{id}` | Cancel a reservation |

### Example room JSON
```json
{"roomNumber":"101","roomType":"Deluxe","capacity":2,"pricePerNight":3500.00,"available":true}
```
### Example reservation JSON
```json
{"guestName":"Aarav Patil","guestEmail":"aarav@example.com","room":{"id":1},"checkIn":"2026-11-10","checkOut":"2026-11-12"}
```
