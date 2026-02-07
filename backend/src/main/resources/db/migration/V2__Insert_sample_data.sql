-- Insert root level topics
INSERT INTO topic (id, title, slug, content, parent_id, display_order) VALUES
(1, 'Data Structures & Algorithms', 'dsa', '# Data Structures & Algorithms

Welcome to the DSA section. Here you will find comprehensive guides on various data structures and algorithms.

## What You Will Learn
- Core data structures (Arrays, Trees, Graphs, etc.)
- Algorithm design techniques
- Time and space complexity analysis
- Problem-solving patterns', NULL, 1),

(2, 'Object-Oriented Programming', 'oop', '# Object-Oriented Programming

Learn the fundamental principles of OOP and how to design clean, maintainable code.

## Core Concepts
- Encapsulation
- Inheritance
- Polymorphism
- Abstraction', NULL, 2),

(3, 'Database Management Systems', 'dbms', '# Database Management Systems

Master the art of database design and query optimization.

## Topics Covered
- Relational database design
- Normalization
- SQL queries
- Indexing and performance', NULL, 3);

-- Insert DSA subtopics
INSERT INTO topic (id, title, slug, content, parent_id, display_order) VALUES
(4, 'Arrays', 'dsa-arrays', '# Arrays

An array is a collection of items stored at contiguous memory locations.

## Characteristics
- Fixed size (in most languages)
- Constant time access: O(1)
- Sequential memory allocation

## Common Operations
```java
// Declaration and initialization
int[] arr = new int[5];
arr[0] = 10;

// Accessing elements
int element = arr[0]; // O(1)
```

## Time Complexity
- Access: O(1)
- Search: O(n)
- Insertion: O(n)
- Deletion: O(n)

## Use Cases
- When you need fast access by index
- Implementing other data structures (stacks, queues)
- Matrix operations', 1, 1),

(5, 'Linked Lists', 'dsa-linked-lists', '# Linked Lists

A linked list is a linear data structure where elements are stored in nodes, and each node points to the next node.

## Types
1. **Singly Linked List** - Each node points to the next
2. **Doubly Linked List** - Each node points to both next and previous
3. **Circular Linked List** - Last node points back to first

## Implementation
```java
class Node {
    int data;
    Node next;
    
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;
    
    void insert(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
}
```

## Time Complexity
- Access: O(n)
- Search: O(n)
- Insertion (at head): O(1)
- Deletion (at head): O(1)', 1, 2),

(6, 'Trees', 'dsa-trees', '# Trees

A tree is a hierarchical data structure with a root node and subtrees of children nodes.

## Binary Tree
A tree where each node has at most two children (left and right).

```java
class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
```

## Tree Traversal
### Inorder (Left, Root, Right)
```java
void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    System.out.print(node.value + " ");
    inorder(node.right);
}
```

### Preorder (Root, Left, Right)
```java
void preorder(TreeNode node) {
    if (node == null) return;
    System.out.print(node.value + " ");
    preorder(node.left);
    preorder(node.right);
}
```

### Postorder (Left, Right, Root)
```java
void postorder(TreeNode node) {
    if (node == null) return;
    postorder(node.left);
    postorder(node.right);
    System.out.print(node.value + " ");
}
```

## Binary Search Tree (BST)
A binary tree where left child < parent < right child.

**Search Time Complexity:**
- Average: O(log n)
- Worst: O(n) for skewed tree', 1, 3),

(7, 'Graphs', 'dsa-graphs', '# Graphs

A graph is a non-linear data structure consisting of vertices (nodes) and edges.

## Representation
### Adjacency List
```java
Map<Integer, List<Integer>> graph = new HashMap<>();
```

### Adjacency Matrix
```java
int[][] graph = new int[V][V];
```

## Graph Traversal
### Breadth-First Search (BFS)
```java
void bfs(int start) {
    boolean[] visited = new boolean[V];
    Queue<Integer> queue = new LinkedList<>();
    
    visited[start] = true;
    queue.add(start);
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        System.out.print(vertex + " ");
        
        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.add(neighbor);
            }
        }
    }
}
```

### Depth-First Search (DFS)
```java
void dfs(int vertex, boolean[] visited) {
    visited[vertex] = true;
    System.out.print(vertex + " ");
    
    for (int neighbor : graph.get(vertex)) {
        if (!visited[neighbor]) {
            dfs(neighbor, visited);
        }
    }
}
```', 1, 4),

(8, 'Sorting Algorithms', 'dsa-sorting', '# Sorting Algorithms

Sorting is arranging data in a particular order.

## Quick Sort
```java
void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = low - 1;
    
    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            i++;
            swap(arr, i, j);
        }
    }
    swap(arr, i + 1, high);
    return i + 1;
}
```
**Time Complexity:** O(n log n) average, O(n²) worst

## Merge Sort
```java
void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}
```
**Time Complexity:** O(n log n) always', 1, 5);

-- Insert OOP subtopics
INSERT INTO topic (id, title, slug, content, parent_id, display_order) VALUES
(9, 'Encapsulation', 'oop-encapsulation', '# Encapsulation

Encapsulation is bundling data and methods that operate on that data within a single unit (class).

## Key Principles
- Hide internal state
- Expose only necessary functionality
- Use access modifiers (private, protected, public)

## Example
```java
public class BankAccount {
    private double balance; // Private field
    
    // Public getter
    public double getBalance() {
        return balance;
    }
    
    // Public setter with validation
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    // Cannot directly access balance from outside
}
```

## Benefits
- **Data Protection:** Prevents unauthorized access
- **Flexibility:** Internal implementation can change without affecting external code
- **Maintainability:** Easier to update and debug', 2, 1),

(10, 'Inheritance', 'oop-inheritance', '# Inheritance

Inheritance allows a class to inherit properties and methods from another class.

## Types
1. **Single Inheritance** - One parent, one child
2. **Multilevel Inheritance** - Chain of inheritance
3. **Hierarchical Inheritance** - Multiple children from one parent

## Example
```java
// Parent class
public class Animal {
    protected String name;
    
    public void eat() {
        System.out.println(name + " is eating");
    }
}

// Child class
public class Dog extends Animal {
    public void bark() {
        System.out.println(name + " is barking");
    }
}

// Usage
Dog dog = new Dog();
dog.name = "Buddy";
dog.eat();  // Inherited method
dog.bark(); // Own method
```

## Method Overriding
```java
@Override
public void eat() {
    System.out.println(name + " is eating dog food");
}
```

## Benefits
- Code reusability
- Establishes IS-A relationship
- Supports polymorphism', 2, 2),

(11, 'Polymorphism', 'oop-polymorphism', '# Polymorphism

Polymorphism allows objects to take multiple forms. The same method behaves differently based on the object calling it.

## Types
### 1. Compile-time (Method Overloading)
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

### 2. Runtime (Method Overriding)
```java
public abstract class Shape {
    abstract double area();
}

public class Circle extends Shape {
    private double radius;
    
    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

public class Rectangle extends Shape {
    private double length, width;
    
    @Override
    double area() {
        return length * width;
    }
}

// Usage - Same reference, different behavior
Shape shape1 = new Circle();
Shape shape2 = new Rectangle();
shape1.area(); // Calls Circle''s area()
shape2.area(); // Calls Rectangle''s area()
```

## Benefits
- Flexibility in code
- Easier to extend functionality
- Supports interface-based programming', 2, 3),

(12, 'Abstraction', 'oop-abstraction', '# Abstraction

Abstraction hides complex implementation details and shows only essential features.

## Abstract Classes
```java
public abstract class Vehicle {
    abstract void start();
    abstract void stop();
    
    // Concrete method
    public void honk() {
        System.out.println("Beep!");
    }
}

public class Car extends Vehicle {
    @Override
    void start() {
        System.out.println("Car starting with key");
    }
    
    @Override
    void stop() {
        System.out.println("Car stopping with brake");
    }
}
```

## Interfaces
```java
public interface Drawable {
    void draw(); // Abstract by default
}

public class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }
}
```

## Abstract Class vs Interface
| Abstract Class | Interface |
|---|---|
| Can have concrete methods | All methods abstract (before Java 8) |
| Single inheritance | Multiple inheritance |
| Can have constructors | No constructors |
| Can have state (fields) | Only constants |

## Benefits
- Reduces complexity
- Separates what from how
- Enhances maintainability', 2, 4);

-- Insert DBMS subtopics
INSERT INTO topic (id, title, slug, content, parent_id, display_order) VALUES
(13, 'Normalization', 'dbms-normalization', '# Database Normalization

Normalization is the process of organizing data to reduce redundancy and improve data integrity.

## Normal Forms

### First Normal Form (1NF)
- Each column contains atomic (indivisible) values
- No repeating groups

**Before 1NF:**
| StudentID | Name | Courses |
|---|---|---|
| 1 | John | Math, Physics |

**After 1NF:**
| StudentID | Name | Course |
|---|---|---|
| 1 | John | Math |
| 1 | John | Physics |

### Second Normal Form (2NF)
- Must be in 1NF
- No partial dependencies (all non-key attributes depend on entire primary key)

### Third Normal Form (3NF)
- Must be in 2NF
- No transitive dependencies (non-key attributes don''t depend on other non-key attributes)

**Example (Not 3NF):**
```sql
CREATE TABLE Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(100),
    dept_id INT,
    dept_name VARCHAR(100) -- Depends on dept_id, not emp_id
);
```

**After 3NF:**
```sql
CREATE TABLE Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(100),
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
);

CREATE TABLE Department (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);
```

## Benefits
- Eliminates data redundancy
- Ensures data consistency
- Easier to maintain', 3, 1),

(14, 'SQL Queries', 'dbms-sql', '# SQL Queries

Structured Query Language (SQL) is used to communicate with databases.

## Basic Queries

### SELECT
```sql
SELECT * FROM employees;
SELECT name, salary FROM employees WHERE salary > 50000;
```

### JOIN Operations
```sql
-- INNER JOIN
SELECT e.name, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id;

-- LEFT JOIN
SELECT e.name, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.dept_id;
```

### Aggregate Functions
```sql
SELECT 
    dept_id,
    COUNT(*) as emp_count,
    AVG(salary) as avg_salary,
    MAX(salary) as max_salary
FROM employees
GROUP BY dept_id
HAVING COUNT(*) > 5;
```

### Subqueries
```sql
SELECT name, salary
FROM employees
WHERE salary > (
    SELECT AVG(salary) FROM employees
);
```

## Window Functions
```sql
SELECT 
    name,
    salary,
    RANK() OVER (ORDER BY salary DESC) as salary_rank
FROM employees;
```

## Common Table Expressions (CTE)
```sql
WITH HighEarners AS (
    SELECT * FROM employees WHERE salary > 100000
)
SELECT * FROM HighEarners WHERE dept_id = 5;
```', 3, 2),

(15, 'Indexing', 'dbms-indexing', '# Database Indexing

Indexes are data structures that improve the speed of data retrieval operations.

## Types of Indexes

### B-Tree Index (Most Common)
```sql
CREATE INDEX idx_employee_name ON employees(name);
```

### Unique Index
```sql
CREATE UNIQUE INDEX idx_employee_email ON employees(email);
```

### Composite Index
```sql
CREATE INDEX idx_name_dept ON employees(name, dept_id);
```

## How Indexes Work
- Think of it like a book''s index
- Trade-off: Faster reads, slower writes
- Uses tree structure for O(log n) search

## The N+1 Select Problem
**Problem:**
```java
// Fetches 1 query for posts
List<Post> posts = postRepository.findAll();

// Then N queries for each post''s author (N+1 problem)
for (Post post : posts) {
    post.getAuthor().getName(); // Triggers separate query
}
```

**Solution:**
```java
// Fetch everything in one query with JOIN
@Query("SELECT p FROM Post p JOIN FETCH p.author")
List<Post> findAllWithAuthors();
```

## When to Use Indexes
✅ Columns used in WHERE clauses
✅ Columns used in JOIN conditions
✅ Columns used in ORDER BY

❌ Small tables
❌ Columns with frequent updates
❌ Columns with low cardinality', 3, 3),

(16, 'Transactions', 'dbms-transactions', '# Database Transactions

A transaction is a sequence of operations performed as a single logical unit of work.

## ACID Properties

### Atomicity
All operations succeed or all fail (no partial updates)

### Consistency
Database remains in valid state before and after transaction

### Isolation
Concurrent transactions don''t interfere with each other

### Durability
Committed changes persist even after system failure

## Transaction Example
```sql
BEGIN TRANSACTION;

UPDATE accounts SET balance = balance - 100 WHERE account_id = 1;
UPDATE accounts SET balance = balance + 100 WHERE account_id = 2;

COMMIT; -- or ROLLBACK if error
```

## Isolation Levels
1. **Read Uncommitted** - Lowest isolation, allows dirty reads
2. **Read Committed** - Prevents dirty reads
3. **Repeatable Read** - Prevents non-repeatable reads
4. **Serializable** - Highest isolation, full transaction isolation

## In Spring Boot
```java
@Transactional
public void transferMoney(Long fromId, Long toId, double amount) {
    Account from = accountRepository.findById(fromId).orElseThrow();
    Account to = accountRepository.findById(toId).orElseThrow();
    
    from.setBalance(from.getBalance() - amount);
    to.setBalance(to.getBalance() + amount);
    
    accountRepository.save(from);
    accountRepository.save(to);
}
```', 3, 4);

-- Insert tags
INSERT INTO tag (id, name, color) VALUES
(1, 'Easy', '#10B981'),
(2, 'Medium', '#F59E0B'),
(3, 'Hard', '#EF4444'),
(4, 'Array', '#3B82F6'),
(5, 'Tree', '#8B5CF6'),
(6, 'Graph', '#EC4899'),
(7, 'SQL', '#06B6D4'),
(8, 'Java', '#F97316'),
(9, 'Interview', '#6366F1'),
(10, 'Google', '#DC2626'),
(11, 'Design', '#14B8A6');

-- Link topics with tags (Many-to-Many relationships)
INSERT INTO topic_tags (topic_id, tag_id) VALUES
-- Arrays
(4, 1), (4, 4), (4, 9),
-- Linked Lists
(5, 2), (5, 9),
-- Trees
(6, 2), (6, 5), (6, 9), (6, 10),
-- Graphs
(7, 3), (7, 6), (7, 9), (7, 10),
-- Sorting
(8, 2), (8, 8), (8, 9),
-- OOP topics
(9, 1), (9, 8), (9, 11),
(10, 1), (10, 8), (10, 11),
(11, 2), (11, 8), (11, 11), (11, 9),
(12, 2), (12, 8), (12, 11),
-- DBMS topics
(13, 2), (13, 7), (13, 11), (13, 9),
(14, 2), (14, 7), (14, 9),
(15, 3), (15, 7), (15, 9), (15, 10),
(16, 2), (16, 7), (16, 8);

-- Reset sequence for next inserts
SELECT setval('topic_id_seq', (SELECT MAX(id) FROM topic));
SELECT setval('tag_id_seq', (SELECT MAX(id) FROM tag));
