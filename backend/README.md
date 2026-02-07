# CSHub Backend

Spring Boot REST API for the CSHub documentation platform.

## 🏗️ Architecture

This backend follows a layered architecture:

```
Controller → Service → Repository → Database
     ↓
    DTO ← Mapper ← Entity
```

### Key Components

1. **Controllers** (`controller/`)
   - REST endpoints
   - Request validation
   - HTTP status codes
   - Swagger documentation

2. **Services** (`service/`)
   - Business logic
   - Transaction management
   - Tree building algorithm (O(n) complexity)

3. **Repositories** (`repository/`)
   - Database access
   - Custom queries with JPQL
   - Optimized queries to avoid N+1 problem

4. **Entities** (`entity/`)
   - JPA entities
   - Self-referencing relationship for tree structure
   - Many-to-Many relationship for tags

5. **DTOs** (`dto/`)
   - Data Transfer Objects
   - Separate API contract from database schema

6. **Mappers** (`mapper/`)
   - Convert between Entity and DTO
   - Handles recursive tree structure

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### Setup

1. Create database:
```sql
CREATE DATABASE cshub;
```

2. Configure environment:
```bash
cp .env.example .env
# Edit .env with your database credentials
```

3. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

4. Access API:
- API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## 📚 API Documentation

API documentation is available at `/swagger-ui.html` when the application is running.

### Main Endpoints

#### Get Topic Tree (for sidebar)
```http
GET /api/topics/tree
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Data Structures & Algorithms",
    "slug": "dsa",
    "children": [
      {
        "id": 4,
        "title": "Arrays",
        "slug": "dsa-arrays",
        "children": []
      }
    ],
    "tags": [
      {
        "id": 1,
        "name": "Easy",
        "color": "#10B981"
      }
    ]
  }
]
```

#### Search Topics
```http
GET /api/topics/search?keyword=array
```

## 🗄️ Database Schema

### Topic Table
```sql
CREATE TABLE topic (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    content TEXT,
    parent_id BIGINT,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_topic_parent FOREIGN KEY (parent_id) 
        REFERENCES topic(id) ON DELETE CASCADE
);
```

### Tag Table
```sql
CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    color VARCHAR(7) DEFAULT '#3B82F6',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

### Topic_Tags Junction Table
```sql
CREATE TABLE topic_tags (
    topic_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (topic_id, tag_id),
    FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);
```

## 🧪 Testing

Run tests:
```bash
mvn test
```

Run specific test:
```bash
mvn test -Dtest=TopicServiceTest
```

## 🔍 Key Implementations

### 1. Tree Building Algorithm

Located in `TopicService.getTopicTree()`:

```java
// Fetch all topics in ONE query (avoids N+1 problem)
List<Topic> allTopics = topicRepository.findAllWithTags();

// Build HashMap for O(1) lookup
Map<Long, TopicDTO> topicMap = new HashMap<>();
for (Topic topic : allTopics) {
    TopicDTO dto = topicMapper.toDTO(topic);
    topicMap.put(topic.getId(), dto);
}

// Build tree structure in O(n) time
for (Topic topic : allTopics) {
    if (topic.getParent() == null) {
        rootTopics.add(dto);
    } else {
        parentDTO.getChildren().add(dto);
    }
}
```

### 2. Global Exception Handling

Located in `GlobalExceptionHandler`:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
```

### 3. Optimized Queries

Located in `TopicRepository`:

```java
// Fetch all topics with their tags in ONE query
@Query("SELECT DISTINCT t FROM Topic t LEFT JOIN FETCH t.tags")
List<Topic> findAllWithTags();

// Case-insensitive search
@Query("SELECT t FROM Topic t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "OR LOWER(t.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Topic> searchByKeyword(@Param("keyword") String keyword);
```

## 📝 Configuration

### Application Properties

Key configurations in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:cshub}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}

# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
```

## 🔐 Security Notes

- Database credentials should be set via environment variables
- Never commit `.env` files
- Use HTTPS in production
- Implement rate limiting
- Add authentication/authorization for write operations

## 📦 Dependencies

- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL Driver
- Flyway
- Hibernate Validator
- SpringDoc OpenAPI (Swagger)
- JUnit 5 & Mockito

## 🚀 Deployment

For production deployment:

1. Set environment variables
2. Build JAR: `mvn clean package`
3. Run: `java -jar target/cshub-backend-1.0.0.jar`

## 🤝 Contributing

When contributing:
1. Follow the existing code style
2. Write unit tests for new features
3. Update API documentation
4. Use conventional commits
