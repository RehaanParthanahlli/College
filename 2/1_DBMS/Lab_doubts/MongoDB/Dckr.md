# MongoDB Lab Assessment 4(c): Complete Notes and Commands

These notes follow your actual lab setup and the two requirements in the assessment: scale out by adding a shard, then scale in by removing a shard while preserving the data.&#x20;

Lab assessment 4(1).pdf



## 1. Aim

To implement a MongoDB sharded cluster using Docker Compose, insert sample data, verify data distribution, scale out by adding a shard, and scale in by safely removing a shard.

## 2. Important concepts

| Component   | Purpose                                                          |
| ----------- | ---------------------------------------------------------------- |
| `mongod`    | MongoDB server process that stores and manages data              |
| `mongos`    | Query router that directs operations to the appropriate shard(s) |
| `configsvr` | Stores sharded-cluster metadata                                  |
| Shard       | Stores a portion of a sharded collection                         |
| Replica set | Maintains copies of a shard's data across its members            |
| Shard key   | Field used to partition a collection across shards               |
| Scale out   | Add a shard to increase cluster capacity                         |
| Scale in    | Remove a shard and migrate its data to remaining shards          |

In this lab, each shard replica set has one member. That demonstrates sharding, but does not provide replica redundancy.

## 3. Project configuration

Project folder:

```
C:\Users\rehaa\OneDrive\Desktop\Not_PC\Current\2_S\VIT\Sem_3\Attic\mongo-sharding-lab
```

Services in the initial Docker Compose setup:

| Service     | Purpose       | Host port |
| ----------- | ------------- | --------- |
| `configsvr` | Config server | `27119`   |
| `shard1`    | First shard   | `27118`   |
| `shard2`    | Second shard  | `27120`   |
| `mongos`    | Query router  | `27017`   |

The additional shard will use host port `27121`.

### Example `docker-compose.yml`

This is a reference for the initial configuration. If your existing file is already working, keep it rather than overwriting it.

```
services:
  configsvr:
    image: mongo:7.0
    container_name: configsvr
    command: ["mongod", "--configsvr", "--replSet", "configRS", "--port", "27017", "--bind_ip_all"]
    ports:
      - "27119:27017"
    volumes:
      - config_data:/data/db
    networks:
      - mongo-net

  shard1:
    image: mongo:7.0
    container_name: shard1
    command: ["mongod", "--shardsvr", "--replSet", "shard1RS", "--port", "27017", "--bind_ip_all"]
    ports:
      - "27118:27017"
    volumes:
      - shard1_data:/data/db
    networks:
      - mongo-net

  shard2:
    image: mongo:7.0
    container_name: shard2
    command: ["mongod", "--shardsvr", "--replSet", "shard2RS", "--port", "27017", "--bind_ip_all"]
    ports:
      - "27120:27017"
    volumes:
      - shard2_data:/data/db
    networks:
      - mongo-net

  mongos:
    image: mongo:7.0
    container_name: mongos
    command: ["mongos", "--configdb", "configRS/configsvr:27017", "--bind_ip_all"]
    ports:
      - "27017:27017"
    depends_on:
      - configsvr
      - shard1
      - shard2
    networks:
      - mongo-net

networks:
  mongo-net:

volumes:
  config_data:
  shard1_data:
  shard2_data:
```

What this file does: defines the containers, their MongoDB roles, ports, persistent volumes, and shared Docker network.

## 4. Start the cluster

Run these commands in PowerShell, not in `mongosh`.

### Step 1: Navigate to the project directory

```
cd "C:\Users\rehaa\OneDrive\Desktop\Not_PC\Current\2_S\VIT\Sem_3\Attic\mongo-sharding-lab"
```

### Step 2: Pull the image

```
docker pull mongo:7.0
```

Docker downloads the image if it isn't already available locally.

### Step 3: Start the initial services

```
docker compose up -d
```

The `-d` flag runs the containers in the background.

### Step 4: Check the containers

```
docker compose ps
```

### Step 5: Initialize the config server replica set

```
docker compose exec configsvr mongosh --eval "rs.initiate({_id: 'configRS', configsvr: true, members: [{_id: 0, host: 'configsvr:27017'}]})"
```

### Step 6: Initialize both shard replica sets

```
docker compose exec shard1 mongosh --eval "rs.initiate({_id: 'shard1RS', members: [{_id: 0, host: 'shard1:27017'}]})"
```

```
docker compose exec shard2 mongosh --eval "rs.initiate({_id: 'shard2RS', members: [{_id: 0, host: 'shard2:27017'}]})"
```

Wait briefly for the replica sets to elect their primary members.

## 5. Connect to `mongos` and register the shards

### Step 7: Open the MongoDB shell

In PowerShell:

```
docker compose exec mongos mongosh
```

The prompt should identify `mongos`, for example:

```
[direct: mongos] test>
```

### Step 8: Register the existing shards

Run these commands inside `mongosh`:

```
sh.addShard("shard1RS/shard1:27017")
sh.addShard("shard2RS/shard2:27017")
```

If a shard is already registered, don't add it again.

### Step 9: Verify the cluster

```
db.adminCommand({ listShards: 1 })
sh.status()
```

Expected: two registered shards, `shard1RS` and `shard2RS`.

## 6. Create the database, collection, and sample data

Run the following commands inside the `mongos` shell.

### Step 10: Select the database

```
use lab4cDB
```

### Step 11: Create the collection

```
db.createCollection("records")
```

If the collection already exists, continue without recreating it.

### Step 12: Insert 1,000 sample documents

First check the count:

```
db.records.countDocuments()
```

Only if the count is `0`, run:

```
let docs = [];

for (let i = 1; i <= 1000; i++) {
  docs.push({
    customerId: i,
    name: "Customer" + i,
    department: ["CSE", "IT", "ECE"][i % 3],
    amount: i * 100
  });
}

db.records.insertMany(docs);
```

Verify:

```
db.records.countDocuments()
```

Expected output:

```
1000
```

Don't run the insertion loop again if the documents already exist, or you'll create duplicates.

### Step 13: Create the shard-key index

```
db.records.createIndex({ customerId: 1 })
```

This creates an ascending index on `customerId`. MongoDB requires a suitable index before sharding this collection with that key.

### Step 14: Shard the collection

```
sh.shardCollection("lab4cDB.records", { customerId: 1 })
```

If the collection is already sharded, inspect its status instead of running this command again.

Meaning: MongoDB uses `customerId` as the shard key to divide the collection into chunks that can be assigned to shards.

## 7. Record the initial distribution

Before scaling out, run:

```
db.adminCommand({ listShards: 1 })
sh.status()
db.records.countDocuments()
db.records.getShardDistribution()
```

Record the output. This is the before scale-out state.

Take Screenshot 1 showing the two registered shards, the document count, and the initial distribution.

## 8. Scale out: add the third shard

### Step 15: Edit `docker-compose.yml`

Add this service under `services:`, aligned with the other services:

```
  shard3:
    image: mongo:7.0
    container_name: shard3
    command: ["mongod", "--shardsvr", "--replSet", "shard3RS", "--port", "27017", "--bind_ip_all"]
    ports:
      - "27121:27017"
    volumes:
      - shard3_data:/data/db
    networks:
      - mongo-net
```

Also add `shard3_data:` under the existing top-level `volumes:` section:

```
volumes:
  config_data:
  shard1_data:
  shard2_data:
  shard3_data:
```

Keep the existing entries. Save the file.

### Step 16: Start the new shard

Run in PowerShell:

```
docker compose up -d shard3
```

### Step 17: Initialize the third shard's replica set

```
docker compose exec shard3 mongosh --eval "rs.initiate({_id: 'shard3RS', members: [{_id: 0, host: 'shard3:27017'}]})"
```

Check its state:

```
docker compose exec shard3 mongosh --quiet --eval "rs.status().members.map(m => ({name: m.name, state: m.stateStr}))"
```

Wait until the state is `PRIMARY`.

### Step 18: Register the new shard

If you exited `mongosh`, reconnect from PowerShell:

```
docker compose exec mongos mongosh
```

Then run inside `mongosh`:

```
sh.addShard("shard3RS/shard3:27017")
```

Verify:

```
db.adminCommand({ listShards: 1 })
sh.status()
```

Expected: three registered shards, `shard1RS`, `shard2RS`, and `shard3RS`.

Take Screenshot 2 showing the third shard added and the updated shard list.

### Step 19: Check data distribution after scale-out

Inside `mongosh`:

```
use lab4cDB
db.records.countDocuments()
db.records.getShardDistribution()
sh.status()
```

The count should remain `1000`.

Adding a shard does not guarantee that MongoDB immediately moves data to it. If the output already shows data on `shard3RS`, save it as Screenshot 3.

If all data remains on the original shards, you can split and move a chunk using the ranged shard key from this lab:

```
sh.splitAt("lab4cDB.records", { customerId: 500 })
```

Then:

```
sh.moveChunk("lab4cDB.records", { customerId: 750 }, "shard3RS")
```

These commands request a split at the specified key and move the chunk containing the specified key to `shard3RS`. They can fail if the requested split point is invalid or the chunk layout differs from what is expected. If an error occurs, inspect `sh.status()` and `db.records.getShardDistribution()` before proceeding.

Recheck:

```
db.records.countDocuments()
db.records.getShardDistribution()
```

The count should still be `1000`. Capture the actual output, not an assumed even distribution.

## 9. Scale in: remove the third shard

The goal is to remove `shard3RS` from the cluster while allowing MongoDB to migrate its data to the remaining shards.

### Step 20: Start shard removal

Run inside `mongosh`, connected to `mongos`:

```
db.adminCommand({ removeShard: "shard3RS" })
```

MongoDB may report:

```
{
  shard: "shard3RS",
  state: "ongoing"
}
```

This means removal is still in progress. Repeat the command periodically:

```
db.adminCommand({ removeShard: "shard3RS" })
```

Continue until the response shows:

```
state: "completed"
```

Do not stop the `shard3` container or delete its volume before removal completes.

Take Screenshot 4 showing the completed removal.

## 10. Verify the final cluster configuration

### Step 21: Check the remaining shards

Inside `mongosh`:

```
db.adminCommand({ listShards: 1 })
sh.status()
```

Expected: only `shard1RS` and `shard2RS` remain registered.

### Step 22: Verify that the data is preserved

```
use lab4cDB
db.records.countDocuments()
db.records.find().limit(5)
db.records.getShardDistribution()
```

Expected:

- Document count remains `1000`.
- The collection remains accessible.
- The distribution shows the data on the remaining shards.
- `shard3RS` no longer appears in the registered shard list.

Take Screenshot 5 showing the final shard list and document count.

You can leave the removed `shard3` container running for now. Removing a shard from MongoDB's cluster configuration does not automatically mean you should immediately delete its Docker container or persistent volume.

## 11. Quick revision sheet

Docker commands: PowerShell

```
docker pull mongo:7.0
docker compose up -d
docker compose ps
docker compose exec mongos mongosh
```

Cluster commands: `mongosh`

```
sh.addShard("shard1RS/shard1:27017")
sh.addShard("shard2RS/shard2:27017")

db.adminCommand({ listShards: 1 })
sh.status()
```

Data commands: `mongosh`

```
use lab4cDB
db.createCollection("records")
db.records.countDocuments()
db.records.createIndex({ customerId: 1 })

sh.shardCollection(
  "lab4cDB.records",
  { customerId: 1 }
)

db.records.getShardDistribution()
```

Scale-out

```
docker compose up -d shard3
```

```
sh.addShard("shard3RS/shard3:27017")
db.adminCommand({ listShards: 1 })
db.records.getShardDistribution()
```

Scale-in

```
db.adminCommand({ removeShard: "shard3RS" })
```

Repeat until `state` is `"completed"`, then verify:

```
db.adminCommand({ listShards: 1 })
sh.status()
db.records.countDocuments()
db.records.getShardDistribution()
```

## 12. Troubleshooting

| Error or issue                      | What to do                                                                                                             |
| ----------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| `Please create an index...`         | Run `db.records.createIndex({ customerId: 1 })` before sharding.                                                       |
| `listShards` returns an empty array | Check replica-set status, then register the existing shards using `sh.addShard()`.                                     |
| Replica set is not `PRIMARY`        | Wait briefly and check `rs.status()` again.                                                                            |
| `shard3RS` is not found             | Check that the third container is running, its replica set is initialized, and it can be reached using `shard3:27017`. |
| No data appears on shard 3          | Check the shard distribution; a new shard does not guarantee immediate data migration.                                 |
| `removeShard` reports `ongoing`     | Repeat the command periodically and wait for `completed`.                                                              |
| Document count is `0`               | Confirm you're using `lab4cDB`, then inspect the collection. Do not blindly reinsert data.                             |

## 13. Viva questions and answers

1\. What is sharding? Sharding is a method of distributing a large dataset across multiple servers or shards.

2\. What is the purpose of `mongos`? It routes client database operations to the appropriate shard or shards.

3\. What does the config server store? Metadata about the sharded cluster, including shard and chunk assignments.

4\. What is a shard key? A field or set of fields used to partition a collection's data. Here, it is `customerId`.

5\. What is scale-out? Adding another shard to increase the cluster's capacity.

6\. What is scale-in? Removing a shard after its data has been migrated to the remaining shards.

7\. Why check `getShardDistribution()`? To inspect how collection data is distributed across the shards.

8\. Why check `countDocuments()` after scale-in? To verify that all 1,000 documents remain accessible after removing the shard.

9\. Does adding a shard automatically distribute all data evenly? No. MongoDB's chunk splitting and balancing behavior determines the actual distribution.

10\. Why must shard removal complete before stopping the shard? MongoDB needs the shard available while it migrates data and completes the removal process.

11\. What is the difference between a shard and a replica set? A shard is a unit of data partitioning in a sharded cluster. A replica set maintains copies of data among its members.

12\. Why use Docker Compose? It defines and manages the related containers, network, ports, and persistent storage from one configuration file.

## 14. Final submission checklist

- Initial two-shard cluster status and data distribution.
- Successful creation of the third shard.
- Three-shard cluster status after scale-out.
- Data distribution after scale-out.
- `removeShard` completion response.
- Final two-shard cluster configuration.
- Final document count of 1,000.
- Final data distribution after scale-in.