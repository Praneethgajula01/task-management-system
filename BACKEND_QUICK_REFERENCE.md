# 🚀 Backend Quick Reference Guide

Quick lookup for common concepts and code patterns.

---

## 📋 File Structure

```
backend/
├── pom.xml                                    ← Dependencies
├── src/main/
│   ├── java/com/ardentix/taskmanagement/
│   │   ├── TaskManagementApplication.java    ← Main class
│   │   ├── entity/                           ← Database tables
│   │   │   ├── User.java
│   │   │   └── Task.java
│   │   ├── dto/                              ← API data objects
│   │   │   ├── RegisterRequest.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── TaskRequest.java
│   │   │   └── TaskResponse.java
│   │   ├── repository/                       ← Database access
│   │   │   ├── UserRepository.java
│   │   │   └── TaskRepository.java
│   │   ├── service/                          ← Business logic
│   │   │   ├── AuthService.java
│   │   │   └── TaskService.java
│   │   ├── controller/                       ← API endpoints
│   │   │   ├── AuthController.java
│   │   │   └── TaskController.java
│   │   ├── security/                         ← Authentication
│   │   │   ├── SecurityConfig.java
│   │   │   └── JwtAuthenticationFilter.java
│   │   └── util/                             ← Helper classes
│   │       ├── JwtUtil.java
│   │       └── SecurityUtil.java
│   └── resources/
│       └── application.properties            ← Configuration
```

---

## 🔑 Key Annotations Cheat Sheet

### Class-Level Annotations

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Entity` | Maps class to database table | `@Entity public class User` |
| `@Service` | Marks as business logic class | `@Service public class AuthService` |
| `@Repository` | Marks as data access class | `@Repository public interface UserRepository` |
| `@RestController` | Marks as API controller | `@RestController public class AuthController` |
| `@Component` | Generic Spring component | `@Component public class JwtUtil` |
| `@Configuration` | Configuration class | `@Configuration public class SecurityConfig` |

### Method-Level Annotations

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@GetMapping` | Handles GET requests | `@GetMapping("/tasks")` |
| `@PostMapping` | Handles POST requests | `@PostMapping("/register")` |
| `@PutMapping` | Handles PUT requests | `@PutMapping("/tasks/{id}")` |
| `@DeleteMapping` | Handles DELETE requests | `@DeleteMapping("/tasks/{id}")` |
| `@RequestMapping` | Base URL for controller | `@RequestMapping("/api/auth")` |
| `@PathVariable` | Extracts URL parameter | `@PathVariable Long id` |
| `@RequestBody` | Converts JSON to object | `@RequestBody RegisterRequest request` |
| `@Valid` | Triggers validation | `@Valid @RequestBody RegisterRequest` |

### Field-Level Annotations

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Id` | Primary key | `@Id private Long id` |
| `@GeneratedValue` | Auto-increment ID | `@GeneratedValue(strategy = IDENTITY)` |
| `@Column` | Column configuration | `@Column(unique = true, nullable = false)` |
| `@OneToMany` | One-to-many relationship | `@OneToMany(mappedBy = "user")` |
| `@ManyToOne` | Many-to-one relationship | `@ManyToOne @JoinColumn(name = "user_id")` |
| `@NotBlank` | Validation: not empty | `@NotBlank private String name` |
| `@Email` | Validation: email format | `@Email private String email` |
| `@Size` | Validation: string length | `@Size(min = 6) private String password` |

---

## 🔄 Request Flow Diagram

```
┌─────────────┐
│   Frontend  │
│   (React)   │
└──────┬──────┘
       │ HTTP Request (JSON)
       │ Authorization: Bearer <token>
       ▼
┌─────────────────────────────────────┐
│      Controller Layer                │
│  - Receives HTTP request             │
│  - Validates input (@Valid)          │
│  - Calls Service                     │
│  - Returns HTTP response             │
└──────┬───────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│       Service Layer                  │
│  - Business logic                    │
│  - Validates business rules          │
│  - Calls Repository                  │
│  - Handles exceptions                │
└──────┬───────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│      Repository Layer                │
│  - Database operations              │
│  - Spring generates SQL             │
│  - Returns Entity objects           │
└──────┬───────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│      Database (MySQL)                │
│  - Stores data                       │
│  - Returns rows                      │
└─────────────────────────────────────┘
```

---

## 🔐 Authentication Flow

```
1. User registers/logs in
   ↓
2. AuthService validates credentials
   ↓
3. JwtUtil generates token
   ↓
4. Token returned to frontend
   ↓
5. Frontend stores token in localStorage
   ↓
6. Frontend sends token with every request:
   Authorization: Bearer <token>
   ↓
7. JwtAuthenticationFilter intercepts request
   ↓
8. Validates token
   ↓
9. Sets authentication in Security Context
   ↓
10. Controller can access current user via SecurityUtil
```

---

## 📝 Common Code Patterns

### Repository Pattern

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

**Spring generates SQL automatically:**
- `findByEmail` → `SELECT * FROM users WHERE email = ?`
- `existsByEmail` → `SELECT COUNT(*) > 0 FROM users WHERE email = ?`

### Service Pattern

```java
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AuthResponse register(RegisterRequest request) {
        // 1. Validate
        // 2. Process
        // 3. Save
        // 4. Return
    }
}
```

### Controller Pattern

```java
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        // Get data from service
        // Return ResponseEntity
    }
}
```

---

## 🗄️ Database Relationships

### One-to-Many (User → Tasks)

**User Entity:**
```java
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Task> tasks;
```

**Task Entity:**
```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

**Database:**
```
users table:
- id (PK)

tasks table:
- id (PK)
- user_id (FK → users.id)
```

---

## 🔧 Common Tasks

### Add a New Field to Entity

1. **Add field to Entity:**
```java
@Column(nullable = false)
private String newField;
```

2. **Add to DTO:**
```java
@NotBlank
private String newField;
```

3. **Update Service:**
```java
entity.setNewField(dto.getNewField());
```

4. **Database updates automatically** (if `ddl-auto=update`)

### Add a New Endpoint

1. **Add method to Controller:**
```java
@GetMapping("/new-endpoint")
public ResponseEntity<ResponseDTO> newEndpoint() {
    // Implementation
}
```

2. **Add method to Service:**
```java
public ResponseDTO newMethod() {
    // Business logic
}
```

### Add Validation

```java
@NotBlank(message = "Field is required")
@Size(min = 3, max = 50, message = "Must be 3-50 characters")
@Email(message = "Must be valid email")
private String field;
```

---

## 🐛 Common Errors & Solutions

### Error: "Cannot find symbol: method getXxx()"

**Cause:** Lombok not generating getters/setters

**Solution:**
1. Install Lombok plugin in IDE
2. Enable annotation processing
3. Rebuild project

### Error: "Port 8080 already in use"

**Solution:**
```properties
# application.properties
server.port=8081
```

### Error: "Cannot connect to database"

**Solution:**
1. Check MySQL is running
2. Verify credentials in `application.properties`
3. Check database exists

### Error: "401 Unauthorized"

**Cause:** Missing or invalid JWT token

**Solution:**
1. Login to get token
2. Include in header: `Authorization: Bearer <token>`
3. Check token hasn't expired

---

## 📚 HTTP Status Codes

| Code | Meaning | When to Use |
|------|---------|-------------|
| 200 | OK | Successful GET/PUT |
| 201 | Created | Successful POST (resource created) |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Invalid input/validation failed |
| 401 | Unauthorized | Not authenticated/invalid token |
| 403 | Forbidden | Authenticated but not authorized |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Internal server error |

---

## 🎯 Best Practices

1. **Always validate input** using `@Valid` and validation annotations
2. **Never expose entities directly** - use DTOs
3. **Always check user ownership** - prevent unauthorized access
4. **Encrypt passwords** - never store plain text
5. **Use meaningful error messages** - help users understand issues
6. **Log important events** - helps debugging
7. **Handle exceptions** - don't expose stack traces to users
8. **Use appropriate HTTP methods** - GET for read, POST for create, etc.
9. **Return appropriate status codes** - 200 for success, 404 for not found, etc.
10. **Keep services focused** - one responsibility per service

---

## 🚀 Quick Commands

### Build Project
```bash
mvn clean install
```

### Run Application
```bash
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

### Package Application
```bash
mvn package
```

### Check Dependencies
```bash
mvn dependency:tree
```

---

## 📖 Further Learning

1. **Spring Boot Documentation**: https://spring.io/projects/spring-boot
2. **Spring Data JPA**: https://spring.io/projects/spring-data-jpa
3. **Spring Security**: https://spring.io/projects/spring-security
4. **JWT.io**: https://jwt.io/ (JWT debugger and info)

---

**Keep this guide handy while coding! 📝**

