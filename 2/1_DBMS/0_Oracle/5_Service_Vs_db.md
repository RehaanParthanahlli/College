## FREE Service is hooked up to ROOT
### 🗂️ Services vs Database
- **Database (CDB/PDB model)**  
  In Oracle 12c and later, you have a **Container Database (CDB)** that can hold multiple **Pluggable Databases (PDBs)**.  
  - `CDB$ROOT` → the root container (system metadata, common users).  
  - `PDBs` → actual user databases (like `freeXDB`).  

- **Service**  
  A service is **not a database itself**. It’s a *logical entry point* that maps connections to a specific container.  
  - Example: `SYS$USERS` → default service for user sessions in the root (`CDB$ROOT`).  
  - Example: `freeXDB` → service that maps to the pluggable database `freeXDB`.  
  - Example: `free` → another service registered to the root.  

So: **each service points to a database container**. When you connect through a service, you’re effectively saying “log me into this container of the CDB.”

---

### 🔎 How to see which service maps to which database
Run:
```sql
SELECT name, con_id
FROM v$active_services
ORDER BY con_id;
```

Then map `CON_ID` to container names:
```sql
SELECT con_id, name
FROM v$pdbs;
```

👉 This way you’ll know:  
- `SYS$USERS` → `CDB$ROOT`  
- `freeXDB` → pluggable database `freeXDB`  
- `free` → root container service  

---

### ✅ Putting it together
- Services are **connection endpoints**.  
- Databases (PDBs) are the **actual data stores**.  
- One service can serve one database container, but a database can have multiple services registered to it.  

---

So yes: in practice, **each service is tied to a single database container**, and that’s how Oracle routes your connections.  
## By default FREE has one CDB which cant be changed in free tier!
- By default, the **FREE edition** of Oracle AI Database gives you **one CDB (Container Database)**.  
- Inside that CDB, you already have the **root container (CDB$ROOT)** and at least one **pluggable database (PDB)** — usually named `FREE` or `FREEPDB1` depending on the setup.  
- Because it’s the *Free* edition, you **cannot create additional CDBs** (that’s restricted to Enterprise/Multitenant licensing).  
- But you **can create multiple PDBs inside the existing CDB** (subject to the limits of the Free edition). Each PDB is effectively its own database, with its own schemas, tables, and features.  
- Services are the “doors” into those containers:  
  - `SYS$USERS` → root container service  
  - `FREE` → service tied to the `FREE` PDB  
  - `freeXDB` → service tied to another pluggable DB  

---

### 🔧 How to explore schemas and features in the `FREE` service (PDB)
1. **Connect to the PDB via the service**
   ```bash
   sqlplus system/yourpassword@FREE
   ```
   → This puts you inside the `FREE` PDB.

2. **List schemas (users) in that PDB**
   ```sql
   SELECT username FROM dba_users ORDER BY username;
   ```

3. **List tables in a schema**
   ```sql
   SELECT table_name FROM all_tables WHERE owner = 'HR';
   ```

4. **List features/objects**
   - Views:
     ```sql
     SELECT view_name FROM all_views WHERE owner = 'HR';
     ```
   - Procedures:
     ```sql
     SELECT object_name FROM all_objects WHERE owner = 'HR' AND object_type = 'PROCEDURE';
     ```

---

### ✅ Bottom line
- **ROOT is a database container** (CDB$ROOT).  
- **Services are tied to containers** (root or PDB).  
- In the Free edition, you **cannot create a new CDB**, but you **can create new PDBs** inside the existing CDB.  
- Each PDB is a full-fledged database with its own schemas and features, accessible via its service.  
## Service name can be SYS$USERS and FREE, each service is connected to CDB$ROOT but this is only the case in v$session but if you wanna know exact service pdb connection
```sql
SELECT s.name AS service_name,
       p.name AS pdb_name,
       s.con_id
FROM   v$services s
LEFT JOIN v$pdbs p
       ON s.con_id = p.con_id
ORDER BY s.name;
```