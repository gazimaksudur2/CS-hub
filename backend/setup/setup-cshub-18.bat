@echo off
echo ========================================
echo CSHub Setup with PostgreSQL 18
echo ========================================

echo [1/4] Starting PostgreSQL 18...
net start postgresql-x64-18

echo [2/4] Creating CSHub database...
cd "C:\Program Files\PostgreSQL\18\bin"
psql -U postgres -p 5433 -c "CREATE DATABASE IF NOT EXISTS cshub;"
psql -U postgres -p 5433 -c "CREATE USER IF NOT EXISTS cshub_user WITH PASSWORD 'cshub_pass';"
psql -U postgres -p 5433 -c "GRANT ALL PRIVILEGES ON DATABASE cshub TO cshub_user;"

echo [3/4] Testing connection...
psql -U cshub_user -p 5433 -d cshub -c "SELECT 'CSHub database is ready!' as message;"

echo [4/4] Configuration:
echo.
echo Backend (application.properties):
echo spring.datasource.url=jdbc:postgresql://localhost:5433/cshub
echo spring.datasource.username=cshub_user
echo spring.datasource.password=cshub_pass
echo.
echo Frontend API should point to: http://localhost:8080
echo.
echo ✓ Setup complete!
pause