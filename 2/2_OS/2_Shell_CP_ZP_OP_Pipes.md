# 📝 Process Creation & Termination (Linux) Notes

## 1. Process

* A **process** is a program in execution.
* Every process has a unique **Process ID (PID)**.
* Linux creates a new process using **`fork()`**.

Example:

```c
pid_t pid = fork();
```

---

# 2. `fork()`

* `fork()` creates a **child process**.
* After `fork()`, two processes execute the same program.

Return Values:

| Return Value | Meaning                              |
| ------------ | ------------------------------------ |
| `< 0`        | Process creation failed              |
| `0`          | Child Process                        |
| `> 0`        | Parent Process (returns child's PID) |

Example:

```c
pid_t pid = fork();

if(pid == 0)
{
    printf("Child Process");
}
else
{
    printf("Parent Process");
}
```

---

# 3. Parent Process

* Original process.
* Creates child process using `fork()`.
* Can wait for child using `wait()`.

Example

```c
printf("Parent PID = %d", getpid());
```

---

# 4. Child Process

* Newly created process.
* Executes independently after `fork()`.

Example

```c
printf("Child PID = %d", getpid());
```

---

# 5. Process IDs

## `getpid()`

Returns current process ID.

```c
getpid();
```

Example

```c
printf("%d", getpid());
```

---

## `getppid()`

Returns Parent Process ID.

```c
getppid();
```

Example

```c
printf("%d", getppid());
```

---

# 6. `wait()`

* Parent waits until child finishes execution.
* Prevents child from becoming a zombie.

Syntax

```c
wait(NULL);
```

Example

```c
wait(NULL);
printf("Child Completed");
```

---

# 7. `sleep()`

Suspends process for specified seconds.

Syntax

```c
sleep(seconds);
```

Example

```c
sleep(5);
```

---

# 8. `exit()`

Terminates a process.

Syntax

```c
exit(0);
```

Meaning

* `0` → Successful termination
* Non-zero → Error termination

---

# 9. Pipe

A **pipe** is used for communication between parent and child processes.

Functions

```c
pipe(pipefd);
```

```c
write(pipefd[1], message, strlen(message)+1);
```

```c
read(pipefd[0], buffer, sizeof(buffer));
```

---

# 10. `read()`

Reads data from a file or pipe.

Syntax

```c
read(fd, buffer, size);
```

---

# 11. `write()`

Writes data into a file or pipe.

Syntax

```c
write(fd, buffer, size);
```

---

# 12. `close()`

Closes a file descriptor.

Syntax

```c
close(fd);
```

---

# 13. Orphan Process

* Parent terminates before child.
* Child becomes **Orphan Process**.
* Linux assigns **init/systemd** as new parent.
* `getppid()` shows the new parent PID.

Example

```c
sleep(5);
printf("%d", getppid());
```

---

# 14. Zombie Process

* Child terminates.
* Parent does **not** call `wait()`.
* Child remains in Process Table as a Zombie.

Example

```c
exit(0);
```

Parent

```c
sleep(10);
```

Check using

```bash
ps
```

or

```bash
ps -el
```

---

# 15. Header Files

```c
#include <stdio.h>
```

Standard Input Output

```c
#include <unistd.h>
```

fork(), sleep(), pipe(), getpid(), getppid(), read(), write()

```c
#include <sys/wait.h>
```

wait()

```c
#include <stdlib.h>
```

exit()

```c
#include <string.h>
```

strlen()

---

# 16. Flow of `fork()`

```
        Parent
           |
        fork()
        /    \
 Child        Parent
```

Both execute simultaneously.

---

# 🔑 Quick Reference

* **Process** → Program in execution
* **fork()** → Creates child process
* **getpid()** → Current Process ID
* **getppid()** → Parent Process ID
* **wait()** → Parent waits for child
* **sleep()** → Pause execution
* **exit()** → Terminate process
* **pipe()** → Parent-child communication
* **read()** → Read from pipe
* **write()** → Write to pipe
* **close()** → Close pipe/file descriptor
* **Orphan Process** → Parent terminates first
* **Zombie Process** → Child terminates, parent doesn't call `wait()`

