# Employee Management System

A menu-driven console application built with **Java** and **MySQL** (connected via **JDBC**).  
It lets you add, view, search, update, and delete employee records.

---

## Project Structure

```
the Employee Management System/
├── sql/
│   └── schema.sql                        ← Database & table setup
├── lib/
│   └── mysql-connector-j-*.jar           ← Place JDBC driver here
├── bin/                                  ← Compiled .class files go here
├── src/
│   └── com/ems/
│       ├── db/
│       │   └── DBConnection.java         ← JDBC connection manager
│       ├── model/
│       │   └── Employee.java             ← Employee data model
│       ├── dao/
│       │   └── EmployeeDAO.java          ← All database operations
│       └── main/
│           └── EmployeeManagementSystem.java  ← Entry point / CLI menu
└── README.md
```

---

## Prerequisites

| Requirement | Version |
|-------------|---------|
| Java JDK    | 14 or higher |
| MySQL Server | 5.7 or higher |
| MySQL Connector/J | 8.x |

---

## Setup Instructions

### Step 1 — Download the MySQL JDBC Driver

1. Go to https://dev.mysql.com/downloads/connector/j/
2. Download **mysql-connector-j-*.jar** (platform-independent).
3. Place the `.jar` file inside the **`lib/`** folder.

### Step 2 — Set up the Database

Open MySQL and run the schema script:

```bash
mysql -u root -p < sql/schema.sql
```

Or paste the contents of `sql/schema.sql` directly into MySQL Workbench and execute it.  
This creates the `employee_management` database and the `employees` table (with 3 sample rows).

### Step 3 — Configure Database Credentials

Open `src/com/ems/db/DBConnection.java` and update these three constants if needed:

```java
private static final String DB_URL  = "jdbc:mysql://localhost:3306/employee_management?useSSL=false&serverTimezone=UTC";
private static final String USER     = "root";       // your MySQL username
private static final String PASSWORD = "root";       // your MySQL password
```

---

## Compile & Run

Open a terminal in the project root directory.

### Compile

**Windows (PowerShell):**
```powershell
javac -cp "lib\mysql-connector-j-*.jar" -d bin `
  src\com\ems\db\DBConnection.java `
  src\com\ems\model\Employee.java `
  src\com\ems\dao\EmployeeDAO.java `
  src\com\ems\main\EmployeeManagementSystem.java
```

**Linux / macOS:**
```bash
javac -cp "lib/mysql-connector-j-*.jar" -d bin \
  src/com/ems/db/DBConnection.java \
  src/com/ems/model/Employee.java \
  src/com/ems/dao/EmployeeDAO.java \
  src/com/ems/main/EmployeeManagementSystem.java
```

### Run

**Windows (PowerShell):**
```powershell
java -cp "bin;lib\mysql-connector-j-*.jar" com.ems.main.EmployeeManagementSystem
```

**Linux / macOS:**
```bash
java -cp "bin:lib/mysql-connector-j-*.jar" com.ems.main.EmployeeManagementSystem
```

> Note the difference: Windows uses `;` as the classpath separator, Linux/macOS use `:`.

---

## Features

| Option | Feature | Details |
|--------|---------|---------|
| 1 | Add Employee | Enter name, email, phone, department, designation, salary, hire date |
| 2 | View All Employees | Displays all records in a formatted table |
| 3 | Search Employee | Search by ID, Name, or Department |
| 4 | Update Employee | Modify any field; press Enter to keep the current value |
| 5 | Delete Employee | Shows the record first and asks for confirmation before deleting |
| 6 | Exit | Closes the DB connection and exits |

---

## Database Schema

```sql
CREATE TABLE employees (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    email       VARCHAR(100)   NOT NULL UNIQUE,
    phone       VARCHAR(15)    NOT NULL,
    department  VARCHAR(50)    NOT NULL,
    designation VARCHAR(50)    NOT NULL,
    salary      DECIMAL(10,2)  NOT NULL,
    hire_date   DATE           NOT NULL
);
```

---

## Concepts Demonstrated

- **Object-Oriented Programming** — Employee model, DAO pattern, separation of concerns
- **JDBC** — `DriverManager`, `PreparedStatement`, `ResultSet`, connection lifecycle
- **MySQL** — DDL (CREATE), DML (INSERT, SELECT, UPDATE, DELETE), parameterized queries
- **Input validation** — type-safe reading with retry loops and date format checking
- **SQL injection prevention** — all queries use `PreparedStatement` with bound parameters
