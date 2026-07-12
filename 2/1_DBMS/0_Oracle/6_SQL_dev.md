## Does multiple connection to one Service make any diff?
### 🔎 Why you see multiple connections
- In SQL Developer, each entry under *Oracle Connections* (like `TNS`, `FREE`, `FREEPDB1`) is just a **saved connection profile**.  
- These profiles don’t create new databases — they’re shortcuts that store login info (username, password, host, port, service/SID).  
- So if you save another connection (say `FREEPDB1`), you’re not creating a new database; you’re just creating another way to connect to an existing service/PDB.

---

### 🗂️ Why one is under **TNS**
- **TNS** refers to Oracle’s *Transparent Network Substrate* — basically the networking layer Oracle uses.  
- If you configure a `tnsnames.ora` file, SQL Developer can read those entries and show them under “TNS.”  
- That’s why you see a “TNS” connection: it’s pulling from your Oracle Net configuration, not from the database itself.

---

### ✅ Key takeaway
- **CDB$ROOT** → the single root container (always there).  
- **PDBs** → the actual pluggable databases (like `FREE`). You can have multiple PDBs inside the one CDB.  
- **Services** → connection endpoints tied to those containers.  
- **SQL Developer connections** → saved profiles in your client tool; they don’t equal new databases, they just point to existing services.

---

👉 So: when you create another connection in SQL Developer (like `FREEPDB1`), you’re not making a new database — you’re just saving another profile to connect to the same or another service.  
