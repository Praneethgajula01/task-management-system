cd backend
mvn spring-boot:run# 🚀 How to Run the Application - Step by Step

## ⚠️ Important: Fix Lombok Issue First

If you see compilation errors about missing getters/setters, we need to fix Lombok. Here are two solutions:

### Solution 1: Use IDE (Recommended)
1. Open the project in **IntelliJ IDEA** or **Eclipse**
2. Install Lombok plugin:
   - **IntelliJ**: Settings → Plugins → Search "Lombok" → Install
   - **Eclipse**: Help → Eclipse Marketplace → Search "Lombok" → Install
3. Enable annotation processing:
   - **IntelliJ**: Settings → Build → Compiler → Annotation Processors → Enable
4. Right-click `backend/pom.xml` → Maven → Reload Project
5. Build the project

### Solution 2: Manual Fix (If IDE doesn't work)
If Lombok still doesn't work, we can manually add getters/setters, but this is not ideal.

---

## 📋 Step-by-Step Running Instructions

### **Step 1: Start MySQL Database**

**Option A: Using XAMPP**
1. Open XAMPP Control Panel
2. Click "Start" next to MySQL
3. Wait until it shows "Running" (green)

**Option B: Using MySQL Workbench**
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Create database (optional - Spring Boot will create it):
   ```sql
   CREATE DATABASE taskdb;
   ```

**Option C: Using Command Line**
1. Open Command Prompt as Administrator
2. Navigate to MySQL bin folder (usually `C:\Program Files\MySQL\MySQL Server 8.0\bin`)
3. Run: `mysql -u root -p`
4. Enter your password
5. Run: `CREATE DATABASE taskdb;`

### **Step 2: Update Database Credentials**

1. Open: `backend/src/main/resources/application.properties`
2. Update these lines with YOUR MySQL credentials:
   ```properties
   spring.datasource.username=root        # Your MySQL username
   spring.datasource.password=YOUR_PASSWORD  # Your MySQL password
   ```
3. Save the file

### **Step 3: Start Backend (Spring Boot)**

**Method 1: Using Batch File (Easiest)**
1. Double-click `RUN_BACKEND.bat`
2. Wait for: `✅ Task Management System is running on http://localhost:8080`
3. **Keep this window open!**

**Method 2: Using Command Line**
1. Open PowerShell
2. Navigate to project:
   ```powershell
   cd "C:\Users\PRANEETH\Personal Task Management System\backend"
   ```
3. Run:
   ```powershell
   mvn spring-boot:run
   ```
4. Wait for success message
5. **Keep this window open!**

**What to expect:**
- First time: Downloads dependencies (takes 2-5 minutes)
- You'll see: `Started TaskManagementApplication`
- If you see errors, check:
  - MySQL is running
  - Database credentials are correct
  - Port 8080 is not in use

### **Step 4: Start Frontend (React)**

**Open a NEW PowerShell window** (keep backend running!)

**Method 1: Using Batch File**
1. Double-click `RUN_FRONTEND.bat`
2. Wait for browser to open automatically

**Method 2: Using Command Line**
1. Open **NEW** PowerShell window
2. Navigate to project:
   ```powershell
   cd "C:\Users\PRANEETH\Personal Task Management System\frontend"
   ```
3. Install dependencies (first time only):
   ```powershell
   npm install
   ```
4. Start React app:
   ```powershell
   npm start
   ```
5. Browser should open automatically at `http://localhost:3000`

**What to expect:**
- First time: Downloads packages (takes 1-2 minutes)
- Browser opens automatically
- You see the Login page

### **Step 5: Test the Application**

1. **Register a new user:**
   - Click "Register here"
   - Fill in: Name, Email, Password (min 6 chars)
   - Click "Register"
   - Should redirect to Tasks page

2. **Create a task:**
   - Fill in: Title (required), Description (optional), Status
   - Click "Create Task"
   - Task appears in the list

3. **Test other features:**
   - Edit a task
   - Change status
   - Delete a task
   - Filter by status
   - Logout and login again

---

## 🐛 Troubleshooting

### **Backend Won't Start**

**Error: "Port 8080 already in use"**
- Solution: Change port in `application.properties`:
  ```properties
  server.port=8081
  ```
- Update frontend API URL in `frontend/src/services/api.js`:
  ```javascript
  baseURL: 'http://localhost:8081/api'
  ```

**Error: "Cannot connect to database"**
- Check MySQL is running
- Verify username/password in `application.properties`
- Try creating database manually:
  ```sql
  CREATE DATABASE taskdb;
  ```

**Error: "Compilation failure" (Lombok errors)**
- See "Fix Lombok Issue" section above
- Or use an IDE with Lombok plugin

### **Frontend Won't Start**

**Error: "Port 3000 already in use"**
- React will ask to use another port (e.g., 3001)
- Click "Y" to accept
- Or set manually:
  ```powershell
  $env:PORT=3001; npm start
  ```

**Error: "Cannot find module"**
- Delete `node_modules` folder
- Delete `package-lock.json`
- Run `npm install` again

**Error: "Network Error" or "Cannot connect to API"**
- Check backend is running on port 8080
- Check browser console (F12) for errors
- Verify CORS settings in `SecurityConfig.java`

### **Database Issues**

**Tables not created:**
- Check `application.properties` has:
  ```properties
  spring.jpa.hibernate.ddl-auto=update
  ```
- Check MySQL is running
- Check database credentials

---

## ✅ Success Checklist

- [ ] MySQL is running
- [ ] Database credentials updated in `application.properties`
- [ ] Backend started successfully (port 8080)
- [ ] Frontend started successfully (port 3000)
- [ ] Browser opened to Login page
- [ ] Can register a new user
- [ ] Can login
- [ ] Can create tasks
- [ ] Can edit/delete tasks

---

## 🎯 Quick Commands Reference

**Start Backend:**
```powershell
cd backend
mvn spring-boot:run
```

**Start Frontend:**
```powershell
cd frontend
npm install  # First time only
npm start
```

**Check if ports are in use:**
```powershell
netstat -ano | findstr :8080  # Backend
netstat -ano | findstr :3000   # Frontend
```

---

## 📞 Need Help?

If you're stuck:
1. Check the error message carefully
2. Verify MySQL is running
3. Check database credentials
4. Make sure both servers are running
5. Check browser console (F12) for frontend errors

**Common Issues:**
- Backend not running → Frontend shows "Network Error"
- Wrong database password → Backend won't start
- Port already in use → Change port number
- Lombok errors → Use IDE with Lombok plugin

---

**Good Luck! 🚀**

