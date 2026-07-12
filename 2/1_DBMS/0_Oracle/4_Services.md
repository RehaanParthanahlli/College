## How to know which service connected to which connection
SQL> SELECT sid, serial#, service_name, con_id
  2  FROM v$session
  3  WHERE audsid = SYS_CONTEXT('USERENV','SESSIONID');

       SID    SERIAL#
---------- ----------
SERVICE_NAME                                                         CON_ID
---------------------------------------------------------------- ----------
       182      26485
SYS$USERS        
## How to check no of users
### 1️⃣ Start SQL*Plus
Open your terminal/command prompt and connect:
```bash
sqlplus / as sysdba
```
(or use `sqlplus username/password@service_name` if you’re connecting as a normal user).

---

### 2️⃣ Count all sessions (users + background)
```sql
SQL> SELECT COUNT(*) AS total_sessions
  2  FROM v$session;
```
This shows the total number of sessions currently connected.

---

### 3️⃣ Count only **user sessions**
```sql
SQL> SELECT COUNT(*) AS user_sessions
  2  FROM v$session
  3  WHERE type = 'USER';
```
This filters out background/system processes and gives you the number of actual user connections.

---

### 4️⃣ See details of connected users
```sql
SQL> SELECT username, machine, service_name, con_id
  2  FROM v$session
  3  WHERE type = 'USER'
  4  ORDER BY username;
```
This lists each user, the machine they’re connecting from, and which service they’re tied to (`SYS$USERS`, `freeXDB`, etc.).

---

### 5️⃣ Optional: Group by service
```sql
SQL> SELECT service_name, COUNT(*) AS session_count
  2  FROM v$session
  3  GROUP BY service_name;
```
This shows how many users are connected per service.

---

✅ So the clean workflow is:
- `sqlplus / as sysdba`
- Run `COUNT(*)` on `v$session`
- Filter with `WHERE type='USER'` for actual user count
- Use `GROUP BY service_name` if you want per‑service breakdown
## How to check no of services and users advanced version
### 🔎 List all users and their associated services
```sql
SELECT username,
       machine,
       service_name,
       con_id
FROM   v$session
WHERE  type = 'USER'
ORDER BY service_name, username;
```

👉 This will show:
- **USERNAME** → who is connected  
- **MACHINE** → the client machine name  
- **SERVICE_NAME** → which service they’re tied to (`SYS$USERS`, `free`, `freeXDB`, etc.)  
- **CON_ID** → which container (1 = CDB$ROOT, others = PDBs)  

---

### 📊 Count users per service
If you want a summary:
```sql
SELECT service_name,
       COUNT(*) AS user_count
FROM   v$session
WHERE  type = 'USER'
GROUP BY service_name
ORDER BY service_name;
```

---

### ⚠️ About Oracle SQL Developer connections
SQL Developer itself doesn’t store “active connections” inside the database — it just uses your saved connection profiles. Those profiles (username, host, port, service/SID) are stored locally in your SQL Developer settings, not in Oracle views.  

So:
- **Active sessions** → check with `v$session` (as shown above).  
- **Saved SQL Developer connections** → you won’t see them inside Oracle; they live in your SQL Developer client config files.  

---

✅ Bottom line:  
- Use `v$session` to see **who is connected and which service they’re tangled with**.  
- SQL Developer connection definitions are **client‑side only**, not queryable from the database.  
## Doubt
- **When you log in as `SYSDBA`** → you’re connecting as the **`SYS` user** into the **root container (CDB$ROOT)**. That session will always be tied to the internal service `SYS$USERS`.  
- **When you log in normally without specifying a service** → Oracle defaults to the root container as well, so you’ll again see yourself under `SYS$USERS`.  
- **When you explicitly log in with `@FREE`** → you’re connecting into the **pluggable database (PDB) named FREE**. That session is tied to the `free` service, and if you check your current session with  
  ```sql
  SELECT SYS_CONTEXT('USERENV','SERVICE_NAME') FROM dual;
  ```  
  you’ll see only `free` for that connection.

---

### Why you saw both `SYS$USERS` and `free`
Because you had **multiple sessions open at the same time**:
- Two sessions in the root (`SYS$USERS`)  
- One session in the PDB (`free`)  

When you query `v$session`, Oracle shows *all active user sessions*, not just your own. That’s why you saw three rows.

---

### ✅ Key takeaway
- **SYSDBA login** → `SYS` user, root container, service = `SYS$USERS`.  
- **Normal login without service** → root container, service = `SYS$USERS`.  
- **Login with `@FREE`** → pluggable database `FREE`, service = `free`.  

So yes: if you log in as `SYSTEM@FREE`, your *current session* is only tied to `free`. The extra `SYS$USERS` rows are just other sessions you left open in the root.

---

👉 If you want to confirm only *your current connection’s service*, use:
```sql
SELECT SYS_CONTEXT('USERENV','SERVICE_NAME') AS current_service
FROM dual;
```
