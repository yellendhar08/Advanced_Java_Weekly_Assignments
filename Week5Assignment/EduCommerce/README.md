# EduCommerce - Student Performance & Attendance Management System
# Spring Boot Microservices

## Project Structure
```
EduCommerce/
├── ConfigServer/          (Config Server   - Port 8888)
├── EurekaServer/          (Eureka Server   - Port 8761)
├── ApiGateway/            (API Gateway     - Port 8080)
├── StudentService/        (Student Service - Port 8081)
├── AttendanceService/     (Attendance      - Port 8082)
├── ResultService/         (Result Service  - Port 8083)
└── github-config-repo/    (Upload these files to your GitHub repo)
```

## GitHub Config Server Setup (Do this FIRST)
1. Create a new GitHub repository (e.g., "educommerce-config")
2. Upload the 3 files from github-config-repo/ folder to that repo:
   - student-service.yml
   - attendance-service.yml
   - result-service.yml
3. Open ConfigServer/src/main/resources/application.properties
4. Replace the GitHub URL with your repo URL:
   spring.cloud.config.server.git.uri=https://github.com/YOUR_USERNAME/educommerce-config

## MySQL Databases Required
Run these in MySQL:
CREATE DATABASE studentdb;
CREATE DATABASE attendancedb;
CREATE DATABASE resultdb;
(Or they auto-create because of createDatabaseIfNotExist=true)

## Startup Order (IMPORTANT)
1. Start MySQL
2. Start Zipkin:  java -jar zipkin.jar  (download from zipkin.io)
3. Start EurekaServer
4. Start ConfigServer
5. Start StudentService
6. Start AttendanceService
7. Start ResultService
8. Start ApiGateway

## Environment Variables (optional, defaults are set)
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
export JWT_SECRET=your-secret-key
export ZIPKIN_URL=http://localhost:9411

## Postman API Testing

### Step 1 - Register (NO token needed)
POST http://localhost:8080/auth/register
Body: { "name":"Rahul Verma", "email":"rahul@test.com", "password":"pass123", "department":"CS", "semester":3 }

### Step 2 - Login (get JWT token)
POST http://localhost:8080/auth/login
Body: { "email":"rahul@test.com", "password":"pass123" }
Copy the "token" from response

### Step 3 - Use token in all other requests
Header: Authorization: Bearer <paste_token_here>

### Student APIs
GET    http://localhost:8080/students
GET    http://localhost:8080/students/1
PUT    http://localhost:8080/students/1
DELETE http://localhost:8080/students/1

### Course APIs
POST   http://localhost:8080/courses
GET    http://localhost:8080/courses

### Enrollment APIs
POST   http://localhost:8080/enroll
GET    http://localhost:8080/enroll/students/1/courses

### Attendance APIs
POST   http://localhost:8080/attendance
GET    http://localhost:8080/attendance/student/1
GET    http://localhost:8080/attendance/course/1

### Result APIs (Circuit Breaker here)
POST   http://localhost:8080/results
GET    http://localhost:8080/results/student/1   <- fetches attendance % too
GET    http://localhost:8080/results/course/1

## Circuit Breaker Test
1. Stop AttendanceService
2. Call GET http://localhost:8080/results/student/1
3. Should still return results with attendancePercentage: -1
   (instead of crashing - this is the Circuit Breaker fallback)

## Zipkin Tracing
Open: http://localhost:9411
Click "Run Query" to see request traces across all services
