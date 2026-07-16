# ORACLE DBMS: Commands Mapped to Core Concepts
## Complete Reference for Student 25BCE1087 | PDB: COLLEGE_PDB

---

## CONCEPT 1: DATA MODEL
**What it is:** The relational framework — tables, rows, columns, keys.
**Oracle uses:** Relational Data Model

```sql
-- No direct SQL command. This is the FOUNDATION.
-- Everything you create follows this model.
```

---

## CONCEPT 2: DATABASE SYSTEM ARCHITECTURE (Three-Level)

### 2A. INTERNAL / PHYSICAL LEVEL
**What it is:** How data sits on disk. B-trees, blocks, storage.
**Do YOU touch it?** ❌ NO — DBMS handles it automatically.

```sql
-- You ONLY write this (logical command):
CREATE INDEX idx_student_name ON Student(name);
-- DBMS internally builds B-tree on disk (PHYSICAL LEVEL)
-- You never see the B-tree. Abstraction hides it.
```

### 2B. CONCEPTUAL / LOGICAL LEVEL
**What it is:** YOUR SCHEMA — tables, columns, relationships, constraints.
**Do YOU touch it?** ✅ YES — This is YOUR ENTIRE JOB as a student.

```sql
-- CREATE TABLE = LOGICAL LEVEL SCHEMA DESIGN
-- You define structure, data types, relationships

CREATE TABLE Department (
    dept_id     NUMBER PRIMARY KEY,           -- IC: PRIMARY KEY (entity integrity)
    dept_name   VARCHAR2(50) NOT NULL UNIQUE, -- IC: NOT NULL + UNIQUE
    established DATE DEFAULT SYSDATE            -- IC: DEFAULT value
);

CREATE TABLE Student (
    roll_no     VARCHAR2(10) PRIMARY KEY,     -- IC: PRIMARY KEY
    name        VARCHAR2(50) NOT NULL,        -- IC: NOT NULL
    email       VARCHAR2(100) UNIQUE,         -- IC: UNIQUE
    age         NUMBER CHECK (age >= 17 AND age <= 100),  -- IC: CHECK constraint
    dept_id     NUMBER NOT NULL,              -- IC: NOT NULL + FOREIGN KEY below
    enrollment_date DATE DEFAULT SYSDATE,     -- IC: DEFAULT

    -- IC: FOREIGN KEY (referential integrity)
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
);
```

### 2C. EXTERNAL / VIEW LEVEL
**What it is:** Virtual tables hiding complexity. User-specific perspectives.
**Do YOU touch it?** ✅ YES — You create views for abstraction.

```sql
-- CREATE VIEW = EXTERNAL LEVEL ABSTRACTION
-- Hides JOIN complexity from end users

CREATE VIEW Student_Department_View AS
SELECT 
    s.roll_no,
    s.name,
    s.email,
    d.dept_name,
    d.established
FROM Student s
JOIN Department d ON s.dept_id = d.dept_id;

-- Now user simply does:
-- SELECT * FROM Student_Department_View;
-- Without knowing the JOIN exists. ABSTRACTION achieved.
```

---

## CONCEPT 3: INSTANCE vs SCHEMA

### SCHEMA = Structure (Static, Blueprint)
```sql
-- These commands DEFINE the schema:
CREATE TABLE Student (...);     -- Schema: table structure
ALTER TABLE Student ADD (phone VARCHAR2(15));  -- Schema: modify structure
DROP TABLE Student;              -- Schema: remove structure
```

### INSTANCE = Data (Dynamic, Changes over time)
```sql
-- These commands MODIFY the instance:
INSERT INTO Student VALUES ('25BCE1087', 'John Doe', 'john@email.com', 20, 1, SYSDATE);
-- ↑ This creates an INSTANCE of the schema

UPDATE Student SET age = 21 WHERE roll_no = '25BCE1087';
-- ↑ Changes the current instance

DELETE FROM Student WHERE roll_no = '25BCE1087';
-- ↑ Removes data from instance

SELECT * FROM Student;
-- ↑ Views the current instance (snapshot of data RIGHT NOW)
```

---

## CONCEPT 4: DDL (Data Definition Language) — Schema Level
**Purpose:** Define, modify, remove schema objects.
**Result:** Changes SCHEMA, not data.

```sql
-- DDL: CREATE — Build new schema objects
CREATE TABLE Course (
    course_code VARCHAR2(10) PRIMARY KEY,     -- IC: PRIMARY KEY
    course_name VARCHAR2(100) NOT NULL,         -- IC: NOT NULL
    credits     NUMBER CHECK (credits > 0),     -- IC: CHECK
    dept_id     NUMBER,
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id)  -- IC: FOREIGN KEY
);

-- DDL: ALTER — Modify existing schema
ALTER TABLE Course ADD (duration_weeks NUMBER);
ALTER TABLE Course MODIFY (course_name VARCHAR2(150));
ALTER TABLE Course DROP COLUMN duration_weeks;

-- DDL: DROP — Remove schema object completely
DROP TABLE Course;  -- Schema + all instance data GONE

-- DDL: TRUNCATE — Remove all instance data, keep schema
TRUNCATE TABLE Student;  -- Instance cleared, Schema remains

-- DDL: RENAME — Rename schema object
RENAME Student TO Student_Master;
```

---

## CONCEPT 5: DML (Data Manipulation Language) — Instance Level
**Purpose:** Work with data (instance).
**Result:** Changes INSTANCE, not schema structure.

```sql
-- DML: INSERT — Add rows to instance
INSERT INTO Department (dept_id, dept_name, established)
VALUES (1, 'Computer Science', TO_DATE('1990-06-15', 'YYYY-MM-DD'));

INSERT INTO Student (roll_no, name, email, age, dept_id)
VALUES ('25BCE1087', 'Alice Smith', 'alice@college.edu', 20, 1);

-- DML: UPDATE — Modify existing rows
UPDATE Student 
SET age = 21, email = 'alice.new@college.edu'
WHERE roll_no = '25BCE1087';

-- DML: DELETE — Remove rows
DELETE FROM Student WHERE age < 18;  -- IC: CHECK prevents this anyway!

-- DML: SELECT — Retrieve data (read instance)
SELECT roll_no, name, age FROM Student WHERE dept_id = 1;
```

---

## CONCEPT 6: INTEGRITY CONSTRAINTS (ICs)
**What they are:** Rules protecting data correctness. Enforced by DBMS.

| IC Type | SQL Syntax | What It Enforces |
|---------|-----------|-----------------|
| **PRIMARY KEY** | `PRIMARY KEY` | Uniquely identifies row. NOT NULL + UNIQUE combined. |
| **FOREIGN KEY** | `FOREIGN KEY ... REFERENCES` | Referential integrity — value must exist in parent table. |
| **UNIQUE** | `UNIQUE` | No duplicate values allowed in column. |
| **NOT NULL** | `NOT NULL` | Column cannot be empty. |
| **CHECK** | `CHECK (condition)` | Custom condition must be true. |
| **DEFAULT** | `DEFAULT value` | Auto-assigns value if none provided. |

```sql
-- IC: PRIMARY KEY (Entity Integrity)
CREATE TABLE Faculty (
    faculty_id  NUMBER PRIMARY KEY,  -- Must be unique, never NULL
    name        VARCHAR2(50) NOT NULL
);

-- IC: FOREIGN KEY (Referential Integrity)
CREATE TABLE Enrollment (
    roll_no     VARCHAR2(10),
    course_code VARCHAR2(10),
    semester    VARCHAR2(10),
    grade       VARCHAR2(2),

    PRIMARY KEY (roll_no, course_code, semester),  -- Composite PK
    FOREIGN KEY (roll_no) REFERENCES Student(roll_no),      -- Must exist in Student
    FOREIGN KEY (course_code) REFERENCES Course(course_code) -- Must exist in Course
);

-- IC: CHECK (Domain Integrity)
CREATE TABLE Exam (
    exam_id     NUMBER PRIMARY KEY,
    max_marks   NUMBER CHECK (max_marks > 0),
    pass_marks  NUMBER CHECK (pass_marks > 0 AND pass_marks <= max_marks),
    exam_date   DATE CHECK (exam_date > SYSDATE)  -- Future dates only
);

-- IC: UNIQUE + NOT NULL + DEFAULT combined
CREATE TABLE Library_Member (
    member_id   NUMBER PRIMARY KEY,
    card_number VARCHAR2(20) UNIQUE NOT NULL,  -- Must exist, must be unique
    join_date   DATE DEFAULT SYSDATE,           -- Auto-filled if omitted
    status      VARCHAR2(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'SUSPENDED', 'EXPIRED'))
);
```

---

## CONCEPT 7: ABSTRACTION
**What it is:** Hiding complexity. Three forms in DBMS.

### 7A. Physical Abstraction (Hidden from you)
```sql
-- You write:
CREATE INDEX idx_student_roll ON Student(roll_no);

-- DBMS does PHYSICAL abstraction:
-- - Builds B-tree structure
-- - Decides disk block allocation
-- - Manages buffer cache
-- YOU NEVER SEE ANY OF THIS
```

### 7B. Logical Abstraction (Your schema design)
```sql
-- You design tables that abstract real-world entities:
-- "Student" entity → Student table
-- "Course" entity → Course table
-- "Enrollment" relationship → Enrollment table
-- This is LOGICAL abstraction of reality into relational model.
```

### 7C. View Abstraction (You create these)
```sql
-- Complex query hidden behind simple name:
CREATE VIEW Top_Students AS
SELECT s.roll_no, s.name, AVG(e.grade_points) as gpa
FROM Student s
JOIN Enrollment en ON s.roll_no = en.roll_no
JOIN Exam_Grades e ON en.enroll_id = e.enroll_id
GROUP BY s.roll_no, s.name
HAVING AVG(e.grade_points) > 8.5;

-- User simply runs:
-- SELECT * FROM Top_Students;
-- JOIN, GROUP BY, HAVING all HIDDEN. ABSTRACTION.
```

---

## CONCEPT 8: DBMS vs DATABASE vs DATABASE SYSTEM

| Component | What It Is | Your Interaction |
|-----------|-----------|-----------------|
| **DBMS** | Software (Oracle) | You run SQL commands through it |
| **Database** | The actual data + schema | Stored inside PDB (COLLEGE_PDB) |
| **Database System** | DBMS + Database + Users + Architecture | The complete environment |

```sql
-- You interact with DBMS via SQL:
CONNECT 25BCE1087/password@COLLEGE_PDB
--     ↑user    ↑PDB=Database  ↑DBMS handles connection
```

---

## CONCEPT 9: PDB, SERVICE, USER/SCHEMA — Your Setup

### 9A. CREATE PDB (Database)
```sql
-- CONCEPT: Database creation (CDB architecture)
-- WHO: DBA/Admin (not you as student)
CREATE PLUGGABLE DATABASE COLLEGE_PDB
  ADMIN USER pdbadmin IDENTIFIED BY adminpass
  FILE_NAME_CONVERT = (
    'C:\APP\REHAA\PRODUCT\26AI\ORADATA\FREE\PDBSEED\',
    'C:\APP\REHAA\PRODUCT\26AI\ORADATA\FREE\COLLEGE_PDB\'
  );

-- ↑ Creates a new database (PDB) inside the container
```

### 9B. OPEN PDB
```sql
-- CONCEPT: Database instance activation
ALTER PLUGGABLE DATABASE COLLEGE_PDB OPEN;
-- ↑ Makes the database available for connections
```

### 9C. SWITCH CONTAINER
```sql
-- CONCEPT: Session context change
ALTER SESSION SET CONTAINER = COLLEGE_PDB;
-- ↑ Your session now operates inside COLLEGE_PDB
```

### 9D. CREATE USER = CREATE SCHEMA & CREATE TABLESPACES
```sql
CREATE TABLESPACE users
  DATAFILE 'C:\APP\REHAA\PRODUCT\26AI\ORADATA\FREE\COLLEGE_PDB\users.dbf'
  SIZE 100M
  AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;
```
```sql
-- CONCEPT: Schema creation (Oracle: user = schema)
-- In Oracle, creating a user automatically creates their schema namespace
CREATE USER "25BCE1087" IDENTIFIED BY studentpass
  DEFAULT TABLESPACE users
  QUOTA UNLIMITED ON users;
-- ↑ Schema "25BCE1087" now exists. Can hold tables, views, indexes.
```

### 9E. GRANT PRIVILEGES (Security)
```sql
-- CONCEPT: Access control / Security mechanism of DBMS
GRANT CREATE SESSION TO "25BCE1087";           -- Can connect
GRANT CREATE TABLE TO "25BCE1087";             -- Can create tables (schema design)
GRANT CREATE SEQUENCE TO "25BCE1087";          -- Can create auto-increment
GRANT CREATE TRIGGER TO "25BCE1087";           -- Can create triggers
GRANT CREATE VIEW TO "25BCE1087";              -- Can create views (abstraction)
GRANT CREATE PROCEDURE TO "25BCE1087";         -- Can create stored procedures
-- ↑ DBMS enforces these permissions. Security at logical level.
```

### 9F. CONNECT AS USER & TNS Issue
```text
COLLEGE_PDB =
  (DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521))
    (CONNECT_DATA =
      (SERVICE_NAME = college_pdb)
    )
  )
```
```sql
-- CONCEPT: Database session establishment
sqlplus "25BCE1087"/studentpass@COLLEGE_PDB
-- ↑ You are now inside your schema. Ready to create objects.
```

---

## CONCEPT 10: TRANSACTION CONTROL (Concurrency & Recovery)
**What it is:** DBMS handles locking, logging, recovery. You control boundaries.

```sql
-- CONCEPT: Transaction = Unit of work
-- DBMS handles: locks, logs, isolation, atomicity
-- YOU control: when to start, commit, or rollback

BEGIN  -- or just start with first DML command
    INSERT INTO Student VALUES ('25BCE1088', 'Bob', 'bob@email.com', 19, 1, SYSDATE);
    INSERT INTO Enrollment VALUES ('25BCE1088', 'CS101', 'Fall2026', NULL);

    COMMIT;  -- CONCEPT: Durability — changes permanent
    -- DBMS: writes to redo log, releases locks, makes permanent

EXCEPTION WHEN OTHERS THEN
    ROLLBACK;  -- CONCEPT: Atomicity — all or nothing
    -- DBMS: undoes changes using undo log, restores consistency
END;

-- CONCEPT: Isolation — other users don't see uncommitted data
-- DBMS handles this automatically via locking/MVCC
```

---

## CONCEPT 11: TCL (Transaction Control Language)

```sql
-- TCL: COMMIT — Make changes permanent (Recovery concept)
COMMIT;

-- TCL: ROLLBACK — Undo changes (Atomicity concept)
ROLLBACK;

-- TCL: SAVEPOINT — Mark point within transaction
SAVEPOINT before_grade_update;
UPDATE Enrollment SET grade = 'A' WHERE roll_no = '25BCE1087';
-- Oops, wrong!
ROLLBACK TO SAVEPOINT before_grade_update;  -- Partial rollback
```

---

## CONCEPT 12: DCL (Data Control Language) — Security

```sql
-- DCL: GRANT — Give permissions (Security/Authorization)
GRANT SELECT ON Student TO librarian;
GRANT INSERT, UPDATE ON Enrollment TO faculty_role;

-- DCL: REVOKE — Remove permissions
REVOKE DELETE ON Student FROM librarian;

-- CONCEPT: Role-based security (abstraction of permissions)
CREATE ROLE student_role;
GRANT SELECT ON Course TO student_role;
GRANT SELECT ON Department TO student_role;
GRANT student_role TO "25BCE1087";
-- ↑ Abstracts multiple permissions into single role
```

---

## COMPLETE WORKFLOW: From Zero to Tables

```sql
-- ============================================================
-- PHASE 1: INFRASTRUCTURE (DBA/Admin — Physical Level)
-- CONCEPTS: Database System, Architecture, Physical Abstraction
-- ============================================================
CONNECT / AS SYSDBA

CREATE PLUGGABLE DATABASE COLLEGE_PDB
  ADMIN USER pdbadmin IDENTIFIED BY adminpass
  FILE_NAME_CONVERT = (
    'C:\APP\REHAA\PRODUCT\26AI\ORADATA\FREE\PDBSEED\',
    'C:\APP\REHAA\PRODUCT\26AI\ORADATA\FREE\COLLEGE_PDB\'
  );

ALTER PLUGGABLE DATABASE COLLEGE_PDB OPEN;

-- ============================================================
-- PHASE 2: USER/SCHEMA CREATION (DBA or You)
-- CONCEPTS: Schema, User, Security, Logical Level
-- ============================================================
ALTER SESSION SET CONTAINER = COLLEGE_PDB;

CREATE TABLESPACE users
  DATAFILE 'C:\APP\REHAA\PRODUCT\26AI\ORADATA\FREE\COLLEGE_PDB\users.dbf'
  SIZE 100M
  AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;


CREATE USER "25BCE1087" IDENTIFIED BY studentpass
  DEFAULT TABLESPACE users
  QUOTA UNLIMITED ON users;

GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE,
      CREATE TRIGGER, CREATE VIEW, CREATE PROCEDURE
  TO "25BCE1087";

-- ============================================================
-- PHASE 3: SCHEMA DESIGN (YOU — Logical Level)
-- CONCEPTS: Data Model (Relational), Schema, ICs, Abstraction
-- ============================================================
CONNECT "25BCE1087"/studentpass@COLLEGE_PDB
TNS issue
```text
COLLEGE_PDB =
  (DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521))
    (CONNECT_DATA =
      (SERVICE_NAME = college_pdb)
    )
  )
```


-- Entity: Department
CREATE TABLE Department (
    dept_id     NUMBER PRIMARY KEY,              -- IC: Entity Integrity
    dept_name   VARCHAR2(50) NOT NULL UNIQUE,    -- IC: Domain + Uniqueness
    location    VARCHAR2(100),
    established DATE DEFAULT SYSDATE             -- IC: Default
);

-- Entity: Student
CREATE TABLE Student (
    roll_no     VARCHAR2(10) PRIMARY KEY,        -- IC: Entity Integrity
    name        VARCHAR2(50) NOT NULL,             -- IC: Domain
    email       VARCHAR2(100) UNIQUE,             -- IC: Uniqueness
    age         NUMBER CHECK (age >= 17 AND age <= 100),  -- IC: Domain
    dept_id     NUMBER NOT NULL,
    enrollment_date DATE DEFAULT SYSDATE,        -- IC: Default
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id)  -- IC: Referential
);

-- Entity: Course
CREATE TABLE Course (
    course_code VARCHAR2(10) PRIMARY KEY,
    course_name VARCHAR2(100) NOT NULL,
    credits     NUMBER CHECK (credits BETWEEN 1 AND 6),
    dept_id     NUMBER,
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
);

-- Relationship: Enrollment (M:N between Student and Course)
CREATE TABLE Enrollment (
    enroll_id   NUMBER PRIMARY KEY,
    roll_no     VARCHAR2(10) NOT NULL,
    course_code VARCHAR2(10) NOT NULL,
    semester    VARCHAR2(10) NOT NULL,
    grade       VARCHAR2(2),
    enrollment_date DATE DEFAULT SYSDATE,

    FOREIGN KEY (roll_no) REFERENCES Student(roll_no),
    FOREIGN KEY (course_code) REFERENCES Course(course_code),
    UNIQUE (roll_no, course_code, semester)  -- IC: No duplicate enrollment
);

-- ============================================================
-- PHASE 4: ABSTRACTION (YOU — View Level)
-- CONCEPTS: External Level, Abstraction, Logical Independence
-- ============================================================

-- View: Hide complexity of join
CREATE VIEW Student_Course_Enrollment AS
SELECT 
    s.roll_no,
    s.name,
    c.course_name,
    c.credits,
    e.semester,
    e.grade
FROM Student s
JOIN Enrollment e ON s.roll_no = e.roll_no
JOIN Course c ON e.course_code = c.course_code;

-- View: Security — hide sensitive columns
CREATE VIEW Public_Student_Info AS
SELECT roll_no, name, dept_id  -- Email hidden!
FROM Student;

-- ============================================================
-- PHASE 5: INSTANCE CREATION (YOU — Data Population)
-- CONCEPTS: Instance, DML, Transaction
-- ============================================================

INSERT INTO Department VALUES (1, 'Computer Science', 'Building A', TO_DATE('1995-08-15', 'YYYY-MM-DD'));
INSERT INTO Department VALUES (2, 'Electronics', 'Building B', TO_DATE('1998-01-10', 'YYYY-MM-DD'));

INSERT INTO Student VALUES ('25BCE1087', 'Alice Smith', 'alice@college.edu', 20, 1, SYSDATE);
INSERT INTO Student VALUES ('25BCE1088', 'Bob Jones', 'bob@college.edu', 19, 1, SYSDATE);

INSERT INTO Course VALUES ('CS101', 'Database Systems', 4, 1);
INSERT INTO Course VALUES ('CS102', 'Data Structures', 4, 1);

INSERT INTO Enrollment VALUES (1, '25BCE1087', 'CS101', 'Fall2026', 'A', SYSDATE);
INSERT INTO Enrollment VALUES (2, '25BCE1087', 'CS102', 'Fall2026', 'B', SYSDATE);

COMMIT;  -- Transaction complete, changes durable

-- ============================================================
-- PHASE 6: PHYSICAL OPTIMIZATION (Optional — DBMS does the work)
-- CONCEPTS: Physical Level, Index, Performance
-- ============================================================

CREATE INDEX idx_student_dept ON Student(dept_id);
-- ↑ You write logical command. DBMS builds physical B-tree. Abstraction.

-- ============================================================
-- PHASE 7: QUERYING (YOU — Using the Database)
-- CONCEPTS: Instance, Abstraction via Views
-- ============================================================

-- Query base tables (Logical Level)
SELECT * FROM Student WHERE dept_id = 1;

-- Query view (External Level — abstraction)
SELECT * FROM Student_Course_Enrollment WHERE roll_no = '25BCE1087';
-- ↑ No JOIN needed. View hides complexity.
```

---

## QUICK CONCEPT MAP

| Concept | Where It Lives | SQL You Write |
|---------|---------------|---------------|
| **Data Model** | Foundation | None — it's the paradigm |
| **Physical Level** | Internal/Storage | `CREATE INDEX` (command only) |
| **Logical Level** | Conceptual/Schema | `CREATE/ALTER/DROP TABLE` |
| **View Level** | External/Abstraction | `CREATE VIEW` |
| **Schema** | Structure | DDL commands |
| **Instance** | Data | DML commands |
| **ICs** | Rules on data | `PRIMARY KEY`, `FOREIGN KEY`, etc. |
| **Abstraction** | Hiding complexity | Views, roles, logical design |
| **Security** | Access control | `GRANT`, `REVOKE`, roles |
| **Transaction** | Atomic unit | `COMMIT`, `ROLLBACK`, `SAVEPOINT` |
| **Concurrency** | Simultaneous access | Handled by DBMS (isolation levels) |
| **Recovery** | Durability after crash | Handled by DBMS (logs, checkpoints) |

---

## SUMMARY: What You Do vs What DBMS Does

| Task | You Do | DBMS Does |
|------|--------|-----------|
| Design tables, relationships | ✅ `CREATE TABLE` | ❌ |
| Define constraints | ✅ `PRIMARY KEY`, `FOREIGN KEY` | ✅ Enforce them |
| Create views | ✅ `CREATE VIEW` | ✅ Execute underlying query |
| Create indexes | ✅ `CREATE INDEX` command | ✅ Build physical B-tree |
| Manage disk blocks | ❌ | ✅ Physical storage |
| Handle concurrent access | ❌ | ✅ Locking, isolation |
| Recover from crash | ❌ | ✅ Redo/undo logs |
| Control transactions | ✅ `COMMIT`/`ROLLBACK` | ✅ Ensure ACID properties |
| Grant access | ✅ `GRANT`/`REVOKE` | ✅ Check permissions |

---

*Generated for: Student 25BCE1087 | PDB: COLLEGE_PDB*
*Concepts covered: Data Model, Architecture, Abstraction, Schema, Instance, DDL, DML, DCL, TCL, ICs, Security, Transactions*
