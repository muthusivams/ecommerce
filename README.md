# Production-Ready eCommerce Microservices (Java 8 + Spring Boot 2.x)

## 1) Architecture Overview
- **Java**: 8
- **Spring Boot**: 2.3.12.RELEASE
- **Spring Cloud**: Hoxton.SR12
- **Service discovery**: Eureka (`discovery-server`)
- **API Gateway**: Spring Cloud Gateway (`api-gateway`)
- **Persistence**: MySQL (service-per-schema)
- **Cache**: Redis (`cart-service`)
- **Messaging**: Kafka (order, payment, inventory, notification events)
- **Security**: JWT (authentication + role claim)
- **Inter-service calls**: OpenFeign
- **API docs**: OpenAPI/Swagger via springdoc
- **Ops**: Docker + docker-compose

## 2) Microservices and Responsibilities
- **user-service**: registration, login, profile CRUD starter, role model (`ADMIN`, `CUSTOMER`)
- **product-service**: product CRUD, category, pagination
- **inventory-service**: stock tracking, reservation, reduction + stock events
- **cart-service**: cart operations in Redis
- **order-service**: order lifecycle, inventory reservation, payment trigger, idempotency by requestId, consumes payment events
- **payment-service**: mock payment processing + payment events
- **notification-service**: consumes order/payment events and emits mock email logs

## 3) Folder Structure
```
.
├── discovery-server/
├── api-gateway/
├── user-service/
├── product-service/
├── inventory-service/
├── cart-service/
├── order-service/
├── payment-service/
├── notification-service/
├── infra/mysql-init/
├── postman/
├── docker-compose.yml
└── pom.xml
```

Each service uses layered architecture:
`controller -> service -> repository -> entity`

## 4) Key Domain Models
- User: `id, email, password, fullName, role`
- Product: `id, name, category, description, price`
- Inventory: `productId, availableStock`
- Order: `id, userId, productId, quantity, amount, status, requestId`
- Payment: `id, orderId, amount, status`

## 5) Kafka Topics
- `stock-events` (inventory publications)
- `order-events` (order lifecycle publications)
- `payment-events` (payment results)

## 6) Security and JWT
- Login endpoint issues JWT with `subject=email` and claim `role`.
- Gateway validates JWT for protected routes and forwards identity in `X-User-Email` header.

## 7) Example Endpoints
### User Service
- `POST /api/users/auth/register`
- `POST /api/users/auth/login`
- `GET /api/users/{id}`

### Product Service
- `POST /api/products`
- `PUT /api/products/{id}`
- `GET /api/products/{id}`
- `GET /api/products?page=0&size=20&category=Electronics`

### Inventory Service
- `POST /api/inventory/track/{productId}?stock=100`
- `POST /api/inventory/reserve/{productId}?qty=1`
- `POST /api/inventory/reduce/{productId}?qty=1`

### Cart Service
- `POST /api/cart/{userId}/items`
- `PUT /api/cart/{userId}/items`
- `DELETE /api/cart/{userId}/items/{productId}`
- `GET /api/cart/{userId}`

### Order Service
- `POST /api/orders`
- `GET /api/orders/{id}`

### Payment Service
- `POST /api/payments/process/{orderId}?amount=1200.00`

## 8) Curl Commands
```bash
# Register
curl -X POST http://localhost:8080/api/users/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"alice@example.com","password":"Pass@123","fullName":"Alice"}'

# Login
TOKEN=$(curl -s -X POST http://localhost:8080/api/users/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"alice@example.com","password":"Pass@123"}' | jq -r '.token')

# Create Product
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"name":"Laptop","category":"Electronics","description":"14 inch","price":1200.00}'

# Create Order (idempotent by requestId)
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"userId":1,"productId":1,"quantity":1,"amount":1200.00,"requestId":"req-001"}'
```

## 9) Run Locally
```bash
# Build all modules
mvn clean package -DskipTests

# Start full stack
docker-compose up --build
```

### MySQL connectivity note
If you run a service directly from your IDE/terminal (outside Docker), keep `MYSQL_HOST` as default (`localhost`) or set it explicitly:

```bash
export MYSQL_HOST=localhost
export MYSQL_PORT=3306
```

When running inside Docker Compose, set `MYSQL_HOST=mysql` for the container network (or keep service-level env overrides).


### Docker Desktop troubleshooting (Windows)
If you see an error like:

```text
open //./pipe/dockerDesktopLinuxEngine: The system cannot find the file specified
```

it means Docker Compose cannot reach the Docker Engine (Docker Desktop is not running, not in Linux containers mode, or context is misconfigured).

Use:

```powershell
# Verify Docker Desktop/Engine is reachable
docker version
docker context ls

# Ensure Linux engine context is active
docker context use desktop-linux

# Then retry
docker compose up --build
```

Also ensure Docker Desktop is started and WSL2 integration is enabled for your distro.

## 10) OpenAPI
- User Service Swagger UI: `http://localhost:8081/swagger-ui/index.html`
- Similar endpoints are available when springdoc dependency is present in other services.

## 11) Centralized Logging Strategy
- Services emit structured, trace-friendly logs to stdout.
- In production, plug stdout streams into ELK/Loki/Datadog collector.

## 12) Postman
Collection file:
- `postman/ecommerce-microservices.postman_collection.json`


## 13) Maven/IDE Troubleshooting
- In IntelliJ *Runner parameters*, do **not** prepend `mvn` as an argument.
- Correct command is goals only, for example: `clean package -DskipTests`.
- If `mvn` is added to goals, Maven fails with `Unknown lifecycle phase "mvn"`.
