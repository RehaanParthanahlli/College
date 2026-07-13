## 🗂️ What are PDBs in Oracle??

- **PDB** stands for **Pluggable Database**.  
- Introduced with **Oracle 12c**, it’s part of the **Multitenant Architecture**.  
- A **CDB (Container Database)** is the main database that can hold multiple **PDBs**.  
- Think of a **CDB** as a big box, and each **PDB** is a smaller box inside it.  

This architecture allows:
- Easier consolidation of multiple databases.
- Simplified upgrades and patching (done at the CDB level).
- Better resource management and isolation between applications.

---
## 🔑 Key Components

- **CDB (Container Database)** → The root container that holds system metadata and common users.
- **PDB (Pluggable Database)** → Independent databases plugged into the CDB. Each PDB has its own schemas, users, and data.
- **Root Container (CDB$ROOT)** → Stores Oracle system metadata and common objects.
- **Seed PDB (PDB$SEED)** → A template used to create new PDBs quickly.

---
## 📊 ASCII Diagram of Oracle Multitenant Architecture

Here’s a simple visualization:

```
+---------------------------------------------------+
|                 Container Database (CDB)          |
|                                                   |
|   +-------------------+                           |
|   |   CDB$ROOT        |  <-- Root container       |
|   |   (System metadata|                           |
|   |   Common users)   |                           |
|   +-------------------+                           |
|                                                   |
|   +-------------------+                           |
|   |   PDB$SEED        |  <-- Seed PDB (template)  |
|   +-------------------+                           |
|                                                   |
|   +-------------------+                           |
|   |   PDB1            |  <-- User/application DB  |
|   |   (Schemas, data) |                           |
|   +-------------------+                           |
|                                                   |
|   +-------------------+                           |
|   |   PDB2            |  <-- Another DB           |
|   +-------------------+                           |
|                                                   |
|   +-------------------+                           |
|   |   PDB3            |  <-- Yet another DB       |
|   +-------------------+                           |
|                                                   |
+---------------------------------------------------+
```

---
## ⚡ Why PDBs are Useful

- **Isolation** → Each PDB is independent, so issues in one don’t affect others.  
- **Portability** → You can unplug a PDB from one CDB and plug it into another.  
- **Efficiency** → Shared memory and background processes reduce overhead.  
- **Scalability** → Manage hundreds of PDBs under one CDB.  

---

👉 So in short: **CDB is the container, PDBs are the pluggable databases inside it.**  
It’s like having multiple mini-databases inside one big Oracle instance.
## 26ai has only one CDB$ROOT, this is linked to 3 services FREE, SYS$USERS(Default)
In Oracle Free 26ai you always have **one CDB (the root container, `CDB$ROOT`)**, but you can have **multiple PDBs (pluggable databases)** inside it. Each PDB is like its own separate database, and each one has a service that lets you connect to it.

---

### 🔎 How to check the number of databases (PDBs) in your CDB
Run this query in SQL*Plus:

```sql
SELECT con_id, name, open_mode
FROM v$pdbs;
```

👉 This will show:
- **CON_ID** → numeric ID of the container  
- **NAME** → name of the PDB (e.g., `FREE`, `FREEXDB`)  
- **OPEN_MODE** → whether it’s open (`READ WRITE`) or closed  

---

### 📊 To count them directly
```sql
SELECT COUNT(*) AS pdb_count
FROM v$pdbs;
```

This gives you the number of pluggable databases inside your CDB.

---

### ✅ Key points
- **CDB$ROOT** is always there (CON_ID = 1).  
- **PDBs** are the “databases” you can create and use inside the CDB.  
- Each PDB has its own SYSTEM, SYSAUX, TEMP, USERS tablespaces, schemas, and features.  
- Services (`FREE`, `freeXDB`, etc.) are tied to those PDBs.  

---

So:  
- You cannot create another **CDB** in Free edition.  
- But you can create multiple **PDBs** inside the one CDB you already have.  
- Use `v$pdbs` to list and count them.
## One service, one DB
### 🗂️ Relationship between CDB, PDBs, and Services
- **One CDB (Container Database)** → always just one in Oracle Free. That’s your `CDB$ROOT`.  
- **Multiple PDBs (Pluggable Databases)** → you *can* create several inside that single CDB. Each PDB is a self‑contained database with its own SYSTEM, SYSAUX, TEMP, USERS tablespaces, schemas, and features.  
- **Services** → each PDB has at least one service registered to it. The root also has its own internal services (`SYS$USERS`, `SYS$BACKGROUND`, etc.).  

---

### ❌ Misconception
> “One service can have multiple PDBs”  
No — a **service is tied to a single container** (either the root or one PDB). You cannot have one service spanning multiple PDBs.  

👉 Instead:  
- **One CDB** can host multiple PDBs.  
- **Each PDB** has its own service(s).  
- You connect to a PDB by using its service name.  

---

### 🔎 How to check which service belongs to which PDB
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

This way you’ll see exactly:
- `SYS$USERS` → CDB$ROOT (con_id = 1)  
- `FREE` → PDB named FREE (con_id = 2, for example)  
- `freeXDB` → another PDB (con_id = 3, etc.)  

---

### ✅ Bottom line
- You have **one CDB** in Free edition.  
- You can create **multiple PDBs** inside it.  
- **Each PDB has its own service** — services don’t span multiple PDBs.  
## So, Oracle stores each services con_id as 1 in v$session 
### 🗂️ How services, root, and PDBs really fit together
- **CDB$ROOT (CON_ID = 1)**  
  - This is the single root container.  
  - All services are *registered* here — that’s why in `v$services` you see `CON_ID = 1` for `free`, `freeXDB`, `SYS$USERS`, etc.  

- **PDBs (like FREEPDB1, CON_ID = 3)**  
  - These are the actual pluggable databases where you store schemas, tables, and data.  
  - Each PDB has at least one service that *routes connections into it*.  
  - For you, the `FREE` service is registered in root but routes into `FREEPDB1`.  

- **PDB$SEED (CON_ID = 2)**  
  - The read‑only template used to create new PDBs.  
  - No user connections here.

---

### ✅ Key takeaway
- Services are **always registered in the root (CON_ID = 1)**.  
- But each service is mapped to a **specific container** (root or one PDB).  
- So when you connect with `@FREE`, you’re routed into **FREEPDB1**, even though the service shows up as registered in root.  
- That’s why your `SYS_CONTEXT` check showed `CDB$ROOT` + `free` — the service lives in root, but your usable database is the PDB.

---

### 🔎 How to see the mapping cleanly
Try this join:
```sql
SELECT s.name AS service_name,
       p.name AS pdb_name,
       s.con_id
FROM   v$services s
LEFT JOIN v$pdbs p
       ON s.con_id = p.con_id
ORDER BY s.name;
```

👉 This way you’ll see each service alongside the actual PDB name it belongs to.  
For `free`, you’ll see it registered in root (`con_id = 1`), but you’ll also see `freepdb1` listed separately as the real PDB.

---

So in short: **FREE is always registered in root, but root itself hosts multiple PDBs, and the service routes you into one of them (FREEPDB1).**