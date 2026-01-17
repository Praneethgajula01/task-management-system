# Learning Guide - Key Concepts Explained

This guide explains the important concepts you've learned while building this project.

## 🎯 Table of Contents

1. [Spring Boot Architecture](#spring-boot-architecture)
2. [JWT Authentication](#jwt-authentication)
3. [React Fundamentals](#react-fundamentals)
4. [Database Design](#database-design)
5. [REST API Design](#rest-api-design)

---

## Spring Boot Architecture

### What is Spring Boot?

Spring Boot is a Java framework that simplifies Spring application development. It provides:
- **Auto-configuration**: Automatically configures your application
- **Embedded Server**: Runs on Tomcat (no need for external server)
- **Starter Dependencies**: Pre-configured dependency sets

### Layered Architecture

```
┌─────────────────────────────────┐
│     Controller Layer            │  ← Handles HTTP requests
│  (AuthController, TaskController)│
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│      Service Layer              │  ← Business logic
│  (AuthService, TaskService)     │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│    Repository Layer              │  ← Database operations
│  (UserRepository, TaskRepository)│
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│      Entity Layer               │  ← Database tables
│      (User, Task)               │
└─────────────────────────────────┘
```

**Why This Structure?**
- **Separation of Concerns**: Each layer has a specific responsibility
- **Testability**: Easy to test each layer independently
- **Maintainability**: Changes in one layer don't affect others
- **Reusability**: Services can be reused by multiple controllers

### Key Annotations

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@SpringBootApplication` | Marks main application class | `TaskManagementApplication.java` |
| `@RestController` | Creates REST API controller | `AuthController.java` |
| `@Service` | Marks business logic class | `AuthService.java` |
| `@Repository` | Marks data access interface | `UserRepository.java` |
| `@Entity` | Maps class to database table | `User.java` |
| `@Autowired` | Injects dependencies | Constructor injection |
| `@RequestMapping` | Maps URL to method | `@RequestMapping("/api/auth")` |
| `@GetMapping` | Handles GET requests | `@GetMapping("/tasks")` |
| `@PostMapping` | Handles POST requests | `@PostMapping("/register")` |

---

## JWT Authentication

### What is JWT?

**JWT (JSON Web Token)** is a stateless authentication mechanism.

**Token Structure:**
```
header.payload.signature
```

**Example:**
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwidXNlcklkIjoxLCJpYXQiOjE2MDAwMDAwMDAsImV4cCI6MTYwMDA4NjQwMH0.signature
```

### How JWT Works

1. **User Logs In**
   - User sends email/password
   - Server validates credentials
   - Server generates JWT token (contains user info)
   - Token sent to client

2. **Client Stores Token**
   - Frontend stores token in localStorage
   - Token sent with every API request

3. **Server Validates Token**
   - Server extracts token from request header
   - Verifies signature (ensures token not tampered)
   - Checks expiration
   - Extracts user info from token

### Advantages of JWT

✅ **Stateless**: No server-side session storage  
✅ **Scalable**: Works across multiple servers  
✅ **Mobile-Friendly**: Easy to use in mobile apps  
✅ **Secure**: Signed with secret key  

### Security Features

- **Password Encryption**: BCrypt (one-way hashing)
- **Token Expiration**: Tokens expire after 24 hours
- **Signature Verification**: Prevents token tampering
- **HTTPS**: Should be used in production

---

## React Fundamentals

### Component-Based Architecture

React applications are built using **components** - reusable pieces of UI.

**Example:**
```javascript
function TaskCard({ task }) {
  return (
    <div className="task-card">
      <h3>{task.title}</h3>
      <p>{task.description}</p>
    </div>
  );
}
```

### React Hooks

**useState** - Manages component state:
```javascript
const [count, setCount] = useState(0);
// count is the value
// setCount is the function to update it
```

**useEffect** - Runs code after render:
```javascript
useEffect(() => {
  fetchTasks(); // Runs after component mounts
}, []); // Empty array = run once
```

### Component Lifecycle

```
Mount → Update → Unmount
  ↓       ↓        ↓
Render  Re-render  Cleanup
```

### Props vs State

| Props | State |
|-------|-------|
| Passed from parent | Managed within component |
| Immutable (read-only) | Mutable (can change) |
| Used for configuration | Used for dynamic data |

### Event Handling

```javascript
const handleClick = () => {
  console.log('Button clicked!');
};

<button onClick={handleClick}>Click Me</button>
```

### Controlled Components

Form inputs controlled by React state:
```javascript
const [email, setEmail] = useState('');

<input 
  value={email} 
  onChange={(e) => setEmail(e.target.value)} 
/>
```

---

## Database Design

### Entity-Relationship Model

**User Entity:**
- id (Primary Key)
- email (Unique)
- password (Encrypted)
- name

**Task Entity:**
- id (Primary Key)
- title
- description
- status (Enum: PENDING, IN_PROGRESS, COMPLETED)
- user_id (Foreign Key → User)
- createdAt
- updatedAt

### Relationships

**One-to-Many (User → Tasks)**
- One user can have many tasks
- Each task belongs to one user
- Implemented using `@OneToMany` and `@ManyToOne`

### JPA Annotations

| Annotation | Purpose |
|------------|---------|
| `@Entity` | Marks class as database table |
| `@Table` | Specifies table name |
| `@Id` | Marks primary key |
| `@GeneratedValue` | Auto-generates ID |
| `@Column` | Maps to column |
| `@OneToMany` | One-to-many relationship |
| `@ManyToOne` | Many-to-one relationship |
| `@JoinColumn` | Foreign key column |

### Database Operations

**CRUD Operations:**
- **Create**: `repository.save(entity)`
- **Read**: `repository.findById(id)`
- **Update**: `repository.save(entity)` (with existing ID)
- **Delete**: `repository.delete(entity)`

---

## REST API Design

### HTTP Methods

| Method | Purpose | Example |
|--------|---------|---------|
| GET | Retrieve data | Get all tasks |
| POST | Create resource | Create new task |
| PUT | Update resource | Update task |
| DELETE | Remove resource | Delete task |

### HTTP Status Codes

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful GET/PUT |
| 201 | Created | Successful POST |
| 400 | Bad Request | Invalid input |
| 401 | Unauthorized | Not authenticated |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Internal error |

### RESTful URL Design

```
GET    /api/tasks          → Get all tasks
GET    /api/tasks/1        → Get task with ID 1
POST   /api/tasks          → Create new task
PUT    /api/tasks/1        → Update task with ID 1
DELETE /api/tasks/1        → Delete task with ID 1
```

### Request/Response Format

**Request (POST /api/tasks):**
```json
{
  "title": "Complete assignment",
  "description": "Finish the project",
  "status": "PENDING"
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Complete assignment",
  "description": "Finish the project",
  "status": "PENDING",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

---

## Key Takeaways

### Backend (Spring Boot)
1. **Layered Architecture** separates concerns
2. **JWT** provides stateless authentication
3. **JPA** simplifies database operations
4. **REST APIs** follow standard conventions

### Frontend (React)
1. **Components** are reusable UI pieces
2. **Hooks** manage state and side effects
3. **Props** pass data between components
4. **Axios** simplifies API calls

### Full-Stack Integration
1. **CORS** enables cross-origin requests
2. **JWT tokens** authenticate API calls
3. **Error handling** provides user feedback
4. **Validation** ensures data integrity

---

## Next Steps to Learn

1. **Testing**: Write unit tests and integration tests
2. **Error Handling**: Implement global error handling
3. **Pagination**: Add pagination for large task lists
4. **Search**: Implement task search functionality
5. **Deployment**: Deploy to cloud platforms (AWS, Heroku)
6. **Docker**: Containerize the application
7. **CI/CD**: Set up continuous integration/deployment

---

## Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev/)
- [JWT.io](https://jwt.io/)
- [REST API Tutorial](https://restfulapi.net/)

---

**Happy Learning! 🚀**

