Shell scripting, ` \*+-^ $ `expr` while for if else --> .sh --> sh file.sh --> .\.a.out
Add info about vi & gedit nano

# 📝 Shell Scripting Concepts Notes

## 1. Variables
- Assign values without spaces around `=`.
```bash
var=10
name="Rehaan"
```
- Access with `$var`.
```bash
echo $var
echo "My name is $name"
```

---

## 2. Arithmetic
- Use `expr` or `$(( ))` for calculations.
```bash
expr 5 + 3
echo $((5 + 3))
sum=$((a + b))
```

---

## 3. Input & Output
- **Input**: `read variable`
```bash
echo "Enter a number:"
read num
```
- **Output**: `echo "text"`
```bash
echo "You entered $num"
```

---

## 4. Conditional Statements (`if`)
### Syntax:
```bash
if [ condition ]
then
   commands
elif [ condition ]
then
   commands
else
   commands
fi
```

### Common Operators:
- Numeric:  
  - `-eq` (equal), `-ne` (not equal)  
  - `-gt` (greater than), `-lt` (less than)  
  - `-ge` (greater or equal), `-le` (less or equal)
- String:  
  - `=` (equal), `!=` (not equal)  
  - `-z` (empty string), `-n` (non-empty string)

Example:
```bash
if [ $a -gt $b ]
then
    echo "a is greater"
fi
```

---

## 5. Loops

### `for` loop
```bash
for i in 1 2 3 4 5
do
    echo $i
done
```

### C-style `for` loop
```bash
for (( i=1; i<=10; i++ ))
do
    echo $i
done
```

### `while` loop
```bash
i=1
while [ $i -le 5 ]
do
    echo $i
    i=$((i+1))
done
```

---

## 6. Combining `for` and `if`
- Useful for filtering values (like odd numbers).
```bash
for (( i=1; i<=20; i++ ))
do
    if [ $((i % 2)) -ne 0 ]
    then
        echo $i
    fi
done
```

---

## 7. Script Structure
- First line: **shebang** tells system which shell to use.
```bash
#!/bin/bash
```
- Save file as `script.sh`.
- Run with:
```bash
bash script.sh
```

---

## 8. Best Practices
- Always quote variables: `"$var"` (avoids errors with spaces).
- Use indentation for readability.
- Comment your code with `#`.

---

# 🔑 Quick Reference
- **Variables** → `var=value`, `$var`
- **Arithmetic** → `expr`, `$(( ))`
- **Input/Output** → `read`, `echo`
- **If** → `[ condition ]` with operators
- **Loops** → `for`, `while`
- **Combine** → `for + if` for filtering

---

This is the **concept toolkit** you need. With these notes, you can build scripts for largest number, factorial, multiplication table, odd numbers, and beyond.  
