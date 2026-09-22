# 1
In Oracle Database, the difference between **single quotes (`'`)** and **double quotes (`"`)** is important:

- **Single quotes `' '`**  
  - Used for **string literals**.  
  - Example:  
    ```sql
    SELECT 'Hello World' FROM dual;
    ```

- **Double quotes `" "`**  
  - Used for **delimiting identifiers** (like table names, column names, or aliases).  
  - They make identifiers **case-sensitive** and allow the use of special characters or reserved words.  
  - Example:  
    ```sql
    CREATE TABLE "MyTable" ("Employee Name" VARCHAR2(50));
    SELECT "Employee Name" FROM "MyTable";
    ```
  - Without double quotes, Oracle automatically converts identifiers to **uppercase**.  
    For example, `SELECT empname FROM employees;` works if the column was created as `EMPNAME`.  
    But if you created it as `"empName"` (with quotes), you must always reference it exactly as `"empName"`.

👉 So, think of it this way:
- `' '` → for **data values** (strings).  
- `" "` → for **object names** (identifiers).  