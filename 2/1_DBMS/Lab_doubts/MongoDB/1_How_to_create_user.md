# How to Create a MongoDB User

## 1. Start the server (Terminal 1 — leave running)

```bash
mongod
```

Skip this if MongoDB is already running as a Windows service.

## 2. Connect via shell (Terminal 2)

```bash
mongosh
```

## 3. Switch to the admin database

```javascript
use admin
```

## 4. Create the user

```javascript
db.createUser({
  user: "25BCE1087",
  pwd: "25BCE1087",
  roles: [ { role: "readWrite", db: "25BCE1087" } ]
})
```

For full admin rights instead of scoped readWrite:

```javascript
db.createUser({
  user: "25BCE1087",
  pwd: "25BCE1087",
  roles: [ { role: "root", db: "admin" } ]
})
```

## 5. Verify

```javascript
db.getUsers()
```

## 6. Exit

```javascript
exit
```

## 7. Enable authorization (after user is created)

Edit `mongod.conf`:

```yaml
security:
  authorization: enabled
```

Restart `mongod`, then reconnect with:

```bash
mongosh -u 25BCE1087 -p --authenticationDatabase admin
```