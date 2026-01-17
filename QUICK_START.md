# Quick Start Guide

Get the application running in 5 minutes!

## Prerequisites Check

```bash
# Check Java version (need 17+)
java -version

# Check Maven
mvn -version

# Check Node.js (need 16+)
node -v
npm -v
```

## Step-by-Step Setup

### 1. Database Setup (30 seconds)

1. Start MySQL Server
2. Create database (optional - Spring Boot will auto-create):
   ```sql
   CREATE DATABASE taskdb;
   ```
3. Update credentials in `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

### 2. Backend Setup (2 minutes)

```bash
# Navigate to backend
cd backend

# Build and run
mvn spring-boot:run
```

Wait for: `✅ Task Management System is running on http://localhost:8080`

### 3. Frontend Setup (2 minutes)

**Open a NEW terminal window:**

```bash
# Navigate to frontend
cd frontend

# Install dependencies (first time only)
npm install

# Start React app
npm start
```

Browser will open at http://localhost:3000

### 4. Test It! (30 seconds)

1. Click "Register here"
2. Create an account
3. Create your first task!

## Troubleshooting

**Backend won't start?**
- Check MySQL is running
- Verify database credentials
- Check port 8080 is free

**Frontend won't start?**
- Check Node.js is installed
- Run `npm install` again
- Check port 3000 is free

**Can't connect to API?**
- Ensure backend is running on port 8080
- Check browser console for errors

## That's It! 🎉

You now have a fully functional task management system!

For detailed documentation, see [README.md](README.md)

