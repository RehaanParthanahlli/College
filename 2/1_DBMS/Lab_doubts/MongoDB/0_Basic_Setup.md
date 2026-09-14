# MongoDB Notes — Beginner to Advanced

## 0. Install & Verify

- Download MongoDB Community Server: https://www.mongodb.com/try/download/community
- Download MongoDB Compass (GUI): https://www.mongodb.com/try/download/compass
- `mongosh` (the modern shell) is **not bundled** with the Community Server installer — it must be installed separately via its own **MSI installer**: https://www.mongodb.com/try/download/shell
  - Running the MSI typically adds `mongosh` to PATH automatically. If you instead download the ZIP version, you'll need to manually add its folder to PATH yourself.
- After installing, add MongoDB's `bin` folder to your system's **PATH environment variable** (Windows: System Properties → Environment Variables → Path; Linux/Mac: add `export PATH=$PATH:/path/to/mongodb/bin` to your shell profile).
- Verify installation in a terminal:
  ```bash
  mongod --version
  mongosh --version
  ```
  If both print version info, the install and PATH setup worked.

---

## 1. What is MongoDB?

- MongoDB is a **NoSQL, document-oriented database**.
- Instead of storing data in rigid rows/columns like relational databases, it stores data as flexible, JSON-like **documents**.
- **Used for:** applications needing flexible/evolving schemas, high write throughput, large-scale horizontal scaling, and unstructured or semi-structured data — e.g., content management systems, catalogs, real-time analytics, IoT data, mobile/web app backends.
- **Where it's used:** startups and enterprises alike — anywhere requirements change fast and a fixed table schema becomes a bottleneck.

---

## 2. Architecture: MongoDB vs Oracle

Oracle (e.g., Oracle 21c/26ai) uses a multitenant structure:
**Service → PDB (Pluggable Database) → CDB (Container Database)**

MongoDB uses a much simpler hierarchy:

| MongoDB Term | Rough SQL/Oracle Equivalent |
|---|---|
| Database (DB) | Database/Schema |
| Collection | Table |
| Document | Row |
| Field | Column |

- A **Database** holds multiple **Collections**.
- A **Collection** holds multiple **Documents**.
- A **Document** holds multiple **Fields** (key-value pairs).
- Unlike SQL tables, documents in the *same collection* don't need to have the same fields — schema is flexible by default.

---

## 3. Why NoSQL? (History & Data Models)

- Around the 2000s, applications (social media, large-scale web apps) began hitting limits with traditional SQL databases:
  - Rigid schemas made rapid iteration hard.
  - Scaling relational databases horizontally (across many servers) is difficult due to joins and ACID transaction overhead.
- This gave rise to **NoSQL** ("Not Only SQL") databases, which trade some relational guarantees for flexibility and scale. Different NoSQL databases use different **data models**:

| Data Model | Example DB | Structure |
|---|---|---|
| Key-Value | Redis | Simple key → value pairs, very fast |
| Document | MongoDB | JSON/BSON-like documents, nested structures |
| Graph | Neo4j | Nodes and edges, optimized for relationships |
| Column-family | Cassandra | Wide columns grouped by row key |

- MongoDB specifically falls under the **Document data model**.

---

## 4. BSON — What Documents Are Actually Stored As

- MongoDB documents *look* like JSON but are actually stored on disk as **BSON** (Binary JSON).
- **BSON** = a binary-encoded serialization format that extends JSON.
- Why BSON instead of plain JSON?
  - **More data types**: BSON supports types JSON doesn't have natively, like `Date`, `ObjectId`, `Binary data`, `Int32`/`Int64`, `Decimal128`.
  - **Faster to parse**: Binary format is quicker for machines to read/write than parsing text-based JSON.
  - **Traversable**: BSON stores field lengths as part of its encoding, making it faster to skip over fields without scanning the whole document.
- When you interact via `mongosh` or Compass, MongoDB shows you a JSON-like representation for readability — but internally it's BSON.

---

## 5. Normal Workflow (End to End)

1. **Install MongoDB Community Server** (the actual database engine — runs as the `mongod` process).
2. **Install MongoDB Compass** (GUI client) or **mongosh** (shell/CLI client) — these are tools to *connect to* and interact with the server.
3. **Connect** to the running `mongod` instance using a connection string.
4. Select/create a **Database**.
5. Select/create a **Collection** inside that database.
6. Insert/query **Documents** inside that collection.
7. Each document is made up of **Fields**.

---

## 6. Connection Strings

- A connection is simply a **URL** telling the client where and how to reach the database.
- General format:
  ```
  mongodb://username:password@host:PORT/database
  ```
- **Types of connections:**
  - **Global/local, no auth (default dev setup):**
    ```
    mongodb://localhost:27017
    ```
  - **User-specific (with authentication):**
    ```
    mongodb://username:password@localhost:27017
    ```
  - **Cloud-based** (e.g., MongoDB Atlas) — instead of `localhost`, you point to a cloud cluster URL:
    ```
    mongodb+srv://username:password@cluster0.mongodb.net/database
    ```
- Default port for MongoDB: **27017**.

---

## 7. Enabling Authentication (User-Specific Access)

- By default, a fresh MongoDB install has **no authentication enabled** — anyone who can reach the port can connect.
- To require login credentials, you must enable access control in the config file (`mongod.conf`):
  ```yaml
  security:
    authorization: enabled
  ```
- After enabling this and restarting `mongod`, clients **must** supply valid username/password credentials to connect (i.e., you're forced into the "user-specific" connection string style).

---

## 8. Changing the Bind IP (Network Access)

- By default, MongoDB only listens on `127.0.0.1` (localhost) — meaning only the local machine can connect to it. This is a security default.
- To allow connections from other machines/IPs, edit `mongod.conf`:
  ```yaml
  net:
    port: 27017
    bindIp: 127.0.0.1,192.168.1.10
  ```
  or to allow all IPs (not recommended without auth):
  ```yaml
  net:
    bindIp: 0.0.0.0
  ```
- Restart the `mongod` service after editing the config file for changes to take effect.

---

## 9. Creating a User

- **Correction/clarification on workflow:** user creation is **not done by editing `mongod.conf`** — the conf file only *enables* the authorization feature. The actual user is created by running a command **inside the mongo shell (`mongosh`)**, connected to the `admin` database:
  ```javascript
  use admin

  db.createUser({
    user: "myUser",
    pwd: "myPassword",
    roles: [ { role: "readWrite", db: "myDatabase" } ]
  })
  ```
- Typical sequence:
  1. Start `mongod` **without** auth enabled (first time).
  2. Connect via `mongosh`.
  3. Run `db.createUser(...)` to create an admin/regular user.
  4. Edit `mongod.conf` → set `security.authorization: enabled`.
  5. Restart `mongod`.
  6. From now on, connect using the `mongodb://username:password@...` connection string.

---

## 10. Restarting `mongod` After a Config Change

Any edit to `mongod.conf` (bindIp, authorization, etc.) only takes effect after a restart. How you restart depends on how it's running:

**If running as a Windows Service** (default if installed via MSI with default options):

```powershell
net stop MongoDB
net start MongoDB
```

Run in a terminal opened as Administrator. Or via GUI: `Win + R` → `services.msc` → find **MongoDB Server** → right-click → Restart.

**If running manually** (started yourself by typing `mongod` in a terminal):

1. Go to that terminal, press `Ctrl + C` to stop it
2. Start it again:
   ```bash
   mongod
   ```

---

## 11. Common Confusion: `use <db>` Works Even Without Login

**Mistake I made:** After enabling `security.authorization: enabled` and connecting via plain `mongosh` (no credentials), I assumed auth wasn't working because `use COLLEGE_25BCE1087` still succeeded and "connected" without asking for a password.

**Why this happens:** `use <dbname>` is a **client-side only** command — it just switches which database your shell session is currently pointed at. It does **not** send an actual query to the server, so there's nothing for the server to authenticate/authorize yet.

Authentication only gets enforced when you run a real **operation** that talks to the server — e.g. `show dbs`, `db.getUsers()`, `find()`, `insertOne()`, etc.

**Proof it was actually working the whole time** — running `show dbs` without logging in correctly threw:

```
test> show dbs
MongoServerError[Unauthorized]: Command listDatabases requires authentication
```

**Takeaway:** Don't judge whether auth is enabled based on whether `use <db>` succeeds. Test with a real operation like `show dbs` instead.

---

*(End of notes — strictly covering the points from class.)*