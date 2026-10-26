# MongoDB Docker Sharding: Exam Preparation Roadmap

We're going to learn this as a **practical lab exercise**, not as a collection of commands to memorize five minutes before the exam. You need to understand the architecture, write a working `docker-compose.yml`, create a sharded cluster, scale it out by adding a shard, and scale it in by removing a shard **without losing data**.

I've got your two MongoDB PPTs and your reference notes. We'll use the reference notes as the main guide, especially the MongoDB Lab Assessment 4(c) workflow. Your notes explicitly cover creating a two-shard cluster, adding a third shard, checking data distribution, and safely removing that third shard. Pasted markdown

We'll follow that workflow rather than wandering into unrelated Docker topics. You have an exam tomorrow, not a Docker research lab to run for the next six months.

## The final goal

By the end, you should be able to perform this entire sequence independently:

1. Write the Docker Compose configuration.
2. Start the MongoDB containers.
3. Initialize the replica sets.
4. Connect through `mongos` and register the shards.
5. Create a database and collection, insert sample data, and shard the collection.
6. Verify the initial cluster and data distribution.
7. **Scale out:** Add a third shard.
8. Verify the new shard and data distribution.
9. **Scale in:** Remove the third shard safely.
10. Verify that the remaining cluster works and all documents are preserved.

Your reference notes use MongoDB 7.0, a shared Docker network, persistent volumes, two initial shards, and a `mongos` router. We'll use those same conventions so the commands you learn match your lab. Pasted markdown

---
# Learning Plan: Part 1 to Part 9

We'll learn in nine parts. Each part builds on the previous one, so you understand why each command exists before you have to reproduce it in the exam.

## Part 1: Sharding Fundamentals and Architecture

**Objective:** Understand what you're building before touching the configuration file.

### Concepts
- What is MongoDB sharding?
- Why do we need sharding?
- Vertical scaling vs. horizontal scaling.
- What is a shard?
- What is `mongos`?
- What is a config server?
- What is a replica set?
- What is a shard key?
- Difference between scaling out and scaling in.

### Architecture you'll understand



We'll trace a database request through the cluster and understand which component receives it, which component routes it, and which component stores the data.

**By the end of Part 1:** You should be able to draw the architecture and explain the purpose of every component without memorizing a paragraph.

---

## Part 2: Docker and Docker Compose Fundamentals

**Objective:** Learn enough Docker to create and manage the MongoDB cluster.

### Concepts
- What is a Docker image?
- What is a container?
- What is Docker Compose?
- What does a `services` section define?
- Why do containers need a shared network?
- What are volumes, and why does MongoDB need persistent storage?
- What is the difference between a container port and a host port?
- What do `depends_on` and `-d` mean?

### Commands to learn

| Command | Purpose |
|---|---|
| `docker pull mongo:7.0` | Download the MongoDB image |
| `docker compose up -d` | Create and start the configured services |
| `docker compose ps` | Check container status |
| `docker compose logs` | Inspect container logs |
| `docker compose down` | Stop and remove the Compose-managed containers and network |

**By the end of Part 2:** You should understand how Compose translates a configuration file into running containers.

We'll also distinguish between removing a container and deleting persistent data. Confusing those two during a database lab is an impressively efficient way to create extra work.

---

## Part 3: Writing the Initial `docker-compose.yml`

**Objective:** Create the initial cluster configuration from scratch.

Your initial configuration will contain four services:

| Service | Role | Host port |
|---|---|---:|
| `configsvr` | Stores cluster metadata | `27119` |
| `shard1` | First shard | `27118` |
| `shard2` | Second shard | `27120` |
| `mongos` | Query router | `27017` |

These are the services and ports specified in your reference notes. Pasted markdown

### What we'll learn

- How to structure the YAML file.
- How to define a MongoDB service.
- The purpose of `image`, `command`, `ports`, `volumes`, and `networks`.
- The difference between a normal MongoDB server and one started with `--configsvr` or `--shardsvr`.
- Why each shard needs its own replica set name.
- How to define named volumes and a shared network.
- How to recognize YAML indentation errors.

### Your practical task

Write a complete `docker-compose.yml` containing all four services.

**By the end of Part 3:** You should be able to reproduce the initial configuration in the exam and explain each important line.

---

## Part 4: Starting the Cluster and Initializing Replica Sets

**Objective:** Turn the configured containers into usable MongoDB components.

Creating containers is not the same as configuring a functioning sharded cluster. This distinction is important.

### Concepts
- Why a MongoDB shard uses a replica set.
- What replica-set initialization does.
- What `rs.initiate()` means.
- What a replica-set primary is.
- Why containers must be able to resolve one another by service name.
- Why `mongos` must be configured to find the config server.

### Commands we'll practise

**Start the services in PowerShell:**

```powershell
docker compose up -d
```

**Initialize the config server replica set:**

```powershell
docker compose exec configsvr mongosh --eval "rs.initiate({_id: 'configRS', configsvr: true, members: [{_id: 0, host: 'configsvr:27017'}]})"
```

**Initialize the first shard:**

```powershell
docker compose exec shard1 mongosh --eval "rs.initiate({_id: 'shard1RS', members: [{_id: 0, host: 'shard1:27017'}]})"
```

**Initialize the second shard:**

```powershell
docker compose exec shard2 mongosh --eval "rs.initiate({_id: 'shard2RS', members: [{_id: 0, host: 'shard2:27017'}]})"
```

These follow the initialization procedure in your reference notes. Pasted markdown

**By the end of Part 4:** You should know how to initialize all three replica sets and verify that their members become `PRIMARY`.

---

## Part 5: Connecting Shards and Creating a Sharded Collection

**Objective:** Make the individual components function as one sharded cluster.

### Concepts
- Why we connect through `mongos`.
- What `sh.addShard()` does.
- How to verify registered shards.
- How to create a database and collection.
- What a shard key does.
- Why a suitable index is required for the lab's ranged shard key.
- What `sh.shardCollection()` does.
- How to check document counts and shard distribution.

### Commands we'll practise

**Connect to `mongos`:**

```powershell
docker compose exec mongos mongosh
```

**Register the two shards inside `mongosh`:**

```javascript
sh.addShard("shard1RS/shard1:27017")
sh.addShard("shard2RS/shard2:27017")
```

**Verify the cluster:**

```javascript
db.adminCommand({ listShards: 1 })
sh.status()
```

**Create the collection and shard it:**

```javascript
use lab4cDB

db.createCollection("records")

db.records.createIndex({ customerId: 1 })

sh.shardCollection(
  "lab4cDB.records",
  { customerId: 1 }
)
```

The index and shard key here match your reference notes. Pasted markdown

**By the end of Part 5:** You should have a functioning two-shard cluster and understand how MongoDB distributes a sharded collection.

---

## Part 6: Testing and Verifying the Initial Cluster

**Objective:** Establish a correct baseline before scaling.

You cannot prove that scaling preserved data if you never checked the data beforehand. Apparently, databases also require evidence.

### Concepts
- Inserting sample documents.
- Checking the document count.
- Understanding chunk-based data distribution.
- Checking registered shards versus actual data placement.
- Reading `sh.status()`.
- Using `getShardDistribution()`.

### Commands we'll practise

```javascript
db.records.countDocuments()

db.adminCommand({ listShards: 1 })

sh.status()

db.records.getShardDistribution()
```

Your reference lab uses 1,000 sample documents and asks you to record the initial distribution before scaling out. Pasted markdown

**By the end of Part 6:** You should be able to establish the baseline against which the scaled cluster will be compared.

---

## Part 7: Scale Out by Adding a Third Shard

**Objective:** Increase the cluster from two shards to three.

This is one of the main tasks you'll need to master for the exam.

### Concepts
- What scaling out means.
- Why the Compose file must be modified.
- Why the new shard needs its own volume and replica set.
- Why creating a container does not automatically register it with MongoDB.
- Why adding a shard does not guarantee immediate data movement.
- How to verify the new shard.

### Workflow

1. Add `shard3` to `docker-compose.yml`.
2. Add `shard3_data` under the top-level `volumes` section.
3. Start the new service.
4. Initialize its replica set.
5. Wait for the replica-set member to become primary.
6. Register the shard through `mongos`.
7. Verify the cluster and collection distribution.

### Commands we'll practise

**Start the new shard:**

```powershell
docker compose up -d shard3
```

**Initialize its replica set:**

```powershell
docker compose exec shard3 mongosh --eval "rs.initiate({_id: 'shard3RS', members: [{_id: 0, host: 'shard3:27017'}]})"
```

**Register it inside `mongosh` connected to `mongos`:**

```javascript
sh.addShard("shard3RS/shard3:27017")
```

**Verify:**

```javascript
db.adminCommand({ listShards: 1 })

sh.status()

db.records.countDocuments()

db.records.getShardDistribution()
```

This follows the scale-out procedure in your notes. Pasted markdown

**By the end of Part 7:** You should be able to add a third shard and verify that the cluster recognizes it.

---

## Part 8: Scale In by Removing the Third Shard

**Objective:** Reduce the cluster from three shards back to two without losing data.

This is the other major practical task.

### Concepts
- What scaling in means.
- Why a shard must be drained before removal.
- How MongoDB migrates data away from the shard.
- Why removal may initially report `ongoing`.
- Why you must wait for `completed`.
- Why stopping a container is not the same as removing a shard from the cluster.
- How to verify data preservation.

### Command we'll master

Run inside `mongosh`, connected to `mongos`:

```javascript
db.adminCommand({ removeShard: "shard3RS" })
```

If the result reports that removal is still in progress, repeat the command periodically:

```javascript
db.adminCommand({ removeShard: "shard3RS" })
```

Continue until the response reports:

```javascript
state: "completed"
```

Then verify:

```javascript
db.adminCommand({ listShards: 1 })

sh.status()

use lab4cDB

db.records.countDocuments()

db.records.getShardDistribution()
```

Your notes explicitly require waiting for removal to complete and then checking that the original 1,000 documents remain accessible. Pasted markdown

**By the end of Part 8:** You should be able to remove a shard safely and explain why you must not simply stop the container first.

---

## Part 9: Exam Simulation, Troubleshooting and Viva

**Objective:** Reproduce the entire lab without relying on step-by-step notes.

We'll combine everything you've learned into one practical assessment.

### Task A: Initial setup
- Write the Compose file.
- Start the containers.
- Initialize the replica sets.
- Register the initial two shards.
- Create and shard the collection.

### Task B: Scale out
- Add `shard3`.
- Start and initialize it.
- Register it.
- Verify the cluster.

### Task C: Scale in
- Remove `shard3`.
- Wait for completion.
- Verify that the cluster contains only the original two shards.
- Confirm that the data is preserved.

### Task D: Debugging
I'll give you scenarios such as:

| Problem | What you'll need to diagnose |
|---|---|
| A container exits immediately | Container logs and configuration |
| Replica set does not become primary | Initialization and member connectivity |
| `listShards` returns no shards | Registration and cluster configuration |
| MongoDB reports that an index is missing | Index creation and shard-key requirements |
| Third shard is registered but has no data | Chunk distribution and balancing |
| `removeShard` reports `ongoing` | Migration progress and removal state |
| Document count unexpectedly changes | Database selection, insertion history and data integrity |

These are based on the troubleshooting scenarios in your reference notes. Pasted markdown

We'll also practise the viva questions covering sharding, `mongos`, config servers, shard keys, scaling, replica sets and Docker Compose. Pasted markdown

**By the end of Part 9:** You should be able to explain, execute and troubleshoot the lab rather than merely recognize commands when someone else types them.

Correct. That changes the preparation strategy significantly. If your mam provides the `docker-compose.yml`, **you do not need to memorise how to write the Compose file. You need to master the commands and the order of execution after receiving it.**

Your practical workflow is essentially:

**Start cluster → Initialise replica sets → Register shards → Shard collection → Insert data → Verify distribution → Add shard → Verify → Remove shard safely.**

Let's organise this into an exam-ready procedure.

# MongoDB Lab Assessment: Complete Workflow

## Part 1: Start the initial cluster

Your mam provides the Compose file containing:
- `configsvr`
- `shard1`
- `shard2`
- `mongos`

Run these commands in PowerShell or the terminal, in the directory containing the Compose file.

```powershell
docker compose up -d
```

Check that the containers are running:

```powershell
docker compose ps
```

**Purpose:** Starts the containers. It does not initialise replica sets or register shards automatically.

---

## Part 2: Initialise all three replica sets

You need to initialise the config server replica set and both shard replica sets.

### 2.1 Initialise the config server

```powershell
docker compose exec configsvr mongosh --eval "rs.initiate({_id: 'configRS', configsvr: true, members: [{_id: 0, host: 'configsvr:27017'}]})"
```

### 2.2 Initialise Shard 1

```powershell
docker compose exec shard1 mongosh --eval "rs.initiate({_id: 'shard1RS', members: [{_id: 0, host: 'shard1:27017'}]})"
```

### 2.3 Initialise Shard 2

```powershell
docker compose exec shard2 mongosh --eval "rs.initiate({_id: 'shard2RS', members: [{_id: 0, host: 'shard2:27017'}]})"
```

**Remember the mapping:**

| Component | Replica set name |
|---|---|
| Config server | `configRS` |
| Shard 1 | `shard1RS` |
| Shard 2 | `shard2RS` |

Wait for the replica set members to become `PRIMARY` before proceeding.

---

## Part 3: Register the shards through `mongos`

Connect to the router:

```powershell
docker compose exec mongos mongosh
```

You are now inside the MongoDB shell connected to `mongos`.

Run the following commands **inside `mongosh`**, not in PowerShell.

### 3.1 Register Shard 1

```javascript
sh.addShard("shard1RS/shard1:27017")
```

### 3.2 Register Shard 2

```javascript
sh.addShard("shard2RS/shard2:27017")
```

### 3.3 Verify registered shards

```javascript
db.adminCommand({ listShards: 1 })
```

You should see both `shard1RS` and `shard2RS`.

Check the cluster status:

```javascript
sh.status()
```

**Concept:** Initialising a replica set and registering a shard are two different operations. You must do both.

---

## Part 4: Create the database, collection and sample data

For the lab, use the database `lab4cDB` and collection `records`.

### 4.1 Select the database

```javascript
use lab4cDB
```

MongoDB creates the database when data is first stored in it. Merely running `use` does not necessarily create a persistent database.

### 4.2 Insert 1,000 sample documents

```javascript
for (let i = 1; i <= 1000; i++) {
    db.records.insertOne({
        customerId: i,
        name: "Customer" + i,
        department: "Dept" + (i % 5),
        amount: i * 100
    });
}
```

Each document contains:
- `customerId`: unique customer identifier
- `name`: customer name
- `department`: department
- `amount`: numeric amount

### 4.3 Verify the document count

```javascript
db.records.countDocuments()
```

**Expected output:**

```text
1000
```

---

## Part 5: Enable sharding for the collection

This is one of the most important parts of the practical.

### 5.1 Create an index on the shard key

```javascript
db.records.createIndex({ customerId: 1 })
```

Here, `1` means ascending index order.

### 5.2 Enable sharding on the database

```javascript
sh.enableSharding("lab4cDB")
```

### 5.3 Shard the collection

```javascript
sh.shardCollection("lab4cDB.records", { customerId: 1 })
```

The shard key is:

```javascript
{ customerId: 1 }
```

It tells MongoDB which field to use when distributing the collection's data.

### 5.4 Verify sharding

```javascript
sh.status()
```

```javascript
db.records.getShardDistribution()
```

```javascript
db.records.countDocuments()
```

Check that the collection is sharded, inspect its distribution, and confirm that the document count remains 1,000.

**Important:** Sharding a collection does not guarantee that its documents will immediately be evenly distributed across every shard. Distribution depends on chunk boundaries, balancing and the state of the cluster.

---

## Part 6: Split chunks using `splitAt` (if required)

Your lab may ask you to split a chunk manually.

```javascript
sh.splitAt("lab4cDB.records", { customerId: 500 })
```

This requests a split at the specified shard-key value.

For example, a split at `customerId: 500` creates a chunk boundary around that value, provided the split is valid for the existing chunk layout.

**Crucial distinction:**

- `splitAt()` divides a chunk.
- `moveChunk()` moves a chunk to another shard.

Splitting a chunk does **not**, by itself, guarantee that the new chunk will move to another shard.

To request that a chunk containing a particular shard-key value be moved to Shard 2, for example:

```javascript
sh.moveChunk("lab4cDB.records", { customerId: 750 }, "shard2RS")
```

The requested move must be valid for the current chunk layout. If the command fails, inspect the cluster state rather than repeatedly running it and hoping MongoDB develops sympathy.

Verify the result:

```javascript
db.records.getShardDistribution()
```

```javascript
sh.status()
```

---

# Part 7: Add the third shard (Scale Out)

Your mam may provide an updated Compose file containing `shard3`. You do not need to write that service definition yourself if she supplies it.

## 7.1 Start the new shard

```powershell
docker compose up -d shard3
```

## 7.2 Initialise its replica set

Run this in PowerShell:

```powershell
docker compose exec shard3 mongosh --eval "rs.initiate({_id: 'shard3RS', members: [{_id: 0, host: 'shard3:27017'}]})"
```

Wait until `shard3RS` becomes `PRIMARY`.

## 7.3 Register the third shard

If you exited `mongosh`, reconnect:

```powershell
docker compose exec mongos mongosh
```

Now run inside `mongosh`:

```javascript
sh.addShard("shard3RS/shard3:27017")
```

## 7.4 Verify

```javascript
db.adminCommand({ listShards: 1 })
```

```javascript
sh.status()
```

```javascript
db.records.countDocuments()
```

```javascript
db.records.getShardDistribution()
```

You should see three registered shards, assuming the operation succeeded.

**Remember:** Adding a shard to the cluster does not mean all existing data will instantly move to it. If the lab requires data on the third shard, you may need valid chunk-splitting and chunk-migration operations, depending on the existing distribution.

---

# Part 8: Remove the third shard (Scale In)

This is where you must be careful. **Never stop or delete Shard 3 before MongoDB has completed removing it from the cluster.**

Run this command inside `mongosh`, connected through `mongos`:

```javascript
db.adminCommand({ removeShard: "shard3RS" })
```

The command may return a response indicating that draining is in progress.

Run it again to check progress:

```javascript
db.adminCommand({ removeShard: "shard3RS" })
```

Repeat as necessary until the response indicates:

```javascript
state: "completed"
```

Do not assume the first response means removal is finished.

### Verify removal

```javascript
db.adminCommand({ listShards: 1 })
```

```javascript
sh.status()
```

```javascript
db.records.countDocuments()
```

```javascript
db.records.getShardDistribution()
```

Expected final state:
- `shard1RS` remains registered.
- `shard2RS` remains registered.
- `shard3RS` has been removed from the cluster.
- The document count remains **1,000**.

Only after MongoDB completes the removal should you consider stopping the third shard's container. Removing a shard from the cluster and deleting its container or storage volume are separate operations.

---

# Part 9: Your Exam Cheat Sheet

Memorise this sequence rather than treating each command as an isolated spell.

| Step | Task | Command to remember |
|---|---|---|
| 1 | Start initial cluster | `docker compose up -d` |
| 2 | Initialise config replica set | `rs.initiate(...)` |
| 3 | Initialise Shard 1 | `rs.initiate(...)` |
| 4 | Initialise Shard 2 | `rs.initiate(...)` |
| 5 | Connect to router | `docker compose exec mongos mongosh` |
| 6 | Register Shard 1 | `sh.addShard(...)` |
| 7 | Register Shard 2 | `sh.addShard(...)` |
| 8 | Check registered shards | `db.adminCommand({ listShards: 1 })` |
| 9 | Create sample data | `insertOne()` in a loop |
| 10 | Check document count | `db.records.countDocuments()` |
| 11 | Create shard-key index | `db.records.createIndex({ customerId: 1 })` |
| 12 | Enable database sharding | `sh.enableSharding("lab4cDB")` |
| 13 | Shard collection | `sh.shardCollection(...)` |
| 14 | Inspect distribution | `db.records.getShardDistribution()` |
| 15 | Split a chunk | `sh.splitAt(...)` |
| 16 | Start Shard 3 | `docker compose up -d shard3` |
| 17 | Initialise Shard 3 | `rs.initiate(...)` |
| 18 | Register Shard 3 | `sh.addShard(...)` |
| 19 | Remove Shard 3 safely | `db.adminCommand({ removeShard: "shard3RS" })` |
| 20 | Verify final state | `listShards`, `sh.status()`, `countDocuments()` |

## Three things you absolutely must understand

1. **`rs.initiate()`** initialises a replica set.
2. **`sh.addShard()`** registers a shard with the cluster through `mongos`.
3. **`sh.shardCollection()`** enables sharding for a specific collection using a shard key.
