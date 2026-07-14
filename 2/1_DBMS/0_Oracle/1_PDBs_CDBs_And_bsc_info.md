# Oracle Free

Oracle Free installs something similar to

```text
Oracle Instance
       │
       ▼
CDB
│
├── CDB$ROOT
│
└── FREEPDB1
```

The default services are

```text
FREE
FREEPDB1
```

---
# Oracle Multitenant Architecture

Before Oracle 12c, one database looked like this:

```text
Oracle Instance
       │
       ▼
Database
       │
   All users
```

Everything lived in one database.

Starting from Oracle 12c (and continuing in Oracle 26ai), Oracle introduced the **Multitenant Architecture**.

Now it looks like this:

```text
                  Oracle Instance
                        │
                        ▼
             Container Database (CDB)
                        │
        ┌───────────────┴───────────────┐
        │                               │
   CDB$ROOT                       Pluggable Databases
                                     │
                    ┌────────────────┴───────────────┐
                    │                                │
               FREEPDB1                         Another PDB
```

Think of a **CDB** as a building.

Each **PDB** is an apartment inside that building.

---

# What is a CDB?

A **Container Database (CDB)** is the main database.

It contains:

* Oracle system metadata
* Background processes
* Memory structures
* One or more PDBs

The CDB itself is **not where applications should usually store data**.

---

## Your database

When you type

```sql
SHOW CON_NAME;
```

you got

```text
CDB$ROOT
```

That means

> "I am currently inside the root container."

---
# What is CDB$ROOT?(Just another container, but powerful)

The root container contains

* Oracle dictionary
* Oracle internal packages
* Common users
* Metadata for all PDBs

Think of it as the **head office**.

```text
Company Headquarters

│

├── HR Policies
├── Employee Database
├── Finance Rules
└── Branch Offices
```

The branch offices are the PDBs.

---
# What is a PDB?

A **Pluggable Database** is a self-contained database inside the CDB.

It has its own

* tables
* users
* schemas
* procedures
* privileges

For example

```text
CDB
│
├── FREEPDB1
│      │
│      ├── HR Schema
│      ├── SALES Schema
│      └── APP Schema
│
└── TESTPDB
       │
       ├── Student Schema
       └── College Schema
```

Each PDB behaves almost like an independent Oracle database.

---
# Difference between FREE and FREEPDB1(Free linked to Root of CDB, where as freepdb1 linked to freepdb1)

```text
FREE
│
▼
CDB$ROOT
│
Contains
Oracle metadata
System objects
Common users
Administration
```

---

```text
FREEPDB1
│
▼
Actual Pluggable Database
│
Contains
Your tables
Your schemas
Your application
```

As a developer, you should generally work in **FREEPDB1**, not `CDB$ROOT`.

---
# How many PDBs exist?

Connect as SYSDBA

```bash
sqlplus / as sysdba
```

Then run

```sql
SHOW PDBS;
```

Example

```text
CON_ID CON_NAME    OPEN MODE
------ ----------  ----------
2      PDB$SEED    READ ONLY
3      FREEPDB1    READ WRITE
```

Explanation

* **PDB$SEED** is a template Oracle uses to create new PDBs.
* **FREEPDB1** is your working PDB.

---

# Am I inside a CDB or PDB?

```sql
SHOW CON_NAME;
```

Example

```text
CDB$ROOT
```

or

```text
FREEPDB1
```

---

# Service Mapping

This is probably the easiest way to remember it.

| Service    | Connects To |
| ---------- | ----------- |
| `FREE`     | `CDB$ROOT`  |
| `FREEPDB1` | `FREEPDB1`  |

So

```bash
sqlplus system/password@FREE
```

connects to

```text
CDB$ROOT
```

While

```bash
sqlplus system/password@FREEPDB1
```

connects to

```text
FREEPDB1
```

Notice that the **service** is simply the "door" Oracle exposes for clients to connect.

---
# List all services

```sql
SELECT NAME FROM V$SERVICES;
```

Example

```text
FREE
FREEPDB1
SYS$BACKGROUND
SYS$USERS
```

Ignore

```text
SYS$BACKGROUND
SYS$USERS
```

These are internal Oracle services.

---
# Which service belongs to which PDB?

Run

```sql
SELECT NAME, PDB FROM CDB_SERVICES;
```

Example

```text
NAME         PDB
-----------  ----------
FREE         CDB$ROOT
FREEPDB1     FREEPDB1
```

This is one of the most useful queries.

---
# Which services are actually running not just exist(4_More_doubts)

# List all users in the current container

```sql
SELECT USERNAME
FROM DBA_USERS
ORDER BY USERNAME;
```

Example

```text
ANONYMOUS
APPQOSSYS
CTXSYS
DBSNMP
HR
MDSYS
SYSTEM
SYS
...
```

If you're connected to a PDB, you'll see users in that PDB (plus common users where applicable).

---
# See current user

```sql
SHOW USER;
```

Example

```text
USER is "SYSTEM"
```

---

# List all containers

```sql
SELECT NAME, CON_ID FROM V$CONTAINERS;
```

Example

```text
NAME         CON_ID
-----------  ------
CDB$ROOT         1
PDB$SEED         2
FREEPDB1         3
```

---
# See current container

```sql
SHOW CON_NAME;
```

Example

```text
FREEPDB1
```

---
# Check if the database is a CDB

```sql
SELECT CDB FROM V$DATABASE;
```

Output

```text
CDB
---
YES
```

---
# Switch between containers

See where you are:

```sql
SHOW CON_NAME;
```

Switch to the PDB:

```sql
ALTER SESSION SET CONTAINER = FREEPDB1;
```

Switch back to the root:

```sql
ALTER SESSION SET CONTAINER = CDB$ROOT;
```

Verify:

```sql
SHOW CON_NAME;
```

---

# Common users vs Local users

Oracle has two kinds of users.

## Common users

Exist in the **entire CDB**.

Examples:

```text
SYS
SYSTEM
```

Common users often have names beginning with `C##` when you create them yourself, for example:

```text
C##ADMIN
```

They are visible across all PDBs.

---

## Local users

Exist only inside one PDB.

Example

```text
FREEPDB1

│

├── HR
├── SCOTT
└── REHAAN
```

If you create

```sql
CREATE USER rehaan IDENTIFIED BY password;
```

inside `FREEPDB1`, that user exists **only** there.

---

# Commands every beginner should know

| Task                  | Command                                   |
| --------------------- | ----------------------------------------- |
| Current user          | `SHOW USER;`                              |
| Current container     | `SHOW CON_NAME;`                          |
| List PDBs             | `SHOW PDBS;`                              |
| List containers       | `SELECT NAME, CON_ID FROM V$CONTAINERS;`  |
| List services         | `SELECT NAME FROM V$SERVICES;`            |
| Service → PDB mapping | `SELECT NAME, PDB FROM CDB_SERVICES;`     |
| Check if CDB          | `SELECT CDB FROM V$DATABASE;`             |
| List users            | `SELECT USERNAME FROM DBA_USERS;`         |
| Count users           | `SELECT COUNT(*) FROM DBA_USERS;`         |
| Switch to a PDB       | `ALTER SESSION SET CONTAINER = FREEPDB1;` |
| Switch to root        | `ALTER SESSION SET CONTAINER = CDB$ROOT;` |

## The complete mental model

```text
                    Oracle Instance
                           │
                           ▼
               Container Database (CDB)
                           │
            ┌──────────────┴──────────────┐
            │                             │
      CDB$ROOT                      PDB$SEED
            │                             │
            │                        Template for
            │                        new PDBs
            │
            └──────────────┐
                           │
                     FREEPDB1
                           │
             ┌─────────────┼─────────────┐
             │             │             │
          Tables        Users        Schemas
             │             │             │
           HR           REHAAN       SALES
```

**Connection services:**

```text
FREE
  │
  ▼
CDB$ROOT

FREEPDB1
  │
  ▼
FREEPDB1
```

So remember the hierarchy:

* **Oracle Instance**: The running database software (memory + background processes).
* **CDB**: The overall container database.
* **CDB$ROOT**: The administrative root container inside the CDB.
* **PDB**: A pluggable database where application data normally lives.
* **Service**: A connection endpoint that routes clients to a particular container (such as `FREE` → `CDB$ROOT` or `FREEPDB1` → `FREEPDB1`).
* **Schema/User**: The owner of database objects inside a container. Users live inside a PDB unless they are common users created for the entire CDB.
