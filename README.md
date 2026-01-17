# Personal Task Management System

A full-stack web application for managing personal tasks with secure user authentication. Built with React.js (Frontend), Spring Boot (Backend), and MySQL (Database).

## 🎯 Project Overview

This application allows users to:
- **Register** and **Login** securely
- **Create** new tasks with title, description, and status
- **View** all their tasks in a beautiful interface
- **Edit** existing tasks
- **Delete** tasks
- **Filter** tasks by status (Pending, In Progress, Completed)

## 🏗️ Architecture

### **Frontend (React.js)**
- **Technology**: React 18.2.0
- **Routing**: React Router DOM
- **HTTP Client**: Axios
- **Styling**: CSS3 with responsive design

### **Backend (Spring Boot)**
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Security**: Spring Security with JWT (JSON Web Tokens)
- **Database**: MySQL (with H2 for development)
- **ORM**: Spring Data JPA / Hibernate

### **Database**
- **MySQL**: Production database
- **Tables**: `users`, `tasks`
- **Relationships**: One-to-Many (User → Tasks)

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version`

2. **Maven 3.6+**
   - Download from: https://maven.apache.org/download.cgi
   - Verify: `mvn -version`

3. **MySQL Server 8.0+**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Create a database named `taskdb` (or update `application.properties`)

4. **Node.js 16+ and npm**
   - Download from: https://nodejs.org/
   - Verify: `node -v` and `npm -v`

5. **Git** (for cloning the repository)

## 🚀 Setup Instructions

### Step 1: Clone the Repository

```bash
git clone <your-repository-url>
cd "Personal Task Management System"
```

### Step 2: Database Setup

1. **Start MySQL Server**

2. **Create Database** (optional - Spring Boot will create it automatically)
   ```sql
   CREATE DATABASE taskdb;
   ```

3. **Update Database Credentials**
   
   Edit `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   ```

### Step 3: Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the Spring Boot application**
   ```bash
   mvn spring-boot:run
   ```
   
   Or run the main class: `TaskManagementApplication.java`

4. **Verify Backend is Running**
   - Open browser: http://localhost:8080
   - You should see a Whitelabel Error Page (this is normal - no root endpoint)
   - Check console for: `✅ Task Management System is running on http://localhost:8080`

### Step 4: Frontend Setup

1. **Open a new terminal** (keep backend running)

2. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

3. **Install dependencies**
   ```bash
   npm install
   ```

4. **Start the React development server**
   ```bash
   npm start
   ```

5. **Verify Frontend is Running**
   - Browser should automatically open: http://localhost:3000
   - You should see the Login page

### Step 5: Test the Application

1. **Register a New User**
   - Click "Register here" on the login page
   - Fill in: Name, Email, Password (min 6 characters)
   - Click "Register"

2. **Create Tasks**
   - After registration/login, you'll see the task management page
   - Fill in task form: Title (required), Description (optional), Status
   - Click "Create Task"

3. **Manage Tasks**
   - Click "Edit" to modify a task
   - Click "Delete" to remove a task
   - Use the status filter to view specific tasks

## 📁 Project Structure

```
Personal Task Management System/
│
├── backend/                          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/ardentix/taskmanagement/
│   │   │   │       ├── TaskManagementApplication.java
│   │   │   │       ├── controller/   # REST API Controllers
│   │   │   │       │   ├── AuthController.java
│   │   │   │       │   └── TaskController.java
│   │   │   │       ├── service/      # Business Logic
│   │   │   │       │   ├── AuthService.java
│   │   │   │       │   └── TaskService.java
│   │   │   │       ├── repository/   # Data Access Layer
│   │   │   │       │   ├── UserRepository.java
│   │   │   │       │   └── TaskRepository.java
│   │   │   │       ├── entity/       # Database Entities
│   │   │   │       │   ├── User.java
│   │   │   │       │   └── Task.java
│   │   │   │       ├── dto/          # Data Transfer Objects
│   │   │   │       │   ├── RegisterRequest.java
│   │   │   │       │   ├── LoginRequest.java
│   │   │   │       │   ├── AuthResponse.java
│   │   │   │       │   ├── TaskRequest.java
│   │   │   │       │   └── TaskResponse.java
│   │   │   │       ├── security/     # Security Configuration
│   │   │   │       │   ├── SecurityConfig.java
│   │   │   │       │   └── JwtAuthenticationFilter.java
│   │   │   │       └── util/         # Utility Classes
│   │   │   │           ├── JwtUtil.java
│   │   │   │           └── SecurityUtil.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/                     # Test files
│   └── pom.xml                       # Maven dependencies
│
├── frontend/                         # React Frontend
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── components/              # React Components
│   │   │   ├── Login.js
│   │   │   ├── Register.js
│   │   │   └── TaskList.js
│   │   ├── services/                # API Service Layer
│   │   │   └── api.js
│   │   ├── App.js                   # Main App Component
│   │   ├── App.css
│   │   ├── index.js                # Entry Point
│   │   └── index.css               # Global Styles
│   └── package.json                 # npm dependencies
│
└── README.md                        # This file
```

## 🔐 Authentication Flow

### **Registration Flow**
1. User fills registration form (name, email, password)
2. Frontend sends POST request to `/api/auth/register`
3. Backend validates input, checks if email exists
4. Password is encrypted using BCrypt
5. User is saved to database
6. JWT token is generated and returned
7. Frontend stores token in localStorage
8. User is redirected to task management page

### **Login Flow**
1. User enters email and password
2. Frontend sends POST request to `/api/auth/login`
3. Backend finds user by email
4. Password is verified using BCrypt
5. JWT token is generated and returned
6. Frontend stores token in localStorage
7. User is redirected to task management page

### **Protected Routes**
- All task endpoints require authentication
- JWT token is sent in `Authorization: Bearer <token>` header
- Backend validates token on each request
- If token is invalid/expired, user is redirected to login

## 🔑 Key Concepts Explained

### **1. Spring Boot Architecture**

**Layered Architecture:**
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Database operations
- **Entity Layer**: Database table mappings

**Benefits:**
- Separation of concerns
- Easy to test and maintain
- Follows SOLID principles

### **2. JWT (JSON Web Token) Authentication**

**What is JWT?**
- Stateless authentication mechanism
- Token contains user information (email, ID)
- Signed with secret key to prevent tampering
- No need for server-side sessions

**Token Structure:**
```
header.payload.signature
```

**Advantages:**
- Scalable (no session storage needed)
- Works across multiple servers
- Mobile-friendly

### **3. React Component Lifecycle**

**useState Hook:**
- Manages component state (data that changes)
- Triggers re-render when state updates
- Example: `const [count, setCount] = useState(0)`

**useEffect Hook:**
- Runs code after component renders
- Used for API calls, subscriptions, etc.
- Example: `useEffect(() => { fetchData() }, [])`

**Controlled Components:**
- Form inputs controlled by React state
- Value and onChange handler required
- React controls the input value

### **4. REST API Design**

**HTTP Methods:**
- `GET`: Retrieve data
- `POST`: Create new resource
- `PUT`: Update existing resource
- `DELETE`: Remove resource

**Status Codes:**
- `200 OK`: Success
- `201 Created`: Resource created
- `400 Bad Request`: Invalid input
- `401 Unauthorized`: Authentication required
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

### **5. Database Relationships**

**One-to-Many (User → Tasks):**
- One user can have many tasks
- Each task belongs to one user
- Foreign key: `user_id` in `tasks` table

**JPA Annotations:**
- `@OneToMany`: User side of relationship
- `@ManyToOne`: Task side of relationship
- `@JoinColumn`: Foreign key column name

## 🛠️ API Endpoints

### **Authentication Endpoints**

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login user | No |

### **Task Endpoints**

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/tasks` | Get all tasks | Yes |
| GET | `/api/tasks/{id}` | Get task by ID | Yes |
| POST | `/api/tasks` | Create new task | Yes |
| PUT | `/api/tasks/{id}` | Update task | Yes |
| DELETE | `/api/tasks/{id}` | Delete task | Yes |

## 🧪 Testing the API

### Using cURL

**Register User:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

**Create Task (replace TOKEN with actual token):**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"title":"Complete assignment","description":"Finish the task management project","status":"PENDING"}'
```

**Get All Tasks:**
```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer TOKEN"
```

## 🐛 Troubleshooting

### **Backend Issues**

**Port 8080 already in use:**
- Change port in `application.properties`: `server.port=8081`
- Update frontend API base URL accordingly

**Database connection error:**
- Verify MySQL is running
- Check username/password in `application.properties`
- Ensure database `taskdb` exists (or let Spring Boot create it)

**Maven build fails:**
- Ensure JDK 17+ is installed
- Check internet connection (Maven downloads dependencies)
- Try: `mvn clean install -U`

### **Frontend Issues**

**Port 3000 already in use:**
- React will prompt to use another port (e.g., 3001)
- Or set port: `set PORT=3001 && npm start` (Windows)

**API connection error:**
- Ensure backend is running on http://localhost:8080
- Check CORS configuration in `SecurityConfig.java`
- Verify API base URL in `frontend/src/services/api.js`

**npm install fails:**
- Delete `node_modules` and `package-lock.json`
- Run `npm install` again
- Check Node.js version (16+)

## 📚 Learning Resources

### **Spring Boot**
- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Spring Security Guide](https://spring.io/guides/gs/securing-web/)

### **React**
- [React Official Documentation](https://react.dev/)
- [React Router Documentation](https://reactrouter.com/)
- [Axios Documentation](https://axios-http.com/)

### **JWT**
- [JWT.io](https://jwt.io/) - JWT debugger and information
- [Introduction to JWT](https://jwt.io/introduction)

### **MySQL**
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [SQL Tutorial](https://www.w3schools.com/sql/)

## 🎓 What You've Learned

By completing this project, you've learned:

1. **Full-Stack Development**
   - Frontend (React) and Backend (Spring Boot) integration
   - RESTful API design and consumption

2. **Authentication & Security**
   - JWT token-based authentication
   - Password encryption (BCrypt)
   - Protected routes and API endpoints

3. **Database Management**
   - Entity-Relationship modeling
   - JPA/Hibernate ORM
   - SQL database operations

4. **React Concepts**
   - Component-based architecture
   - State management (useState)
   - Side effects (useEffect)
   - Routing and navigation

5. **Spring Boot Concepts**
   - Dependency Injection
   - Layered architecture
   - REST controllers
   - Service layer pattern

6. **Best Practices**
   - Code organization and structure
   - Error handling
   - Input validation
   - Responsive UI design

## 🚀 Deployment (Optional)

### **Backend Deployment**
- Deploy to platforms like: Heroku, AWS, Azure, Google Cloud
- Update database configuration for production
- Set environment variables for sensitive data (JWT secret, DB credentials)

### **Frontend Deployment**
- Build production version: `npm run build`
- Deploy to: Netlify, Vercel, GitHub Pages, AWS S3
- Update API base URL to production backend URL

## 📝 License

This project is created for educational purposes as part of the Ardentix internship selection process.

## 👨‍💻 Author

Developed as part of the Ardentix internship assignment.

---

**Happy Coding! 🎉**

If you have any questions or need help, feel free to reach out!

