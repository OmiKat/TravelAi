# Travel


Travel is a Spring Boot (Java 21) REST service that provides basic travel-related features:
- User authentication (JWT)
- Destinations CRUD and search
- Trip creation and listing (per authenticated user)
- An AI itinerary generator backed by a Groq-compatible API

Project layout (important files)
- `pom.xml` — Maven build configuration
- `src/main/java/...` — application source
- `src/main/resources/application.properties` — default configuration (contains placeholders/secrets — do NOT commit secrets)

Requirements
- Java 21
- Maven (or use the bundled `mvnw` wrapper)
- PostgreSQL (or another JDBC-compatible datasource)

Quickstart — build and run

Build with the wrapper and run tests:

```fish
./mvnw clean package
./mvnw test
```

Run the application (development):

```fish
./mvnw spring-boot:run
```

Or run the packaged jar:

```fish
java -jar target/Travel-0.0.1-SNAPSHOT.jar
```

Configuration

The application reads Spring properties from `src/main/resources/application.properties`. It currently uses the following custom properties (examples):

- `jwt.secret` — secret used to sign JWT tokens
- `jwt.expiration` — token lifetime in milliseconds
- `groq.api.url` — external AI API endpoint
- `groq.api.key` — external AI API key

Important: Do not commit secrets. For local development, prefer environment variables or a separate `application-local.properties` not checked in. Spring Boot maps environment variables to properties by uppercasing and replacing dots with underscores, for example:

```fish
# set JWT secret and datasource via environment variables before starting
set -x JWT_SECRET "your_jwt_secret_here"
set -x GROQ_API_KEY "your_groq_api_key_here"
set -x SPRING_DATASOURCE_URL "jdbc:postgresql://localhost:5432/travel_db?user=...&password=..."
./mvnw spring-boot:run
```

API Endpoints (summary)

Authentication
- POST /api/v1/auth/register — register a new user (RegisterRequest JSON)
- POST /api/v1/auth/login — login and receive `AuthResponse` with JWT

Destinations
- POST /api/v1/destinations — create destination (requires valid request body)
- GET /api/v1/destinations — list all destinations
- GET /api/v1/destinations/{id} — get destination by UUID
- GET /api/v1/destinations/search?name=... — search destinations by name

Trips (requires authentication — include JWT in Authorization header as `Bearer <token>`)
- POST /api/v1/trip/create — create a trip for the authenticated user
- GET /api/v1/trip — list trips for the authenticated user

AI
- POST /api/v1/ai/itinerary — request an itinerary (uses external Groq API configured with `groq.api.key`)

Example: Login and fetch protected resource

```fish
# login
curl -s -X POST http://localhost:8080/api/v1/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"password"}'

# assuming you received a token in the response as {"token":"..."}
# list trips with token
curl -s http://localhost:8080/api/v1/trip -H "Authorization: Bearer <token>"
```

Testing

- Unit and integration tests: `./mvnw test`

Troubleshooting
- Database connection errors: verify `SPRING_DATASOURCE_URL` and that the DB accepts connections from your environment.
- JWT issues: ensure `jwt.secret` is set and consistent between runs.
- External AI API failures: check `groq.api.key` and `groq.api.url`.
