# MODULE 1 (Part 2): Relational Model Concepts, Constraints & Schemas

---

## Table of Contents

1. [Relational Model Concepts](#1-relational-model-concepts)
2. [Characteristics of Relations](#2-characteristics-of-relations)
3. [Relational Model Constraints](#3-relational-model-constraints)
4. [Entity Integrity](#4-entity-integrity)
5. [Referential Integrity & Foreign Keys](#5-referential-integrity--foreign-keys)
6. [Relational Database Schema](#6-relational-database-schema)
7. [Dealing with Constraint Violations](#7-dealing-with-constraint-violations)
8. [Update Operations](#8-update-operations)
9. [SQL Implementation](#9-sql-implementation)
10. [Summary & Exam Points](#10-summary--exam-points)

---

## 1. Relational Model Concepts

### 1.1 Definition & History

| Aspect | Detail |
|--------|--------|
| **Based on** | Mathematical concept of a **Relation** |
| **Proposed by** | Dr. E.F. Codd, IBM Research |
| **Year** | 1970 |
| **Paper** | *"A Relational Model for Large Shared Data Banks,"* Communications of the ACM |
| **Significance** | Caused a major revolution; earned Codd the **ACM Turing Award** |

> **Key Point**: Strength comes from **formal foundation** — set theory and predicate logic.

### 1.2 Informal vs Formal Terms

| Informal Term | Formal Term | Description |
|---------------|-------------|-------------|
| **Table** | **Relation** | 2D structure of rows & columns |
| **Column Header** | **Attribute** | Name indicating meaning of data |
| **All possible values** | **Domain** | Set of valid values for an attribute |
| **Row** | **Tuple** | Ordered set of values (one record) |
| **Table Definition** | **Schema** | Structure/description |
| **Populated Table** | **State** | Actual data at a moment |

### 1.3 Formal Definitions

#### **Schema of a Relation**
```
R(A₁, A₂, ..., Aₙ)
```
- **R** = Relation name
- **A₁...Aₙ** = Attributes

**Example:**
```
CUSTOMER (Cust-id, Cust-name, Address, Phone#)
```

#### **Tuple**
- Ordered set of values in angled brackets: `<...>`
- **4-tuple example:**
  ```
  <632895, "John Smith", "101 Main St.", "(404)894-2000">
  ```

#### **Domain**
- Has **logical definition** + **data type/format**
- **Example**: `USA_phone_numbers` = 10-digit numbers, format `(ddd)ddd-dddd`
- **Attribute name** designates the **role** of a domain
  - Same `Date` domain → `Invoice-date` and `Payment-date` (different meanings)

#### **Relation State**
> **r(R) ⊆ dom(A₁) × dom(A₂) × ... × dom(Aₙ)**

- **r(R)** = specific state/population of relation R
- **r(R) = {t₁, t₂, ..., tₙ}** where each tᵢ is an n-tuple
- **tᵢ = <v₁, v₂, ..., vₙ>** where vⱼ ∈ dom(Aⱼ)

**Cartesian Product Example:**
- dom(A₁) = {0, 1}, dom(A₂) = {a, b, c}
- dom(A₁) × dom(A₂) = {`<0,a>`, `<0,b>`, `<0,c>`, `<1,a>`, `<1,b>`, `<1,c>`}
- Possible state: **r(R) = {`<0,a>`, `<0,b>`, `<1,c>`}**

---

## 2. Characteristics of Relations

### 2.1 Ordering of Tuples
- **NOT ordered** — different order = same relation state

### 2.2 Ordering of Attributes
- Considered **ordered** in R(A₁, A₂, ..., Aₙ)
- **Alternative**: Self-describing — includes **name + value**
  ```
  t = {<name, "John">, <SSN, 123456789>}
  ```

### 2.3 Values in a Tuple
- All values are **atomic (indivisible)**
- Each vᵢ must be from **dom(Aᵢ)**
- **NULL** = unknown / not available / inapplicable

### 2.4 Notation
| Notation | Meaning |
|----------|---------|
| **t[Aᵢ]** or **t.Aᵢ** | Value of attribute Aᵢ in tuple t |
| **t[Aᵤ, Aᵥ, ..., Aᵥ]** | Subtuple with values of specified attributes |

### 2.5 Key of a Relation
- Value(s) that **uniquely identify** a row
- **SSN** = key in STUDENT table
- **Artificial/Surrogate Key**: Row-ids or sequential numbers assigned

---

## 3. Relational Model Constraints

> **Definition**: Conditions that must hold on all valid relation states.

### 3.1 Types of Constraints

| Type | Description | Example |
|------|-------------|---------|
| **Inherent/Implicit** | Based on data model itself | No lists as attribute values |
| **Schema-based/Explicit** | Expressed in schema | Max cardinality in ER model |
| **Application-based/Semantic** | Beyond model power; app-enforced | "Max 56 hrs/week per employee" |

### 3.2 Main Explicit Constraints

1. **Domain constraints**
2. **Key constraints**
3. **Entity integrity constraints**
4. **Referential integrity constraints**

---

### 3.1 Domain Constraints

- Every value must be from the **domain of its attribute**
- Can be **NULL** if allowed

**Violation Example:**

| ID | NAME | AGE |
|----|------|-----|
| 1000 | Tom | 17 |
| 1001 | Johnson | 24 |
| 1002 | Leonardo | **A** |

> **Violation**: "A" is not an integer — violates domain constraint.

**SQL:**
```sql
CREATE TABLE Students (
    StudentID INT PRIMARY KEY,
    Name VARCHAR(50),
    Age INT CHECK (Age > 0)  -- Domain constraint
);
```

---

### 3.2 Key Constraints

#### **Superkey (SK)**
- Set of attributes where **no two tuples** have same value
- For any distinct t₁, t₂: **t₁[SK] ≠ t₂[SK]**
- Must hold in **any valid state**

#### **Key (Candidate Key)**
- **Minimal superkey** — removing any attribute breaks uniqueness

> **A Key is a Superkey, but NOT vice versa!**

#### **Example — CAR Relation**
```
CAR(State, Reg#, SerialNo, Make, Model, Year)
```
| Key Type | Attribute Set | Is Key? |
|----------|---------------|---------|
| Candidate Key 1 | {State, Reg#} | ✅ Yes |
| Candidate Key 2 | {SerialNo} | ✅ Yes |
| Superkey (not key) | {SerialNo, Make} | ❌ Not minimal |

#### **Primary Key**
- One **chosen** candidate key (arbitrarily if multiple)
- **Underlined** in schema notation
- Provides **tuple identity** and **referenceability**

**Example:**
```
CAR(State, Reg#, SerialNo, Make, Model, Year)
```
> SerialNo chosen as PK

**Rules:**
- Choose **smallest** candidate key (usually)
- Choice can be **subjective**

**Key Hierarchy:**
```
Any Key → Superkey
Any set containing a Key → Superkey
Minimal Superkey = Key
```

---

### 3.3 Constraints on NULL Values

| Constraint | NULL Allowed? | Reason |
|------------|---------------|--------|
| **Primary Key** | ❌ **NO** | Used to identify tuples |
| **Foreign Key** | ✅ Yes (unless NOT NULL) | References may be unknown |
| **Other attributes** | Depends on schema | May be constrained |

**SQL Example:**
```sql
CREATE TABLE Students (
    sid CHAR(20) PRIMARY KEY,     -- Cannot be NULL
    login CHAR(10) UNIQUE,        -- Can be NULL
    gpa REAL,
    name VARCHAR(50) NOT NULL     -- Explicitly no NULL
);
```

---

## 4. Entity Integrity

> **Definition**: Primary key attributes **PK** of each relation schema R **cannot have NULL values** in any tuple.

**Why?** PK values identify individual tuples. If NULL → cannot identify row.

**Formula:** **t[PK] ≠ null** for any tuple t

**Violation Example:**

| EMP_ID | EMP_NAME | SALARY |
|--------|----------|--------|
| 123 | Jack | 30000 |
| 142 | Harry | 60000 |
| **NULL** | Jackson | 27000 |

> ❌ **NOT ALLOWED** — PK cannot be NULL.

---

## 5. Referential Integrity & Foreign Keys

### 5.1 Definition

> **Referential Integrity**: Constraint involving **two relations** — **referencing** (R₁) and **referenced** (R₂).

- R₁ has **FK** (foreign key) referencing **PK** of R₂
- t₁ in R₁ references t₂ in R₂ if: **t₁[FK] = t₂[PK]**

### 5.2 Foreign Key Constraint

FK value in R₁ can be:
1. **Existing PK value** in R₂, OR
2. **NULL** (if allowed, and FK not part of PK in R₁)

### 5.3 Violation Example

**Employee:**

| EMP_ID | NAME | AGE | D_No |
|--------|------|-----|------|
| 1 | Jack | 20 | 11 |
| 2 | Harry | 40 | 24 |
| 3 | John | 27 | **18** |
| 4 | Devil | 38 | 13 |

**Department:**

| D_No | Location |
|------|----------|
| 11 | Mumbai |
| 24 | Delhi |
| 13 | Noida |

> ❌ **VIOLATION**: D_No = 18 doesn't exist in Department. FK constraint violated!

### 5.4 Schema Diagram Notation

- **PK attributes**: **Underlined**
- **FK constraints**: **Directed arc** (arrow) from FK to referenced table

### 5.5 COMPANY Database — Complete FK Mapping

```
EMPLOYEE(Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno)
                                        ↑↑↑↑                    ↑
                                        ││││                     │
DEPARTMENT(Dname, Dnumber, Mgr_ssn, Mgr_start_date)              │
              ↑↑↑↑                                                 │
DEPT_LOCATIONS(Dnumber, Dlocation)                                 │
PROJECT(Pname, Pnumber, Plocation, Dnum)                         │
WORKS_ON(Essn, Pno, Hours)                                         │
DEPENDENT(Essn, Dependent_name, Sex, Bdate, Relationship)            │
```

| Foreign Key | References | Relation |
|-------------|------------|----------|
| EMPLOYEE.Dno | DEPARTMENT.Dnumber | EMPLOYEE → DEPARTMENT |
| EMPLOYEE.Super_ssn | EMPLOYEE.Ssn | Self-reference |
| DEPARTMENT.Mgr_ssn | EMPLOYEE.Ssn | DEPARTMENT → EMPLOYEE |
| DEPT_LOCATIONS.Dnumber | DEPARTMENT.Dnumber | DEPT_LOCATIONS → DEPARTMENT |
| PROJECT.Dnum | DEPARTMENT.Dnumber | PROJECT → DEPARTMENT |
| WORKS_ON.Essn | EMPLOYEE.Ssn | WORKS_ON → EMPLOYEE |
| WORKS_ON.Pno | PROJECT.Pnumber | WORKS_ON → PROJECT |
| DEPENDENT.Essn | EMPLOYEE.Ssn | DEPENDENT → EMPLOYEE |

---

## 6. Relational Database Schema

### 6.1 Schema Definition

> **S = {R₁, R₂, ..., Rₙ}** + set **IC** of integrity constraints

### 6.2 Database State

> **DB = {r₁, r₂, ..., rₘ}** where:
- Each rᵢ is a state of Rᵢ
- All rᵢ satisfy integrity constraints in IC

- **Valid state**: Satisfies all constraints
- **Invalid state**: Violates at least one constraint

### 6.3 State Changes
- New state arises whenever database is changed
- Basic operations: **INSERT**, **DELETE**, **MODIFY**

---

## 7. Dealing with Constraint Violations

### 7.1 Actions on Violation

| Action | Description |
|--------|-------------|
| **RESTRICT/REJECT** | Cancel the operation |
| **CASCADE** | Propagate changes to referencing tuples |
| **SET NULL** | Set FK to NULL |
| **SET DEFAULT** | Set FK to default value |
| **User routine** | Execute custom error handler |

### 7.2 INSERT Violations

INSERT can violate **any** constraint:

| Constraint | Violation |
|------------|-----------|
| Domain | Value not in specified domain |
| Key | Duplicate key value |
| Referential Integrity | FK references non-existent PK |
| Entity Integrity | PK is NULL |

**Examples:**
```sql
-- Duplicate PK
INSERT INTO Student VALUES (101, 'Kumar', 'D01');
-- Error: PRIMARY KEY constraint violated. ID 101 exists.

-- Invalid FK
INSERT INTO Student VALUES (103, 'Priya', 'D10');
-- Error: FOREIGN KEY constraint violated. D10 doesn't exist.
```

### 7.3 DELETE Violations

DELETE violates **only Referential Integrity**:
- When PK being deleted is referenced by other tuples

**Options:**

| Option | Action |
|--------|--------|
| **RESTRICT** | Reject deletion |
| **CASCADE** | Delete referencing tuples too |
| **SET NULL** | Set referencing FKs to NULL |
| **SET DEFAULT** | Set referencing FKs to default |

**Example:**
```sql
-- Can't delete D01 if students still reference it
DELETE FROM Department WHERE DeptID = 'D01';
-- Error: Referential Integrity Violation
```

### 7.4 UPDATE Violations

| Attribute Updated | Possible Violations |
|-------------------|---------------------|
| **Primary Key** | Domain, Key, Entity Integrity, Referential Integrity |
| **Foreign Key** | Domain, Referential Integrity |
| **Ordinary attribute** | Domain constraints only |

**Examples:**
```sql
-- FK violation
UPDATE Student SET DeptID = 'D20' WHERE SID = 101;
-- Error: D20 doesn't exist in Department.

-- PK NULL violation
UPDATE Student SET SID = NULL WHERE SID = 101;
-- Error: Primary Key cannot be NULL.
```

---

## 8. Update Operations

### 8.1 Basic Operations
1. **INSERT** — add tuple
2. **DELETE** — remove tuple
3. **MODIFY** — change attribute values

### 8.2 Rules
- Constraints must **NOT** be violated
- Operations may need to be **grouped**
- Updates may **propagate automatically** to maintain integrity

---

## 9. SQL Implementation

### 9.1 CREATE TABLE with Constraints

```sql
-- Students with PK and UNIQUE
CREATE TABLE Students (
    sid CHAR(20),
    login CHAR(10),
    gpa REAL,
    name VARCHAR(50),
    PRIMARY KEY (sid),
    UNIQUE (login)
);

-- Enrolled with Composite PK and FK
CREATE TABLE Enrolled (
    sid CHAR(20),
    cid CHAR(20),
    grade CHAR(2),
    PRIMARY KEY (sid, cid),
    FOREIGN KEY (sid) REFERENCES Students(sid)
);
```

### 9.2 FK with Referential Actions

```sql
CREATE TABLE Enrolled (
    sid CHAR(20),
    cid CHAR(20),
    grade CHAR(2),
    PRIMARY KEY (sid, cid),
    FOREIGN KEY (sid)
        REFERENCES Students
        ON DELETE CASCADE
        ON UPDATE SET DEFAULT
);
```

### 9.3 SQL Options Summary

| Option | ON DELETE | ON UPDATE |
|--------|-----------|-----------|
| **NO ACTION** (default) | Reject | Reject |
| **CASCADE** | Delete referencing | Update referencing |
| **SET NULL** | Set FK = NULL | Set FK = NULL |
| **SET DEFAULT** | Set FK = default | Set FK = default |

### 9.4 Assertions

```sql
CREATE ASSERTION max_salary 
CHECK (NOT EXISTS (
    SELECT * FROM Employees WHERE salary > 100000
));
```

### 9.5 NULL Handling

```sql
-- COALESCE function
SELECT ID, COALESCE(salary, 0) AS salary FROM instructor;

-- DEFAULT constraint
CREATE TABLE Employees (
    ID INT PRIMARY KEY,
    salary DECIMAL(10,2) DEFAULT 0.00
);
```

---

## 10. Summary & Exam Points

### Key Formulas & Definitions

| Concept | Definition |
|---------|------------|
| **Relation State** | r(R) ⊆ dom(A₁) × dom(A₂) × ... × dom(Aₙ) |
| **Superkey** | No two tuples have same SK value |
| **Key** | Minimal superkey |
| **Entity Integrity** | t[PK] ≠ null |
| **Referential Integrity** | t₁[FK] = t₂[PK] or NULL |

### Constraint Violations Summary

| Operation | Violations Possible |
|-----------|---------------------|
| **INSERT** | Domain, Key, Entity Integrity, Referential Integrity |
| **DELETE** | Referential Integrity only |
| **UPDATE** | Depends on attribute being updated |

### Exam-Ready Quick Points

1. **Codd's paper (1970)** → Foundation of relational model → Turing Award
2. **Tuple = ordered set of values**; **Relation = set of tuples**
3. **Key = minimal superkey**; **Primary key = chosen candidate key**
4. **Entity Integrity** → PK ≠ NULL
5. **Referential Integrity** → FK = valid PK or NULL
6. **DELETE** only violates referential integrity
7. **CASCADE** propagates changes; **SET NULL** sets to NULL; **RESTRICT** rejects
8. **Assertions** express conditions database must always satisfy

### In-Class Exercise Solution

**Schema:**
- STUDENT(**SSN**, Name, Major, Bdate)
- COURSE(**Course#**, Cname, Dept)
- ENROLL(**SSN**, **Course#**, Quarter, Grade)
- BOOK_ADOPTION(**Course#**, **Quarter**, **Book_ISBN**)
- TEXT(**Book_ISBN**, Book_Title, Publisher, Author)

**Foreign Keys:**
| FK | References | In Table |
|----|-----------|----------|
| ENROLL.SSN | STUDENT.SSN | ENROLL |
| ENROLL.Course# | COURSE.Course# | ENROLL |
| BOOK_ADOPTION.Course# | COURSE.Course# | BOOK_ADOPTION |
| BOOK_ADOPTION.Book_ISBN | TEXT.Book_ISBN | BOOK_ADOPTION |

---

### Textbook References

| Topic | Silberschatz | Elmasri |
|-------|--------------|---------|
| Relational Concepts | Ch 2.1 (pg 37) | Ch 5.1 |
| Characteristics | Ch 5.1.2 | Ch 5.1.2 |
| Keys | Ch 2.3 (pg 43) | Ch 5.2 |
| Integrity Constraints | Ch 1.4.1, 3.2.2, 4.4 | Ch 5.2 |
| Violations | — | Ch 5.3 |
| NULL Handling | Ch 3.6, 3.7.4, 4.5.2 | — |
