# 📘 ORACLE 26ai LAB ASSESSMENT 1 — COMPLETE STUDY NOTES

---

## PART 1: DDL (Data Definition Language)

### 1.1 CREATE TABLE — Simple Table Creation

The `CREATE TABLE` statement is used to define a new table and its structure.

**Basic Syntax:**
```sql
CREATE TABLE table_name (
    column1 datatype,
    column2 datatype,
    column3 datatype
);
```

**Key Oracle 26ai Data Types to Know:**

| Data Type | Purpose | Example |
|-----------|---------|---------|
| `VARCHAR2(n)` | Variable-length character string (use this, NOT `VARCHAR`) | `VARCHAR2(20)` |
| `NUMBER(p,s)` | Numeric data; `p` = precision, `s` = scale | `NUMBER(9)`, `NUMBER(9,2)` |
| `NUMBER` | Integer or floating-point number | `NUMBER` |
| `INT` | Integer (synonym for `NUMBER(38)`) | `INT` |
| `DATE` | Date and time (stores century, year, month, day, hour, minute, second) | `DATE` |
| `CHAR(n)` | Fixed-length character string | `CHAR(10)` |
| `DECIMAL(p,s)` | Synonym for `NUMBER(p,s)` in Oracle | `DECIMAL(10,2)` |

> **Oracle 26ai Note:** Always prefer `VARCHAR2` over `VARCHAR` in Oracle — `VARCHAR2` is Oracle's own optimized implementation and is guaranteed to behave consistently across versions.

---

### 1.2 ALTER TABLE — The Five Core Operations

The `ALTER TABLE` statement modifies the structure of an existing table. There are **five main operations**:

---

#### 🔹 **1. ADD** — Adding a New Column

**Syntax:**
```sql
ALTER TABLE table_name
ADD column_name datatype;
```

**What to Remember:**
- You can add multiple columns in a single statement:
  ```sql
  ALTER TABLE table_name
  ADD (col1 datatype, col2 datatype, col3 datatype);
  ```
- New columns are added at the end of the table (you cannot specify position in standard Oracle).
- New columns are `NULL` by default unless you specify `DEFAULT` value.
- If the table already has data, the new column will contain `NULL` for all existing rows unless `DEFAULT` is provided.

**With DEFAULT Value:**
```sql
ALTER TABLE table_name
ADD column_name datatype DEFAULT 'some_value';
```

---

#### 🔹 **2. MODIFY** — Changing Column Properties

**Syntax:**
```sql
ALTER TABLE table_name
MODIFY column_name new_datatype;
```

**What You Can MODIFY:**
| Change | Allowed? | Notes |
|--------|----------|-------|
| Data type | ✅ Yes | e.g., `VARCHAR2(20)` → `VARCHAR2(50)` |
| Size/Length | ✅ Yes | Can increase; decreasing only if no data violates |
| Add `NOT NULL` | ✅ Yes | Only if column has no NULL values |
| Remove `NOT NULL` | ✅ Yes | Always allowed |
| Change `DEFAULT` | ✅ Yes | Only affects future inserts |

**What You CANNOT MODIFY:**
- You **cannot** rename a column using `MODIFY` (use `RENAME COLUMN` instead).
- You **cannot** decrease the size if existing data would be truncated.
- You **cannot** change `NUMBER` to `VARCHAR2` if data exists (without conversion).

**Multiple Modifications:**
```sql
ALTER TABLE table_name
MODIFY (col1 VARCHAR2(30), col2 NUMBER(10,2) NOT NULL);
```

---

#### 🔹 **3. DROP** — Removing a Column

**Syntax:**
```sql
ALTER TABLE table_name
DROP COLUMN column_name;
```

**What to Remember:**
- Dropping a column permanently removes all data in that column.
- You cannot drop the **only** column in a table (Oracle requires at least one column).
- You cannot drop a column that is part of a **PRIMARY KEY** without first dropping the constraint.
- You cannot drop a column referenced by a **FOREIGN KEY** constraint.

**Drop Multiple Columns:**
```sql
ALTER TABLE table_name
DROP (column1, column2, column3);
```

**CASCADE CONSTRAINTS Option:**
```sql
ALTER TABLE table_name
DROP COLUMN column_name CASCADE CONSTRAINTS;
```
- Use this if the column is referenced by constraints in other tables.

**SET UNUSED Alternative:**
```sql
ALTER TABLE table_name
SET UNUSED COLUMN column_name;
```
- Marks column as unused (invisible) but doesn't physically remove it immediately.
- Good for large tables — physical removal happens later during maintenance.
- To drop all unused columns: `ALTER TABLE table_name DROP UNUSED COLUMNS;`

---

#### 🔹 **4. RENAME** — Renaming a Column

**Syntax:**
```sql
ALTER TABLE table_name
RENAME COLUMN old_column_name TO new_column_name;
```

**What to Remember:**
- Only renames the column; data type and constraints remain.
- Views, triggers, and stored procedures referencing the old name will break and need updating.
- In Oracle 26ai, this is an online operation for most cases.

---

#### 🔹 **5. DROP** (Table Level) — Dropping the Entire Table

**Syntax:**
```sql
DROP TABLE table_name;
```

**Options:**
```sql
DROP TABLE table_name CASCADE CONSTRAINTS;  -- Drops FK references too
DROP TABLE table_name PURGE;                -- Skips recycle bin, permanently deletes
```

**What to Remember:**
- `DROP TABLE` removes the table definition, all data, all indexes, all triggers, and all constraints.
- By default, dropped tables go to the **Recycle Bin** and can be recovered:
  ```sql
  FLASHBACK TABLE table_name TO BEFORE DROP;
  ```
- Use `PURGE` to bypass the Recycle Bin completely.

---

### 1.3 DESCRIBE (DESC) Command

**Syntax:**
```sql
DESC table_name;
-- or
DESCRIBE table_name;
```

**What It Shows:**
- Column Name
- Data Type (with size/precision)
- Nullable? (`NULL` or `NOT NULL`)

**Example Output:**
```
Name        Null?    Type
----------- -------- ------------
EMP_ID      NOT NULL NUMBER(5)
EMP_NAME             VARCHAR2(20)
DESIGNATION          VARCHAR2(20)
SALARY               NUMBER(10,2)
```

> **Important:** `DESC` is a SQL*Plus/SQL Developer command, not a standard SQL statement. In some GUI tools, you might use `DESCRIBE`.

---

## PART 2: DDL WITH CONSTRAINTS

### 2.1 Types of Constraints in Oracle

| Constraint | Purpose | Can Be NULL? |
|------------|---------|--------------|
| **PRIMARY KEY** | Uniquely identifies each row; combination of `UNIQUE` + `NOT NULL` | ❌ No |
| **FOREIGN KEY** | Links to PRIMARY KEY of another table | ✅ Yes (unless `NOT NULL` added) |
| **UNIQUE** | Ensures no duplicate values in column(s) | ✅ Yes (one NULL allowed) |
| **NOT NULL** | Ensures column cannot contain NULL | ❌ No |
| **CHECK** | Ensures values meet a specific condition | Depends on condition |
| **DEFAULT** | Provides default value if none specified | N/A |

---

### 2.2 Two Ways to Define Constraints

#### **A) Column-Level Constraints** (Inline)
Defined right after the column definition.

```sql
CREATE TABLE table_name (
    col1 datatype CONSTRAINT constraint_name PRIMARY KEY,
    col2 datatype CONSTRAINT constraint_name UNIQUE,
    col3 datatype CONSTRAINT constraint_name CHECK (condition),
    col4 datatype CONSTRAINT constraint_name REFERENCES parent_table(parent_col)
);
```

#### **B) Table-Level Constraints** (Out-of-line)
Defined at the end of all column definitions, after a comma.

```sql
CREATE TABLE table_name (
    col1 datatype,
    col2 datatype,
    col3 datatype,
    CONSTRAINT constraint_name PRIMARY KEY (col1),
    CONSTRAINT constraint_name UNIQUE (col2),
    CONSTRAINT constraint_name CHECK (condition),
    CONSTRAINT constraint_name FOREIGN KEY (col3) REFERENCES parent_table(parent_col)
);
```

**When MUST You Use Table-Level Constraints:**
- When a constraint involves **multiple columns** (composite keys)
- When you need to name the constraint explicitly for later reference

---

### 2.3 Naming Conventions for Constraints

Oracle auto-generates names if you don't specify, but **always name your constraints** for easier management.

| Constraint Type | Naming Convention |
|-----------------|-------------------|
| PRIMARY KEY | `PK_tablename` or `PK_tablename_column` |
| FOREIGN KEY | `FK_tablename_referencedtable` |
| UNIQUE | `UQ_tablename_column` |
| CHECK | `CHK_tablename_column_condition` |
| NOT NULL | `NN_tablename_column` |

---

### 2.4 DEFAULT Constraint

**Syntax (Column-Level):**
```sql
col_name datatype DEFAULT 'default_value'
```

**Important Notes:**
- `DEFAULT` is **not** a constraint in the traditional sense but behaves like one.
- It provides a value when `INSERT` doesn't specify one.
- `DEFAULT` works with `INSERT` but NOT with explicit `NULL` insertion:
  ```sql
  INSERT INTO table (col) VALUES (NULL);  -- Inserts NULL, not default!
  ```

---

### 2.5 CHECK Constraint Deep Dive

**Syntax:**
```sql
-- Column-level
col_name datatype CHECK (condition)

-- Table-level
CONSTRAINT chk_name CHECK (condition)
```

**Common CHECK Conditions:**
```sql
-- Greater than
CHECK (age > 10)

-- Less than
CHECK (no_of_seats < 50)

-- Range
CHECK (price BETWEEN 100 AND 1000)

-- List of values
CHECK (gender IN ('Male', 'Female', 'Other'))

-- Pattern matching (limited)
CHECK (email LIKE '%@%.%')
```

**Important:**
- `CHECK` constraints cannot reference other tables (use triggers or FK for that).
- `CHECK` constraints cannot use SYSDATE or other non-deterministic functions.
- `CHECK` conditions evaluate to TRUE or UNKNOWN (NULL is treated as acceptable unless `NOT NULL` is also present).

---

### 2.6 Creating Constraints Using ALTER TABLE

This is a **critical skill** for your lab — you need to create PK and FK using `ALTER`.

#### **Adding PRIMARY KEY via ALTER:**
```sql
ALTER TABLE table_name
ADD CONSTRAINT constraint_name PRIMARY KEY (column_name);
```

#### **Adding FOREIGN KEY via ALTER:**
```sql
ALTER TABLE child_table
ADD CONSTRAINT constraint_name FOREIGN KEY (child_column)
REFERENCES parent_table(parent_column);
```

#### **Adding UNIQUE Constraint via ALTER:**
```sql
ALTER TABLE table_name
ADD CONSTRAINT constraint_name UNIQUE (column_name);
```

#### **Adding CHECK Constraint via ALTER:**
```sql
ALTER TABLE table_name
ADD CONSTRAINT constraint_name CHECK (condition);
```

#### **Adding NOT NULL via ALTER:**
```sql
ALTER TABLE table_name
MODIFY column_name datatype NOT NULL;
```
> Note: `NOT NULL` is modified, not added as a separate constraint object.

---

### 2.7 Deleting (Dropping) Constraints via ALTER

#### **Drop a Specific Constraint:**
```sql
ALTER TABLE table_name
DROP CONSTRAINT constraint_name;
```

#### **Drop PRIMARY KEY:**
```sql
ALTER TABLE table_name
DROP PRIMARY KEY;
-- OR with name:
ALTER TABLE table_name
DROP CONSTRAINT pk_name;
```

#### **Drop FOREIGN Key:**
```sql
ALTER TABLE child_table
DROP CONSTRAINT fk_constraint_name;
```

#### **Drop UNIQUE Constraint:**
```sql
ALTER TABLE table_name
DROP CONSTRAINT unique_constraint_name;
```

#### **Drop CHECK Constraint:**
```sql
ALTER TABLE table_name
DROP CONSTRAINT check_constraint_name;
```

#### **Drop NOT NULL:**
```sql
ALTER TABLE table_name
MODIFY column_name datatype NULL;
```

#### **CASCADE Option for Foreign Keys:**
```sql
ALTER TABLE parent_table
DROP PRIMARY KEY CASCADE;  -- Drops all dependent FK constraints too
```

---

### 2.8 FOREIGN KEY Referential Actions

When defining FK, you can specify what happens when parent data changes:

```sql
ON DELETE CASCADE      -- Deletes child rows when parent is deleted
ON DELETE SET NULL     -- Sets FK column to NULL when parent is deleted
ON UPDATE CASCADE      -- Not supported in Oracle for FK
```

**Syntax:**
```sql
ALTER TABLE child_table
ADD CONSTRAINT fk_name FOREIGN KEY (child_col)
REFERENCES parent_table(parent_col)
ON DELETE CASCADE;
```

---

## PART 3: DML (Data Manipulation Language)

### 3.1 INSERT Statement

**Single Row Insert:**
```sql
INSERT INTO table_name (col1, col2, col3)
VALUES (value1, value2, value3);
```

**Insert All Columns (must match order):**
```sql
INSERT INTO table_name
VALUES (val1, val2, val3, val4);
```

**Insert with NULL:**
```sql
INSERT INTO table_name (col1, col2)
VALUES ('value1', NULL);
```

**Insert Date Values:**
```sql
-- Method 1: Using TO_DATE function (RECOMMENDED)
INSERT INTO table_name (date_col)
VALUES (TO_DATE('22-JUN-2019', 'DD-MON-YYYY'));

-- Method 2: Using ANSI date literal
INSERT INTO table_name (date_col)
VALUES (DATE '2019-06-22');
```

**Date Format Masks (Important for Oracle):**
| Format Element | Meaning | Example |
|----------------|---------|---------|
| `DD` | Day of month (01-31) | 22 |
| `MM` | Month number (01-12) | 06 |
| `MON` | Month abbreviation | JUN |
| `MONTH` | Full month name | JUNE |
| `YY` | Two-digit year | 19 |
| `YYYY` | Four-digit year | 2019 |
| `HH` | Hour (01-12) | 05 |
| `HH24` | Hour (00-23) | 17 |
| `MI` | Minutes | 30 |
| `SS` | Seconds | 45 |

---

### 3.2 SELECT Statement — Single Table Retrieval

#### **Basic SELECT:**
```sql
SELECT column1, column2 FROM table_name;
SELECT * FROM table_name;  -- All columns
```

#### **DISTINCT — Remove Duplicates:**
```sql
SELECT DISTINCT column_name FROM table_name;
```

#### **WHERE Clause — Filtering:**
```sql
SELECT * FROM table_name WHERE condition;
```

#### **Comparison Operators:**
| Operator | Meaning |
|----------|---------|
| `=` | Equal to |
| `<>` or `!=` | Not equal to |
| `>` | Greater than |
| `<` | Less than |
| `>=` | Greater than or equal |
| `<=` | Less than or equal |
| `BETWEEN ... AND ...` | Within a range (inclusive) |
| `IN (list)` | Match any value in list |
| `NOT IN (list)` | Not match any value in list |
| `IS NULL` | Is NULL |
| `IS NOT NULL` | Is not NULL |
| `LIKE` | Pattern matching |

---

### 3.3 LIKE Operator — Pattern Matching

| Wildcard | Meaning | Example |
|----------|---------|---------|
| `%` | Zero or more characters | `'a%'` → starts with 'a' |
| `_` | Exactly one character | `'_a%'` → second letter is 'a' |
| `[^]` | Not in range (not standard Oracle) | — |

**Examples:**
```sql
-- Names starting with 'S'
WHERE lname LIKE 'S%'

-- Names with 'a' as second letter
WHERE fname LIKE '_a%'

-- Names starting with 'S' or 'J'
WHERE lname LIKE 'S%' OR lname LIKE 'J%'

-- Area with second letter 'B'
WHERE area LIKE '_B%'
```

---

### 3.4 Logical Operators

| Operator | Meaning |
|----------|---------|
| `AND` | Both conditions must be true |
| `OR` | At least one condition must be true |
| `NOT` | Negates the condition |

**Operator Precedence:** `NOT` > `AND` > `OR` — use parentheses to override.

---

### 3.5 Working with Dates in WHERE Clause

```sql
-- Using TO_DATE
WHERE issue_date = TO_DATE('22-JUN-2019', 'DD-MON-YYYY')

-- Using EXTRACT to get month
WHERE EXTRACT(MONTH FROM issue_date) = 9   -- September

-- Using BETWEEN for date ranges
WHERE issue_date BETWEEN TO_DATE('01-SEP-2019', 'DD-MON-YYYY') 
                      AND TO_DATE('30-SEP-2019', 'DD-MON-YYYY')
```

**EXTRACT Function:**
```sql
EXTRACT(YEAR FROM date_column)     -- Returns year as number
EXTRACT(MONTH FROM date_column)    -- Returns month as number (1-12)
EXTRACT(DAY FROM date_column)      -- Returns day as number
```

---

### 3.6 Arithmetic in SELECT

```sql
SELECT price, price * 1.15 AS new_price FROM books;
```

**Arithmetic Operators:** `+`, `-`, `*`, `/`

---

### 3.7 Column Aliases (Renaming Output)

```sql
SELECT column_name AS alias_name FROM table;
SELECT column_name alias_name FROM table;      -- AS is optional
SELECT column_name "Alias With Spaces" FROM table;  -- Double quotes for spaces
```

---

## PART 4: GROUP / AGGREGATE FUNCTIONS

### 4.1 Aggregate Functions

| Function | Purpose | Returns NULL If? |
|----------|---------|------------------|
| `COUNT(*)` | Count all rows | Never NULL |
| `COUNT(column)` | Count non-NULL values | Never NULL |
| `SUM(column)` | Total of values | All NULL |
| `AVG(column)` | Average of values | All NULL |
| `MAX(column)` | Maximum value | All NULL |
| `MIN(column)` | Minimum value | All NULL |

**Important:** Aggregate functions ignore `NULL` values (except `COUNT(*)`).

---

### 4.2 GROUP BY Clause

Groups rows with same values into summary rows.

```sql
SELECT column1, aggregate_function(column2)
FROM table_name
GROUP BY column1;
```

**Rules:**
- Every column in `SELECT` that is NOT in an aggregate function MUST be in `GROUP BY`.
- `WHERE` filters rows BEFORE grouping.
- You cannot use column aliases in `GROUP BY`.

---

### 4.3 HAVING Clause

Filters groups AFTER aggregation (unlike `WHERE` which filters rows before).

```sql
SELECT dept, AVG(salary)
FROM employees
GROUP BY dept
HAVING AVG(salary) > 50000;
```

**WHERE vs HAVING:**
| WHERE | HAVING |
|-------|--------|
| Filters individual rows | Filters groups |
| Cannot use aggregate functions | Can use aggregate functions |
| Executes before GROUP BY | Executes after GROUP BY |

---

## PART 5: SINGLE ROW FUNCTIONS

### 5.1 Numeric Functions

| Function | Purpose | Example | Result |
|----------|---------|---------|--------|
| `ABS(n)` | Absolute value | `ABS(-11)` | 11 |
| `CEIL(n)` | Smallest integer ≥ n | `CEIL(22/3)` | 8 |
| `FLOOR(n)` | Largest integer ≤ n | `FLOOR(22/3)` | 7 |
| `ROUND(n, d)` | Round to d decimal places | `ROUND(11.45)` | 11 |
| `ROUND(n, d)` | Round to d decimal places | `ROUND(11.45, 1)` | 11.5 |
| `TRUNC(n, d)` | Truncate to d decimal places | `TRUNC(11.99)` | 11 |
| `SIGN(n)` | Sign of number (-1, 0, 1) | `SIGN(-11)` | -1 |
| `MOD(m, n)` | Remainder of m/n | `MOD(10, 3)` | 1 |
| `POWER(m, n)` | m raised to power n | `POWER(2, 3)` | 8 |
| `SQRT(n)` | Square root | `SQRT(16)` | 4 |

---

### 5.2 Character Functions

| Function | Purpose | Example | Result |
|----------|---------|---------|--------|
| `UPPER(string)` | Convert to uppercase | `UPPER('database')` | DATABASE |
| `LOWER(string)` | Convert to lowercase | `LOWER('HELLO')` | hello |
| `INITCAP(string)` | First letter of each word uppercase | `INITCAP('hello world')` | Hello World |
| `LENGTH(string)` | Number of characters | `LENGTH('hello')` | 5 |
| `CONCAT(str1, str2)` | Concatenate two strings | `CONCAT('good', 'morning')` | goodmorning |
| `SUBSTR(str, start, len)` | Extract substring | `SUBSTR('jkefer', 3, 3)` | efe |
| `INSTR(str, substr)` | Position of substring | `INSTR('hello', 'l')` | 3 |
| `INSTR(str, substr, start, occurrence)` | Position with options | `INSTR('hello', 'l', 1, 2)` | 4 |
| `LPAD(str, n, pad)` | Left pad to length n | `LPAD('hi', 5, '*')` | ***hi |
| `RPAD(str, n, pad)` | Right pad to length n | `RPAD('hi', 5, '*')` | hi*** |
| `TRANSLATE(str, from, to)` | Character-by-character replacement | `TRANSLATE('GEOGRAPHY', 'E', 'Z')` | GZOGRAPHY |
| `REPLACE(str, old, new)` | String replacement | `REPLACE('hello', 'l', 'x')` | hexxo |
| `TRIM(str)` | Remove leading/trailing spaces | `TRIM('  hello  ')` | hello |
| `LTRIM(str)` | Remove leading spaces | — | — |
| `RTRIM(str)` | Remove trailing spaces | — | — |

**TRANSLATE vs REPLACE:**
- `TRANSLATE` replaces character-by-character: `'ABC' → 'XYZ'` means A→X, B→Y, C→Z
- `REPLACE` replaces the entire substring: `REPLACE('ABC', 'ABC', 'XYZ')` → `'XYZ'`

---

### 5.3 The DUAL Table

Oracle's special one-row, one-column table used for evaluating expressions and functions.

```sql
SELECT ABS(-11) FROM DUAL;
SELECT CEIL(22/3) FROM DUAL;
SELECT SYSDATE FROM DUAL;
```

**Why DUAL?**
- Every `SELECT` needs a `FROM` clause in Oracle.
- `DUAL` provides a dummy table for calculations that don't need real data.

---

## PART 6: CONSTRAINTS REFERENCE FOR YOUR LAB TABLES

### 6.1 Tram Table Constraints

| Column | Data Type | Constraint | How to Implement |
|--------|-----------|------------|----------------|
| TramNo | VARCHAR2(10) | PRIMARY KEY | `CONSTRAINT PK_Tram PRIMARY KEY (TramNo)` |
| Sourceloc | VARCHAR2(20) | DEFAULT 'Chennai' | `DEFAULT 'Chennai'` |
| Destinationloc | VARCHAR2(20) | — | No constraint |
| Couchtype | VARCHAR2(20) | NOT NULL | `Couchtype VARCHAR2(20) NOT NULL` |

---

### 6.2 Reservationdetail Table Constraints

| Column | Data Type | Constraint | How to Implement |
|--------|-----------|------------|----------------|
| PNRNum | NUMBER(9) | PRIMARY KEY | `CONSTRAINT PK_Res PRIMARY KEY (PNRNum)` |
| Journey_date | DATE | — | No constraint |
| No_of_seats | INT | < 50 | `CHECK (No_of_seats < 50)` |
| location | VARCHAR2(50) | — | No constraint |
| Contact_No | NUMBER(9) | UNIQUE | `CONSTRAINT UQ_Contact UNIQUE (Contact_No)` |
| BusNo | VARCHAR2(10) | FOREIGN KEY | `CONSTRAINT FK_BusNo FOREIGN KEY (BusNo) REFERENCES Tram(TramNo)` |
| Seat_no | NUMBER | NOT NULL | `Seat_no NUMBER NOT NULL` |

> **Note:** The document says `BusNo` is a foreign key. Since `TramNo` is the PK of Tram table, `BusNo` in Reservationdetail references `TramNo`.

---

### 6.3 Ticketdetail Table Constraints

| Column | Data Type | Constraint | How to Implement |
|--------|-----------|------------|----------------|
| Ticket_No | INT | PRIMARY KEY | `CONSTRAINT PK_Ticket PRIMARY KEY (Ticket_No)` |
| Journey_date | DATE | NOT NULL | `Journey_date DATE NOT NULL` |
| Age | INT | > 10 | `CHECK (Age > 10)` |
| Gender | CHAR(10) | NOT NULL | `Gender CHAR(10) NOT NULL` |
| Sourcelocn | VARCHAR2(10) | DEFAULT 'Chennai' | `DEFAULT 'Chennai'` |
| Destinationlocn | VARCHAR2(10) | — | No constraint |
| Dep_time | VARCHAR2(10) | — | No constraint |
| Bus_No | VARCHAR2(10) | UNIQUE | `CONSTRAINT UQ_BusNo UNIQUE (Bus_No)` |

---

### 6.4 Passenger Table Constraints

| Column | Data Type | Constraint | How to Implement |
|--------|-----------|------------|----------------|
| PNR_No | NUMBER(9) | PRIMARY KEY | `CONSTRAINT PK_Pass PRIMARY KEY (PNR_No)` |
| Ticket_No | NUMBER(9) | FOREIGN KEY | `CONSTRAINT FK_Ticket FOREIGN KEY (Ticket_No) REFERENCES Ticketdetail(Ticket_No)` |
| Name | VARCHAR2(15) | — | No constraint |
| Age | INT | > 10 | `CHECK (Age > 10)` |
| Gender | CHAR(10) | NOT NULL | `Gender CHAR(10) NOT NULL` |
| Contact_no | NUMBER(9) | UNIQUE | `CONSTRAINT UQ_PassContact UNIQUE (Contact_no)` |

---

### 6.5 Cancellation Table Constraints

| Column | Data Type | Constraint | How to Implement |
|--------|-----------|------------|----------------|
| PNR_No | NUMBER(9) | FOREIGN KEY | `CONSTRAINT FK_CancelPNR FOREIGN KEY (PNR_No) REFERENCES Reservationdetail(PNRNum)` |
| Journey_date | DATE | — | No constraint |
| Seat_no | INT | NOT NULL | `Seat_no INT NOT NULL` |
| Contact_No | NUMBER(9) | UNIQUE | `CONSTRAINT UQ_CancelContact UNIQUE (Contact_No)` |

---

## PART 7: IMPORTANT ORACLE 26ai SPECIFIC NOTES

### 7.1 Table Naming with Register Number
As per your lab instructions: **Table names should include your register number.**
```sql
-- Example: If register number is 21001
CREATE TABLE Tram_21001 (...);
CREATE TABLE Reservationdetail_21001 (...);
```

### 7.2 Oracle 26ai Enhancements to Know
- **JSON Relational Duality Views** — New in 23ai/26ai, but not needed for basic DDL/DML.
- **AI Vector Search** — New feature, not relevant to this lab.
- **SQL Domains** — Enhanced data type definitions.
- **Schema Annotations** — Metadata tagging for columns.

For this lab, standard Oracle SQL syntax works perfectly in 26ai.

### 7.3 Common Errors to Avoid

| Error | Cause | Solution |
|-------|-------|----------|
| `ORA-00904: invalid identifier` | Column name doesn't exist or misspelled | Check column names |
| `ORA-02270: no matching unique/primary key` | FK references non-existent PK | Create parent table first |
| `ORA-02291: integrity constraint violated` | FK value doesn't exist in parent | Insert parent data first |
| `ORA-00001: unique constraint violated` | Duplicate value in UNIQUE/PK column | Check data for duplicates |
| `ORA-02290: check constraint violated` | CHECK condition failed | Verify data meets condition |
| `ORA-01400: cannot insert NULL` | Inserting NULL into NOT NULL column | Provide value or remove NOT NULL |

---

## PART 8: EXECUTION ORDER FOR YOUR LAB

### For DDL Section:
1. Create all tables with basic structure (or with constraints inline)
2. Use `DESC` to verify structure
3. Use `ALTER TABLE` to add PK and FK constraints
4. Use `ALTER TABLE` to add UNIQUE constraints at table level
5. Verify with `DESC` and `SELECT * FROM USER_CONSTRAINTS`
6. Use `ALTER TABLE DROP CONSTRAINT` to delete unique constraints
7. Finally, `DROP TABLE` all tables (with `CASCADE CONSTRAINTS` if needed)

### For DML Section:
1. Create tables (Member, Books, Transaction)
2. Insert data into Member table
3. Insert data into Books table
4. Insert data into Transaction table
5. Run all SELECT queries
6. Run aggregate queries
7. Run single-row function queries using DUAL

---

## PART 9: SYSTEM TABLES FOR VERIFICATION

```sql
-- View all constraints on your table
SELECT constraint_name, constraint_type, search_condition
FROM USER_CONSTRAINTS
WHERE table_name = 'YOUR_TABLE_NAME';

-- View all columns in your table
SELECT column_name, data_type, data_length, nullable
FROM USER_TAB_COLUMNS
WHERE table_name = 'YOUR_TABLE_NAME';

-- View all tables you own
SELECT table_name FROM USER_TABLES;
```

**Constraint Type Codes:**
| Code | Meaning |
|------|---------|
| `P` | PRIMARY KEY |
| `R` | FOREIGN KEY (Referential) |
| `U` | UNIQUE |
| `C` | CHECK |
| `O` | READ ONLY |
