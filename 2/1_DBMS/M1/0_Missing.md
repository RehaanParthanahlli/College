# KEYs(More Info!),SUPER/CANDIDATE/COMPOSITE KEYs, KEYs as Constraints?
## 🔑 Keys in Databases
- A **Key** is an attribute (or a set of attributes) used to uniquely identify a tuple (row) in a relation (table).
- Keys ensure **uniqueness** and help maintain **data integrity**.
- They are fundamental in defining relationships between tables.

---

## 🗝️ Super Key
- A **Super Key** is any set of attributes that can uniquely identify a tuple in a relation.
- It may contain **extra attributes** beyond what is necessary.
- Example:  
  In a table `STUDENT(StudentID, Name, Email, Phone)`:  
  - `{StudentID}` is a super key.  
  - `{StudentID, Email}` is also a super key (though redundant).

👉 Every relation must have at least one super key.

---

## 🗝️ Candidate Key
- A **Candidate Key** is a **minimal super key** — meaning no attribute can be removed without losing uniqueness.
- It is the **most efficient identifier** for tuples.
- Example:  
  In `STUDENT(StudentID, Name, Email, Phone)`:  
  - `{StudentID}` is a candidate key.  
  - `{Email}` could also be a candidate key (if emails are unique).  
  - `{StudentID, Email}` is **not** a candidate key (because it’s not minimal).

👉 A table can have multiple candidate keys, but one is chosen as the **Primary Key**.

---

## 🗝️ Composite Key
- A **Composite Key** is a candidate key that consists of **two or more attributes**.
- Used when a single attribute is not sufficient to uniquely identify a tuple.
- Example:  
  In `COURSE_ENROLLMENT(StudentID, CourseID, Semester)`:  
  - Neither `StudentID` nor `CourseID` alone is unique.  
  - `{StudentID, CourseID}` together form a **composite key**.

---

## 🗝️ Primary Key vs Alternate Key
- **Primary Key**: The chosen candidate key to uniquely identify tuples.  
- **Alternate Key**: Other candidate keys not chosen as the primary key.  
- Example:  
  In `STUDENT(StudentID, Email)`:  
  - Primary Key = `StudentID`  
  - Alternate Key = `Email`

---

## 🗝️ Keys as Constraints
Keys are not just identifiers; they act as **constraints** to enforce rules in the database:
- **Primary Key Constraint**: Ensures uniqueness and non-null values.
- **Unique Constraint**: Ensures uniqueness but allows null values.
- **Foreign Key Constraint**: Ensures referential integrity between tables.
- **Composite Key Constraint**: Ensures uniqueness across multiple attributes.

👉 These constraints prevent **duplicate records**, maintain **consistency**, and enforce **relationships**.

---

## 📊 Summary Table

| Key Type        | Definition | Example |
|-----------------|------------|---------|
| Super Key       | Any set of attributes that uniquely identifies a tuple | {StudentID, Email} |
| Candidate Key   | Minimal super key | {StudentID}, {Email} |
| Composite Key   | Candidate key with multiple attributes | {StudentID, CourseID} |
| Primary Key     | Chosen candidate key | StudentID |
| Alternate Key   | Candidate key not chosen as primary | Email |
| Keys as Constraints | Rules enforcing uniqueness & integrity | Primary Key, Foreign Key |

---

✅ In short:  
- **Super Key** = all possible identifiers.  
- **Candidate Key** = minimal identifiers.  
- **Composite Key** = multi-attribute identifier.  
- **Primary Key** = chosen candidate key.  
- **Constraints** = rules applied to enforce uniqueness and relationships.
