# 📘 ORACLE 26ai LAB 6 — COMPLETE STUDY NOTES

---

## PART 1: SUBQUERIES (Nested SELECT Statements)

### 1.1 What is a Subquery?

A **subquery** (or inner query) is a `SELECT` statement nested inside another `SELECT`, `INSERT`, `UPDATE`, or `DELETE` statement. The outer query uses the result of the subquery to perform its operation.

**Basic Structure:**
```sql
SELECT column1, column2
FROM table_name
WHERE column_name OPERATOR (SELECT column_name FROM another_table WHERE condition);
```

---

### 1.2 Types of Subqueries

| Type | Description | Operator Used |
|------|-------------|---------------|
| **Single-Row Subquery** | Returns exactly one row | `=`, `>`, `<`, `>=`, `<=`, `<>` |
| **Multiple-Row Subquery** | Returns one or more rows | `IN`, `NOT IN`, `ANY`, `SOME`, `ALL` |
| **Correlated Subquery** | References columns from the outer query | `EXISTS`, `NOT EXISTS` |
| **Nested Subquery** | Subquery inside another subquery | `IN` (multi-level) |

---

### 1.3 Single-Row Subquery (`=` operator)

Returns exactly **one row** with **one column**. Used with comparison operators.

**Syntax:**
```sql
SELECT a.ord_num, a.ord_amount, a.cust_code, a.agent_code
FROM orders a
WHERE a.agent_code = (SELECT b.agent_code
                        FROM agents b
                        WHERE b.agent_name = 'Alex');
```

**Execution Flow:**
1. Inner query runs first: finds agent_code for 'Alex' → returns `A003`
2. Outer query runs: `WHERE a.agent_code = 'A003'`
3. Final result: All orders handled by agent Alex

**Important Rules:**
- If the subquery returns **more than one row**, Oracle throws: `ORA-01427: single-row subquery returns more than one row`
- If the subquery returns **no rows**, the condition evaluates to `NULL` (no rows returned)
- You **must** use `=` only when you are certain the subquery returns exactly one row

---

### 1.4 Multiple-Row Subquery — `IN` Operator

The `IN` operator checks if a value matches **any value** in the list returned by the subquery.

**Syntax:**
```sql
SELECT ord_num, ord_amount, ord_date, cust_code, agent_code
FROM orders
WHERE agent_code IN (SELECT agent_code
                     FROM agents
                     WHERE working_area = 'Bangalore');
```

**Execution Flow:**
1. Inner query: `SELECT agent_code FROM agents WHERE working_area = 'Bangalore'`
   - Returns: `A001`, `A007`, `A011`
2. Outer query becomes: `WHERE agent_code IN ('A001', 'A007', 'A011')`
3. Result: All orders from agents working in Bangalore

**Key Points:**
- `IN` is equivalent to `= ANY`
- The subquery can return **zero, one, or many rows**
- `IN` handles `NULL` values carefully (see warning below)

---

### 1.5 Multiple-Row Subquery — `NOT IN` Operator

Returns rows where the value is **not present** in the subquery result set.

**Syntax:**
```sql
SELECT ord_num, ord_amount, ord_date, cust_code, agent_code
FROM orders
WHERE agent_code NOT IN (SELECT agent_code
                         FROM agents
                         WHERE working_area = 'Bangalore');
```

**Execution Flow:**
1. Inner query returns: `A001`, `A007`, `A011`
2. Outer query becomes: `WHERE agent_code NOT IN ('A001', 'A007', 'A011')`
3. Result: All orders from agents **NOT** in Bangalore

**⚠️ CRITICAL WARNING — NULL Handling with NOT IN:**

If the subquery returns **even one NULL value**, `NOT IN` returns **NO ROWS AT ALL** (not zero matching rows — literally empty result).

**Why?**
- `x NOT IN (A, B, NULL)` is evaluated as `x <> A AND x <> B AND x <> NULL`
- Any comparison with `NULL` yields `UNKNOWN`
- `AND` with `UNKNOWN` makes the entire condition `UNKNOWN` → row is rejected

**Safe Practice:**
```sql
-- Always filter NULLs in the subquery when using NOT IN
WHERE agent_code NOT IN (SELECT agent_code
                         FROM agents
                         WHERE working_area = 'Bangalore'
                         AND agent_code IS NOT NULL);
```

---

### 1.6 Subquery with `NULL` — The `IN` Exception

```sql
SELECT employeeid, firstname, lastname
FROM employees
WHERE employeeid IN (3, 5, NULL);
```

| employeeid | Result |
|------------|--------|
| 3 | ✅ Included (3 = 3 is TRUE) |
| 5 | ✅ Included (5 = 5 is TRUE) |
| 7 | ❌ Excluded (7 <> 3, 7 <> 5, 7 <> NULL → UNKNOWN) |

> **Rule:** `IN` with NULL in the list still works for matching values, but non-matching values become UNKNOWN.

---

## PART 2: CORRELATED SUBQUERIES

### 2.1 What Makes It "Correlated"?

A **correlated subquery** references one or more columns from the **outer query** in its `WHERE` clause. Unlike regular subqueries, it is executed **once for every row** processed by the outer query.

| Regular Subquery | Correlated Subquery |
|------------------|---------------------|
| Runs **once** independently | Runs **once per outer row** |
| No reference to outer query | References outer query columns |
| Faster for small datasets | Can be slower on large datasets |
| Uses `IN`, `NOT IN` | Uses `EXISTS`, `NOT EXISTS` |

---

### 2.2 `EXISTS` Operator

`EXISTS` returns `TRUE` if the subquery returns **at least one row**, otherwise `FALSE`.

**Syntax:**
```sql
SELECT employee_id, manager_id, first_name, last_name
FROM employees a
WHERE EXISTS (SELECT employee_id
              FROM employees b
              WHERE b.manager_id = a.employee_id);
```

**What it does:**
- Finds all employees who are **managers** (i.e., at least one employee reports to them)

**Execution Flow (Row-by-Row):**
1. Outer query picks row: `Steven (100)`
2. Subquery runs: `SELECT employee_id FROM employees b WHERE b.manager_id = 100`
   - Returns rows (Neena, Lex report to 100) → **TRUE**
   - Steven is included in result
3. Outer query picks row: `Bruce (104)`
4. Subquery runs: `SELECT employee_id FROM employees b WHERE b.manager_id = 104`
   - Returns no rows → **FALSE**
   - Bruce is excluded

**Key Points:**
- `EXISTS` stops scanning as soon as it finds **one matching row** (short-circuit evaluation)
- The actual column selected in the subquery doesn't matter — Oracle ignores it
- Commonly used pattern: `SELECT 1 FROM ...` or `SELECT * FROM ...` (both work the same)

---

### 2.3 `NOT EXISTS` Operator

`NOT EXISTS` returns `TRUE` if the subquery returns **zero rows**.

**Syntax:**
```sql
SELECT employee_id, manager_id, first_name, last_name
FROM employees a
WHERE NOT EXISTS (SELECT employee_id
                  FROM employees b
                  WHERE b.manager_id = a.employee_id);
```

**What it does:**
- Finds all employees who are **NOT managers** (leaf nodes in org chart)

**Execution Flow:**
1. Outer query picks row: `Steven (100)`
2. Subquery: `SELECT ... WHERE b.manager_id = 100` → returns rows → EXISTS is TRUE → NOT EXISTS is **FALSE** → excluded
3. Outer query picks row: `Bruce (104)`
4. Subquery: `SELECT ... WHERE b.manager_id = 104` → no rows → EXISTS is FALSE → NOT EXISTS is **TRUE** → included

**Result:** Bruce, David, Valli, Diana, Daniel (employees with no subordinates)

---

### 2.4 Correlated Subquery vs JOIN — When to Use What?

| Use Case | Preferred Approach |
|----------|-------------------|
| Check for existence of related rows | `EXISTS` / `NOT EXISTS` |
| Need columns from both tables | `JOIN` |
| Find rows that have NO match in another table | `NOT EXISTS` (more reliable than `NOT IN`) |
| Performance on large tables | `EXISTS` often faster than `IN` with duplicates |

> **Oracle Best Practice:** Use `NOT EXISTS` instead of `NOT IN` when dealing with nullable columns.

---

## PART 3: SET COMPARISON OPERATORS (`ALL`, `ANY`, `SOME`)

These operators compare a value against **all** or **any** values returned by a subquery.

### 3.1 `ALL` Operator

Compares against **every** value returned by the subquery. The condition must be true for **all** of them.

**Syntax:**
```sql
SELECT name
FROM customer c
WHERE c.age >= ALL (SELECT d.age FROM customer d);
```

**Meaning:**
- `age >= ALL (...)` → age is greater than or equal to **the maximum** age in the subquery result
- Equivalent to: `age >= (SELECT MAX(age) FROM customer)`

**Example Data:**
| ID | NAME | AGE |
|----|------|-----|
| 1 | aaa | 20 |
| 2 | bbb | 21 |
| 3 | ccc | 20 |
| 4 | ddd | 20 |

**Result:** `bbb` (age 21 is >= all ages: 20, 21, 20, 20)

---

### 3.2 `ANY` and `SOME` Operators

`ANY` and `SOME` are **synonyms** — they do the exact same thing.

Compares against **at least one** value. The condition must be true for **any one** of them.

**Syntax:**
```sql
SELECT name
FROM customer c
WHERE c.age < ANY (SELECT d.age FROM customer d);
```

**Meaning:**
- `age < ANY (...)` → age is less than **at least one** value in the subquery
- Equivalent to: `age < (SELECT MAX(age) FROM customer)`

**Result:** `aaa`, `ccc`, `ddd` (all have age 20, which is less than 21)

---

### 3.3 Quick Reference Table

| Operator | Meaning | Equivalent To |
|----------|---------|---------------|
| `> ALL` | Greater than every value | `> (SELECT MAX(...) ...)` |
| `< ALL` | Less than every value | `< (SELECT MIN(...) ...)` |
| `>= ALL` | Greater than or equal to max | `>= (SELECT MAX(...) ...)` |
| `<= ALL` | Less than or equal to min | `<= (SELECT MIN(...) ...)` |
| `> ANY` | Greater than at least one value | `> (SELECT MIN(...) ...)` |
| `< ANY` | Less than at least one value | `< (SELECT MAX(...) ...)` |
| `= ANY` | Equal to any value | Same as `IN` |

---

## PART 4: NESTED QUERIES (Multi-Level Subqueries)

A subquery can contain **another subquery** inside it. Oracle allows multiple levels of nesting.

### 4.1 Three-Level Nested Query Example

**Scenario:** Find names of students enrolled in either 'DSA' or 'DBMS' courses.

**Tables:**
- `STUDENT` (S_ID, S_NAME, ...)
- `STUDENT_COURSE` (S_ID, C_ID)
- `COURSE` (C_ID, C_NAME)

**Query:**
```sql
SELECT S_NAME
FROM STUDENT
WHERE S_ID IN (SELECT S_ID
               FROM STUDENT_COURSE
               WHERE C_ID IN (SELECT C_ID
                              FROM COURSE
                              WHERE C_NAME = 'DSA' OR C_NAME = 'DBMS'));
```

**Execution Order (Inside → Out):**
1. **Innermost:** `SELECT C_ID FROM COURSE WHERE C_NAME = 'DSA' OR C_NAME = 'DBMS'`
   - Returns C_IDs for DSA and DBMS
2. **Middle:** `SELECT S_ID FROM STUDENT_COURSE WHERE C_ID IN (...)`
   - Returns S_IDs of students enrolled in those courses
3. **Outer:** `SELECT S_NAME FROM STUDENT WHERE S_ID IN (...)`
   - Returns student names

**Important Notes:**
- Each subquery must be enclosed in its own parentheses
- Performance degrades with deep nesting — consider `JOIN` alternatives for production
- Maximum nesting depth in Oracle is **255** levels (practically never reached)

---

## PART 5: SET OPERATIONS

Set operations combine the results of two or more `SELECT` statements into a single result set.

### 5.1 Union Compatibility (Mandatory Rule)

For set operations to work, both queries must be **union compatible**:

| Requirement | Description |
|-------------|-------------|
| **Same number of columns** | Both SELECTs must return the same count of columns |
| **Compatible data types** | Corresponding columns must have compatible (or same) domains |
| **Column names** | Can differ — result uses names from the **first** query |

**Examples:**
```sql
-- ✅ UNION COMPATIBLE: Same columns, same domains
SELECT sid, sname FROM R
UNION
SELECT sid, studentname FROM S;

-- ❌ NOT UNION COMPATIBLE: Different number of columns
SELECT sid, sname FROM R
UNION
SELECT sid, sname, marks FROM S;

-- ❌ NOT UNION COMPATIBLE: Incompatible domains
SELECT sid, sname FROM R
UNION
SELECT sid, marks FROM S;  -- sname (VARCHAR) vs marks (NUMBER)
```

---

### 5.2 `UNION`

Combines results from two queries and **removes duplicates**.

**Syntax:**
```sql
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_TEST
UNION
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_DESIGN;
```

**Behavior:**
- Returns all unique rows from both tables
- Sorts the result automatically (implicit `DISTINCT` + sort)
- Slower than `UNION ALL` due to duplicate elimination

---

### 5.3 `UNION ALL`

Combines results from two queries and **keeps all duplicates**.

**Syntax:**
```sql
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_TEST
UNION ALL
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_DESIGN;
```

**Behavior:**
- Returns every row from both queries (including duplicates)
- **Faster** than `UNION` — no sorting or duplicate checking
- Use this when you know there are no duplicates or when duplicates are expected/needed

---

### 5.4 `INTERSECT`

Returns only rows that appear in **both** result sets.

**Syntax:**
```sql
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_TEST
INTERSECT
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_DESIGN;
```

**Behavior:**
- Returns common rows between the two queries
- Removes duplicates
- Result is automatically sorted

---

### 5.5 `MINUS`

Returns rows from the first query that **do not appear** in the second query.

**Syntax:**
```sql
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_TEST
MINUS
SELECT EMPID, EMP_NAME, EMP_ADDRESS, EMP_SSN FROM EMP_DESIGN;
```

**Behavior:**
- Returns rows unique to the first query
- Order matters: `A MINUS B` ≠ `B MINUS A`
- Removes duplicates

---

### 5.6 Set Operations Summary Table

| Operation | Symbol | Duplicates | Sorting | Commutative? |
|-----------|--------|------------|---------|--------------|
| `UNION` | ∪ | Removed | Yes | Yes |
| `UNION ALL` | ∪ | Kept | No | Yes |
| `INTERSECT` | ∩ | Removed | Yes | Yes |
| `MINUS` | − | Removed | Yes | **No** |

---

### 5.7 Using `ORDER BY` with Set Operations

`ORDER BY` can only appear **once** at the very end and must use column positions or aliases from the first query.

```sql
SELECT EMPID, EMP_NAME FROM EMP_TEST
UNION
SELECT EMPID, EMP_NAME FROM EMP_DESIGN
ORDER BY EMP_NAME;  -- ✅ Valid

SELECT EMPID, EMP_NAME FROM EMP_TEST
UNION
SELECT EMPID, EMP_NAME FROM EMP_DESIGN
ORDER BY 2;  -- ✅ Valid (sort by 2nd column)
```

---

## PART 6: VIEWS

### 6.1 What is a View?

A **View** is a **virtual table** based on the result of a `SELECT` query. It does not store data physically (unless it's a Materialized View) — it stores only the query definition.

**Why Use Views?**
- Simplify complex queries
- Restrict data access (security)
- Provide logical data independence
- Present data in a customized format

---

### 6.2 Creating a Simple View

**Syntax:**
```sql
CREATE VIEW view_name AS
SELECT column1, column2
FROM table_name
WHERE condition;
```

**Example:**
```sql
CREATE VIEW DetailsView AS
SELECT NAME, ADDRESS
FROM StudentDetails
WHERE S_ID < 5;
```

**Retrieving Data:**
```sql
SELECT * FROM DetailsView;
```

---

### 6.3 Creating a View from Multiple Tables (Complex View)

```sql
CREATE VIEW MarksView AS
SELECT s.NAME, s.ADDRESS, m.MARKS
FROM StudentDetails s, StudentMarks m
WHERE s.NAME = m.NAME;
```

**Retrieving Data:**
```sql
SELECT * FROM MarksView;
```

**Result:**
| NAME | ADDRESS | MARKS |
|------|---------|-------|
| Harsh | Kolkata | 90 |
| Pratik | Delhi | 80 |
| Dhanraj | Bihar | 95 |
| Ram | Rajasthan | 85 |

---

### 6.4 View with Join Condition (Lab-Style Example)

```sql
CREATE VIEW ClassList AS
SELECT s.Number, s.Name
FROM student s, enrolled e
WHERE s.number = e.student_num
  AND e.cnum = 3753
  AND e.section_num = 'X1';
```

**Query the View:**
```sql
SELECT * FROM ClassList;
```

---

### 6.5 Dropping a View

```sql
DROP VIEW view_name;
```

**Important:**
- Dropping a view does **not** affect the underlying tables or their data
- If underlying table is dropped, the view becomes invalid (`ORA-04063: view has errors`)

---

### 6.6 View Restrictions & Important Notes

| Aspect | Rule |
|--------|------|
| **Data Storage** | Views don't store data (except Materialized Views) |
| **DML Operations** | Simple views support `INSERT`, `UPDATE`, `DELETE`. Complex views (with joins, aggregates, DISTINCT) may not |
| **Security** | Can hide columns/rows from users |
| **Performance** | Query runs every time you access the view |
| **Naming** | View name must be unique within the schema |

---

## PART 7: COMMON ERRORS & TROUBLESHOOTING

| Error Code | Error Message | Cause | Solution |
|------------|---------------|-------|----------|
| `ORA-01427` | single-row subquery returns more than one row | Used `=` with a subquery returning multiple rows | Use `IN` or limit subquery with `ROWNUM = 1` |
| `ORA-01400` | cannot insert NULL | Inserting NULL into a NOT NULL column | Provide a value or alter the column |
| `ORA-01789` | query block has incorrect number of result columns | Set operation on queries with different column counts | Ensure both SELECTs have same number of columns |
| `ORA-01790` | expression must have same datatype as corresponding expression | Incompatible data types in set operation | Cast columns to matching types using `TO_CHAR()`, `TO_NUMBER()` |
| `ORA-00942` | table or view does not exist | Typo in table/view name; insufficient privileges | Check spelling; verify permissions |
| `ORA-01031` | insufficient privileges | No CREATE VIEW privilege | Grant from DBA: `GRANT CREATE VIEW TO user;` |
| `ORA-00998` | must name this expression with a column alias | Set operation on computed columns without aliases | Add column alias: `SELECT col1 + col2 AS total FROM ...` |

---

## PART 8: EXECUTION ORDER FOR LAB 6

### Step-by-Step Practice Flow:

1. **Subqueries Practice**
   - Write single-row subqueries using `=`
   - Write multiple-row subqueries using `IN`
   - Write `NOT IN` queries (remember the NULL trap!)

2. **Correlated Subqueries**
   - Write `EXISTS` to find managers/related records
   - Write `NOT EXISTS` to find leaf nodes/unmatched records
   - Compare performance with `IN` alternatives

3. **Set Comparison Operators**
   - Practice `ALL`, `ANY`, `SOME` with aggregate-equivalent queries

4. **Nested Queries**
   - Build 3-level nested queries
   - Verify results at each level independently

5. **Set Operations**
   - Ensure union compatibility first
   - Run `UNION`, `UNION ALL`, `INTERSECT`, `MINUS`
   - Observe duplicate behavior differences

6. **Views**
   - Create simple views
   - Create complex views with joins
   - Query views like regular tables
   - Drop views cleanly

---

## PART 9: QUICK SYNTAX CHEAT SHEET

```sql
-- Single-Row Subquery
SELECT * FROM table WHERE col = (SELECT col FROM table2 WHERE condition);

-- Multiple-Row Subquery (IN)
SELECT * FROM table WHERE col IN (SELECT col FROM table2 WHERE condition);

-- Multiple-Row Subquery (NOT IN) — Filter NULLs!
SELECT * FROM table WHERE col NOT IN (SELECT col FROM table2 WHERE condition AND col IS NOT NULL);

-- Correlated Subquery (EXISTS)
SELECT * FROM outer_table a WHERE EXISTS (SELECT 1 FROM inner_table b WHERE b.col = a.col);

-- Correlated Subquery (NOT EXISTS)
SELECT * FROM outer_table a WHERE NOT EXISTS (SELECT 1 FROM inner_table b WHERE b.col = a.col);

-- Set Comparison
SELECT * FROM table WHERE col > ALL (SELECT col FROM table2);
SELECT * FROM table WHERE col > ANY (SELECT col FROM table2);

-- Set Operations
SELECT col FROM table1 UNION SELECT col FROM table2;
SELECT col FROM table1 UNION ALL SELECT col FROM table2;
SELECT col FROM table1 INTERSECT SELECT col FROM table2;
SELECT col FROM table1 MINUS SELECT col FROM table2;

-- Views
CREATE VIEW v_name AS SELECT col FROM table WHERE condition;
SELECT * FROM v_name;
DROP VIEW v_name;
```

---

## PART 10: ORACLE 26ai SPECIFIC NOTES

### 10.1 JSON Relational Duality Views (New in 26ai)
Oracle 26ai introduces JSON Relational Duality Views that allow accessing relational data as JSON documents. This is **advanced** and not required for basic Lab 6, but good to know:
```sql
-- Conceptual (not exam focus)
CREATE JSON RELATIONAL DUALITY VIEW StudentView AS ...
```

### 10.2 Schema Annotations
New in 26ai — you can annotate columns with metadata:
```sql
CREATE TABLE students (
    id NUMBER ANNOTATIONS (DISPLAY_NAME 'Student ID'),
    name VARCHAR2(50) ANNOTATIONS (DISPLAY_NAME 'Full Name')
);
```

### 10.3 For Your Lab
- Standard Oracle SQL syntax works perfectly in 26ai
- Views are created and dropped the same way as previous versions
- Set operations behave identically to Oracle 11g/12c/19c/21c

---

*End of Lab 6 Notes — Good Luck with your Assessment!*
