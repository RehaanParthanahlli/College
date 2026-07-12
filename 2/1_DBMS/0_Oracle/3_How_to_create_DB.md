## How to create a DB
### 🔍 Check services (databases)
Run this outside SQL*Plus, in your terminal/command prompt:
```bash
lsnrctl status
```
This shows all services registered with the Oracle listener (e.g., `ORCL`, `XE`, `XYZ`). That’s how you know how many databases/services are available.

---

### ⚙️ Start/stop a service (database instance)
Log in as SYSDBA:
```bash
sqlplus / as sysdba
```
Then:
```sql
STARTUP;              -- starts the database service
SHUTDOWN IMMEDIATE;   -- stops the database service
```

---

### 🔑 Log into a service
Use a username + service name:
```bash
sqlplus system/password@XYZ
```
Here `XYZ` is the service name you saw in `lsnrctl status`.

---

### 🔄 Multiple services running together
Yes, you can run multiple services at once (e.g., `ORCL` and `XYZ`).  
👉 Your SQL queries always execute **in the service you connected to**.  
So if you log in with `sqlplus user@XYZ`, all queries run in `XYZ`. If you connect to `ORCL`, they run there.

---

### 🗑️ Delete a service
Oracle doesn’t have a simple SQL command for this. To remove a service:
1. Shut down the database (`SHUTDOWN IMMEDIATE`).
2. Delete its data files and configuration at the OS level (done via DBCA or manually).

---

### 📦 Create a new database (service)
Use the **Database Configuration Assistant (DBCA)**:
```bash
dbca
```
This wizard lets you create a new database. Once created, it registers as a new service with the listener (e.g., `XYZ`).

---
## Two methods to create a connection
### 🧩 Create a connection
After the service exists, you create a connection in SQL Developer or SQL*Plus by specifying:
- Username
- Password
- Service name (`XYZ`)

---

✅ **Summary flow**:
1. `lsnrctl status` → see services  
2. `STARTUP` / `SHUTDOWN` → open/close service  
3. `sqlplus user@service` → log in  
4. Multiple services can run; queries go to the one you connected to  
5. Delete service = remove database files/config  
6. Create service = use DBCA (creates a new database under a new service)  
7. Connections = just saved login info pointing to a service  

### 🖥️ Method 1: Using DBCA (Database Configuration Assistant)
This is the easiest and most common way:
```bash
dbca
```
- Launches a wizard (GUI or command‑line).
- Lets you specify database name (e.g., `XYZ`), storage, character set, etc.
- Once created, the database automatically registers as a service with the listener.

---

### ⚙️ Method 2: Custom SQL*Plus commands
You can create a database manually from SQL*Plus, but this is advanced and requires SYSDBA privileges.

Steps:

1. **Log in as SYSDBA**:
```bash
sqlplus / as sysdba
```

2. **Start an instance (without a database yet)**:
```sql
STARTUP NOMOUNT;
```

3. **Run CREATE DATABASE**:
```sql
CREATE DATABASE XYZ
   USER SYS IDENTIFIED BY strongpassword
   USER SYSTEM IDENTIFIED BY strongpassword
   LOGFILE GROUP 1 ('/u01/app/oracle/oradata/XYZ/redo01.log') SIZE 100M,
           GROUP 2 ('/u01/app/oracle/oradata/XYZ/redo02.log') SIZE 100M
   MAXLOGFILES 5
   DATAFILE '/u01/app/oracle/oradata/XYZ/system01.dbf' SIZE 500M AUTOEXTEND ON
   SYSAUX DATAFILE '/u01/app/oracle/oradata/XYZ/sysaux01.dbf' SIZE 100M AUTOEXTEND ON
   DEFAULT TABLESPACE users
      DATAFILE '/u01/app/oracle/oradata/XYZ/users01.dbf' SIZE 100M AUTOEXTEND ON
   TEMPORARY TABLESPACE temp
      TEMPFILE '/u01/app/oracle/oradata/XYZ/temp01.dbf' SIZE 100M AUTOEXTEND ON
   UNDO TABLESPACE undotbs1
      DATAFILE '/u01/app/oracle/oradata/XYZ/undo01.dbf' SIZE 200M AUTOEXTEND ON
   CHARACTER SET AL32UTF8
   NATIONAL CHARACTER SET AL16UTF16;
```

4. **Run catalog scripts** (to set up data dictionary):
```sql
@?/rdbms/admin/catalog.sql
@?/rdbms/admin/catproc.sql
```

5. **Create users and connections**:
```sql
CREATE USER rehaan IDENTIFIED BY mypassword;
GRANT CONNECT, RESOURCE TO rehaan;
```

Now you can connect:
```bash
sqlplus rehaan/mypassword@XYZ
```

---

### 🔄 Key points
- **DBCA** is recommended for beginners — it handles all the file paths, tablespaces, and scripts automatically.  
- **Manual CREATE DATABASE** is powerful but requires you to define everything (redo logs, tablespaces, character sets, etc.).  
- Once created, the database registers as a **service** (like `XYZ`) and you can connect to it with any user you define.  
- Multiple services can run together; your SQL executes only in the service you connected to.  
## Doubt(We have to add connection manually!)
When you **create a database/service** (say `XYZ`), Oracle registers it with the listener. But in tools like **SQL Developer** or even in SQL*Plus, you still need to **create a connection yourself** that points to that service.  

Here’s how it works step‑by‑step:

---

### 🔑 Creating a connection in SQL Developer
1. Open SQL Developer → click the green **+** (New Connection).  
2. Fill in:
   - **Connection Name** → any label you like (e.g., `XYZ_Conn`).  
   - **Username** → e.g., `SYSTEM` or a user you created (`REHAAN_USER`).  
   - **Password** → the password for that user.  
   - **Hostname** → usually `localhost` if it’s on your machine.  
   - **Port** → default is `1521`.  
   - **Service Name** → `XYZ` (the database service you created).  
3. Test → Save → Connect.  

---

### ⚙️ In SQL*Plus
You don’t “create” a connection object, you just connect directly:
```bash
sqlplus system/password@XYZ
```
or
```bash
sqlplus rehaan/mypassword@XYZ
```

---

### ✅ Key distinction
- **Service name (XYZ)** → the actual database instance Oracle knows about.  
- **Connection name** → just a friendly label in SQL Developer so you can recognize which saved login is which.  

So yes: after creating the database/service, you must **add a connection manually in the UI** (or connect directly in SQL*Plus) to start working with it.
