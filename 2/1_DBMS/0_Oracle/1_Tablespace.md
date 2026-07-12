## 🗂️ Tablespace vs Database

- A **Database** is the whole collection of data, schemas, users, and objects.  
- A **Tablespace** is a **logical storage subdivision** of that database.  
- Inside a tablespace, Oracle maps **physical datafiles** on disk where the actual data lives.  

👉 So, a tablespace is more like a **folder inside your database** that organizes where data is stored, not a separate mini‑database.

---
## 📊 ASCII Analogy

```
[ Oracle Database ]
       |
       +-- Tablespace: SYSTEM
       |       +-- Datafile: system01.dbf
       |
       +-- Tablespace: USERS
       |       +-- Datafile: users01.dbf
       |       +-- Datafile: users02.dbf
       |
       +-- Tablespace: TEMP
       |       +-- Tempfile: temp01.dbf
       |
       +-- Tablespace: UNDO
               +-- Datafile: undo01.dbf
```

- **Database** → The whole box.  
- **Tablespaces** → Compartments inside the box.  
- **Datafiles** → Actual files on disk holding the data.  

---
## ⚡ Why Oracle Uses Tablespaces

- **Organization** → Separate system data from user/application data.  
- **Flexibility** → Add more datafiles when storage grows.  
- **Performance** → Place different tablespaces on different disks.  
- **Security** → Control which users can use which tablespaces.  

---
## 🛠️ Quick Example

```sql
-- Create a new tablespace
CREATE TABLESPACE sales_data
DATAFILE '/u01/app/oracle/oradata/sales01.dbf' SIZE 200M AUTOEXTEND ON;

-- Assign a user to use this tablespace
ALTER USER rehaan DEFAULT TABLESPACE sales_data;
```

---

✅ So: **Tablespace = logical storage unit inside a database, not a separate database itself.**
## Code
```SQL
SQL> SELECT tablespace_name, status, contents, logging
  2  FROM dba_tablespaces
  3  ORDER BY tablespace_name;

TABLESPACE_NAME                STATUS    CONTENTS              LOGGING
------------------------------ --------- --------------------- ---------
SYSAUX                         ONLINE    PERMANENT             LOGGING
SYSTEM                         ONLINE    PERMANENT             LOGGING
TEMP                           ONLINE    TEMPORARY             NOLOGGING
UNDOTBS1                       ONLINE    UNDO                  LOGGING
USERS                          ONLINE    PERMANENT             LOGGING
```
### Explanation
### 📂 SYSTEM
- **Purpose:** Holds the data dictionary — all metadata about database objects (users, tables, views, privileges, etc.).  
- **Critical role:** Without SYSTEM, Oracle can’t function because it stores the definitions of everything in the database.  
- **Usage:** Created automatically when the database is created; you don’t put user data here.

---

### 📂 SYSAUX
- **Purpose:** An auxiliary tablespace to offload SYSTEM.  
- **Critical role:** Stores metadata and components like AWR (Automatic Workload Repository), RMAN catalog, Enterprise Manager repository, and other optional features.  
- **Usage:** Helps keep SYSTEM lean by housing non‑core metadata.

---

### 📂 UNDO
- **Purpose:** Stores undo information for transactions.  
- **Critical role:** Allows rollback of uncommitted changes, supports read consistency (so queries see a stable view of data), and is used for recovery.  
- **Usage:** Every transaction that modifies data generates undo records here.

---

### 📂 TEMP
- **Purpose:** Temporary workspace for operations.  
- **Critical role:** Used for sorting, joins, index creation, and other operations that need scratch space.  
- **Usage:** Data here is transient — it’s not permanent, and Oracle reuses it as needed.

---

### ✅ Summary
- **SYSTEM** → Core metadata dictionary.  
- **SYSAUX** → Auxiliary metadata/features.  
- **UNDO** → Transaction rollback and consistency.  
- **TEMP** → Scratchpad for sorting/joins.  

Together, these are the backbone of your CDB root. Every PDB you create will also have its own SYSTEM, SYSAUX, TEMP, and USERS tablespaces, so each pluggable database is self‑contained.
