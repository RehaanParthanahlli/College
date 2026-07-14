# Module 1: Introduction to Database Systems

---

## 📑 Table of Contents

1. [Basic Definitions](#1-basic-definitions)
2. [Database Management System (DBMS)](#2-database-management-system-dbms)
3. [Need for Database Systems](#3-need-for-database-systems)
4. [Characteristics of Database Approach](#4-characteristics-of-database-approach)
5. [Advantages of Using the DBMS Approach](#5-advantages-of-using-the-dbms-approach)
6. [Database Applications Examples](#6-database-applications-examples)
7. [Actors on the Database Management Scene](#7-actors-on-the-database-management-scene)
8. [Types of DBMS & Data Models](#8-types-of-dbms--data-models)
9. [Categories of Data Models](#9-categories-of-data-models)
10. [Three-Schema Architecture](#10-three-schema-architecture)
11. [Data Abstraction & Data Independence](#11-data-abstraction--data-independence)
12. [Instances, Schema & Database State](#12-instances-schema--database-state)
13. [The Database System Environment](#13-the-database-system-environment)
14. [Centralized & Client-Server Architectures](#14-centralized--client-server-architectures)
15. [DBMS Languages](#15-dbms-languages)
16. [Introduction to Modern Databases](#16-introduction-to-modern-databases)
17. [Introduction to Relational Model](#17-introduction-to-relational-model)
18. [Integrity Constraints](#18-integrity-constraints)

---

## 1. Basic Definitions

### 1.1 Data

**Definition**: Data is a raw material or fact — unprocessed, unorganized facts that need to be processed to become meaningful.

**Examples**:
- Registration Number: `21BCE1234`
- Name: `John Doe`
- Age: `20`

> **DBMS Perspective**: In database terms, **data** refers to the actual values stored inside a table — the contents of each cell at the intersection of a row (tuple) and a column (attribute).

**Visualizing Data in a Table**:

```
+-----------+---------+-----+
| StudentID | Name    | Age |
+-----------+---------+-----+
| 1         | Alice   | 20  |  ← Row 1 (Tuple)
| 2         | Bob     | 22  |  ← Row 2 (Tuple)
| 3         | Charlie | 21  |  ← Row 3 (Tuple)
+-----------+---------+-----+
     ↑          ↑        ↑
  Column     Column   Column
(Attribute) (Attribute)(Attribute)
```

| Component | Terminology |
|-----------|-------------|
| Table | Relation |
| Row | Tuple / Record |
| Column | Attribute / Field |
| Cell Value | **Data** |

> ⚠️ **Common Misconception**: A row is NOT data — it is a *collection* of data values. The **individual cell values** (`Alice`, `20`, `Bob`) are the actual data.

---

### 1.2 Information

**Definition**: Information is data that has been processed, organized, or structured to provide meaning, context, and usefulness. It is the result of combining, comparing, and performing calculations on data.

| Aspect | Data | Information |
|--------|------|-------------|
| Nature | Raw facts | Processed, meaningful data |
| Example | `20`, `22`, `21` | "Average age of students is 21" |
| Form | Unprocessed | Organized, contextualized |
| Usefulness | Limited | Actionable |

**Example Transformation**:

**Raw Data (Students Table)**:
| StudentID | Name | Age |
|-----------|------|-----|
| 1 | Alice | 20 |
| 2 | Bob | 22 |
| 3 | Charlie | 21 |

**Information (Query Result)**:
```
+-------------------+
| Average StudentAge|
+-------------------+
| 21                |
+-------------------+
```

```sql
-- SQL to derive information from data
SELECT AVG(Age) AS Average_StudentAge FROM Students;
```

**Types of Information in DBMS**:

| Type | Description | SQL Example |
|------|-------------|-------------|
| **Descriptive** | Lists what exists in the database | `SELECT * FROM Students;` |
| **Comparative** | Highlights differences | `SELECT Name, Age FROM Students WHERE Age = (SELECT MAX(Age) FROM Students);` |
| **Aggregated** | Summarizes using functions | `SELECT AVG(Age), COUNT(*) FROM Students;` |
| **Derived/Analytical** | Transforms data for meaning | `SELECT Name, CASE WHEN Age >= 21 THEN 'Senior' ELSE 'Junior' END FROM Students;` |
| **Predictive** | Forecasts based on history | `SELECT AVG(EnrollmentCount) + 10 FROM PastEnrollments;` |

> 💡 **Key Insight**: Data is the **input**; Information is the **meaningful output** after processing.

---

### 1.3 Database

**Definition**: A database is a **collection of interrelated data** that represents some aspect of the real world, stored and accessed electronically through a computer system.

**Extended Definition**:
- An **organized collection of data**, generally stored and accessed electronically from a computer system.
- It is **controlled by a Database Management System (DBMS)**.
- A **database application** is any program that interacts with the database during execution.
- A **database system** includes the DBMS, the database itself, and the application programs that use it.

**Key Characteristics**:
- Modeled in **rows and columns** in a series of tables
- Data can be **accessed, arranged, managed, modified, and organized**
- Can be **manual or computerized** (modern databases are almost always computerized)

**Examples of Databases**:
- Department records
- Course catalogs
- Student grades
- Bank account information
- Airline reservations

> 📌 **In Short**: A database is a structured, meaningful, and purpose-driven collection of data, managed by a DBMS, representing real-world information.

---

### 1.4 Properties of Database

| # | Property | Explanation |
|---|----------|-------------|
| 1 | **Represents real world** | Models some aspect of reality (university, bank, airline) |
| 2 | **Logically coherent** | Data has inherent meaning and relationships |
| 3 | **Designed for specific purpose** | Built to serve particular needs |
| 4 | **Any size and complexity** | From small address books to global systems |
| 5 | **Manual or computerized** | Can be paper-based or digital |

---

## 2. Database Management System (DBMS)

### 2.1 What is DBMS?

**Definition**: A Database Management System is **software designed to store, retrieve, define, and manage data** in a database.

**Examples**: MS-Access, MySQL, SQL Server, Oracle, PostgreSQL, MariaDB, SQLite, IBM DB2

**Core Role**: The DBMS acts as an **intermediary** between:
- The **database** (the data itself)
- The **users/applications** (that need to use the data)

```
┌─────────────────┐     ┌─────────┐     ┌─────────────────┐
│   End Users     │◄───►│  DBMS   │◄───►│    Database     │
│  Applications   │     │ Software│     │  (Stored Data)  │
└─────────────────┘     └─────────┘     └─────────────────┘
        ▲                                      ▲
        └────────────── Queries ───────────────┘
```

> ⚠️ **Critical Distinction**: **DBMS ≠ Database**
> - **Database** = The data itself (the content)
> - **DBMS** = The software system that manages the data

---

### 2.2 DBMS Functionalities

| Category | Functions |
|----------|-----------|
| **Data Definition** | Define database in terms of data types, structures, and constraints |
| **Data Loading** | Construct or load initial database contents on secondary storage |
| **Data Manipulation** | Retrieval (querying, reports); Modification (insert, delete, update) |
| **Web Access** | Accessing the database through Web applications |
| **Security** | Protection measures to prevent unauthorized access |
| **Visualization** | Presentation and visualization of data |
| **Maintenance** | Maintaining database and associated programs over the lifetime |

---

### 2.3 Popular DBMS Software

| DBMS | Type | Developer |
|------|------|-----------|
| MySQL | Open-source RDBMS | Oracle Corporation |
| Microsoft Access | Desktop RDBMS | Microsoft |
| Oracle | Commercial RDBMS | Oracle Corporation |
| PostgreSQL | Open-source RDBMS | PostgreSQL Global Development Group |
| dBASE | File-based DBMS | dBASE LLC |
| FoxPro | File-based DBMS | Microsoft |
| SQLite | Embedded RDBMS | SQLite Consortium |
| IBM DB2 | Commercial RDBMS | IBM |
| LibreOffice Base | Desktop DBMS | The Document Foundation |
| MariaDB | Open-source RDBMS | MariaDB Foundation |
| Microsoft SQL Server | Commercial RDBMS | Microsoft |

---

## 3. Need for Database Systems

### 3.1 University Database Example

A university database illustrates core DBMS concepts:

**Entities (Data)**:
- Students
- Instructors  
- Classes

**Application Programs**:
- Add new students, instructors, and courses
- Register students for courses; generate class rosters
- Assign grades; compute GPA; generate transcripts

---

### 3.2 Problems with File Systems (Why DBMS is Needed)

Before DBMS, applications were built directly on file systems, leading to:

| Problem | Description | Real-World Impact |
|---------|-------------|-----------------|
| **Data Redundancy & Inconsistency** | Same data stored in multiple files/formats; updates don't propagate | Duplicate, conflicting records |
| **Difficulty Accessing Data** | Need new programs for each new task | High development cost |
| **Data Isolation** | Data scattered across multiple files and formats | Hard to get complete picture |
| **Integrity Problems** | Constraints buried in application code | Hard to enforce rules; easy to violate |
| **Atomicity of Updates** | Failures leave partial updates | Money transferred from A but not to B |
| **Concurrent Access Anomalies** | Multiple users overwrite each other's changes | Lost updates, incorrect balances |
| **Security Problems** | Hard to grant selective access | All-or-nothing access control |

**Detailed Explanations**:

**🔴 Data Redundancy & Inconsistency**:
In file systems, making backup copies is necessary, but when data is updated in one location, copies often fail to update — creating **inconsistent data**. Databases solve this through:
- **Transactions** following **ACID properties**
- **Normalization** eliminating insertion, deletion, and update anomalies

**ACID Properties**:
| Property | Meaning |
|----------|---------|
| **A**tomicity | All operations complete, or none do |
| **C**onsistency | Database remains valid before and after |
| **I**solation | Concurrent transactions don't interfere |
| **D**urability | Committed changes survive failures |

**🔴 Atomicity Example**:
> Transferring funds from Account A to Account B should either **complete entirely** or **not happen at all**. A failure mid-transfer must not leave money deducted from A but not added to B.

**🔴 Concurrent Access Example**:
> Two people read balance = ₹100 simultaneously. Both withdraw ₹50. Without concurrency control, balance becomes ₹50 instead of ₹0 (lost update).

---

### 3.3 Data Retrieval

**File System Approach**: Requires custom programs in high-level languages (C, Java) for every retrieval need.

**DBMS Approach**: Uses **SQL (Structured Query Language)** — standardized, fast, secure.

```sql
SELECT Name, Age FROM Students WHERE Age > 20;
```

---

### 3.4 Data Integrity

**Definition**: Ensures only valid, required data enters the database.

**Mechanism**: **Integrity Constraints** — rules enforced by the DBMS:
- Primary Key constraints
- Foreign Key constraints
- CHECK constraints
- NOT NULL constraints

```sql
CREATE TABLE Students (
    StudentID NUMBER PRIMARY KEY,
    Name VARCHAR2(50) NOT NULL,
    Age NUMBER CHECK (Age > 0)
);
```

---

### 3.5 Data Security

| Aspect | File System | DBMS |
|--------|-------------|------|
| Authentication | Basic file permissions | Multi-level (user, admin, role-based) |
| Authorization | All-or-nothing | Granular (SELECT, INSERT, UPDATE, DELETE) |
| Encryption | Not built-in | Often supported |

```sql
-- Grant specific privileges
GRANT SELECT, INSERT ON Students TO clerk_user;
REVOKE DELETE ON Students FROM clerk_user;
```

---

### 3.6 Data Indexing

**Problem**: File systems scan sequentially — slow for large data.

**Solution**: **Indexing** — computational technique for fast data identification and retrieval.

```sql
CREATE INDEX idx_student_name ON Students(Name);
```

**How it works**: Creates a data structure (typically B-Tree) that allows O(log n) lookup instead of O(n) scan.

---

## 4. Characteristics of Database Approach

### 4.1 Self-Describing Nature

A **catalog** (data dictionary) stores metadata — descriptions of data structures, types, and constraints.

- **Metadata = Data about data**
- Allows DBMS to work with different applications without modification
- Enables the database to be self-contained and self-describing

---

### 4.2 Insulation Between Programs and Data (Program-Data Independence)

Changing **data structures** or **storage organization** does NOT require changing application programs.

```
┌─────────────────┐         ┌─────────────────┐
│  Application    │◄───────►│   DBMS Layer    │
│    Program      │         │  (Insulation)   │
└─────────────────┘         └─────────────────┘
                                    │
                              ┌─────┴─────┐
                              ▼           ▼
                         ┌────────┐  ┌──────────┐
                         │ Schema │  │ Physical │
                         │(Logic) │  │ Storage  │
                         └────────┘  └──────────┘
```

---

### 4.3 Data Abstraction

A **data model** hides low-level storage details and presents users with a **conceptual view** of the database.

| Level | What User Sees | What is Hidden |
|-------|---------------|----------------|
| Physical | Raw bits, bytes, files | Everything above |
| Logical | Tables, relationships | Storage details |
| View | Relevant subset only | Irrelevant data |

---

### 4.4 Support of Multiple Views

Different users see **different subsets** of the same database:

| User Role | View |
|-----------|------|
| Student | Own grades, enrolled courses |
| Instructor | Class roster, all student grades |
| Administrator | Full database, statistics |
| Accountant | Fee payments, financial data |

---

### 4.5 Sharing of Data & Multi-User Transaction Processing

**Concurrency Control**: Ensures each transaction is correctly executed or aborted.

**Recovery**: Each completed transaction's effect is **permanently recorded**.

**OLTP (Online Transaction Processing)**: Supports hundreds of concurrent transactions per second.

---

## 5. Advantages of Using the DBMS Approach

### Core Advantages

| # | Advantage | Description |
|---|-----------|-------------|
| 1 | **Controlling Redundancy** | Normalization eliminates duplicate data |
| 2 | **Restricting Unauthorized Access** | Role-based security at multiple levels |
| 3 | **Persistent Storage** | Data survives program execution |
| 4 | **Efficient Query Processing** | Indexes, optimization, caching |
| 5 | **Backup and Recovery** | Automated protection against failures |
| 6 | **Multiple User Interfaces** | SQL, GUI, APIs, reporting tools |
| 7 | **Complex Relationships** | PK-FK relationships model reality |
| 8 | **Integrity Constraints** | Enforced at database level |
| 9 | **Rules and Triggers** | Automated actions on data changes |

### Additional Implications

| # | Implication | Benefit |
|---|-------------|---------|
| 1 | **Enforcing Standards** | Consistent naming, formats across organization |
| 2 | **Reduced Development Time** | Reusable schema and queries |
| 3 | **Flexibility** | Schema evolution without breaking apps |
| 4 | **Up-to-Date Information** | Real-time data availability |
| 5 | **Economies of Scale** | Centralized management reduces costs |

---

## 6. Database Applications Examples

### Enterprise Information Systems

| Domain | Data Managed |
|--------|-------------|
| **Sales** | Customers, products, purchases, orders |
| **Accounting** | Payments, receipts, assets, ledgers |
| **Human Resources** | Employees, salaries, payroll taxes, benefits |

### Industry-Specific Applications

| Industry | Database Use |
|----------|-------------|
| **Manufacturing** | Production management, inventory, supply chain |
| **Banking & Finance** | Accounts, loans, transactions, credit cards, stock market data |
| **Universities** | Student registration, grades, transcripts |
| **Airlines** | Reservations, schedules, crew management |
| **Telecommunication** | Call records, billing, prepaid balances |
| **Web Services** | E-commerce orders, recommendations, ads |
| **Navigation** | Locations, routes, points of interest |

### Modern Usage Patterns

- **ATM transactions** — direct database access
- **Mobile Apps** — backend database queries
- **Online Purchases** — inventory, payment, order tracking
- **Major Areas**: OLTP (Online Transaction Processing), Data Analytics

---

## 7. Actors on the Database Management Scene

### 7.1 Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    ACTORS ON THE SCREEN                      │
│  (Use & Control the Database)                               │
├─────────────────────────────────────────────────────────────┤
│  • End Users          • System Analysts                     │
│  • Application Programmers                                  │
├─────────────────────────────────────────────────────────────┤
│                   ACTORS BEHIND THE SCREEN                 │
│  (Design & Develop DBMS Software & Tools)                   │
├─────────────────────────────────────────────────────────────┤
│  • DBMS Software Developers                                 │
│  • Computer System Operators                                │
└─────────────────────────────────────────────────────────────┘
```

---

### 7.2 Database Administrators (DBA)

**Responsibilities**:
- Authorize access to the database
- Coordinate and monitor database usage
- Acquire software and hardware resources

**Technical Duties**:
| Duty | Description |
|------|-------------|
| Schema Definition | Design database structure |
| Storage Structure | Define access methods and file organization |
| Schema Modification | Evolve database as requirements change |
| Authorization | Grant/revoke data access privileges |
| Routine Maintenance | Backup, tuning, performance monitoring |

---

### 7.3 Database Designers

Define:
- **Content** — what data to store
- **Structure** — how data is organized
- **Constraints** — rules data must follow
- **Functions/Transactions** — operations on data

Must communicate with end-users to understand requirements.

---

### 7.4 End Users

| Type | Behavior | Examples |
|------|----------|----------|
| **Casual** | Access occasionally, ad-hoc queries | Managers checking reports |
| **Naïve/Parametric** | Repetitive, predefined operations | Bank tellers, reservation clerks |
| **Sophisticated** | Complex analysis, custom queries | Data scientists, engineers, analysts |
| **Stand-alone** | Personal database usage | Tax software, address books |

---

### 7.5 System Analysts and Application Programmers

- **System Analysts**: Design application specifications
- **Application Programmers**: Implement database applications using APIs (ODBC, JDBC)

---

### 7.6 Workers Behind the Screen

| Role | Contribution |
|------|-------------|
| **DBMS Software Developers** | Build database engines, tools, utilities |
| **Computer System Operators** | Maintain hardware, OS, network infrastructure |

---

## 8. Types of DBMS & Data Models

### 8.1 Classification Based on Data Model

| Model | Structure | Examples |
|-------|-----------|----------|
| **Relational** | Tables (rows & columns) | Oracle, MySQL, PostgreSQL |
| **Object-Oriented** | Objects (attributes + methods) | ObjectDB, db4o |
| **Big Data/NoSQL** | Key-value, Document, Column, Graph | MongoDB, Redis, Cassandra |
| **XML** | Hierarchical XML documents | Oracle XML DB, BaseX |
| **Network** | Graph with records and links | IDMS, TurboIMAGE |
| **Hierarchical** | Tree (parent-child) | IBM IMS |

---

### 8.2 Hierarchical DBMS

**Structure**: Tree-like, top-down or bottom-up. Each parent can have many children; each child has exactly one parent.

```
        ┌─────────┐
        │  Root   │
        │ (College)│
        └────┬────┘
             │
    ┌────────┼────────┐
    ▼        ▼        ▼
┌───────┐┌───────┐┌───────┐
│  CSE  ││  ECE  ││  IT   │
│(Dept) ││(Dept) ││(Dept) │
└───┬───┘└───────┘└───────┘
    │
┌───┴───┐
▼       ▼
Student Student
```

**Merits**:
- Simple design
- Strong data integrity via parent-child relationship
- Data sharing feasible (single database)
- Handles large volumes well

**De-Merits**:
- Complex implementation
- Insert, update, delete anomalies
- Maintenance difficult — structural changes cascade

---

### 8.3 Network DBMS

**Structure**: Graph-like (not tree). Records can have **multiple parents** — supports many-to-many relationships.

```
    ┌─────────┐         ┌─────────┐
    │Student A│◄───────►│ Course X│
    └────┬────┘         └────┬────┘
         │    ╲       ╱      │
         │     ╲     ╱       │
         │      ╲   ╱        │
         ▼       ▼ ▼         ▼
    ┌─────────┐   ╱ ╲   ┌─────────┐
    │Student B│◄─╱   ╲─►│ Course Y│
    └─────────┘         └─────────┘
```

**Merits**:
- Handles one-one, one-many, many-many relationships
- Based on standards (CODASYL)
- Isolates programs from structural details

**De-Merits**:
- Pointer complexity
- Structural independence is hard to achieve

---

### 8.4 Relational DBMS (RDBMS)

**Structure**: Data organized into **tables (relations)** related via **keys**.

```
┌───────────────┐         ┌─────────────────┐
│    STUDENT    │         │   DEPARTMENT    │
├─────┬─────────┤         ├────────┬────────┤
│ SID │  Name   │◄────────│ DeptID │  Name  │
├─────┼─────────┤  FK=PK  ├────────┼────────┤
│ 101 │   Anu   │    │    │  D01   │  CSE   │
│ 102 │   Raj   │────┘    │  D02   │  ECE   │
│ 103 │  Sara   │         │  D03   │   IT   │
└─────┴─────────┘         └────────┴────────┘
```

**Merits**:
- No insert/update/delete anomalies
- Schema changes don't require full reorganization
- Easy to implement and maintain

**De-Merits**:
- Inefficiencies with very large data volumes
- Requires powerful hardware

```sql
CREATE TABLE Students (
    StudentID NUMBER PRIMARY KEY,
    Name VARCHAR2(50),
    Age NUMBER
);
```

---

### 8.5 Object-Oriented DBMS

**Structure**: Data stored as **objects** with **attributes** (data) and **methods** (operations).

```sql
-- Oracle supports object types
CREATE TYPE Address AS OBJECT (
    Street VARCHAR2(50),
    City   VARCHAR2(50)
);

CREATE TABLE Employee (
    EmpID NUMBER PRIMARY KEY,
    Name VARCHAR2(50),
    Addr Address  -- Object type as column
);
```

**Use Cases**: Multimedia, CAD, complex scientific data

---

## 9. Categories of Data Models

### 9.1 What is a Data Model?

A **data model** is a framework that defines:
- **Structure** of the database (tables, objects, trees, graphs)
- **Relationships** among data
- **Constraints** on data
- **Operations** allowed on data

> **Purpose**: The blueprint that translates real-world requirements into database design.

---

### 9.2 High-Level (Conceptual/Semantic) Data Models

- Close to how **users perceive** data
- Uses concepts: **entities, attributes, relationships**
- **Example**: Entity-Relationship (ER) Model

```
    ┌──────────┐              ┌──────────┐
    │ STUDENT  │──────────────│  COURSE  │
    │──────────│   Enrolls    │──────────│
    │ SID (PK) │◄────────────►│ CID (PK) │
    │ Name     │              │ Title    │
    │ Age      │              │ Credits  │
    └──────────┘              └──────────┘
```

---

### 9.3 Representational (Implementation) Data Models

- Bridge between conceptual and physical
- Used in actual DBMS implementations
- **Examples**: Relational, Network, Hierarchical, Object-Relational

| Model | Characteristic |
|-------|---------------|
| Relational | Tables, rows, columns |
| Network | Graph of records with links |
| Hierarchical | Tree structure |
| Object-Relational | Hybrid: tables + object features |

---

### 9.4 Low-Level (Physical/Internal) Data Models

Describes how data is **stored on disk/memory**:
- Record formats
- Record orderings
- **Access paths**: Indexing, hashing for efficient search

```
┌─────────────────────────────────────┐
│  Physical Storage Structure        │
├─────────────────────────────────────┤
│  • Data files (tablespaces)         │
│  • Indexes (B-Trees, Hash)          │
│  • Buffer management                │
│  • Disk block allocation            │
└─────────────────────────────────────┘
```

---

### 9.5 Self-Describing Data Models

Combines **data + metadata** in the same structure.

**Examples**:
- **XML**: `<student id="101"><name>Anu</name></student>`
- **NoSQL/JSON**: Documents contain both structure and values

```json
{
    "_id": "student_101",
    "name": "Anu",
    "dept": "CSE",
    "_schema_version": "2.1"
}
```

---

### 9.6 History of Data Models

| Model | Year | Pioneer | First Commercial System |
|-------|------|---------|------------------------|
| **Relational** | 1970 | E.F. Codd (IBM) | DB2 (1981-82) |
| **Network** | 1964-65 | Honeywell (IDS) | IDMS, DMS 1100, IMAGE |
| **Hierarchical** | 1965 | IBM + North American Rockwell | IMS |
| **Object-Oriented** | 1980s | Persistent OOP languages | ObjectStore, Versant, GemStone |
| **Object-Relational** | 1990s | Informix Universal Server | Oracle 10i, DB2, SQL Server |
| **Self-Describing/NoSQL** | 2000s | Web applications | MongoDB, Cassandra, Neo4j |

---

## 10. Three-Schema Architecture

### 10.1 Purpose

Proposed to achieve:
- ✅ Use of catalog to store descriptions (metadata)
- ✅ **Program-data independence**
- ✅ Support of **multiple views**

> **Goal**: Separate user applications from physical database storage.

---

### 10.2 The Three Levels

```
┌─────────────────────────────────────────────┐
│           EXTERNAL LEVEL (View)             │
│    ┌─────────┐  ┌─────────┐  ┌─────────┐  │
│    │ User A  │  │ User B  │  │ User C  │  │
│    │ (Grades)│  │(Roster) │  │(Finance)│  │
│    └────┬────┘  └────┬────┘  └────┬────┘  │
│         └──────────────┼──────────────┘      │
│                        ▼                    │
│              External Schemas               │
└────────────────────────┬────────────────────┘
                         │ Mapping
┌────────────────────────▼────────────────────┐
│         CONCEPTUAL LEVEL (Schema)           │
│                                             │
│    • Entities, data types, relationships    │
│    • User operations, constraints           │
│    • Complete database structure            │
│    • Uses representational data model       │
│                                             │
└────────────────────────┬────────────────────┘
                         │ Mapping
┌────────────────────────▼────────────────────┐
│          INTERNAL LEVEL (Physical)          │
│                                             │
│    • Physical storage structure             │
│    • Indexes, file organizations            │
│    • Access paths, buffer management          │
│    • Uses physical data model               │
│                                             │
└─────────────────────────────────────────────┘
```

---

### 10.3 Data Independence

| Type | Definition | Example |
|------|------------|---------|
| **Logical Data Independence** | Change conceptual schema without changing external schemas or applications | Add new column to table; existing views still work |
| **Physical Data Independence** | Change internal schema without changing conceptual schema | Reorganize files, add indexes; SQL queries unchanged |

> **Key Principle**: When a lower-level schema changes, **only the mappings** need updating. Higher-level schemas and applications remain **unchanged**.

---

## 11. Data Abstraction & Data Independence

### 11.1 Levels of Data Abstraction

| Level | Description | Users |
|-------|-------------|-------|
| **Physical** | How data is actually stored: complex low-level structures | DBAs, system programmers |
| **Logical** | What data is stored and relationships among data | DBAs, developers |
| **View** | Only relevant portion of database for specific users | End users |

---

### 11.2 Physical vs. Logical Data Independence

```
┌─────────────────────────────────────────────────────────┐
│  LOGICAL DATA INDEPENDENCE                              │
│  ┌─────────────┐      ┌─────────────┐                 │
│  │   BEFORE    │      │    AFTER    │                 │
│  │  Student()  │  →   │Student(Phone)│  ← Added column │
│  │  - SID      │      │  - SID      │                 │
│  │  - Name     │      │  - Name     │                 │
│  │  - Age      │      │  - Age      │                 │
│  └─────────────┘      │  - Phone    │                 │
│                       └─────────────┘                 │
│  External views and applications: UNCHANGED ✓        │
└─────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────┐
│  PHYSICAL DATA INDEPENDENCE                             │
│  ┌─────────────┐      ┌─────────────┐                 │
│  │   BEFORE    │      │    AFTER    │                 │
│  │ Heap File   │  →   │ B-Tree Index│  ← New index    │
│  │ (slow scan) │      │ (fast lookup)│                 │
│  └─────────────┘      └─────────────┘                 │
│  Conceptual schema and applications: UNCHANGED ✓       │
└─────────────────────────────────────────────────────────┘
```

---

## 12. Instances, Schema & Database State

### 12.1 Schema vs. Instance

| Aspect | Schema | Instance / State |
|--------|--------|------------------|
| **Definition** | Overall design of database | Data at a particular moment |
| **Also called** | Intension | Extension |
| **Change frequency** | Very rarely (schema evolution) | Every update |
| **Contains** | Structure, constraints, relationships | Actual values/records |

```
Schema (Design)                    Instance (Data)
┌─────────────────┐               ┌─────────────────┐
│  STUDENT        │               │ SID │ Name  │ Age │
│  ─────────────  │               ├─────┼───────┼─────┤
│  SID: INT (PK)  │  ←──────→     │ 101 │ Anu   │ 20  │
│  Name: VARCHAR  │               │ 102 │ Raj   │ 22  │
│  Age: INT       │               │ 103 │ Sara  │ 21  │
│  CHECK: Age > 0 │               └─────┴───────┴─────┘
└─────────────────┘
```

---

### 12.2 Types of Database States

| State | Description |
|-------|-------------|
| **Empty State** | Schema defined, no data yet |
| **Initial State** | First populated with data |
| **Current State** | After all updates at any moment |
| **Valid State** | Satisfies all schema constraints |

---

## 13. The Database System Environment

### 13.1 DBMS Component Modules

```
┌─────────────────────────────────────────────────────────────┐
│                    USER INTERFACES                          │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────────────┐  │
│  │End Users│ │  DBAs   │ │Programmers│ │System Analysts │  │
│  └────┬────┘ └────┬────┘ └────┬────┘ └────────┬────────┘  │
│       └─────────────┴──────────┴─────────────────┘          │
│                         │                                   │
│              ┌──────────▼──────────┐                      │
│              │   Query Processor     │                      │
│              │  (Parse, Optimize,     │                      │
│              │   Execute SQL)        │                      │
│              └──────────┬────────────┘                      │
│                         │                                   │
│              ┌──────────▼──────────┐                      │
│              │  Transaction Manager │                      │
│              │  (ACID, Concurrency, │                      │
│              │   Recovery)          │                      │
│              └──────────┬────────────┘                      │
│                         │                                   │
│              ┌──────────▼──────────┐                      │
│              │   Storage Manager   │                      │
│              │  (Files, Indexes,   │                      │
│              │   Buffer, Disk)     │                      │
│              └─────────────────────┘                      │
└─────────────────────────────────────────────────────────────┘
```

---

### 13.2 Database System Utilities

| Utility | Purpose |
|---------|---------|
| **Loading** | Import data from external sources |
| **Backup** | Create copies for disaster recovery |
| **Reorganization** | Defragment, restructure for performance |
| **Performance Monitoring** | Query optimization, tuning |
| **Data Dictionary** | Metadata catalog management |

---

## 14. Centralized & Client-Server Architectures

### 14.1 Centralized DBMS

**Structure**: Everything at a single location (mainframe).

```
┌─────────────────────────────────────┐
│         CENTRALIZED DBMS             │
│  ┌─────────────────────────────┐   │
│  │  DBMS + Hardware + Apps +   │   │
│  │  User Interface (all in one)│   │
│  └─────────────────────────────┘   │
│              │                      │
│         LAN/WAN                     │
│      ┌─────┼─────┐                 │
│      ▼     ▼     ▼                 │
│   User1  User2  User3               │
└─────────────────────────────────────┘
```

| Advantages | Disadvantages |
|------------|---------------|
| ✅ Maximum data integrity | ❌ Slower access (network delays) |
| ✅ Minimal redundancy | ❌ Traffic bottlenecks |
| ✅ Stronger security | ❌ Concurrency issues |
| ✅ Cost-effective | ❌ Single point of failure |
| ✅ Easy access | |

---

### 14.2 Client-Server DBMS

**Two-Tier Architecture**:

```
┌─────────────┐         ┌─────────────────┐
│   CLIENT    │◄───────►│     SERVER      │
│  (UI + App) │  ODBC/  │  (DBMS Software) │
│             │  JDBC   │                 │
└─────────────┘         └─────────────────┘
```

**Three-Tier Architecture** (Web Applications):

```
┌─────────┐     ┌─────────────┐     ┌─────────────┐
│ Client  │◄───►│   Web/App   │◄───►│   Database  │
│(Browser)│ HTTP │   Server    │ SQL │   Server    │
└─────────┘     │(Business    │     │  (DBMS)     │
                │ Logic,       │     └─────────────┘
                │ Security)    │
                └─────────────┘
                • Encrypt at server
                • Decrypt at client
```

| Advantages | Disadvantages |
|------------|---------------|
| ✅ Centralized control | ❌ Server failure stops operations |
| ✅ Scalable (upgrade components) | ❌ Complex administration |
| ✅ Flexible (new tech integration) | ❌ Higher costs |
| ✅ Interoperability | ❌ Network congestion |

---

### 14.3 File System vs. DBMS

| Aspect | File System | DBMS |
|--------|-------------|------|
| **Data Storage** | Raw files | Structured tables/objects |
| **Redundancy** | High | Minimal (normalization) |
| **Consistency** | Hard to maintain | ACID properties |
| **Access** | Custom programs | Standard SQL |
| **Security** | Basic permissions | Multi-level auth |
| **Concurrency** | Poor | Locks, transactions |
| **Backup/Recovery** | Manual | Automated |
| **Indexing** | Not supported | Built-in |
| **Integrity** | Code-buried | Schema constraints |
| **Scalability** | Difficult | Designed for scale |

---

## 15. DBMS Languages

### 15.1 Data Definition Language (DDL)

Defines and manages database **schema**.

| Command | Purpose |
|---------|---------|
| `CREATE` | Create tables, indexes, schemas |
| `ALTER` | Modify existing structure |
| `DROP` | Delete schema objects |
| `TRUNCATE` | Remove all rows from table |
| `RENAME` | Rename objects |

```sql
CREATE TABLE Student (
    SID INT PRIMARY KEY,
    Name VARCHAR(50),
    Dept VARCHAR(20),
    CGPA DECIMAL(3,2)
);
```

---

### 15.2 Data Manipulation Language (DML)

Accesses and manipulates **data**.

| Command | Purpose |
|---------|---------|
| `SELECT` | Read/retrieve records |
| `INSERT` | Add new records |
| `UPDATE` | Modify existing records |
| `DELETE` | Remove records |

---

### 15.3 Data Control Language (DCL)

Controls **access and privileges**.

| Command | Purpose |
|---------|---------|
| `GRANT` | Give permissions to users |
| `REVOKE` | Remove permissions |

---

### 15.4 Transaction Control Language (TCL)

Manages **transaction boundaries**.

| Command | Purpose |
|---------|---------|
| `COMMIT` | Save changes permanently |
| `ROLLBACK` | Undo uncommitted changes |

---

### 15.5 Types of DML

| Type | Description | Example |
|------|-------------|---------|
| **Low-Level (Procedural)** | User specifies HOW to access data | Network/hierarchical DMLs |
| **High-Level (Non-procedural/Declarative)** | User specifies WHAT data is needed | SQL, QBE (Query By Example) |

---

## 16. Introduction to Modern Databases

### 16.1 Overview

| Database Type | Core Strength | Use Cases |
|---------------|---------------|-----------|
| **Relational** | Consistency, ACID | Banking, ERP, CRM |
| **Semi-Structured** | Flexibility | Web apps, mobile backends |
| **Graph** | Relationship analysis | Social networks, fraud detection |
| **Vector** | Similarity search | AI/ML, LLMs, recommendation |
| **Time-Series** | Trend analysis | IoT, monitoring, finance |
| **Distributed** | Scalability | Global applications, big data |
| **Unstructured** | Large file storage | Media, documents, backups |

---

### 16.2 Relational Database

- Data in **tables (relations)**
- Components: Rows (tuples), Columns (attributes), Primary Keys, Foreign Keys

| SID | Name | Dept |
|-----|------|------|
| 101 | Anu | CSE |
| 102 | Raj | ECE |

**Advantages**: High consistency, ACID transactions, SQL support  
**Examples**: MySQL, PostgreSQL, Oracle, SQL Server

---

### 16.3 Semi-Structured Database

- **No fixed schema** — flexible structure
- Uses: JSON, XML, YAML, BSON

```json
{
    "StudentID": 101,
    "Name": "Anu",
    "Department": "CSE",
    "Skills": ["Python", "Java"]
}
```

**Advantages**: Flexible, easy to modify, great for web apps  
**Examples**: MongoDB, CouchDB, Firebase Firestore

---

### 16.4 Graph Database

- **Nodes** (entities) + **Edges** (relationships) + **Properties**

```
    ┌─────────┐  FRIENDS_WITH  ┌─────────┐
    │  Alice  │◄──────────────►│   Bob   │
    └────┬────┘                └────┬────┘
         │     ╲              ╱      │
         │      ╲   WORKS_AT ╱       │
         ▼       ╲    │    ╱        ▼
    ┌─────────┐    ╲  │  ╱     ┌─────────┐
    │  Google │◄─────►▼◄──────►│  Meta   │
    └─────────┘                 └─────────┘
```

**Applications**: Social networks, recommendation systems, fraud detection, knowledge graphs, road navigation  
**Examples**: Neo4j, Amazon Neptune, TigerGraph

---

### 16.5 Vector Database

- Stores **embeddings** (numerical vectors) instead of raw values
- **Embedding**: AI converts text/image/audio into vectors where similar items have similar vectors

```
"Database course" → [0.23, -0.87, 0.15, ..., 0.91]
"SQL tutorial"    → [0.25, -0.82, 0.19, ..., 0.88]  ← Similar!
"Chocolate cake"  → [-0.71, 0.34, 0.92, ..., -0.12] ← Different!
```

**Applications**: AI chatbots, LLMs, RAG systems, image search, voice search  
**Examples**: Pinecone, Milvus, Weaviate, ChromaDB, FAISS

---

### 16.6 Time-Series Database

- Optimized for **time-stamped data**

| Time | Temperature |
|------|-------------|
| 10:00 | 30°C |
| 10:01 | 31°C |
| 10:02 | 32°C |

> Every record contains a timestamp.

**Applications**: IoT, weather, stock market, server monitoring, smart cities  
**Examples**: InfluxDB, OpenTSDB, TimescaleDB

---

### 16.7 Distributed Database

- Spread across **multiple computers/sites**

| Fragmentation Type | Description |
|-------------------|-------------|
| **Horizontal** | Rows split across sites |
| **Vertical** | Columns split across sites |

**Advantages**: High availability, scalability, fault tolerance, load balancing  
**Examples**: Google Spanner, Cassandra, CockroachDB

---

### 16.8 Unstructured Database

- Stores data **without predefined format**

| Data Type | Examples |
|-----------|----------|
| Images | Google Photos, medical imaging |
| Videos | Netflix, YouTube |
| Audio | Spotify, podcasts |
| Documents | PDFs, emails, contracts |

**Examples**: Elasticsearch, Hadoop HDFS, Amazon S3, Azure Blob Storage

---

## 17. Introduction to Relational Model

### 17.1 Background

- **Developed by**: Dr. Edgar F. Codd (IBM, 1970)
- **Based on**: Mathematical Relations, Set Theory, Predicate Logic
- **Storage**: Information stored as **Tables (Relations)**

**Why Study It?**
- Most widely used model
- Vendors: IBM, Informix, Microsoft, Oracle, Sybase
- Legacy competitor: Hierarchical (IBM IMS)
- Modern competitor: Object-oriented (ObjectStore, Versant)
- Emerging synthesis: **Object-Relational** (Oracle, DB2, Informix UDS)

---

### 17.2 What is a Relation?

A **relation** is a two-dimensional table consisting of rows and columns.

**Example**: STUDENT

| SID | Name | Dept | CGPA |
|-----|------|------|------|
| 101 | Anu | CSE | 8.5 |
| 102 | Raj | ECE | 8.9 |
| 103 | Sara | IT | 9.1 |

---

### 17.3 Terminology

| Relational Term | Meaning | Synonym |
|-----------------|---------|---------|
| Relation | Table | — |
| Tuple | Row | Record |
| Attribute | Column | Field |
| Degree | Number of columns | Arity |
| Cardinality | Number of rows | — |
| Domain | Allowed values for attribute | — |
| Schema | Table structure | Intension |
| Instance | Table contents at a moment | Extension |

---

### 17.4 Characteristics of Relations

1. ✅ **Unique table name**
2. ✅ **Unique attribute names**
3. ✅ **Atomic values** (no multi-valued attributes)
4. ✅ **No duplicate rows**
5. ✅ **Order of rows not important**
6. ✅ **Order of columns not important**
7. ✅ **Every column has same data type**

---

### 17.5 Relational Database Definitions

**Relational Database**: A set of relations

**Relation = Schema + Instance**

| Component | Description | Example |
|-----------|-------------|---------|
| **Schema** | Name + attribute names & types | `Students(sid:string, name:string, login:string, age:integer, gpa:real)` |
| **Instance** | Actual table with rows & columns | 3 rows, 5 columns |

**Cardinality** = number of rows  
**Degree/Arity** = number of columns

---

### 17.6 Example Instance

**Students Relation**: Cardinality = 3, degree = 5, all rows distinct

| sid | name | login | age | gpa |
|-----|------|-------|-----|-----|
| 53666 | Jones | jones@cs | 18 | 3.4 |
| 53688 | Smith | smith@ee | 18 | 3.2 |
| 53650 | Smith | smith@math | 19 | 3.8 |

---

### 17.7 Creating Relations in SQL

```sql
CREATE TABLE Students (
    sid CHAR(20),
    name CHAR(20),
    login CHAR(10),
    age INTEGER,
    gpa REAL
);

CREATE TABLE Enrolled (
    sid CHAR(20),
    cid CHAR(20),
    grade CHAR(2)
);
```

> The type (domain) of each field is specified and **enforced by the DBMS** whenever tuples are added or modified.

---

## 18. Integrity Constraints

### 18.1 What are Integrity Constraints?

**Definition**: A set of **rules** to maintain the quality and validity of information in a database.

- Ensure insertion, updating, and deletion do not violate data integrity
- Specified when schema is defined
- Checked when relations are modified
- A **legal instance** satisfies all specified ICs

**Why They Matter**:
- ✅ Data accuracy
- ✅ Valid relationships
- ✅ Prevent duplicate/invalid records
- ✅ Avoid data entry errors

---

### 18.2 Types of Integrity Constraints

| Type | Rule | Example |
|------|------|---------|
| **Domain** | Values must belong to valid set | `Age` must be integer > 0 |
| **Entity Integrity** | Primary key cannot be NULL | `EmpID` must have a value |
| **Referential Integrity** | Foreign key must match PK or be NULL | `DeptID` in Employee must exist in Department |
| **Key** | Keys must uniquely identify tuples | No duplicate `SID` values |

---

### 18.3 Domain Constraints

Defines valid set of values for an attribute.

| ID | NAME | SEMESTER | AGE |
|----|------|----------|-----|
| 1000 | Tom | 1st | 17 |
| 1001 | Johnson | 2nd | 24 |
| 1002 | Leonardo | 5th | 21 |
| 1003 | Kate | 3rd | 19 |
| 1004 | Morgan | 8th | **A** |

> ❌ **A is NOT allowed** — AGE is an integer attribute.

---

### 18.4 Entity Integrity Constraints

**Primary key value CANNOT be NULL.**

| EMP_ID | EMP_NAME | SALARY |
|--------|----------|--------|
| 123 | Jack | 30000 |
| 142 | Harry | 60000 |
| 164 | John | 20000 |
| **NULL** | Jackson | 27000 |

> ❌ **NULL in Primary Key is NOT allowed** — cannot identify the row.

---

### 18.5 Referential Integrity Constraints

If a **foreign key** in Table 1 refers to the **primary key** of Table 2, every FK value must:
- Be **NULL**, OR
- **Exist** in Table 2

**Employee Table (Table 1)**:

| EMP_NAME | NAME | AGE | D_No |
|----------|------|-----|------|
| 1 | Jack | 20 | 11 |
| 2 | Harry | 40 | 24 |
| 3 | John | 27 | **18** ← ❌ NOT in Department |
| 4 | Devil | 38 | 13 |

**Department Table (Table 2)**:

| D_No | D_Location |
|------|------------|
| 11 | Mumbai |
| 24 | Delhi |
| 13 | Noida |

> ❌ **D_No = 18 is invalid** — not defined as Primary Key in Department table.

---

### 18.6 Key Constraints

- An entity set can have multiple **candidate keys**
- One candidate key is chosen as **primary key**
- Primary key must be **unique and non-NULL**

| ID | NAME | SEMESTER | AGE |
|----|------|----------|-----|
| 1000 | Tom | 1st | 17 |
| 1001 | Johnson | 2nd | 24 |
| 1002 | Leonardo | 5th | 21 |
| 1003 | Kate | 3rd | 19 |
| **1002** | Morgan | 8th | 22 |

> ❌ **Duplicate ID 1002** — violates key constraint.

---

### 18.7 Superkey, Candidate Key, Primary Key

| Term | Definition |
|------|------------|
| **Superkey** | Set of fields where no two tuples have same values |
| **Candidate Key** | Minimal superkey (no proper subset is a superkey) |
| **Primary Key** | One candidate key chosen by DBA |
| **Foreign Key** | Logical pointer referencing another table's PK |

---

### 18.8 Primary and Candidate Keys in SQL

```sql
CREATE TABLE Students (
    sid CHAR(20),
    login CHAR(10),
    gpa REAL,
    ...
    PRIMARY KEY (sid),      -- Chosen primary key
    UNIQUE (login)          -- Candidate key
);
```

> "No two students have the same sid and no two students have the same login."

```sql
CREATE TABLE Enrolled (
    sid CHAR(20),
    cid CHAR(20),
    grade CHAR(2),
    PRIMARY KEY (sid, cid)  -- Composite primary key
);
```

> "For a given student and course, there is a single grade."

---

### 18.9 Foreign Keys and Referential Integrity

**Foreign Key**: Set of fields in one relation that refers to a tuple in another (or same) relation. Must correspond to the primary key of the referenced relation. Like a **logical pointer**.

```
┌─────────────────┐         ┌─────────────────┐
│    Enrolled     │         │    Students     │
│  sid │ cid │ g │         │  sid │ name │...│
├──────┼─────┼───┤   FK    ├──────┼──────┼───┤
│ 53666│ CS101│ A │◄────────│ 53666│ Jones│   │
│ 53688│ CS101│ B │◄────────│ 53688│ Smith│   │
│ 53650│ EE101│ A │◄────────│ 53650│ Smith│   │
└──────┴─────┴───┘         └──────┴──────┴───┘
         │                        ▲
         └────────────────────────┘
           Referential Integrity
```

> If all foreign key constraints are enforced → **referential integrity achieved** (no dangling references).

**Data model without referential integrity?** Links in HTML!

---

### 18.10 Foreign Keys in SQL

```sql
CREATE TABLE Enrolled (
    sid CHAR(20),
    cid CHAR(20),
    grade CHAR(2),
    PRIMARY KEY (sid, cid),
    FOREIGN KEY (sid) REFERENCES Students (sid)
);
```

> Only students listed in the Students relation can enroll in courses.

---

### 18.11 Constraints on NULL

| Constraint | NULL Allowed? |
|------------|---------------|
| **Primary Key** | ❌ NEVER |
| **Foreign Key** | ✅ Yes (unless specified NOT NULL) |

```sql
Name VARCHAR(50) NOT NULL  -- Explicitly disallow NULL
```

---

### 18.12 Enforcing Referential Integrity

Consider: `Enrolled.sid` is a foreign key referencing `Students.sid`.

| Scenario | Options |
|----------|---------|
| Insert Enrolled with non-existent sid | **Reject** |
| Delete a Students tuple that is referenced | (1) CASCADE delete referencing tuples, (2) Reject, (3) SET NULL, (4) SET DEFAULT |
| Update primary key of referenced Students tuple | Same 4 options |

---

### 18.13 Referential Integrity in SQL

SQL supports **4 options** on deletes and updates:

| Option | Behavior |
|--------|----------|
| **NO ACTION** (default) | Reject the delete/update |
| **CASCADE** | Delete/update all referencing tuples |
| **SET NULL** | Set FK value to NULL |
| **SET DEFAULT** | Set FK to default value |

```sql
CREATE TABLE Enrolled (
    sid CHAR(20),
    cid CHAR(20),
    grade CHAR(2),
    PRIMARY KEY (sid, cid),
    FOREIGN KEY (sid)
        REFERENCES Students
        ON DELETE CASCADE        -- Auto-delete enrollments
        ON UPDATE SET DEFAULT    -- Set to default if sid changes
);
```

---

### 18.14 Constraint Violations

A **constraint violation** occurs when INSERT, UPDATE, or DELETE breaks database rules.

**INSERT Violations**:

```sql
-- Example 1: Duplicate Primary Key
INSERT INTO Student VALUES (101, 'Kumar', 'D01');
-- ❌ ERROR: PRIMARY KEY constraint violated. Student ID 101 already exists.

-- Example 2: Invalid Foreign Key
INSERT INTO Student VALUES (103, 'Priya', 'D10');
-- ❌ ERROR: FOREIGN KEY constraint violated. Department D10 does not exist.
```

**UPDATE Violations**:

```sql
-- Example 1: Invalid Foreign Key
UPDATE Student SET DeptID = 'D20' WHERE SID = 101;
-- ❌ ERROR: Department D20 not available.

-- Example 2: NULL Primary Key
UPDATE Student SET SID = NULL WHERE SID = 101;
-- ❌ ERROR: Primary Key cannot be NULL.
```

**DELETE Violations**:

```sql
-- Department Table:        Student Table:
-- ┌──────┐                 ┌─────┬──────┐
-- │DeptID│                 │ SID │DeptID│
-- ├──────┤                 ├─────┼──────┤
-- │ D01  │                 │ 101 │ D01  │  ← Referenced!
-- └──────┘                 └─────┴──────┘

DELETE FROM Department WHERE DeptID = 'D01';
-- ❌ ERROR: Cannot delete! Students still reference D01.
-- This is a REFERENTIAL INTEGRITY VIOLATION.
```

---

## 📊 Quick Reference: Synonyms

| Formal Term | Common Synonyms |
|-------------|-----------------|
| Relation | Table |
| Tuple | Row, Record |
| Attribute | Column, Field |
| Data | Cell value |
| Schema | Intension (design) |
| Instance | Extension, State, Snapshot |
| DBMS | Database engine, Database server |

---

## 🎯 Summary

| Topic | Key Takeaway |
|-------|-------------|
| **Data vs Information** | Data = raw facts; Information = processed meaning |
| **Database** | Organized, interrelated, purposeful data collection |
| **DBMS** | Software that manages database access, security, integrity |
| **Need for DBMS** | Solves redundancy, inconsistency, security, concurrency issues of file systems |
| **Characteristics** | Self-describing, program-data independence, abstraction, multiple views, multi-user processing |
| **Data Models** | Conceptual → Representational → Physical; plus Self-describing |
| **Three-Schema Architecture** | External → Conceptual → Internal; enables data independence |
| **Schema vs Instance** | Schema = stable design; Instance = changing data |
| **DBMS Languages** | DDL (define), DML (manipulate), DCL (control), TCL (transaction) |
| **Modern Databases** | Relational, Semi-structured, Graph, Vector, Time-series, Distributed, Unstructured |
| **Relational Model** | Tables, tuples, attributes, keys, degrees, cardinality |
| **Integrity Constraints** | Domain, Entity, Referential, Key — enforce data quality |
