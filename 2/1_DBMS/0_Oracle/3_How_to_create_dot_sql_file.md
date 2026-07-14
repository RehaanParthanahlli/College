## 🔑 `.c` vs `.sql`

- **`.c` file** → Source code for a program. You compile it with `gcc` → it becomes a binary executable.  
- **`.sql` file** → Source code for database commands. You run it in Oracle (via SQL*Plus or SQL Developer) → it becomes tables, data, indexes stored inside the database.  

👉 Both are **instructions**, but `.c` produces a program, `.sql` produces database objects/data.

---
## 🖥️ SQL*Plus (CLI Workflow)

1. **Login to SQL*Plus**  
   ```bash
   sqlplus sys/Oracle123@//localhost:1521/CDB1 as sysdba
   ```
   - `sys/Oracle123` → username/password  
   - `localhost:1521` → host and port  
   - `CDB1` → service name (could be a CDB or a PDB)

2. **Check which DB/container you’re in**  
   ```sql
   SHOW CON_NAME;   -- Shows current container (CDB$ROOT, PDB1, etc.)
   SHOW USER;       -- Shows current user
   ```

3. **Parse/execute a script.sql**  
   ```bash
   @/home/rehaan/scripts/script.sql
   ```
   - The `@` command tells SQL*Plus to parse and run the `.sql` file line by line.  
   - It executes inside whichever DB/container you’re connected to.

---
## 🖱️ SQL Developer (UI Workflow)

1. **Open SQL Developer**.  
2. **Create a Connection** → This is like choosing which DB to log into.  
   - Connection name: `PDB1_conn`  
   - Username/password: `pdb_admin / password`  
   - Service name: `PDB1`  
3. **Test & Connect** → Now you’re inside that DB.  
4. **Run a script.sql**:  
   - Go to **File → Open → script.sql**.  
   - Press the green **Run Script** button (or F5).  
   - SQL Developer parses the script and executes it in the connected DB.

👉 In SQL Developer, each **connection = one database (CDB root or PDB)**. So yes, you’re right: `PDB1_conn` is essentially “the DB” you’re connected to.

---
## 📊 Summary Flow

```
[C World]                          [Oracle SQL World]
------------------------------------------------------------
file.c (source code)               script.sql (SQL commands)
gcc compiler                      SQL engine parses
binary executable                  tables/data in tablespaces
OS runs program                    Oracle stores objects/data
```

---

✅ So:  
- In CLI, you connect with `sqlplus`, check container, then run `@script.sql`.  
- In UI, you connect via a **connection** (like `PDB1_conn`), then run the script.  