# How to differentiate btw the existing services and running services
## Services
### 🔍 List all services
```sql
SELECT name, network_name, creation_date, pdb
FROM v$services;
```
- `v$services` gives you every service registered with the database.
- Useful for checking what’s available, regardless of whether it’s active.

### ⚡ List only running services
```sql
SELECT name, network_name, creation_date
FROM v$active_services;
```
- `v$active_services` filters down to services that are currently running/active.
- This is what you’d use to see which services are actually in use.

---

💡 **Tip:**  
If you want to see which instances are hosting those services, you can join with `gv$active_services` (global view across RAC) or check `v$instance` for context.
## Sessions
### 👥 List all sessions
```sql
SELECT sid, serial#, username, service_name, status, machine, program
FROM v$session;
```
- `sid` and `serial#` uniquely identify a session.
- `username` shows the database user.
- `service_name` ties the session back to the service it connected through.
- `status` tells you if it’s `ACTIVE` or `INACTIVE`.

### 🔎 Filter only active sessions
```sql
SELECT sid, serial#, username, service_name, status, machine, program
FROM v$session
WHERE status = 'ACTIVE';
```
- This narrows it down to sessions currently running work.

---

💡 **Extra tip:**  
If you want to see **sessions grouped by service**, you can do:
```sql
SELECT service_name, COUNT(*) AS session_count
FROM v$session
GROUP BY service_name;
```
That way you know how many users are connected under each service.
# How to see no of tables(Either Oracle SQL Dev UI or SELECT table_names from user_tables;)
# How to change the password of user?
```sql
ALTER USER username IDENTIFIED BY newPassword;
```