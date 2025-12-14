# Deploying Student Management System to Cloud

Since this is a primarily Java Spring Boot application, it is best deployed to a platform that supports Docker containers natively. **Vercel is designed for static frontends** and technically does not support running persistent Java applications.

We recommend using **Render** or **Railway** for the best experience.

## Option 1: Deploy to Render (Recommended)

Render is a "zero-devops" cloud platform that is very similar to Vercel but for backends.

1.  **Push your code to GitHub/GitLab**.
2.  Sign up at [render.com](https://render.com).
3.  Click **New +** -> **Web Service**.
4.  Connect your repository.
5.  Render will automatically detect the `Dockerfile` in your project.
6.  **Settings**:
    *   **Name**: `student-management-system`
    *   **Runtime**: Docker
    *   **Region**: (Choose closest to you)
    *   **Branch**: `main` (or master)
    *   **Free Instance Type**: Yes
7.  Click **Create Web Service**.

Render will build your Docker container (this may take 5-10 minutes) and deploy it.

Once deployed, you can access your API documentation at:
`https://<your-app-name>.onrender.com/sms/swagger-ui.html`

### Database on Render
By default, the app is now configured to use an **In-Memory H2 Database** (good for demos, but data is lost on restart).
To use a real PostgreSQL database on Render:
1.  Create a **New +** -> **PostgreSQL** database on Render.
2.  In your Web Service **Environment Variables**:
    *   `SPRING_DATASOURCE_URL`: (Internal URL from Render Postgres)
    *   `SPRING_DATASOURCE_USERNAME`: (from Render Postgres)
    *   `SPRING_DATASOURCE_PASSWORD`: (from Render Postgres)

## Option 2: Deploy to Railway

Railway is another excellent alternative.

1.  Sign up at [railway.app](https://railway.app).
2.  Click **New Project** -> **Deploy from GitHub repo**.
3.  Select your repository.
4.  Railway will detect the `Dockerfile` and build it.
5.  (Optional) Add a MySQL or PostgreSQL plugin in Railway and it will provide variables automatically.

## Why not Vercel?
Vercel is optimized for Frontend frameworks (Next.js, React) and Serverless Functions (Node.js/Python). Java Spring Boot applications are "long-running processes" (they stay alive listening for requests), which Vercel's infrastructure does not support. Deploying this JAR to Vercel would require complex hacking (GraalVM Native Image + Custom Runtime) which is not recommended for this project.
