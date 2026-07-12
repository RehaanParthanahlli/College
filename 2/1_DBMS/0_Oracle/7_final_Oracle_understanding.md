## Understanding 
### 🗂️ Oracle Free Tier Rules
- **One CDB only** → You can’t create multiple CDBs in Free tier. You always have one root (`CDB$ROOT`).  
- **Multiple PDBs allowed** → Inside that one CDB, you can spin up multiple pluggable databases (`FREEPDB1`, `TESTPDB`, etc.).  
- **Multiple users allowed** → Each PDB can have many schemas/users.  
- **Services are mandatory** → You never connect directly to a PDB; you connect through a service.

---

### 🔎 Services vs. PDBs
- **Services are always registered in root (CON_ID = 1).**  
- **One service = one container (root or one PDB).**  
  - Example:  
    - `SYS$USERS` → routes into root.  
    - `FREE` → registered in root but routes into `FREEPDB1`.  
- **No “one service → many PDBs” and no “one PDB → many services” (unless you explicitly create more services for the same PDB).**  
  - It’s always a **1:1 mapping** between a service and its target container.

---

### ✅ Why your queries looked odd
- In `v$services` or `v$session`, services always show `CON_ID = 1` because they’re registered in root.  
- But when you connect through `FREE`, Oracle routes you into `FREEPDB1`.  
- That’s why you saw `CDB$ROOT` in `SYS_CONTEXT`, even though your actual data lives in `FREEPDB1`.

---

### 🚀 Visual chain
```
CDB$ROOT (one per Free tier)
   ├── PDB$SEED (read-only template)
   ├── FREEPDB1 (your working database)
   │       └── Service: FREE → routes here
   └── Other PDBs you create
           └── Each has its own service
```

---

### Bottom line
- **One CDB only** in Free tier.  
- **Many PDBs possible** inside it.  
- **One service = one PDB (or root).**  
- Services are always shown as registered in root (`CON_ID = 1`), but they route into their target PDB.  
