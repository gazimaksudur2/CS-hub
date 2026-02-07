# CSHub - Quick Setup Guide

This guide will help you get CSHub running on your local machine in minutes.

## ✅ Prerequisites Checklist

Before starting, ensure you have:

- [ ] Java 17 or higher installed (`java -version`)
- [ ] Maven 3.8+ installed (`mvn -version`)
- [ ] Node.js 18+ installed (`node -version`)
- [ ] PostgreSQL 14+ installed and running
- [ ] A PostgreSQL user with database creation privileges

## 🗄️ Step 1: Database Setup

### Option A: Using PostgreSQL CLI

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE cshub;

# Verify
\l

# Exit
\q
```

### Option B: Using pgAdmin

1. Open pgAdmin
2. Right-click on "Databases"
3. Select "Create" → "Database"
4. Name: `cshub`
5. Click "Save"

## 🔧 Step 2: Backend Setup

```bash
# Navigate to backend directory
cd backend

# Create environment file
cp .env.example .env

# Edit .env with your database credentials
# (Use notepad, VS Code, or any text editor)
notepad .env
```

**Required .env values:**
```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=cshub
DB_USERNAME=postgres
DB_PASSWORD=your_password_here
```

**Build and run:**
```bash
# Clean build
mvn clean install

# Run the application
mvn spring-boot:run
```

**Verify backend is running:**
- Open browser: http://localhost:8080/swagger-ui.html
- You should see the Swagger API documentation

## 🎨 Step 3: Frontend Setup

Open a **new terminal** (keep the backend running):

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies (this may take a few minutes)
npm install

# Create environment file
cp .env.example .env

# Start development server
npm run dev
```

**Verify frontend is running:**
- Open browser: http://localhost:3000
- You should see the CSHub home page

## ✅ Step 4: Verify Everything Works

### Test the Application

1. **Check Sidebar Navigation**
   - Click on "Data Structures & Algorithms" in the sidebar
   - It should expand to show child topics

2. **Test Topic Viewing**
   - Click on "Arrays" under DSA
   - You should see formatted content with code examples

3. **Test Search**
   - Type "tree" in the search bar
   - You should see matching topics appear

4. **Test Dark Mode**
   - Click the moon/sun icon in the header
   - The theme should switch

### Check API

Visit http://localhost:8080/api/topics/tree

You should see JSON response with topics.

## 🎯 Quick Start Commands

### Start Backend
```bash
cd backend
mvn spring-boot:run
```

### Start Frontend
```bash
cd frontend
npm run dev
```

### Run Tests
```bash
cd backend
mvn test
```

## 🐛 Troubleshooting

### Backend won't start

**Error: "Connection refused" or database connection error**
- Solution: Check PostgreSQL is running
- Verify database credentials in `.env`
- Ensure database `cshub` exists

**Error: "Port 8080 already in use"**
- Solution: Kill the process using port 8080 or change port in `application.properties`

```bash
# Windows: Find process on port 8080
netstat -ano | findstr :8080
# Kill process by PID
taskkill /PID <PID> /F

# Linux/Mac: Find and kill process
lsof -ti:8080 | xargs kill -9
```

**Error: Flyway migration fails**
- Solution: Drop and recreate database
```sql
DROP DATABASE cshub;
CREATE DATABASE cshub;
```

### Frontend won't start

**Error: "Port 3000 already in use"**
- Solution: Kill the process or change port in `vite.config.ts`

**Error: Dependencies installation fails**
- Solution: Clear npm cache and retry
```bash
npm cache clean --force
npm install
```

**Error: "Network Error" when fetching data**
- Solution: Ensure backend is running on port 8080
- Check `VITE_API_URL` in frontend `.env` file

### API returns empty data

**No topics showing in sidebar**
- Verify Flyway migrations ran successfully
- Check backend logs for errors
- Verify database has data:
```sql
psql -U postgres -d cshub
SELECT * FROM topic;
```

## 📱 Accessing the Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs

## 🔐 Default Configuration

### Backend
- Port: 8080
- Context Path: `/api`
- Database: PostgreSQL on localhost:5432
- Sample data: Automatically loaded via Flyway

### Frontend
- Port: 3000
- API Proxy: Configured to forward `/api` to backend
- Hot reload: Enabled

## 🚀 Next Steps

1. **Explore the Code**
   - Check out `backend/src/main/java/com/cshub/service/TopicService.java` for the tree-building algorithm
   - Look at `frontend/src/components/Sidebar/SidebarItem.tsx` for recursive component

2. **Read the Documentation**
   - Main README: Project overview and architecture
   - Backend README: API documentation
   - Frontend README: Component structure

3. **Try the API**
   - Use Swagger UI to test endpoints
   - Try creating a new topic via POST request

4. **Customize**
   - Add your own CS topics
   - Modify the color scheme in Tailwind config
   - Add new features

## 💡 Tips

### Development Workflow

1. Keep both terminals open (backend + frontend)
2. Backend auto-reloads on Java file changes (if using Spring Boot DevTools)
3. Frontend has hot-reload enabled by default
4. Check browser console for frontend errors
5. Check terminal for backend errors

### Database Management

**View all topics:**
```sql
psql -U postgres -d cshub
SELECT id, title, slug, parent_id FROM topic ORDER BY display_order;
```

**Reset database:**
```bash
# Stop backend
# Drop and recreate database
psql -U postgres
DROP DATABASE cshub;
CREATE DATABASE cshub;
\q

# Restart backend (Flyway will recreate tables and data)
mvn spring-boot:run
```

### IDE Recommendations

**Backend (Java):**
- IntelliJ IDEA (recommended)
- Eclipse with Spring Tools
- VS Code with Java extensions

**Frontend (TypeScript/React):**
- VS Code (recommended)
- WebStorm
- Any editor with TypeScript support

## 🆘 Still Having Issues?

1. Check all services are running:
   - PostgreSQL service
   - Backend on port 8080
   - Frontend on port 3000

2. Verify environment files:
   - `backend/.env` exists with correct database credentials
   - `frontend/.env` exists with API URL

3. Check logs:
   - Backend logs in terminal
   - Frontend logs in browser console
   - PostgreSQL logs (if applicable)

4. Try a clean restart:
   ```bash
   # Stop all services
   # Clear caches
   cd backend && mvn clean
   cd ../frontend && rm -rf node_modules && npm install
   # Restart everything
   ```

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Tailwind CSS](https://tailwindcss.com/docs)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

**Congratulations!** 🎉 You now have CSHub running locally. Happy coding!
