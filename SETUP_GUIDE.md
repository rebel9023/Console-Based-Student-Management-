# Student Management System - Setup Guide

## 🚀 Quick Setup (5 Minutes)

### Step 1: Verify Java Installation
```bash
java -version
```
**Expected Output:** `java version "17" or higher`

If not installed, download from [oracle.com/java](https://www.oracle.com/java/technologies/downloads/)

### Step 2: Verify Maven Installation
```bash
mvn -version
```
**Expected Output:** `Apache Maven 3.6.0 or higher`

If not installed, download from [maven.apache.org](https://maven.apache.org/download.cgi)

### Step 3: Navigate to Project
```bash
cd StudentManagementSystem
```

### Step 4: Build Project
```bash
mvn clean package
```
**Expected Result:** `BUILD SUCCESS`

### Step 5: Run Application
```bash
java -cp target/classes com.sms.App
```

🎉 **Success!** The console menu should appear.

---

## 📊 Phase 1 vs Phase 2

### Phase 1: In-Memory (Quickest Start ⚡)
- **No database needed**
- **Data lost on restart**
- **Perfect for testing/learning**
- Runs immediately after build

### Phase 2: MySQL Database (Persistent 💾)
- **Requires MySQL server**
- **Data persists permanently**
- **Production ready**
- Requires additional setup

---

## 🗄️ Setting Up Phase 2 (MySQL)

### Windows Users

#### 1. Install MySQL
```bash
# Download from mysql.com
# Or use Chocolatey
choco install mysql

# Verify installation
mysql --version
```

#### 2. Start MySQL Service
```bash
# Check if running
sc query MySQL80

# Start service
net start MySQL80

# Or use MySQL Workbench (GUI)
```

#### 3. Create Database
```bash
# Open Command Prompt and connect
mysql -u root -p

# Enter password (default: empty or 'root')

# Run schema
SOURCE C:\path\to\StudentManagementSystem\database\schema.sql;
```

#### 4. Configure Application
Edit: `src\main\resources\application.properties`
```properties
db.url=jdbc:mysql://localhost:3306/student_management_system
db.username=root
db.password=root
```

#### 5. Build and Run
```bash
mvn clean package
java -cp target/classes com.sms.App
```

---

### Linux Users (Ubuntu/Debian)

#### 1. Install MySQL
```bash
sudo apt-get update
sudo apt-get install mysql-server

# Verify
mysql --version
```

#### 2. Start MySQL
```bash
sudo service mysql start

# Check status
sudo service mysql status
```

#### 3. Create Database
```bash
# Connect to MySQL
sudo mysql -u root -p

# Or without sudo if configured differently
mysql -u root -p

# Run schema
SOURCE /path/to/StudentManagementSystem/database/schema.sql;
SHOW DATABASES; # Verify creation
```

#### 4. Configure User (If needed)
```bash
# Set password for root
sudo mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';"

# Flush privileges
sudo mysql -e "FLUSH PRIVILEGES;"
```

#### 5. Configure Application
```bash
# Edit configuration
nano src/main/resources/application.properties
```

Change:
```properties
db.url=jdbc:mysql://localhost:3306/student_management_system
db.username=root
db.password=root
```

#### 6. Build and Run
```bash
mvn clean package
java -cp target/classes com.sms.App
```

---

### macOS Users

#### 1. Install MySQL via Homebrew
```bash
# Install Homebrew if not present
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install MySQL
brew install mysql

# Verify
mysql --version
```

#### 2. Start MySQL
```bash
brew services start mysql

# Verify status
brew services list
```

#### 3. Initial Setup
```bash
# Run security script
mysql_secure_installation

# Set root password (recommended: 'root')
```

#### 4. Create Database
```bash
# Connect
mysql -u root -p

# Run schema
SOURCE /path/to/StudentManagementSystem/database/schema.sql;
SHOW DATABASES; # Verify
```

#### 5. Configure Application
```bash
# Edit file
nano src/main/resources/application.properties
```

Update:
```properties
db.url=jdbc:mysql://localhost:3306/student_management_system
db.username=root
db.password=your_password
```

#### 6. Build and Run
```bash
mvn clean package
java -cp target/classes com.sms.App
```

---

## 🧪 Testing Your Setup

### Verify Phase 1 (In-Memory)
```bash
# Should work immediately without database
mvn clean package
java -cp target/classes com.sms.App

# Try:
# 1. Add New Student (option 1)
# 2. View All Students (option 2)
# 3. Exit (option 0)
```

### Verify Phase 2 (Database)
```bash
# First verify MySQL is running
mysql -u root -p -e "SHOW DATABASES;"

# Should show: student_management_system

# Build with database config
mvn clean package

# Run application
java -cp target/classes com.sms.App

# Verify operations save to database
```

---

## 🔧 Troubleshooting

### Problem: "Java not found"
```bash
# Solution: Install Java 17+
# Windows: Download from oracle.com
# Linux: sudo apt-get install openjdk-17-jdk
# macOS: brew install openjdk@17
```

### Problem: "mvn not found"
```bash
# Solution: Install Maven
# Download from maven.apache.org
# Add to PATH environment variable
# Or use: apt-get install maven (Linux)
```

### Problem: "Database connection refused"
```bash
# Solution 1: Start MySQL
Windows: net start MySQL80
Linux:   sudo service mysql start
macOS:   brew services start mysql

# Solution 2: Check credentials in application.properties
# Solution 3: Verify database created
mysql -u root -p -e "SHOW DATABASES;" | grep student_management_system
```

### Problem: "Permission denied" (Linux/macOS)
```bash
# Solution: Run with sudo if needed
sudo mysql -u root -p
# Or configure MySQL without sudo requirement
```

### Problem: "Port 3306 already in use"
```bash
# Solution: Check if MySQL is running on another port
mysql -u root -p -h 127.0.0.1 --port=3306

# Or change port in application.properties
db.url=jdbc:mysql://localhost:3307/student_management_system
```

---

## 📁 Project Structure After Setup

```
StudentManagementSystem/
├── pom.xml                 # Maven config (no changes needed)
├── src/
│   ├── main/
│   │   ├── java/com/sms/   # Source code
│   │   └── resources/
│   │       ├── application.properties  # ⚙️ EDIT FOR PHASE 2
│   │       └── logback.xml
│   └── test/               # Test files
├── database/
│   └── schema.sql          # ⚙️ RUN FOR PHASE 2
├── docs/                   # Documentation
├── target/                 # Generated (after build)
└── logs/                   # Generated (at runtime)
```

---

## 🎯 First Run Steps

### 1. Initial Menu
```
╔════════════════════════════════════════════════════════╗
║    STUDENT MANAGEMENT SYSTEM - Console Application    ║
║           Version 1.0 - Welcome!                      ║
╚════════════════════════════════════════════════════════╝

MAIN MENU
==================================================
1. Add New Student
2. View All Students
3. Search Student
4. Update Student
5. Delete Student
6. Student Statistics
0. Exit
==================================================
```

### 2. Try Adding a Student
```
Enter your choice: 1

Enter first name: John
Enter last name: Doe
Enter email: john@example.com
Enter phone number: 555-0101

✅ Student added successfully!
Student ID: 1
Name: John Doe
Email: john@example.com
```

### 3. View All Students
```
Enter your choice: 2

ALL STUDENTS
ID      First Name      Last Name      Email                 Status
1       John            Doe            john@example.com      ACTIVE
```

### 4. Exit
```
Enter your choice: 0
Exiting application...
```

---

## 📚 Next Steps

After successful setup:

1. **Read Documentation**
   - Open `README.md` for comprehensive guide
   - Check `docs/SRS.md` for requirements
   - Review `docs/TDD_Phase1.md` for testing

2. **Explore Code**
   - Start with `src/main/java/com/sms/App.java`
   - Understand `StudentDaoMemoryImpl` (Phase 1)
   - Study `StudentDaoJdbcImpl` for database version

3. **Run Tests**
   ```bash
   mvn test
   ```

4. **View Logs**
   ```bash
   # Logs are saved to
   logs/sms-application.log
   logs/sms-error.log
   ```

5. **Generate Documentation**
   ```bash
   mvn javadoc:javadoc
   # Open target/site/apidocs/index.html
   ```

---

## 🆘 Getting Help

1. **Check Logs**
   - `logs/sms-application.log` - Application logs
   - `logs/sms-error.log` - Error-specific logs

2. **Review Documentation**
   - README.md - Full documentation
   - docs/SRS.md - Requirements
   - docs/TDD_*.md - Test plans

3. **Common Issues**
   - Verify Java version: `java -version`
   - Verify MySQL running: `mysql -u root -p`
   - Check configuration: `application.properties`
   - View error logs: `logs/sms-error.log`

4. **Quick Fixes**
   ```bash
   # Clean rebuild
   mvn clean package
   
   # Reset application
   rm -rf target logs
   
   # Verify database
   mysql -u root -p < database/schema.sql
   ```

---

## ✅ Verification Checklist

- [ ] Java 17+ installed
- [ ] Maven 3.6+ installed
- [ ] Project extracted to working directory
- [ ] Maven build successful (`mvn clean package`)
- [ ] Application runs (`java -cp target/classes com.sms.App`)
- [ ] Phase 1 (in-memory) operations work
- [ ] (Optional) MySQL installed for Phase 2
- [ ] (Optional) Database created from schema.sql
- [ ] (Optional) application.properties configured
- [ ] (Optional) Phase 2 (database) operations work

---

## 🎓 Learning Paths

### For Beginners
1. Run Phase 1 (in-memory) first
2. Understand CRUD operations
3. Explore console menu
4. Read code comments
5. Study documentation

### For Intermediate
1. Set up Phase 2 with database
2. Study DAO pattern
3. Review SQL queries
4. Understand JDBC
5. Run unit tests

### For Advanced
1. Modify code structure
2. Add new features
3. Optimize database queries
4. Implement connection pooling
5. Add transaction support

---

## 📞 Support Resources

- **Java Docs:** https://docs.oracle.com/en/java/javase/17/
- **Maven Docs:** https://maven.apache.org/
- **MySQL Docs:** https://dev.mysql.com/doc/
- **JDBC Docs:** https://docs.oracle.com/javase/tutorial/jdbc/

---

**Last Updated:** December 2024  
**Status:** Ready for Use  
**Next Step:** Run `java -cp target/classes com.sms.App`
