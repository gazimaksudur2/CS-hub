# CSHub - CS Knowledge Hub

A high-performance, full-stack documentation platform for Computer Science concepts (DSA, OOP, DBMS). Built to demonstrate advanced software engineering skills through practical implementation of the concepts it teaches.

## 🎯 Project Overview

CSHub is more than a documentation site—it's a showcase of how CS fundamentals apply to real-world application development:

- **DSA (Trees/Recursion)**: Hierarchical topic navigation using self-referencing database relationships and recursive algorithms
- **OOP (Polymorphism)**: Content block system with factory pattern implementation
- **DBMS (Normalization)**: Properly normalized database schema with Many-to-Many relationships
- **Algorithms (Search)**: Debounced search functionality with optimized database queries

## 🏗️ Architecture

### Tech Stack

**Backend:**
- Java 17 with Spring Boot 3.2
- Spring Data JPA (Hibernate)
- PostgreSQL with Flyway migrations
- Swagger/OpenAPI documentation
- JUnit 5 & Mockito for testing

**Frontend:**
- React 18 with TypeScript (Strict mode)
- Vite for fast development
- Tailwind CSS for styling
- Zustand for state management
- React Router for navigation
- react-markdown with syntax highlighting

### Key Design Decisions

#### 1. Solving the N+1 Problem (The "Killer Feature")

**Problem:** Fetching topics with their children recursively would create N+1 database queries.

**Solution:** 
```java
// Fetch all topics with tags in ONE query
@Query("SELECT DISTINCT t FROM Topic t LEFT JOIN FETCH t.tags")
List<Topic> findAllWithTags();

// Reconstruct tree in memory using HashMap - O(n) complexity
Map<Long, TopicDTO> topicMap = new HashMap<>();
// Build tree structure in second pass
```

**Interview Answer:**
> "I avoided the N+1 select problem by fetching all topics in a single query with JOIN FETCH, then reconstructing the tree structure in memory using a HashMap for O(n) time complexity instead of O(n²)."

#### 2. Recursive UI Components

The `SidebarItem` component renders itself for child nodes, mirroring the tree data structure:

```tsx
export const SidebarItem: React.FC<SidebarItemProps> = ({ topic, level = 0 }) => {
  return (
    <div>
      <div onClick={handleClick}>...</div>
      {hasChildren && isExpanded && (
        <div>
          {topic.children.map((child) => (
            <SidebarItem topic={child} level={level + 1} />
          ))}
        </div>
      )}
    </div>
  );
};
```

#### 3. Polymorphic Content Blocks (OOP)

Factory pattern for rendering different content types:

```tsx
export const ContentBlock: React.FC<ContentBlockProps> = ({ block }) => {
  switch (block.type) {
    case 'text': return <TextBlockComponent block={block} />;
    case 'code': return <CodeBlockComponent block={block} />;
    case 'image': return <ImageBlockComponent block={block} />;
  }
};
```

#### 4. Database Schema (3NF Normalization)

```sql
-- Self-referencing relationship for tree structure
CREATE TABLE topic (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    content TEXT,
    parent_id BIGINT,
    CONSTRAINT fk_topic_parent FOREIGN KEY (parent_id) REFERENCES topic(id)
);

-- Many-to-Many relationship
CREATE TABLE topic_tags (
    topic_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (topic_id, tag_id),
    FOREIGN KEY (topic_id) REFERENCES topic(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Node.js 18+ and npm
- PostgreSQL 14+
- Maven 3.8+

### Database Setup

1. Install PostgreSQL and create a database:
```sql
CREATE DATABASE cshub;
```

2. Create a `.env` file in the backend directory:
```bash
cd backend
cp .env.example .env
```

3. Update the `.env` file with your database credentials:
```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=cshub
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

### Backend Setup

```bash
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Or run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The backend will start on `http://localhost:8080`

**API Documentation:** `http://localhost:8080/swagger-ui.html`

### Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Create environment file
cp .env.example .env

# Start development server
npm run dev
```

The frontend will start on `http://localhost:3000`

### Running Tests

```bash
cd backend
mvn test
```

## 📚 API Endpoints

### Topics
- `GET /api/topics/tree` - Get hierarchical topic tree (for sidebar)
- `GET /api/topics` - Get all topics (flat list)
- `GET /api/topics/{id}` - Get topic by ID
- `GET /api/topics/slug/{slug}` - Get topic by slug
- `GET /api/topics/search?keyword={keyword}` - Search topics
- `GET /api/topics/tag/{tagName}` - Get topics by tag
- `POST /api/topics` - Create new topic
- `PUT /api/topics/{id}` - Update topic
- `DELETE /api/topics/{id}` - Delete topic

### Tags
- `GET /api/tags` - Get all tags
- `GET /api/tags/{id}` - Get tag by ID
- `POST /api/tags` - Create new tag
- `DELETE /api/tags/{id}` - Delete tag

## 🎓 Key Learning Demonstrations

### 1. Database Design
- **3NF Normalization**: Separate tables for topics and tags with junction table
- **Self-referencing FK**: Parent-child relationship for tree structure
- **Indexing**: Strategic indexes on foreign keys and slug for performance

### 2. Algorithm Implementation
- **Tree Traversal**: Recursive algorithm to build hierarchical structure
- **HashMap Optimization**: O(n) complexity for tree construction
- **Debouncing**: Search optimization to reduce API calls

### 3. OOP Principles
- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: Entity base classes
- **Polymorphism**: Content block system with factory pattern
- **Abstraction**: Service layer abstracts business logic from controllers

### 4. Best Practices
- **DTO Pattern**: Never expose entities directly to frontend
- **Global Exception Handling**: @ControllerAdvice for consistent error responses
- **Repository Pattern**: Spring Data JPA repositories
- **Dependency Injection**: Constructor injection throughout
- **API Documentation**: Swagger/OpenAPI for all endpoints
- **Environment Variables**: Secure configuration management
- **Database Migrations**: Flyway for version-controlled schema changes

## 🎤 Interview Talking Points

### "What was the hardest part?"

> "The hardest part was optimizing the database queries for the sidebar navigation. Since it's a recursive tree structure, querying the database for every child node created the N+1 Select Problem. I solved this by fetching all topics in a single query using JOIN FETCH, then reconstructing the tree structure in memory using a HashMap implementation in Java (O(n) complexity), which significantly reduced page load time."

### "What would you improve?"

> "Given more time, I would add:
> 1. Full-text search using PostgreSQL's built-in search capabilities
> 2. Caching layer with Redis for frequently accessed topics
> 3. Content versioning system to track topic changes
> 4. User authentication and role-based access control
> 5. Rate limiting on API endpoints
> 6. Comprehensive integration tests"

### "How does it demonstrate your skills?"

> "This project demonstrates my understanding of:
> - Full-stack development with modern frameworks
> - Database design and normalization principles
> - Algorithm optimization for performance
> - Clean code architecture and design patterns
> - API design with proper REST conventions
> - TypeScript for type-safe frontend development
> - Testing strategies with JUnit and Mockito"

## 📖 Project Structure

```
cshub/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/cshub/
│   │   │   │   ├── config/          # Configuration classes
│   │   │   │   ├── controller/      # REST controllers
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── entity/          # JPA entities
│   │   │   │   ├── exception/       # Custom exceptions
│   │   │   │   ├── mapper/          # Entity-DTO mappers
│   │   │   │   ├── repository/      # Spring Data repositories
│   │   │   │   └── service/         # Business logic
│   │   │   └── resources/
│   │   │       ├── db/migration/    # Flyway SQL migrations
│   │   │       └── application.properties
│   │   └── test/                    # Unit tests
│   └── pom.xml
│
└── frontend/
    ├── src/
    │   ├── api/                     # API client
    │   ├── components/              # React components
    │   │   ├── ContentBlock/        # Polymorphic content blocks
    │   │   ├── Header/
    │   │   ├── Layout/
    │   │   ├── MarkdownViewer/
    │   │   ├── SearchBar/
    │   │   └── Sidebar/             # Recursive navigation
    │   ├── pages/                   # Page components
    │   ├── store/                   # Zustand state management
    │   ├── styles/                  # Global styles
    │   ├── types/                   # TypeScript types
    │   └── utils/                   # Utility functions
    ├── package.json
    └── vite.config.ts
```

## 🔧 Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/cshub
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=validate

# Flyway
spring.flyway.enabled=true
```

### Frontend Configuration

Edit `frontend/.env`:

```env
VITE_API_URL=http://localhost:8080/api
```

## 🤝 Contributing

This is a portfolio project, but suggestions and improvements are welcome!

## 📝 License

MIT License - feel free to use this project for learning purposes.

## 👤 Author

Built as a demonstration of full-stack development skills for technical interviews.

---

**Note:** This project intentionally implements CS concepts it teaches, making it both a learning resource and a demonstration of software engineering best practices.
