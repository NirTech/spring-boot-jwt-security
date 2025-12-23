# 🛡️ Spring Boot JWT Security API

A professional, stateless RESTful API implementation using **Spring Boot 3.2+** and **Spring Security**. This project features a robust authentication and authorization flow using **JSON Web Tokens (JWT)**.



---

## 🚀 Key Features
* **Stateless Authentication:** Every request is verified via JWT (No server-side sessions), Uses JWT to eliminate the need for server-side sessions.
* **Spring Security 6:** Modern security configuration using `SecurityFilterChain`.
* **Custom Security Filter**: Implements a `OncePerRequestFilter` to validate tokens on every request.
* **Password Security:** Industry-standard **BCrypt** hashing.
* **DTO Pattern:** Utilizes **ModelMapper** to separate internal Entities from external API responses. Clean data separation using **ModelMapper** and **Lombok**.
* **Database**: Persistence layer handled by **Spring Data JPA** and **MySQL**.
* **Modern JJWT Implementation**: Uses version **0.12.6** for enhanced security and fluent API
---

## 🏗️ Architecture Flow
1. **Authentication:** User sends credentials to `/api/auth/login`.
2. **Token Generation:** Server validates credentials and returns a signed JWT.
3. **Authorization:** For protected routes, the client sends the JWT in the `Authorization: Bearer <token>` header.
4. **Validation:** `JwtAuthenticationFilter` intercepts the request and validates the token.



---

## 🛠️ Technology Stack
1. Java 17+
2. Spring Boot 3.2.x
3. Spring Security
4. Spring Data JPA
5. MySQL
6. Lombok
7. ModelMapper
8. JJWT (Java JWT)

---


## 📋 API Endpoints

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/users/create` | Public | Register a new user |
| `POST` | `/api/auth/login` | Public | Generate JWT Token |
| `GET` | `/api/users/getAll` | Private | Retrieve all users |

---

## 🧠 Technical Challenges & Solutions

### 1. The 403 Forbidden Hurdle
Valid tokens were rejected because the `User` entity returned `null` for authorities. I updated `getAuthorities()` to return `List.of()`, satisfying Spring's requirement for a non-null authority collection.

### 2. ModelMapper "Empty Object" Bug
The API returned `null` values for DTOs. This was solved by adding `@NoArgsConstructor` and `@Setter` to the DTO classes, allowing ModelMapper to instantiate and populate objects via reflection.

### 3. JWT Library Versioning (0.11.x vs 0.12.x)
Encountered compilation errors when using modern JWT builder syntax with older library versions. This was resolved by upgrading jjwt version to 0.12.6, which provides a more secure and fluent API, moving away from the deprecated signWith(SignatureAlgorithm, String) approach to the modern signWith(SecretKey) standard.

---

## 🧪 Postman Testing Flow
1. **Register:** `POST` to `/api/users/create`.
2. **Login:** `POST` to `/api/auth/login` (Copy the `token`).
3. **Test Auth:** `GET` to `/api/users/getAll`. In the **Auth** tab, select **Bearer Token** and paste your code.

---

## 🐳 Docker Integration & Deployment

This project is fully containerized to ensure that the Spring Boot API and the MySQL Database work together seamlessly without requiring local software installations.



---

### 🧠 Docker Challenges & Solutions

### 1. The Persistence Trap (Access Denied)
MySQL initializes the root password **only** during the first volume creation. If you change the password in the YAML file later, the container ignores it because it reuses the old volume data.

**Solution:** Used `docker-compose down -v` to delete the old **Docker Volume**, forcing MySQL to recreate the database with the updated credentials.

### 2. The Connection Race Condition
Spring Boot often attempts to connect to the database before the MySQL service is fully "ready," leading to immediate application crashes during bootup.

**Solution:** Implemented a **Healthcheck** in the `docker-compose.yml` so the Java container waits for a successful MySQL "ping" before starting.

### 3. Service Discovery Networking
Inside the Docker network, `localhost` refers to the container itself, not the host machine, causing the app to fail to find the database.

**Solution:** Updated the JDBC URL to use the service name: `jdbc:mysql://mysqldb:3306/JWTPractice`.

---

### 🚀 Standard Build Workflow

To ensure your latest code changes are reflected and the database is fresh, follow this exact sequence:

1. **Package JAR:** `mvn clean package -DskipTests`
2. **Purge Environment:** `docker-compose down -v`
3. **Build & Launch:** `docker-compose up --build`



---

### 🛠️ Maintenance Commands

| Task | Command |
| :--- | :--- |
| **View App Logs** | `docker logs -f jwtbearertoken-java-app-1` |
| **Check Health** | `docker ps` |
| **Restart App** | `docker-compose restart java-app` |
D---

## 🐳 Docker Integration & Deployment

This project is fully containerized to ensure that the Spring Boot API and the MySQL Database work together seamlessly without requiring local software installations.



---

### 🧠 Docker Challenges & Solutions

### 1. The Persistence Trap (Access Denied)
MySQL initializes the root password **only** during the first volume creation. If you change the password in the YAML file later, the container ignores it because it reuses the old volume data.

**Solution:** Used `docker-compose down -v` to delete the old **Docker Volume**, forcing MySQL to recreate the database with the updated credentials.

### 2. The Connection Race Condition
Spring Boot often attempts to connect to the database before the MySQL service is fully "ready," leading to immediate application crashes during bootup.

**Solution:** Implemented a **Healthcheck** in the `docker-compose.yml` so the Java container waits for a successful MySQL "ping" before starting.

### 3. Service Discovery Networking
Inside the Docker network, `localhost` refers to the container itself, not the host machine, causing the app to fail to find the database.

**Solution:** Updated the JDBC URL to use the service name: `jdbc:mysql://mysqldb:3306/JWTPractice`.

---

## 🏗️ Architecture Details

---

| Service  |  Internal Port  |  External Port  |  Role
|:--- | :--- | :--- | :---
| **java-app** |  8083  | 8083  |  Spring Boot API
| **mysqldb**  |3306 | 3307 | MySql 8.0 Database 
### 🚀 Standard Build Workflow

To ensure your latest code changes are reflected and the database is fresh, follow this exact sequence:

1. **Package JAR:** `mvn clean package -DskipTests`
2. **Purge Environment:** `docker-compose down -v`
3. **Build & Launch:** `docker-compose up --build`



---

### 🛠️ Maintenance Commands

| Task | Command |
| :--- | :--- |
| **View App Logs** | `docker logs -f jwtbearertoken-java-app-1` |
| **Check Health** | `docker ps` |
| **Restart App** | `docker-compose restart java-app` |


