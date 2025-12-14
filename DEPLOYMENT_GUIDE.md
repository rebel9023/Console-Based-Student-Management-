# Student Management System - Complete Deployment Guide

The Student Management System can be deployed to multiple cloud platforms. Choose the option that best fits your needs.

## 📋 Quick Comparison

| Platform | Setup Time | Cost | Best For | Free Tier |
|----------|-----------|------|----------|-----------|
| **Azure Container Apps** | 10-15 min | $0.10/hour | Production workloads | 2M requests/month |
| **Render** | 5 min | $7/month | Small apps | Yes |
| **Railway** | 5 min | $5/month | Quick deployment | Yes |
| **Docker Desktop** | 2 min | Free | Local development | Yes |

---

## 🚀 Option 1: Deploy to Azure Container Apps (Recommended for Production)

**Prerequisites:**
- Azure Subscription
- Azure CLI 2.80+ ✅ (already installed)
- Azure Developer CLI 1.22+ ✅ (already installed)

### Step 1: Set Up Azure Account
```powershell
# Log in to Azure
az login

# Set your subscription (if you have multiple)
az account set --subscription "<SUBSCRIPTION_ID>"
```

### Step 2: Initialize AZD Environment
```powershell
cd "f:\Console -based - managment system\StudentManagementSystem"

# Create a new AZD environment
azd env new student-sms --no-prompt

# Set the resource group location (default: eastus)
azd env set AZURE_LOCATION eastus
```

### Step 3: Preview Infrastructure
```powershell
# Dry-run to see what resources will be created
azd provision --preview --no-prompt
```

### Step 4: Deploy to Azure
```powershell
# Full deployment (provision + deploy)
azd up --no-prompt

# This will:
# ✓ Create Azure Container Registry
# ✓ Build and push Docker image
# ✓ Create Container Apps environment
# ✓ Deploy the application
# ✓ Set up monitoring with Application Insights
```

### Step 5: Access Your Application
```powershell
# Get the deployment URL
azd env get-values | grep AZURE_CONTAINER_APPS_URL

# Or check in Azure Portal:
# Resource Groups > sms-<random> > Container Apps > studentmanagementsystem
```

**Deployed URLs:**
- API: `https://<app-name>.azurecontainerapps.io/sms/api`
- Swagger UI: `https://<app-name>.azurecontainerapps.io/sms/swagger-ui.html`

---

## 🐳 Option 2: Deploy to Docker Hub & Pull Anywhere

### Step 1: Build Docker Image Locally
```powershell
cd "f:\Console -based - managment system\StudentManagementSystem"

# Build the image
docker build -t student-management-system:latest .

# Tag for Docker Hub (replace YOUR_DOCKER_USERNAME)
docker tag student-management-system:latest YOUR_DOCKER_USERNAME/student-management-system:latest
```

### Step 2: Push to Docker Hub
```powershell
# Log in to Docker Hub
docker login

# Push the image
docker push YOUR_DOCKER_USERNAME/student-management-system:latest
```

### Step 3: Run Locally with Docker Compose
```powershell
# Start with Docker Compose
docker-compose up -d

# App will be available at: http://localhost:8080/sms
```

### Step 4: Pull and Run on Any Server
```bash
# On your server
docker pull YOUR_DOCKER_USERNAME/student-management-system:latest
docker run -d -p 8080:8080 YOUR_DOCKER_USERNAME/student-management-system:latest
```

---

## ☁️ Option 3: Deploy to Render (Easiest)

### Step 1: Push to GitHub
```powershell
# Initialize git repo (if not already done)
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/YOUR_USERNAME/student-management-system.git
git push -u origin main
```

### Step 2: Deploy on Render
1. Go to [render.com](https://render.com)
2. Sign up with GitHub account
3. Click **New +** → **Web Service**
4. Connect your repository
5. Configure:
   - **Name**: `student-management-system`
   - **Runtime**: `Docker`
   - **Region**: Choose closest to you
   - **Branch**: `main`
6. Click **Create Web Service**

**Cost:** $7/month (includes free tier limits)

---

## 🚂 Option 4: Deploy to Railway (Fast Setup)

### Step 1: Push to GitHub
(Same as Render - push code to GitHub)

### Step 2: Deploy on Railway
1. Go to [railway.app](https://railway.app)
2. Click **New Project** → **Deploy from GitHub repo**
3. Select your repository
4. Railway auto-detects the Dockerfile
5. Click **Deploy**

**Cost:** $5/month minimum

---

## 🏃 Option 5: Run Locally with Docker Compose

### Step 1: Build and Run
```powershell
cd "f:\Console -based - managment system\StudentManagementSystem"

# Build the image
docker build -t student-sms:latest .

# Run with Docker Compose
docker-compose up -d

# Verify it's running
docker ps
```

### Step 2: Access the Application
```
API: http://localhost:8080/sms/api
Swagger UI: http://localhost:8080/sms/swagger-ui.html
```

### Step 3: Stop the Application
```powershell
docker-compose down
```

---

## 🗄️ Database Configuration

### Default (In-Memory H2)
- **Connection**: JDBC URL = `jdbc:h2:mem:studentdb`
- **Persistence**: None (data lost on restart)
- **Best For**: Development and testing

### MySQL (Production)
Add to `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=create-drop
```

### PostgreSQL (Production)
Add to `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=create-drop
```

### Azure Database for MySQL/PostgreSQL
Use Azure CLI or Portal to create a managed database and get the connection string.

---

## 📊 API Endpoints

Once deployed, access these endpoints:

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/sms/api/students` | GET | List all students |
| `/sms/api/students` | POST | Create new student |
| `/sms/api/students/{id}` | GET | Get student by ID |
| `/sms/api/students/{id}` | PUT | Update student |
| `/sms/api/students/{id}` | DELETE | Delete student |
| `/sms/swagger-ui.html` | GET | Swagger UI documentation |

---

## 🐛 Troubleshooting

### Docker Build Issues
```powershell
# Clear Docker cache
docker system prune -a

# Rebuild
docker build --no-cache -t student-sms:latest .
```

### Azure Login Failed
```powershell
# Clear cached credentials
az logout
az login --use-device-code
```

### Application Not Starting
```powershell
# Check logs locally
docker logs <container-id>

# Check Azure logs
azd deploy --debug
```

---

## 📝 Recommended Deployment Strategy

1. **Development**: Docker Compose locally (Option 5)
2. **Testing**: Render or Railway (Option 3/4)
3. **Production**: Azure Container Apps (Option 1)

---

## ✅ Current Project Status

- ✅ Java Runtime: **Java 21 LTS**
- ✅ Spring Boot: **3.2.1**
- ✅ Dockerfile: **Present and ready**
- ✅ Docker Compose: **Configured**
- ✅ Azure Infrastructure: **Bicep templates ready**
- ✅ Build Status: **SUCCESS**

Ready to deploy! 🚀
