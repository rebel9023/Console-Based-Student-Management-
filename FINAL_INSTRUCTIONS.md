# Student Management System - Final Instructions

Congratulation! Your Student Management System is now fully configured and ready to use. 
This version supports both **Database Persistence** (MySQL) and **In-Memory** modes with automatic fallback.

## 🚀 How to Run

We have created simplified scripts to make running the application easy.

### 1. Build the Project
If you make any changes or want to rebuild the project (this will download necessary dependencies):

```cmd
.\build-with-deps.cmd
```

### 2. Run the Application
To start the application:

```cmd
.\run.cmd
```

---

## 💾 Database Configuration

The application is configured to use a MySQL database.

**Current Settings:**
- **URL:** `jdbc:mysql://localhost:3306/student_management_system`
- **Username:** `root`
- **Password:** `root` (or empty)

### Changing Credentials
If your MySQL password is different, edit `src/main/resources/application.properties`:

```properties
spring.datasource.password=YOUR_PASSWORD
```

### Automatic Fallback
If the application cannot connect to the database (e.g., MySQL is not running or password is wrong), it will **automatically switch to In-Memory mode**. 
- You will see a warning message on startup.
- The application will work normally, but data will be lost when you close it.

---

## 🛠️ Folder Structure
- `src/` - Source code
- `target/` - Compiled output and JAR file
- `logs/` - Application logs
- `database/` - SQL schema scripts

## 🆘 Troubleshooting
- **Build Fails:** Ensure you have internet access to download dependencies (first time only).
- **Access Denied:** Check `application.properties` and update the password.
- **Port In Use:** If port 8080 is taken, the console app will still run fine (it doesn't use the web port).

Enjoy your Student Management System!
