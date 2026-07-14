# Understanding SYSDBA Vs SYSTEM
```bash
C:\Users\rehaa>sqlplus / as sysdba

SQL*Plus: Release 23.26.2.0.0 - Production on Tue Jul 14 10:15:09 2026
Version 23.26.2.0.0

Copyright (c) 1982, 2026, Oracle.  All rights reserved.


Connected to:
Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

SQL> SHOW CON_NAME;

CON_NAME
------------------------------
CDB$ROOT
SQL> SHOW USER;
USER is "SYS"
SQL> exit
Disconnected from Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

C:\Users\rehaa>sqlplus system/RehaaN1250@FREE

SQL*Plus: Release 23.26.2.0.0 - Production on Tue Jul 14 10:23:22 2026
Version 23.26.2.0.0

Copyright (c) 1982, 2026, Oracle.  All rights reserved.

Last Successful login time: Sun Jul 12 2026 21:43:48 +05:30

Connected to:
Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

SQL> SHOW CON_NAME;

CON_NAME
------------------------------
CDB$ROOT
SQL> SHOW USER;
USER is "SYSTEM"
SQL> exit
Disconnected from Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

C:\Users\rehaa>sqlplus

SQL*Plus: Release 23.26.2.0.0 - Production on Tue Jul 14 10:23:42 2026
Version 23.26.2.0.0

Copyright (c) 1982, 2026, Oracle.  All rights reserved.

Enter user-name: system
Enter password:
Last Successful login time: Tue Jul 14 2026 10:23:22 +05:30

Connected to:
Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

SQL> SHOW CON_NAME;

CON_NAME
------------------------------
CDB$ROOT
SQL> SHOW USER;
USER is "SYSTEM"
```
## 1. `sqlplus / as sysdba`

```bash
sqlplus / as sysdba
```

This is **Operating System Authentication**.

You didn't type

* username
* password
* service name

because Oracle asked Windows:

> "Is this person allowed to be SYSDBA?"

Since your Windows account belongs to the Oracle admin group (created during installation), Oracle trusts Windows.

Internally it logs you in as

```sql
USER = SYS
```

and gives you the **SYSDBA privilege**.

```
Windows User
      │
      ▼
Operating System Authentication
      │
      ▼
Oracle
      │
      ▼
SYS user + SYSDBA privilege
```

Notice:

```sql
SHOW USER;

USER is "SYS"
```

---
## SYSDBA is NOT a user

This is the biggest misconception.

Many people think

```
SYSDBA = user
```

Wrong.

It's actually

```
SYS = user

SYSDBA = privilege (role)
```

Think of Linux.

```
User
↓

root
```

Privilege

```
sudo
```

You can be

```
root
```

or

```
normal user with sudo
```

Oracle is similar.

---
## 2. `sqlplus system/password@FREE`

```bash
sqlplus system/RehaaN1250@FREE
```

This is a **normal database connection**.

Oracle now needs

* username
* password
* service name

```
SYSTEM
password
FREE
```

The service

```
FREE
```

tells Oracle

> Connect me to this database service.

After authentication

```
USER = SYSTEM
```

No SYSDBA privilege.

```
Client
   │
username/password
   │
   ▼
Listener
   │
   ▼
Service FREE
   │
   ▼
SYSTEM account
```

---
## 3. `sqlplus`

```bash
sqlplus
```

Then

```
Username:
system

Password:
*****
```

This is almost identical to

```bash
sqlplus system/password
```

except SQL*Plus asks interactively.

Oracle also omitted

```
@FREE
```

because it used the **default local database** configured on your machine.

So these two are effectively the same.

```
sqlplus system/password

=
sqlplus system/password@FREE
```

(on a local Oracle Free installation)

---
## Why is `@FREE` called a service?

Because one Oracle installation can provide many database services.

For example

```
FREE
FREEPDB1
HRDB
SALES
TEST
```

Each is a service clients can connect to.

You can see them:

```sql
SELECT name FROM v$services;
```

Example output

```
FREE
FREEPDB1
SYS$BACKGROUND
SYS$USERS
```

---
## What is the difference between SYS and SYSTEM?

Both are administrative accounts.

But they have different purposes.

### SYS

The owner of the entire Oracle database.

Owns

```
Data Dictionary

SYSTEM TABLES

Internal Packages
```

Every Oracle object ultimately belongs to SYS.

Examples

```
DBA_TABLES

USER_TABLES

V$DATABASE

V$INSTANCE
```

belong to SYS.

Never create your application tables under SYS.

---

### SYSTEM

Also an administrator.

But intended for database administration tasks.

Can

* create users
* grant privileges
* create tablespaces

Should not own application data either.

---
## Why is SYS special?

Because SYS owns the Oracle dictionary.

When you connect

```bash
sqlplus / as sysdba
```

Oracle actually enables a very powerful administrative mode.

You can

```sql
STARTUP

SHUTDOWN

RECOVER DATABASE

ALTER DATABASE

ALTER SYSTEM
```

SYSTEM cannot do all of these.

Example

```
SYSDBA

↓
Can start database
Can stop database
Can recover database
Can access everything
```

---
## Analogy

Imagine Oracle is a university.

```
University
│
├── Principal (SYS)
│      owns everything
│
├── Vice Principal (SYSTEM)
│      manages students
│
├── Teachers
│
└── Students
```

Now imagine

```
SYSDBA
```

is not a person.

It's the **Master Key**.

Whoever holds that master key can open every room.

Usually

```
SYS
```

holds it.

Sometimes another admin account can also be granted SYSDBA.

For example

```sql
GRANT SYSDBA TO rehaan;
```

Then

```
rehaan
```

can connect as

```bash
sqlplus rehaan/password as sysdba
```

or

```bash
sqlplus / as sysdba
```

(if OS authentication is configured for that user).

Notice:

```
User
↓

REHAAN

Privilege
↓

SYSDBA
```

The privilege is what grants the administrative powers.

---
## Summary

| Command                        | Authentication                  | Connected As | Needs Password? | SYSDBA? |
| ------------------------------ | ------------------------------- | ------------ | --------------- | ------- |
| `sqlplus / as sysdba`          | Operating system authentication | `SYS`        | No              | ✅ Yes   |
| `sqlplus system/password@FREE` | Database username/password      | `SYSTEM`     | Yes             | ❌ No    |
| `sqlplus` → `system`           | Interactive username/password   | `SYSTEM`     | Yes             | ❌ No    |

One more concept that will make the rest of Oracle much easier: in **Oracle Multitenant**, you're currently connected to **`CDB$ROOT`**, which is the root container of the database. Most user schemas and application development should happen in a **Pluggable Database (PDB)** such as `FREEPDB1`, not in `CDB$ROOT`. That's why `SHOW CON_NAME` is such an important command.
# Understanding Services
```bash
C:\Users\rehaa>sqlplus / as sysdba

SQL*Plus: Release 23.26.2.0.0 - Production on Tue Jul 14 10:28:14 2026
Version 23.26.2.0.0

Copyright (c) 1982, 2026, Oracle.  All rights reserved.


Connected to:
Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

SQL> select name from v$services;

NAME
----------------------------------------------------------------
freeXDB
SYS$BACKGROUND
SYS$USERS
freepdb1
free

SQL> exit
Disconnected from Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

C:\Users\rehaa>sqlplus system/RehaaN1250@FREE

SQL*Plus: Release 23.26.2.0.0 - Production on Tue Jul 14 10:29:37 2026
Version 23.26.2.0.0

Copyright (c) 1982, 2026, Oracle.  All rights reserved.

Last Successful login time: Tue Jul 14 2026 10:23:48 +05:30

Connected to:
Oracle AI Database 26ai Free Release 23.26.2.0.0 - Develop, Learn, and Run for Free
Version 23.26.2.0.0

SQL> select name from v$services;

NAME
----------------------------------------------------------------
freeXDB
SYS$BACKGROUND
SYS$USERS
freepdb1
free
```
## What is a service?

Think of a service as Oracle saying:

> "I am listening for connections on this name."

For example:

```
FREE
FREEPDB1
```

These are like entry doors into the database.

They are **database-wide**, not user-specific.

Imagine an apartment building.

```
Apartment Building
│
├── Door A (FREE)
├── Door B (FREEPDB1)
└── Door C (XDB)
```

Whether you are:

* the building owner (SYS)
* the manager (SYSTEM)
* a resident (HR)

the doors don't suddenly disappear. Everyone can see the building has those entrances.

---
## Why could both SYS and SYSTEM see them?(There is diff btw existing services and running services i.e, session)

Suppose you ran

```sql
SELECT name FROM v$services;
```

As `SYS`

```
FREE
FREEPDB1
SYS$USERS
SYS$BACKGROUND
```

As `SYSTEM`

```
FREE
FREEPDB1
SYS$USERS
SYS$BACKGROUND
```

That's normal because `SYSTEM` has powerful catalog access.

The **list of services belongs to the database**, not to the logged-in user.

---
## Then what changes between SYSDBA and a normal user?

The important difference is **what you're allowed to do after connecting**.

For example:

### SYSDBA

```sql
SHUTDOWN IMMEDIATE;

STARTUP;

ALTER DATABASE OPEN;

RECOVER DATABASE;
```

Works.

---

### SYSTEM

```sql
SHUTDOWN IMMEDIATE;
```

You'll get something like

```
ORA-01031: insufficient privileges
```

because `SYSTEM` is an administrator, but **not** a `SYSDBA`.

---

### A normal application user

Suppose you create

```sql
CREATE USER rehaan IDENTIFIED BY pass;
GRANT CREATE SESSION TO rehaan;
```

Now login:

```bash
sqlplus rehaan/pass@FREEPDB1
```

If that user tries

```sql
SELECT * FROM v$services;
```

they'll likely get

```
ORA-00942
```

or

```
ORA-01031
```

unless you've granted the necessary dictionary access.

So **ordinary users** often cannot even query `V$SERVICES`.

---
## Another subtle point

When you connected as

```bash
sqlplus / as sysdba
```

you connected to

```
CDB$ROOT
```

When you connected as

```bash
sqlplus system/RehaaN1250@FREE
```

you also ended up in

```
CDB$ROOT
```

Notice:

```
SHOW CON_NAME;

CDB$ROOT
```

That means **both sessions were connected to the same container**, so naturally they saw the same database services.

---
## Think of it like a hotel

```
Hotel
│
├── Reception (Services)
├── Room keys
├── Master key
```

* **Services** = the entrances to the hotel. Everyone can see the entrance exists.
* **User account** = your room key.
* **SYSDBA** = the master key that opens every room and lets you lock or unlock the entire hotel.

Seeing the hotel entrance doesn't mean you own the hotel. Humans do enjoy naming things in ways that encourage exactly this confusion.

So the services are a property of the **database instance**, while `SYSDBA`, `SYSTEM`, and ordinary users differ in the **privileges they have once connected**.
# One Service, one DB but one DB multiple services wrong statement
The relationship is actually:

> **One PDB usually has one default service, but one PDB can have multiple services.**

Think of it like this:

```text
Oracle Instance
│
└── CDB
    │
    ├── Root Container (CDB$ROOT)
    │      │
    │      └── Service: FREE
    │
    ├── PDB1 (FREEPDB1)
    │      │
    │      ├── Service: FREEPDB1
    │      ├── Service: SALES_APP
    │      └── Service: REPORTING
    │
    └── PDB2 (HRPDB)
           │
           ├── Service: HRPDB
           └── Service: HR_READONLY
```

Notice:

* `FREE` → points to **CDB$ROOT**
* `FREEPDB1` → points to **FREEPDB1**
* `SALES_APP` and `REPORTING` could **also** point to `FREEPDB1`

So a service is simply a **connection endpoint**.

---

## Why would one PDB have multiple services?

Suppose a company has one PDB called `SALESPDB`.

Different applications connect to it:

```text
SALESPDB
│
├── Sales Website
├── Mobile App
├── Analytics
└── Internal ERP
```

Instead of everyone connecting using

```text
SALESPDB
```

the DBA creates services:

```text
SALES_WEB
SALES_MOBILE
SALES_REPORTING
SALES_BATCH
```

All four services connect to **the same PDB**.

Why?

Because Oracle can assign different characteristics to each service:

* Different workload priorities
* Different failover policies
* Different Resource Manager plans
* Easier monitoring

So services are **logical names**, not databases themselves.

---
## In Oracle Free 26ai

You'll probably see something like:

```sql
SQL> SELECT name, pdb FROM cdb_services;
```

Something similar to:

```text
NAME             PDB
---------------- -----------
FREE             CDB$ROOT
FREEPDB1         FREEPDB1
```

Here it **looks** like

> One service → One PDB

because Oracle Free keeps things simple.

---
## A better way to think about it

Don't think:

```text
PDB = Service
```

Think:

```text
Service
     │
     ▼
Connection entry point
     │
     ▼
Routes you into a PDB
```

A service is like a **website URL**.

For example:

```text
mail.google.com
drive.google.com
docs.google.com
```

All of them ultimately connect to Google's infrastructure, but each has a different purpose.

Similarly:

```text
SALES_WEB
SALES_REPORTING
SALES_BATCH
```

may all connect to the **same PDB**, but Oracle treats them as different connection services.

---

### In your installation

Since you're using Oracle AI Database 26ai Free, you most likely have:

| Service    | Connects to |
| ---------- | ----------- |
| `FREE`     | `CDB$ROOT`  |
| `FREEPDB1` | `FREEPDB1`  |
