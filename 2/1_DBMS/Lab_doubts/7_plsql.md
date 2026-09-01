# 📘 ORACLE PL/SQL — COMPLETE STUDY NOTES

> Compiled from Week 1 (Basics) through Week 5 (Cursors)

---

## PART 1: PL/SQL BASICS (Week 1)

### 1.1 What is PL/SQL?

PL/SQL (Procedural Language / Structured Query Language) is Oracle's procedural extension to SQL. It allows you to write programs that include SQL statements along with procedural constructs like loops, conditions, and exception handling.

### 1.2 PL/SQL Block Structure

Every PL/SQL block has three sections:

| Section | Keyword | Description |
|---------|---------|-------------|
| **Declaration** | `DECLARE` | Variables, constants, cursors declared here (optional) |
| **Executable** | `BEGIN` | Main logic — SQL and procedural statements (mandatory) |
| **Exception** | `EXCEPTION` | Error handling code (optional) |

```sql
DECLARE
    -- declaration section
BEGIN
    -- executable section
EXCEPTION
    -- exception handling section
END;
```

### 1.3 Hello World — Anonymous Block

```sql
SET SERVEROUTPUT ON

BEGIN
    DBMS_OUTPUT.put_line ('Hello World!');
END;
/
```

### 1.4 Declaring Variables

```sql
SET SERVEROUTPUT ON

DECLARE
    l_message VARCHAR2(100) := 'Hello World!';
BEGIN
    DBMS_OUTPUT.put_line(l_message);
END;
/
```

### 1.5 Loops in PL/SQL

**WHILE Loop:**
```sql
SET SERVEROUTPUT ON

DECLARE
    i NUMBER;
BEGIN
    i := 0;
    WHILE i <= 25 LOOP
        DBMS_OUTPUT.put_line(i);
        i := i + 5;
    END LOOP;
END;
/
```

### 1.6 Getting Input from User

Use `&variable_name` substitution variables in SQL*Plus / SQL Developer:

```sql
DECLARE
    v_name VARCHAR2(50);
BEGIN
    v_name := '&enter_name';
    DBMS_OUTPUT.put_line('Hello, ' || v_name);
END;
/
```

---

## PART 2: PROCEDURES (Week 2)

### 2.1 What is a Subprogram?

A **subprogram** is a program unit/module that performs a particular task. These subprograms are combined to form larger programs — this is called **Modular Design**.

A subprogram can be created:
- At **schema level** (standalone)
- Inside a **package**
- Inside a **PL/SQL block**

### 2.2 Procedures vs Functions

| Feature | Procedure | Function |
|---------|-----------|----------|
| Returns value? | No (optional via OUT) | Yes (mandatory) |
| Main purpose | Perform an action | Compute and return a value |
| Called via | `EXEC` or in block | In expressions / assignments |

### 2.3 Procedure Syntax

```sql
CREATE [OR REPLACE] PROCEDURE procedure_name
    [ (parameter [, parameter]) ]
IS
    [declaration_section]
BEGIN
    executable_section
[EXCEPTION
    exception_section]
END [procedure_name];
```

> **Note:** Procedure begins with `IS`/`AS` keyword and ends with `END` keyword.

### 2.4 Simple Procedure Example

```sql
CREATE OR REPLACE PROCEDURE hello_world
IS
    l_message VARCHAR2(100) := 'Hello World!';
BEGIN
    DBMS_OUTPUT.put_line(l_message);
END hello_world;
```

**Execute using:**
```sql
EXEC hello_world;
-- or
EXECUTE hello_world;
-- or within a PL/SQL block:
BEGIN
    hello_world;
END;
```

### 2.5 Procedure with Parameters

```sql
CREATE OR REPLACE PROCEDURE pr2(n NUMBER)
AS
BEGIN
    UPDATE tablename
    SET attribute1 = 'box'
    WHERE id = n;
END;
/

-- Execution:
EXEC pr2(2);
```

### 2.6 Procedure with IN Parameters

```sql
CREATE OR REPLACE PROCEDURE increase_salary(
    p_empid   IN NUMBER,
    p_amount  IN NUMBER
)
IS
BEGIN
    UPDATE employee
    SET salary = salary + p_amount
    WHERE empid = p_empid;
END;
/

-- Call it:
BEGIN
    increase_salary(101, 5000);
END;
/
```

### 2.7 Parameter Modes

| Mode | Description |
|------|-------------|
| **IN** | Value passed from outside (default) |
| **OUT** | Used to return a value outside the procedure |
| **IN OUT** | Both input and output |

---

## PART 3: FUNCTIONS (Week 3)

### 3.1 What is a Function?

A PL/SQL function is the same as a procedure **except that it returns a value**. Therefore, all discussions about procedures also apply to functions.

### 3.2 Function Syntax

```sql
CREATE [OR REPLACE] FUNCTION function_name
    [(parameter_name [IN | OUT | IN OUT] type [, ...])]
    RETURN return_datatype
{IS | AS}
BEGIN
    <function_body>
    RETURN value;
END [function_name];
```

**Where:**
- `function_name` — specifies the name of the function
- `[OR REPLACE]` — allows modifying an existing function
- Parameter list contains name, mode, and types
- `IN` — value passed from outside
- `OUT` — parameter used to return a value
- Function **must** contain a `RETURN` statement
- `RETURN` clause specifies the data type being returned
- `AS` keyword is used instead of `IS` for creating a standalone function

### 3.3 Standalone Function Example

**Function that returns total number of customers:**

```sql
CREATE OR REPLACE FUNCTION totalCustomers
RETURN NUMBER
IS
    total NUMBER(2) := 0;
BEGIN
    SELECT COUNT(*) INTO total FROM customers;
    RETURN total;
END;
/
```

### 3.4 Calling a Function

```sql
DECLARE
    c NUMBER(2);
BEGIN
    c := totalCustomers();
    DBMS_OUTPUT.put_line('Total no. of Customers: ' || c);
END;
/
```

**Output:**
```
Total no. of Customers: 6
PL/SQL procedure successfully completed.
```

### 3.5 Function with Parameters — Finding Maximum

```sql
DECLARE
    a NUMBER;
    b NUMBER;
    c NUMBER;

    FUNCTION findMax(x IN NUMBER, y IN NUMBER)
    RETURN NUMBER
    IS
        z NUMBER;
    BEGIN
        IF x > y THEN
            z := x;
        ELSE
            z := y;
        END IF;
        RETURN z;
    END;

BEGIN
    a := 23;
    b := 45;
    c := findMax(a, b);
    DBMS_OUTPUT.put_line('Maximum of (23, 45): ' || c);
END;
/
```

**Output:**
```
Maximum of (23, 45): 45
PL/SQL procedure successfully completed.
```

### 3.6 Recursive Functions

When a subprogram calls itself, it is called a **recursive call**.

**Factorial Example:**

```sql
DECLARE
    num       NUMBER;
    factorial NUMBER;

    FUNCTION fact(x NUMBER)
    RETURN NUMBER
    IS
        f NUMBER;
    BEGIN
        IF x = 0 THEN
            f := 1;
        ELSE
            f := x * fact(x - 1);
        END IF;
        RETURN f;
    END;

BEGIN
    num := 6;
    factorial := fact(num);
    DBMS_OUTPUT.put_line('Factorial ' || num || ' is ' || factorial);
END;
/
```

**Output:**
```
Factorial 6 is 720
PL/SQL procedure successfully completed.
```

**Factorial Logic:**
```
n! = n * (n-1)!
   = n * (n-1) * (n-2)! ...
   = n * (n-1) * (n-2) * (n-3) * ... * 1
```

---

## PART 4: TRIGGERS (Week 4)

### 4.1 What is a Trigger?

Triggers are **stored programs** that are automatically executed (fired) when certain events occur. They are written to respond to:

| Event Type | Examples |
|------------|----------|
| **DML** | `DELETE`, `INSERT`, `UPDATE` |
| **DDL** | `CREATE`, `ALTER`, `DROP` |
| **Database Operation** | `SERVERERROR`, `LOGON`, `LOGOFF`, `STARTUP`, `SHUTDOWN` |

Triggers can be defined on:
- Table
- View
- Schema
- Database

### 4.2 Benefits of Triggers

- Generating derived column values automatically
- Enforcing referential integrity
- Event logging and storing information on table access
- Auditing
- Synchronous replication of tables
- Imposing security authorizations
- Preventing invalid transactions

### 4.3 Trigger Syntax

```sql
CREATE [OR REPLACE] TRIGGER trigger_name
    {BEFORE | AFTER | INSTEAD OF}
    {INSERT [OR] | UPDATE [OR] | DELETE} [OF col_name]
    ON table_name
    [REFERENCING OLD AS o NEW AS n]
    [FOR EACH ROW]
    [WHEN (condition)]
DECLARE
    declaration_statements
BEGIN
    executable_statements
EXCEPTION
    exception_handling_statements
END;
```

### 4.4 Syntax Components Explained

| Clause | Description |
|----------|-------------|
| `CREATE [OR REPLACE] TRIGGER trigger_name` | Creates or replaces an existing trigger |
| `{BEFORE / AFTER / INSTEAD OF}` | When the trigger executes. `INSTEAD OF` is for views |
| `{INSERT [OR] / UPDATE [OR] / DELETE}` | The DML operation that fires the trigger |
| `[OF col_name]` | Specific column for UPDATE triggers |
| `ON table_name` | The table associated with the trigger |
| `[REFERENCING OLD AS o NEW AS n]` | Allows referring to old and new values |
| `[FOR EACH ROW]` | Row-level trigger (executes for each affected row) |
| `WHEN (condition)` | Condition for rows that fire the trigger (row-level only) |

### 4.5 Row-Level vs Table-Level Triggers

| Type | `FOR EACH ROW` | Execution |
|------|---------------|-----------|
| **Row-Level** | Yes | Fires once for **each row** affected |
| **Table-Level** | No | Fires **once** per SQL statement |

### 4.6 Row-Level Trigger Example

**Scenario:** Display salary difference between old and new values on DML operations.

```sql
CREATE OR REPLACE TRIGGER display_salary_changes
    BEFORE DELETE OR INSERT OR UPDATE ON customers
    FOR EACH ROW
    WHEN (NEW.ID > 0)
DECLARE
    sal_diff NUMBER;
BEGIN
    sal_diff := :NEW.salary - :OLD.salary;
    DBMS_OUTPUT.put_line('Old salary: ' || :OLD.salary);
    DBMS_OUTPUT.put_line('New salary: ' || :NEW.salary);
    DBMS_OUTPUT.put_line('Salary difference: ' || sal_diff);
END;
/
```

### 4.7 Trigger Execution Examples

**INSERT Operation:**
```sql
INSERT INTO CUSTOMERS (ID, NAME, AGE, ADDRESS, SALARY)
VALUES (7, 'Kriti', 22, 'HP', 7500.00);
```

**Output:**
```
Old salary:
New salary: 7500
Salary difference:
```
> Note: Old salary is NULL for INSERT (new record).

**UPDATE Operation:**
```sql
UPDATE customers
SET salary = salary + 500
WHERE id = 2;
```

**Output:**
```
Old salary: 1500
New salary: 2000
Salary difference: 500
```

### 4.8 Important Trigger Rules

| Rule | Explanation |
|------|-------------|
| `OLD` and `NEW` references | Not available for table-level triggers; only for row-level triggers |
| Querying the same table | Use `AFTER` keyword if you need to query the table in the trigger |
| Single vs Multiple operations | Triggers can fire on single (`BEFORE DELETE`) or multiple (`BEFORE DELETE OR INSERT OR UPDATE`) operations |

---

## PART 5: CURSORS (Week 5)

### 5.1 What is a Cursor?

Oracle creates a memory area called the **context area** for processing SQL statements. A **cursor** is a pointer to this context area.

- A cursor holds the rows returned by a SQL statement
- The set of rows the cursor holds is called the **active set**
- You can name a cursor and refer to it in a program to fetch and process rows one at a time

### 5.2 Types of Cursors

| Type | Description |
|------|-------------|
| **Implicit Cursors** | Automatically created by Oracle when SQL is executed |
| **Explicit Cursors** | Programmer-defined for more control over the context area |

### 5.3 Implicit Cursors

Implicit cursors are automatically created when there is no explicit cursor for the statement. Programmers cannot control them directly.

Whenever a DML statement (`INSERT`, `UPDATE`, `DELETE`) is issued, an implicit cursor is associated with it:
- For `INSERT` — cursor holds the data to be inserted
- For `UPDATE`/`DELETE` — cursor identifies the rows to be affected

### 5.4 Implicit Cursor Attributes

In PL/SQL, refer to the most recent implicit cursor as the `SQL` cursor. It has these attributes:

| Attribute | Description |
|-----------|-------------|
| `%FOUND` | Returns `TRUE` if DML affected one or more rows, or `SELECT INTO` returned rows |
| `%NOTFOUND` | Logical opposite of `%FOUND` |
| `%ISOPEN` | Always returns `FALSE` for implicit cursors (Oracle closes automatically) |
| `%ROWCOUNT` | Returns number of rows affected by DML or returned by `SELECT INTO` |
| `%BULK_ROWCOUNT` | Designed for use with `FORALL` statement |
| `%BULK_EXCEPTIONS` | Designed for use with `FORALL` statement |

**Access syntax:** `sql%attribute_name`

### 5.5 Implicit Cursor Example

```sql
DECLARE
    total_rows NUMBER(2);
BEGIN
    UPDATE customers
    SET salary = salary + 500;

    IF sql%notfound THEN
        dbms_output.put_line('No customers selected');
    ELSIF sql%found THEN
        total_rows := sql%rowcount;
        dbms_output.put_line(total_rows || ' customers selected');
    END IF;
END;
/
```

**Output:**
```
6 customers selected
PL/SQL procedure successfully completed.
```

**Updated Table:**

| ID | NAME | AGE | ADDRESS | SALARY |
|----|------|-----|---------|--------|
| 1 | Ramesh | 32 | Ahmedabad | 2500.00 |
| 2 | Khilan | 25 | Delhi | 2000.00 |
| 3 | kaushik | 23 | Kota | 2500.00 |
| 4 | Chaitali | 25 | Mumbai | 7000.00 |
| 5 | Hardik | 27 | Bhopal | 9000.00 |
| 6 | Komal | 22 | MP | 5000.00 |

### 5.6 Explicit Cursors

Explicit cursors are programmer-defined for gaining more control. They are defined in the declaration section and created on a `SELECT` statement returning more than one row.

**Syntax:**
```sql
CURSOR cursor_name IS select_statement;
```

### 5.7 Four Steps of Working with Explicit Cursors

| Step | Action | Purpose |
|------|--------|---------|
| 1. **Declare** | `CURSOR c_name IS SELECT ...` | Define cursor with name and SELECT statement |
| 2. **Open** | `OPEN c_name;` | Allocate memory, make cursor ready |
| 3. **Fetch** | `FETCH c_name INTO var1, var2;` | Retrieve one row at a time |
| 4. **Close** | `CLOSE c_name;` | Release allocated memory |

### 5.8 Declaring a Cursor

```sql
CURSOR c_customers IS
    SELECT id, name, address FROM customers;
```

### 5.9 Opening a Cursor

```sql
OPEN c_customers;
```

### 5.10 Fetching from a Cursor

```sql
FETCH c_customers INTO c_id, c_name, c_addr;
```

### 5.11 Closing a Cursor

```sql
CLOSE c_customers;
```

### 5.12 Complete Explicit Cursor Example

```sql
DECLARE
    c_id     customers.id%type;
    c_name   customers.name%type;
    c_addr   customers.address%type;

    CURSOR c_customers IS
        SELECT id, name, address FROM customers;

BEGIN
    OPEN c_customers;

    LOOP
        FETCH c_customers INTO c_id, c_name, c_addr;
        EXIT WHEN c_customers%notfound;
        dbms_output.put_line(c_id || ' ' || c_name || ' ' || c_addr);
    END LOOP;

    CLOSE c_customers;
END;
/
```

**Output:**
```
1 Ramesh Ahmedabad
2 Khilan Delhi
3 kaushik Kota
4 Chaitali Mumbai
5 Hardik Bhopal
6 Komal MP
PL/SQL procedure successfully completed.
```

### 5.13 Cursor Attributes for Explicit Cursors

| Attribute | Description |
|-----------|-------------|
| `%FOUND` | `TRUE` if last fetch returned a row |
| `%NOTFOUND` | `TRUE` if last fetch returned no row |
| `%ISOPEN` | `TRUE` if cursor is open |
| `%ROWCOUNT` | Number of rows fetched so far |

---

## PART 6: COMPARISON SUMMARY

### 6.1 Procedures vs Functions vs Triggers vs Cursors

| Feature | Procedure | Function | Trigger | Cursor |
|---------|-----------|----------|---------|--------|
| **Returns value?** | No (via OUT) | Yes (RETURN) | No | No |
| **Called explicitly?** | Yes | Yes | No (auto-fired) | Yes |
| **Purpose** | Perform action | Compute value | Respond to events | Process result sets |
| **Stored in DB?** | Yes | Yes | Yes | No (pointer) |
| **Parameters** | IN, OUT, IN OUT | IN, OUT, IN OUT | None (uses OLD/NEW) | None |
| **Created with** | `CREATE PROCEDURE` | `CREATE FUNCTION` | `CREATE TRIGGER` | `CURSOR` declaration |

### 6.2 Implicit vs Explicit Cursors

| Aspect | Implicit Cursor | Explicit Cursor |
|--------|----------------|-----------------|
| **Created by** | Oracle automatically | Programmer |
| **Control** | Limited | Full control |
| **Use case** | Single-row DML | Multi-row SELECT |
| **Attributes** | `sql%attribute` | `cursor_name%attribute` |
| **%ISOPEN** | Always FALSE | TRUE when open |

---

## PART 7: QUICK SYNTAX CHEAT SHEET

```sql
-- ========== ANONYMOUS BLOCK ==========
DECLARE
    -- variables
BEGIN
    -- logic
EXCEPTION
    -- errors
END;
/

-- ========== PROCEDURE ==========
CREATE [OR REPLACE] PROCEDURE proc_name(param IN type)
IS
BEGIN
    -- logic
END;
/

EXEC proc_name(value);

-- ========== FUNCTION ==========
CREATE [OR REPLACE] FUNCTION func_name RETURN type
IS
BEGIN
    -- logic
    RETURN value;
END;
/

-- Call: variable := func_name();

-- ========== TRIGGER ==========
CREATE [OR REPLACE] TRIGGER trigger_name
    BEFORE/AFTER INSERT OR UPDATE OR DELETE ON table_name
    [FOR EACH ROW]
BEGIN
    -- logic using :NEW and :OLD
END;
/

-- ========== CURSOR ==========
DECLARE
    CURSOR c_name IS SELECT col FROM table;
    var table.col%type;
BEGIN
    OPEN c_name;
    LOOP
        FETCH c_name INTO var;
        EXIT WHEN c_name%NOTFOUND;
        -- process
    END LOOP;
    CLOSE c_name;
END;
/
```

---

## PART 8: COMMON ERRORS & TROUBLESHOOTING

| Error | Cause | Solution |
|-------|-------|----------|
| `ORA-01403` | No data found | Handle with exception block |
| `ORA-01422` | Exact fetch returns more than requested number of rows | Use cursor or `ROWNUM` |
| `ORA-06502` | Numeric or value error | Check data types and sizes |
| `ORA-04098` | Trigger is invalid and failed re-validation | Recompile trigger |
| `ORA-01001` | Invalid cursor | Ensure cursor is opened before fetch |

---

## PART 9: CUSTOMERS TABLE (Reference)

Used throughout all examples:

| ID | NAME | AGE | ADDRESS | SALARY |
|----|------|-----|---------|--------|
| 1 | Ramesh | 32 | Ahmedabad | 2000.00 |
| 2 | Khilan | 25 | Delhi | 1500.00 |
| 3 | kaushik | 23 | Kota | 2000.00 |
| 4 | Chaitali | 25 | Mumbai | 6500.00 |
| 5 | Hardik | 27 | Bhopal | 8500.00 |
| 6 | Komal | 22 | MP | 4500.00 |

---

*End of PL/SQL Complete Notes -- Good Luck with your Assessment!*
