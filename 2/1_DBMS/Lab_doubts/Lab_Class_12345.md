Here are your complete, structured notes for **SQL Lab** — extracted directly from all the uploaded files, organized for exam preparation:

---

# SQL LAB NOTES: Complete Reference

---

## Table of Contents

1. [SQL Overview](#1-sql-overview)
2. [DDL Commands](#2-ddl-commands)
3. [DML Commands](#3-dml-commands)
4. [TCL Commands](#4-tcl-commands)
5. [DCL Commands](#5-dcl-commands)
6. [SQL Constraints](#6-sql-constraints)
7. [Basic SELECT Queries](#7-basic-select-queries)
8. [Operators](#8-operators)
9. [Single Row Functions](#9-single-row-functions)
10. [Aggregate Functions](#10-aggregate-functions)
11. [GROUP BY & HAVING](#11-group-by--having)
12. [Joins](#12-joins)
13. [Set Operations](#13-set-operations)
14. [Subqueries](#14-subqueries)
15. [Date Functions](#15-date-functions)
16. [Conversion Functions](#16-conversion-functions)
17. [The DUAL Table](#17-the-dual-table)

---

## 1. SQL Overview

> **SQL** = Structured Query Language

- ANSI (American National Standards Institute) standard
- Works with: MS Access, DB2, Informix, MS SQL Server, Oracle, Sybase, etc.

### What SQL Can Do

| Capability | Description |
|------------|-------------|
| Execute queries | Retrieve data from database |
| Retrieve data | SELECT statements |
| Insert records | INSERT INTO |
| Update records | UPDATE |
| Delete records | DELETE |
| Create databases | CREATE DATABASE |
| Create tables | CREATE TABLE |
| Create stored procedures | CREATE PROCEDURE |
| Create views | CREATE VIEW |
| Set permissions | GRANT, REVOKE |

### SQL Language Categories

| Category | Purpose | Commands |
|----------|---------|----------|
| **DDL** | Data Definition Language | CREATE, ALTER, DROP, TRUNCATE, RENAME |
| **DML** | Data Manipulation Language | SELECT, INSERT, UPDATE, DELETE |
| **TCL** | Transaction Control Language | COMMIT, ROLLBACK, SAVEPOINT |
| **DCL** | Data Control Language | GRANT, REVOKE |

---

## 2. DDL Commands

### 2.1 CREATE TABLE

```sql
CREATE TABLE table_name (
    column_name1 datatype,
    column_name2 datatype,
    ...
);
```

**Example:**
```sql
CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT
);
```

### 2.2 Data Types

| Data Type | Description |
|-----------|-------------|
| `CHAR` | Fixed-length character |
| `VARCHAR(size)` | Variable-length character |
| `INT` | Integer |
| `FLOAT` | Floating point |
| `NUMBER(precision, scale)` | Numeric with precision and scale |
| `DATE` | Date (format: '13-NOV-92') |

### 2.3 DESCRIBE (View Schema)

```sql
DESCRIBE table_name;
-- or
DESC table_name;
```

### 2.4 ALTER TABLE

| Operation | Syntax |
|-----------|--------|
| **Add single column** | `ALTER TABLE t_name ADD (column_name datatype);` |
| **Add multiple columns** | `ALTER TABLE t_name ADD (col1 datatype1, col2 datatype2);` |
| **Modify column** | `ALTER TABLE t_name MODIFY (column_name datatype);` |
| **Rename column** | `ALTER TABLE t_name RENAME COLUMN old_name TO new_name;` |
| **Drop column** | `ALTER TABLE t_name DROP (column_name);` |

**Examples:**
```sql
ALTER TABLE student ADD (address VARCHAR(50));
ALTER TABLE student ADD (father_name VARCHAR(60), mother_name VARCHAR(60), dob DATE);
ALTER TABLE student MODIFY (address VARCHAR(30));
ALTER TABLE student RENAME COLUMN address TO Location;
ALTER TABLE student DROP (address);
```

### 2.5 RENAME Table

```sql
RENAME old_table_name TO new_table_name;
-- Example
RENAME student TO student_record;
```

### 2.6 TRUNCATE TABLE

```sql
TRUNCATE TABLE table_name;
```

> **Properties:**
> - Removes ALL records but keeps table structure
> - Primary key is reset (initialized)
> - Implicitly preceded and followed by COMMIT
> - **CANNOT be rolled back**
> - Faster than DELETE

### 2.7 DROP TABLE

```sql
DROP TABLE table_name;
-- Example
DROP TABLE student;
```

> **Removes table completely** from database

### DELETE vs TRUNCATE vs DROP

| Aspect | DELETE | TRUNCATE | DROP |
|--------|--------|----------|------|
| **Type** | DML | DDL | DDL |
| **Removes** | Specific rows / All rows | All rows | Table structure + data |
| **WHERE clause** | ✅ Yes | ❌ No | ❌ No |
| **Rollback** | ✅ Possible | ❌ Not possible | ❌ Not possible |
| **Primary Key** | Preserved | Reset | N/A |
| **Speed** | Slow | Fast | Fastest |

---

## 3. DML Commands

### 3.1 INSERT

```sql
-- Insert all columns
INSERT INTO table_name VALUES (value1, value2, ...);

-- Insert specific columns
INSERT INTO table_name (col1, col2) VALUES (val1, val2);

-- Insert multiple rows
INSERT INTO Customers VALUES 
    ('Cardinal', 'Tom B.', 'Skagen 21', 'Stavanger', '4006', 'Norway'),
    ('Greasy Burger', 'Per Olsen', 'Gateveien 15', 'Sandnes', '4306', 'Norway');
```

**Interactive Insert (using &):**
```sql
INSERT INTO Persons VALUES (&s_id, '&s_name', &age);
-- Enter value for s_id: 101
-- Enter value for s_name: John
-- Enter value for age: 25
```

**Repeat previous command:**
```sql
SQL> /  -- repeats last command
```

### 3.2 AUTO INCREMENT (Sequence in Oracle)

```sql
CREATE SEQUENCE seq_S1
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1
    CACHE 10;

-- Use in INSERT
INSERT INTO s1 (s_id, s_name, age)
    VALUES (seq_S1.nextval, 'Lars', 34);
```

### 3.3 SELECT

```sql
-- Basic
SELECT column_name FROM table_name;

-- All columns
SELECT * FROM table_name;

-- With WHERE
SELECT * FROM table_name WHERE condition;

-- With comparison operators
SELECT * FROM s1 WHERE age = 23;
SELECT * FROM s1 WHERE s_id = 100;
SELECT sname FROM s1 WHERE s_id = 100;
```

**Comparison Operators:** `<`, `>`, `<=`, `>=`, `=`, `!=` or `<>`

### 3.4 UPDATE

```sql
UPDATE table_name SET column_name = value WHERE condition;

-- Single column
UPDATE s1 SET age = 18 WHERE s_id = 100;

-- Multiple columns
UPDATE s1 SET age = 20, name = 'John' WHERE s_id = 100;
```

### 3.5 DELETE

```sql
-- Delete specific rows
DELETE FROM table_name WHERE condition;

-- Delete all rows
DELETE FROM table_name;
```

---

## 4. TCL Commands

> **Transaction**: A unit of work performed against a database (CREATE, UPDATE, DELETE operations)

| Command | Purpose |
|---------|---------|
| **COMMIT** | Save all changes permanently |
| **ROLLBACK** | Undo changes to a specific savepoint or beginning |
| **SAVEPOINT** | Temporarily save transaction at a point |

### 4.1 SAVEPOINT

```sql
SAVEPOINT savepoint_name;
```

### 4.2 ROLLBACK

```sql
ROLLBACK TO savepoint_name;
-- or
ROLLBACK;  -- undo all changes
```

### 4.3 COMMIT

```sql
COMMIT;
```

**Example Workflow:**
```sql
UPDATE Customers SET country = 'INDIA' WHERE first_name = 'David';
COMMIT;

-- OR with savepoint
UPDATE some_table SET ...;
SAVEPOINT sp_a;
DELETE FROM some_table WHERE ...;
SAVEPOINT sp_b;
ROLLBACK TO sp_a;  -- undo delete, keep update
```

---

## 5. DCL Commands

### 5.1 GRANT

```sql
GRANT privileges ON object_name TO user/role;
```

### 5.2 REVOKE

```sql
REVOKE privileges ON object_name FROM user/role;
```

### Privileges

| Privilege | Description |
|-----------|-------------|
| `SELECT` | Read data |
| `INSERT` | Add new records |
| `UPDATE` | Modify records |
| `DELETE` | Remove records |
| `EXECUTE` | Run procedures |
| `ALTER` | Modify structure |
| `ALL` | All privileges |
| `REFERENCE` | Declare foreign keys |

### Roles

```sql
-- Create role
CREATE ROLE manager;

-- Grant privileges to role
GRANT create table, create view TO manager;

-- Grant role to users
GRANT manager TO SAM, STARK;

-- Revoke from role
REVOKE create table FROM manager;

-- Drop role
DROP ROLE manager;
```

---

## 6. SQL Constraints

### 6.1 Types of Constraints

| Constraint | Description | Level |
|------------|-------------|-------|
| `NOT NULL` | Column cannot have NULL | Column |
| `UNIQUE` | All values must be different | Column/Table |
| `PRIMARY KEY` | NOT NULL + UNIQUE | Column/Table |
| `FOREIGN KEY` | Links to PK of another table | Column/Table |
| `CHECK` | Values must satisfy condition | Column/Table |
| `DEFAULT` | Default value if none specified | Column |

### 6.2 NOT NULL

```sql
-- At creation
CREATE TABLE person (
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL
);

-- Add later
ALTER TABLE person MODIFY (age NUMBER(2) NOT NULL);
```

### 6.3 UNIQUE

```sql
-- Column level
CREATE TABLE Persons (
    P_Id INT UNIQUE,
    LastName VARCHAR(50)
);

-- Table level
CREATE TABLE Persons (
    P_Id INT,
    LastName VARCHAR(50),
    UNIQUE (P_Id, LastName)
);

-- Add later
ALTER TABLE Persons ADD UNIQUE (P_Id);
ALTER TABLE Persons ADD CONSTRAINT uc_PersonID UNIQUE (P_Id, LastName);

-- Drop
ALTER TABLE Persons DROP CONSTRAINT uc_PersonID;
```

### 6.4 PRIMARY KEY

```sql
-- Column level
CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(50)
);

-- Table level (composite)
CREATE TABLE Persons (
    P_Id INT,
    LastName VARCHAR(50),
    CONSTRAINT pk_PersonID PRIMARY KEY (P_Id, LastName)
);

-- Add later
ALTER TABLE Persons ADD PRIMARY KEY (P_Id);
ALTER TABLE Persons ADD CONSTRAINT pk_PersonID PRIMARY KEY (P_Id, LastName);

-- Drop
ALTER TABLE Persons DROP CONSTRAINT pk_PersonID;
```

### 6.5 FOREIGN KEY

```sql
-- At creation
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY,
    P_Id INT,
    FOREIGN KEY (P_Id) REFERENCES Persons(P_Id)
);

-- Add later
ALTER TABLE Orders ADD CONSTRAINT fk_PerOrders 
    FOREIGN KEY (P_Id) REFERENCES Persons(P_Id);

-- Drop
ALTER TABLE Orders DROP CONSTRAINT fk_PerOrders;
```

**Referential Actions:**
```sql
FOREIGN KEY (P_Id) REFERENCES Persons(P_Id)
    ON DELETE CASCADE
    ON UPDATE SET NULL;
```

| Action | Description |
|--------|-------------|
| `CASCADE` | Propagate changes |
| `SET NULL` | Set FK to NULL |
| `SET DEFAULT` | Set to default value |
| `RESTRICT` / `NO ACTION` | Reject operation |

### 6.6 CHECK

```sql
-- Column level
CREATE TABLE Persons (
    P_Id INT CHECK (P_Id > 0),
    City VARCHAR(50)
);

-- Table level
CREATE TABLE Persons (
    P_Id INT,
    City VARCHAR(50),
    CONSTRAINT chk_Person CHECK (City IN ('Mumbai', 'Delhi', 'Chennai'))
);

-- Add later
ALTER TABLE Persons ADD CHECK (P_Id > 0);
ALTER TABLE Persons ADD CONSTRAINT chk_Person CHECK (City IN ('Mumbai', 'Delhi'));

-- Drop
ALTER TABLE Persons DROP CONSTRAINT chk_Person;
```

### 6.7 DEFAULT

```sql
-- At creation
CREATE TABLE Persons (
    City VARCHAR(50) DEFAULT 'Sandnes'
);

-- Add later
ALTER TABLE Persons MODIFY City DEFAULT 'Sandnes';

-- Drop
ALTER TABLE Persons ALTER COLUMN City DROP DEFAULT;
```

---

## 7. Basic SELECT Queries

### 7.1 Basic Structure

```sql
SELECT list_of_attributes
FROM list_of_tables
WHERE list_of_conditions;
```

### 7.2 Logical Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `AND` | Both conditions true | `WHERE dept='Comp.Sci.' AND salary>70000` |
| `OR` | At least one true | `WHERE firstname='Tove' OR lastname='Steve'` |
| `NOT` | Negation | `WHERE NOT ecity='Chennai'` |

### 7.3 Comparison Operators

| Operator | Meaning |
|----------|---------|
| `=` | Equal to |
| `<>` or `!=` | Not equal to |
| `<` | Less than |
| `>` | Greater than |
| `<=` | Less than or equal |
| `>=` | Greater than or equal |

### 7.4 Arithmetic Operations

```sql
SELECT ID, name, dept_name, salary * 1.1 FROM instructor;
```
**Operators:** `+`, `-`, `*`, `/`

### 7.5 Aliasing

```sql
-- Table alias
SELECT * FROM employee AS e WHERE ...;

-- Column alias
SELECT ID, name, dept_name, salary * 1.1 AS new_salary FROM instructor;
SELECT ID, name, salary * 1.1 "New Salary" FROM instructor;  -- space in name
SELECT ID, name, salary * 1.1 new_salary FROM instructor;  -- without AS
```

### 7.6 DISTINCT

```sql
-- Eliminate duplicates
SELECT DISTINCT City FROM Persons;
```

### 7.7 NULL Handling

```sql
-- Select NULL values
SELECT * FROM EMPLOYEE WHERE CITY IS NULL;

-- NVL function (replace NULL)
SELECT name, NVL(SUPERSSN, '333445555') FROM employee;
```

### 7.8 String Operations

| Function | Description | Example |
|----------|-------------|---------|
| `||` | Concatenation | `name || ' has id ' || ssn` |
| `UPPER(s)` | Convert to uppercase | `UPPER(product_name)` |
| `LOWER(s)` | Convert to lowercase | `LOWER(product_name)` |
| `TRIM(s)` | Remove spaces | `TRIM(name)` |
| `LENGTH(s)` | String length | `LENGTH(product_name)` |
| `SUBSTR(str, start, length)` | Extract substring | `SUBSTR(item_id, 1, 2)` |
| `INSTR(str, pattern, start, nth)` | Find position | `INSTR(item_desc, ',', 1)` |
| `REPLACE(char, str1, str2)` | Replace string | `REPLACE(name, 'Jamil', 'Sara')` |
| `LPAD/RPAD` | Pad left/right | `LPAD(name, 10, '*')` |

### 7.9 LIKE / NOT LIKE (Pattern Matching)

| Wildcard | Meaning | Example |
|----------|---------|---------|
| `%` | Matches any substring | `'Intro%'` → starts with "Intro" |
| `_` | Matches any single character | `'_la'` → any char + "la" |

```sql
-- Names starting with 'S'
SELECT * FROM Persons WHERE City LIKE 'S%';

-- Names containing 'Comp'
SELECT * FROM department WHERE building LIKE '%Comp%';

-- First name pattern
SELECT * FROM Persons WHERE FirstName LIKE '_la';

-- NOT LIKE
SELECT * FROM Persons WHERE City NOT LIKE '%tav%';
```

### 7.10 IN / NOT IN

```sql
-- IN
SELECT * FROM Persons WHERE LastName IN ('Hansen', 'Pettersen');

-- NOT IN
SELECT * FROM Persons WHERE City NOT IN ('Mumbai', 'Delhi');
```

### 7.11 BETWEEN / NOT BETWEEN

```sql
-- BETWEEN
SELECT name FROM instructor WHERE salary BETWEEN 90000 AND 100000;

-- Equivalent to
SELECT name FROM instructor WHERE salary >= 90000 AND salary <= 100000;

-- NOT BETWEEN
SELECT * FROM Persons WHERE LastName NOT BETWEEN 'Hansen' AND 'Pettersen';
```

### 7.12 ORDER BY

```sql
-- Ascending (default)
SELECT * FROM Persons ORDER BY LastName;

-- Descending
SELECT * FROM instructor ORDER BY salary DESC;

-- Multiple columns
SELECT * FROM instructor ORDER BY salary DESC, name ASC;
```

---

## 8. Operators

### 8.1 Logical Connectives

| Operator | Use |
|----------|-----|
| `AND` | Both conditions must be true |
| `OR` | At least one condition true |
| `NOT` | Negate condition |

### 8.2 Comparison Operators

`=`, `<`, `<=`, `>`, `>=`, `<>`

### 8.3 Arithmetic Operators

`+`, `-`, `*`, `/`

### 8.4 Set Operations

| Operation | SQL | Symbol |
|-----------|-----|--------|
| Union | `UNION` / `UNION ALL` | ∪ |
| Intersection | `INTERSECT` / `INTERSECT ALL` | ∩ |
| Difference | `EXCEPT` / `MINUS` | − |

```sql
-- UNION (eliminates duplicates)
(SELECT course_id FROM section WHERE semester='Fall' AND year=2017)
UNION
(SELECT course_id FROM section WHERE semester='Spring' AND year=2018);

-- UNION ALL (retains duplicates)
(SELECT course_id FROM section WHERE semester='Fall' AND year=2017)
UNION ALL
(SELECT course_id FROM section WHERE semester='Spring' AND year=2018);

-- INTERSECT
(SELECT course_id FROM section WHERE semester='Fall' AND year=2017)
INTERSECT
(SELECT course_id FROM section WHERE semester='Spring' AND year=2018);

-- EXCEPT (MINUS in Oracle)
(SELECT course_id FROM section WHERE semester='Fall' AND year=2017)
EXCEPT
(SELECT course_id FROM section WHERE semester='Spring' AND year=2018);
```

---

## 9. Single Row Functions

### 9.1 Character Functions

| Function | Description | Example |
|----------|-------------|---------|
| `CONCAT(str1, str2)` | Concatenate two strings | `CONCAT('Hello', 'World')` |
| `LENGTH(str)` | Length of string | `LENGTH('Hello')` → 5 |
| `SUBSTR(str, start, len)` | Extract substring | `SUBSTR('Hello', 1, 3)` → 'Hel' |
| `INSTR(str, pattern)` | Find position of pattern | `INSTR('Hello', 'l')` → 3 |
| `LPAD(str, len, pad)` | Pad left | `LPAD('Hi', 5, '*')` → '***Hi' |
| `RPAD(str, len, pad)` | Pad right | `RPAD('Hi', 5, '*')` → 'Hi***' |
| `TRIM(str)` | Remove spaces | `TRIM('  Hello  ')` |
| `REPLACE(str, old, new)` | Replace substring | `REPLACE('Hello', 'l', 'x')` |
| `UPPER(str)` | Uppercase | `UPPER('hello')` → 'HELLO' |
| `LOWER(str)` | Lowercase | `LOWER('HELLO')` → 'hello' |
| `INITCAP(str)` | Initial capitals | `INITCAP('hello world')` → 'Hello World' |

### 9.2 Number Functions

| Function | Description | Example |
|----------|-------------|---------|
| `ROUND(n, d)` | Round to d decimal places | `ROUND(3.95, 0)` → 4 |
| `TRUNC(n, d)` | Truncate to d decimals | `TRUNC(3.95, 0)` → 3 |
| `MOD(m, n)` | Remainder of m/n | `MOD(10, 3)` → 1 |
| `POWER(m, n)` | m raised to power n | `POWER(2, 3)` → 8 |
| `SIGN(n)` | Sign of n (-1, 0, 1) | `SIGN(-125)` → -1 |
| `SQRT(n)` | Square root | `SQRT(16)` → 4 |

### 9.3 Date Functions

| Function | Description | Syntax |
|----------|-------------|--------|
| `SYSDATE` | Current system date/time | `SELECT SYSDATE FROM dual;` |
| `ADD_MONTHS(d, n)` | Add n months to date d | `ADD_MONTHS(SYSDATE, 3)` |
| `MONTHS_BETWEEN(f, s)` | Months between two dates | `MONTHS_BETWEEN(date1, date2)` |
| `NEXT_DAY(d, day)` | Next occurrence of day | `NEXT_DAY(SYSDATE, 'FRIDAY')` |
| `LAST_DAY(d)` | Last day of month | `LAST_DAY(SYSDATE)` |
| `ROUND(d, format)` | Round date | `ROUND(SYSDATE, 'MONTH')` |
| `TRUNC(d, format)` | Truncate date | `TRUNC(SYSDATE)` |

```sql
-- SYSDATE example
INSERT INTO employee VALUES (..., TRUNC(SYSDATE), ...);
```

### 9.4 Conversion Functions

#### TO_DATE
```sql
-- Convert string to date
SELECT TO_DATE('2012-06-05', 'YYYY-MM-DD') FROM dual;
```

#### TO_CHAR
```sql
-- Date to formatted string
SELECT first_name, 
       TO_CHAR(hire_date, 'MONTH DD, YYYY') AS HIRE_DATE,
       TO_CHAR(salary, '$99999.99') AS Salary
FROM employees WHERE ROWNUM < 5;
```

**TO_CHAR Format Specifiers:**

| Specifier | Description |
|-----------|-------------|
| `YYYY` | 4-digit year |
| `YY` | 2-digit year |
| `MON` | Abbreviated month (Jan-Dec) |
| `MONTH` | Full month name |
| `MM` | Month number (1-12) |
| `DY` | Abbreviated day (Sun-Sat) |
| `DD` | Day of month (1-31) |
| `HH24` | Hour (0-23) |
| `HH` / `HH12` | Hour (1-12) |
| `MI` | Minutes (0-59) |
| `SS` | Seconds (0-59) |

---

## 10. Aggregate Functions

| Function | Description | NULL Handling |
|----------|-------------|---------------|
| `COUNT(*)` | Count all rows | Includes all |
| `COUNT(column)` | Count non-NULL values | Ignores NULL |
| `AVG(column)` | Average of values | Ignores NULL |
| `SUM(column)` | Sum of values | Ignores NULL |
| `MIN(column)` | Minimum value | Ignores NULL |
| `MAX(column)` | Maximum value | Ignores NULL |

```sql
SELECT COUNT(*) FROM employee;
SELECT AVG(salary) FROM employee;
SELECT SUM(salary) FROM employee;
SELECT MIN(salary) FROM employee;
SELECT MAX(salary) FROM employee;
```

---

## 11. GROUP BY & HAVING

### 11.1 GROUP BY

> Groups rows with same values into summary rows

```sql
-- Single column
SELECT name, SUM(salary) FROM Employee GROUP BY name;

-- Multiple columns
SELECT subject, year, COUNT(*) FROM Student GROUP BY subject, year;
```

### 11.2 HAVING

> Filters groups (like WHERE filters rows)

```sql
SELECT CustomerName, SUM(OrderPrice) 
FROM Sales 
GROUP BY CustomerName 
HAVING SUM(OrderPrice) > 1200;
```

**Execution Order:**
```
FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY
```

---

## 12. Joins

> Combines records from two or more tables based on related columns

### 12.1 Types of Joins

| Join Type | Description |
|-----------|-------------|
| **INNER JOIN** | Returns matching rows from both tables |
| **LEFT JOIN** | All rows from left table, NULL for right if no match |
| **RIGHT JOIN** | All rows from right table, NULL for left if no match |
| **FULL JOIN** | All rows when match in either table |
| **NATURAL JOIN** | Join on common column names |
| **SELF JOIN** | Join table to itself |
| **CROSS JOIN** | Cartesian product |

### 12.2 INNER JOIN (Equijoin)

```sql
-- Method 1: Using WHERE
SELECT id, name, age, amount 
FROM customer c, orders o 
WHERE c.id = o.c_id;

-- Method 2: Using JOIN
SELECT id, name, age, amount 
FROM customer 
INNER JOIN orders ON customer.id = orders.c_id;
```

### 12.3 LEFT JOIN

```sql
SELECT id, name, amount, d 
FROM customer 
LEFT JOIN orders ON customer.id = orders.c_id;
```
> Returns all customers, NULL for orders if no match

### 12.4 RIGHT JOIN

```sql
SELECT id, name, amount, d 
FROM customer 
RIGHT JOIN orders ON customer.id = orders.c_id;
```
> Returns all orders, NULL for customer if no match

### 12.5 FULL JOIN

```sql
SELECT id, name, amount, d 
FROM customer 
FULL JOIN orders ON customer.id = orders.c_id;
```
> Returns all records from both tables, NULL where no match

### 12.6 NATURAL JOIN

```sql
-- Automatically joins on common column names
SELECT * FROM customer NATURAL JOIN order2;
```

### 12.7 SELF JOIN

```sql
-- Compare employees with higher salary
SELECT a.name, a.salary, b.name, b.salary 
FROM employee a, employee b 
WHERE a.salary < b.salary;

-- Employee and supervisor
SELECT a.emp_id AS "Emp_ID", a.emp_name AS "Employee Name",
       b.emp_id AS "Supervisor ID", b.emp_name AS "Supervisor Name"
FROM employee a, employee b
WHERE a.emp_supv = b.emp_id;
```

### 12.8 CROSS JOIN (Cartesian Product)

```sql
SELECT * FROM table1 CROSS JOIN table2;
-- or
SELECT * FROM table1, table2;
```

---

## 13. Set Operations

| Operation | SQL | Duplicates |
|-----------|-----|------------|
| Union | `UNION` | Eliminated |
| Union All | `UNION ALL` | Retained |
| Intersection | `INTERSECT` | Eliminated |
| Intersection All | `INTERSECT ALL` | Retained |
| Difference | `EXCEPT` / `MINUS` | Eliminated |
| Difference All | `EXCEPT ALL` | Retained |

```sql
-- Courses in Fall 2017 OR Spring 2018
(SELECT course_id FROM section WHERE semester='Fall' AND year=2017)
UNION
(SELECT course_id FROM section WHERE semester='Spring' AND year=2018);

-- Courses in BOTH Fall 2017 AND Spring 2018
(SELECT course_id FROM section WHERE semester='Fall' AND year=2017)
INTERSECT
(SELECT course_id FROM section WHERE semester='Spring' AND year=2018);

-- Courses in Fall 2017 BUT NOT Spring 2018
(SELECT course_id FROM section WHERE semester='Fall' AND year=2017)
EXCEPT
(SELECT course_id FROM section WHERE semester='Spring' AND year=2018);
```

---

## 14. Subqueries

> Query nested inside another query

```sql
-- Basic subquery
SELECT name FROM instructor 
WHERE dept_name IN (SELECT dept_name FROM department WHERE building='Watson');

-- Correlated subquery
SELECT e.name FROM employee e
WHERE salary > (SELECT AVG(salary) FROM employee WHERE dept_id = e.dept_id);
```

---

## 15. Date Functions (Detailed)

| Function | Description |
|----------|-------------|
| `SYSDATE` | Current date and time |
| `ADD_MONTHS(date, n)` | Add n months |
| `MONTHS_BETWEEN(date1, date2)` | Difference in months |
| `NEXT_DAY(date, 'DAY')` | Next occurrence of day |
| `LAST_DAY(date)` | Last day of month |
| `ROUND(date, format)` | Round date |
| `TRUNC(date)` | Truncate to date (remove time) |

```sql
INSERT INTO employee VALUES (..., TRUNC(SYSDATE), ...);
```

---

## 16. Conversion Functions

### TO_DATE
```sql
SELECT TO_DATE('2012-06-05', 'YYYY-MM-DD') FROM dual;
```

### TO_CHAR
```sql
SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI:SS') FROM dual;
SELECT TO_CHAR(1234.56, '$9,999.99') FROM dual;
```

---

## 17. The DUAL Table

> Special one-row, one-column table in Oracle for computations and selecting pseudo-columns

| Property | Description |
|----------|-------------|
| Columns | 1 (DUMMY, VARCHAR2(1)) |
| Rows | 1 (value 'X') |
| Schema | SYS (accessible to all) |
| Purpose | Selecting system functions, calculations |

```sql
SELECT SYSDATE FROM dual;
SELECT USER FROM dual;
SELECT LOG(2, 4) FROM dual;  -- Returns 2
SELECT 2+3 FROM dual;
```

---

## Quick Reference: SQL Command Summary

| Category | Commands |
|----------|----------|
| **DDL** | CREATE, ALTER, DROP, TRUNCATE, RENAME |
| **DML** | SELECT, INSERT, UPDATE, DELETE |
| **TCL** | COMMIT, ROLLBACK, SAVEPOINT |
| **DCL** | GRANT, REVOKE |
| **Constraints** | NOT NULL, UNIQUE, PRIMARY KEY, FOREIGN KEY, CHECK, DEFAULT |
| **Operators** | AND, OR, NOT, =, <>, <, >, LIKE, IN, BETWEEN |
| **Functions** | Aggregate, Single-row (char, number, date, conversion) |
| **Joins** | INNER, LEFT, RIGHT, FULL, NATURAL, SELF, CROSS |
| **Set Ops** | UNION, INTERSECT, EXCEPT |
