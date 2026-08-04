# MODULE 2 (Second Half): Normalization

---

## Table of Contents

1. [Introduction to Normalization](#1-introduction-to-normalization)
2. [First Normal Form (1NF)](#2-first-normal-form-1nf)
3. [Second Normal Form (2NF)](#3-second-normal-form-2nf)
4. [Third Normal Form (3NF)](#4-third-normal-form-3nf)
5. [Boyce-Codd Normal Form (BCNF)](#5-boyce-codd-normal-form-bcnf)
6. [Multi-valued Dependencies & Fourth Normal Form (4NF)](#6-multi-valued-dependencies--fourth-normal-form-4nf)
7. [Join Dependencies & Fifth Normal Form (5NF)](#7-join-dependencies--fifth-normal-form-5nf)
8. [Decomposition Properties](#8-decomposition-properties)
9. [Denormalization](#9-denormalization)
10. [Summary Table](#10-summary-table)

---

## 1. Introduction to Normalization

### Definition
> **Normalization**: The process of decomposing unsatisfactory "bad" relations by breaking up their attributes into smaller relations to minimize redundancy and anomalies.

### Goals
| Goal | Description |
|------|-------------|
| **Minimize Redundancy** | Avoid storing the same data multiple times |
| **Eliminate Anomalies** | Prevent insertion, deletion, and update anomalies |
| **Ensure Data Integrity** | Maintain consistency across the database |

### Two Critical Properties of Decomposition
| Property | Description | Priority |
|----------|-------------|----------|
| **Lossless Join** | Natural join of decomposed relations yields exactly the original relation | **Cannot be sacrificed** |
| **Dependency Preservation** | All functional dependencies can be checked on individual decomposed relations | Less stringent; may be sacrificed |

### Normal Forms Hierarchy
```
1NF ⊆ 2NF ⊆ 3NF ⊆ BCNF ⊆ 4NF ⊆ 5NF
```
> Each normal form is strictly stronger than the previous one.

---

## 2. First Normal Form (1NF)

### Definition
> A relation is in **1NF** if the domain of every attribute contains **only atomic (indivisible) values**, and every attribute contains **only a single value** from that domain.

### What 1NF Disallows
| Disallowed | Example |
|------------|---------|
| **Composite attributes** | `Address` as a single string instead of `(Street, City, State)` |
| **Multi-valued attributes** | A single employee having multiple phone numbers in one column |
| **Nested relations** | Repeating groups or arrays within a tuple |

### Example — Normalization to 1NF

**Not in 1NF:**
| EmpID | Name | PhoneNumbers |
|-------|------|--------------|
| 1 | John | 555-0100, 555-0200 |

**In 1NF:**
| EmpID | Name | PhoneNumber |
|-------|------|-------------|
| 1 | John | 555-0100 |
| 1 | John | 555-0200 |

> **Note**: 1NF is now considered part of the formal definition of a relation in the basic relational model.

---

## 3. Second Normal Form (2NF)

### Prerequisites
- Relation must be in **1NF**

### Key Definitions
| Term | Definition |
|------|------------|
| **Prime Attribute** | Attribute that is a member of **any candidate key** |
| **Non-prime Attribute** | Attribute that is **not** part of any candidate key |
| **Full Functional Dependency** | X → Y where removal of **any** attribute from X means the FD no longer holds |

### Definition
> A relation schema R is in **2NF** if every **non-prime attribute** is **fully functionally dependent** on **every key** of R (not just the primary key).

### Partial Dependency (Violates 2NF)
> **Partial Dependency**: A non-prime attribute depends on **only a part** of a composite key.

### Example — Normalizing to 2NF

**Relation:** `EMP_PROJ(Emp#, Proj#, Ename, Pname, No_hours)`
- Key: `{Emp#, Proj#}`
- FDs: `Emp# → Ename`, `Proj# → Pname`

**Problem:** `Ename` depends only on `Emp#` (part of key). This is a **partial dependency**.

**Decomposition into 2NF:**
```
EMPLOYEE(Emp#, Ename)
PROJECT(Proj#, Pname)
WORKS_ON(Emp#, Proj#, No_hours)
```

---

## 4. Third Normal Form (3NF)

### Prerequisites
- Relation must be in **2NF**

### Key Definitions
| Term | Definition |
|------|------------|
| **Transitive Dependency** | X → Z that can be derived from X → Y and Y → Z |
| **Trivial FD** | X → Y where Y ⊆ X |

### Definition (General — Multiple Keys)
> A relation schema R is in **3NF** if whenever a **nontrivial FD** X → A holds in R, then either:
> - **(a)** X is a **superkey** of R, OR
> - **(b)** A is a **prime attribute** of R

### Transitive Dependency (Violates 3NF)
> **Problem Case**: X → Y and Y → Z, where **Y is NOT a candidate key**.

### Example — Normalizing to 3NF

**Relation:** `EMP_DEPT(SSN, Ename, Dnumber, Dname, Dmgr_ssn)`
- FDs: `SSN → Dnumber`, `Dnumber → Dname`, `Dnumber → Dmgr_ssn`

**Problem:** `SSN → Dnumber → Dname` is a **transitive dependency** (Dnumber is not a candidate key).

**Decomposition into 3NF:**
```
EMPLOYEE(SSN, Ename, Dnumber)
DEPARTMENT(Dnumber, Dname, Dmgr_ssn)
```

> **Exception**: If Y is a candidate key, transitive dependency is **not a problem**.
> Example: `EMP(SSN, Emp#, Salary)` where `SSN → Emp# → Salary` and `Emp#` is a candidate key.

---

## 5. Boyce-Codd Normal Form (BCNF)

### Prerequisites
- Relation must be in **3NF**

### Definition
> A relation schema R is in **BCNF** if whenever a **nontrivial FD** X → A holds in R, then **X is a superkey** of R.

> BCNF is stricter than 3NF — it **removes condition (b)** from 3NF (no exceptions for prime attributes).

### Key Difference: 3NF vs BCNF

| Condition | 3NF | BCNF |
|-----------|-----|------|
| X is superkey | ✓ Allowed | ✓ Allowed |
| A is prime attribute | ✓ Allowed | ✗ **Not allowed** |
| X → A where X is not superkey and A is non-prime | ✗ Violation | ✗ Violation |

### Example — 3NF but NOT BCNF

**Relation:** `TEACH(Student, Course, Instructor)`
- FD1: `{Student, Course} → Instructor`
- FD2: `Instructor → Course`
- Key: `{Student, Course}`

**Analysis:**
- No non-prime attributes (all are part of the key or determined by key)
- Therefore, it is in **3NF**
- But `Instructor → Course` violates BCNF because `Instructor` is **not a superkey**

**Decomposition into BCNF:**
```
STUDENT_INSTRUCTOR(Student, Instructor)
INSTRUCTOR_COURSE(Instructor, Course)
```

> **Trade-off**: Achieving BCNF may require sacrificing **dependency preservation**. In the example above, FD1 is lost (cannot be checked without joining).

---

## 6. Multi-valued Dependencies & Fourth Normal Form (4NF)

### Multi-valued Dependency (MVD)

> **MVD**: A dependency between attributes where for each value of A, there is a well-defined set of values for B and a well-defined set of values for C, but these sets are **independent** of each other.

**Notation:** `A →→ B` (read as "A multi-determines B")

#### Trivial vs Nontrivial MVD
| Type | Condition |
|------|-----------|
| **Trivial MVD** | B ⊆ A, OR A ∪ B = R |
| **Nontrivial MVD** | Neither of the above conditions holds |

### Definition of 4NF
> A relation schema R is in **4NF** with respect to a set of dependencies F if for every **nontrivial MVD** X →→ Y in F⁺, **X is a superkey** for R.

> 4NF = BCNF + no nontrivial MVDs (except those where the determinant is a superkey)

### Example — Decomposing to 4NF

**Relation:** `EMP(ENAME, PNAME, DNAME)`
- MVD1: `ENAME →→ PNAME` (employee works on multiple projects)
- MVD2: `ENAME →→ DNAME` (employee has multiple dependents)

**Problem:** Projects and dependents are **independent** of each other. This causes redundancy.

**Decomposition into 4NF:**
```
EMP_PROJECTS(ENAME, PNAME)
EMP_DEPENDENTS(ENAME, DNAME)
```

---

## 7. Join Dependencies & Fifth Normal Form (5NF)

### Join Dependency (JD)

> A relation R satisfies a **join dependency** if every legal value of R is equal to the **join of its projections** on subsets A, B, ..., Z.

**Notation:** `JD(R1, R2, ..., Rn)`

### Definition of 5NF (PJNF)
> A relation schema R is in **5NF** (or **Project-Join Normal Form**) with respect to a set of dependencies F if for every **nontrivial join dependency** `JD(R1, R2, ..., Rn)` in F⁺, **every Ri is a superkey** of R.

### Example — 4NF but not 5NF

**Relation:** `SUPPLY(Sname, Part_name, Proj_name)`

**Constraint (Join Dependency):**
> Whenever supplier s supplies part p, and project j uses part p, and supplier s supplies at least one part to project j, then supplier s also supplies part p to project j.

**Decomposition into 5NF:**
```
R1(Sname, Part_name)
R2(Sname, Proj_name)
R3(Part_name, Proj_name)
```

---

## 8. Decomposition Properties

### A. Lossless (Non-additive) Join Decomposition

> A decomposition of R into X and Y is **lossless-join** with respect to FDs F if for every instance r satisfying F:
> ```
> π_X(r) ⋈ π_Y(r) = r
> ```

#### Test for Lossless Binary Decomposition
The decomposition of R into X and Y is **lossless** if and only if F⁺ contains:
- `X ∩ Y → X`, OR
- `X ∩ Y → Y`

> **Useful Result**: If W → Z holds over R and W ∩ Z = ∅, then decomposition into `(R - Z)` and `(W ∪ Z)` is lossless.

### B. Dependency Preserving Decomposition

> A decomposition of R into X and Y is **dependency preserving** if:
> ```
> (F_X ∪ F_Y)⁺ = F⁺
> ```
> where F_X is the projection of F on X.

**Important**: It is essential that all decompositions be **lossless**. Dependency preservation is less stringent and may be sacrificed to achieve BCNF.

---

## 9. Denormalization

### Definition
> **Denormalization**: The process of storing the join of higher normal form relations as a base relation in a lower normal form to improve query performance.

### When to Use
- When **read performance** is critical
- When joins are too expensive
- When data is **read-heavy** and **rarely updated**

### Pros and Cons

| Pros | Cons |
|------|------|
| Faster data retrieval (fewer joins) | Updates and inserts are more expensive |
| Simpler queries | Update/insert code is harder to write |
| Fewer tables to look at | Data may become inconsistent |
| | Requires more storage |
| | Need to maintain redundant data |

> **Note**: Denormalization is an **optimization technique** applied **after** normalization. Most systems use a mix of normalized and denormalized schemas.

---

## 10. Summary Table

| Normal Form | Based On | Key Condition | Violation |
|-------------|----------|---------------|-----------|
| **1NF** | Atomic values | All attributes atomic; no repeating groups | Multi-valued or composite attributes |
| **2NF** | 1NF + Full dependency | No partial dependency of non-prime attributes on any key | Non-prime attribute depends on part of key |
| **3NF** | 2NF + No transitive dependency | For every nontrivial FD X → A: X is superkey OR A is prime | Transitive dependency through non-key |
| **BCNF** | 3NF + Stricter condition | For every nontrivial FD X → A: X **must be** superkey | X → A where X is not superkey (even if A is prime) |
| **4NF** | BCNF + MVDs | Every nontrivial MVD X →→ Y: X is superkey | Nontrivial MVD where determinant is not superkey |
| **5NF** | 4NF + JDs | Every nontrivial JD: all projected relations are superkeys | Join dependency that creates spurious tuples |

### Normal Forms Informally
| Normal Form | Mnemonic |
|-------------|----------|
| 1NF | All attributes depend on the **key** |
| 2NF | All attributes depend on the **whole key** |
| 3NF | All attributes depend on **nothing but the key** |
| BCNF | Every determinant is a **superkey** |

---

## Textbook References

| Topic | Elmasri & Navathe |
|-------|-------------------|
| Normalization Concepts | Chapter 10, Section 10.3 |
| 1NF, 2NF, 3NF | Chapter 10, Sections 10.3.1–10.3.6 |
| BCNF | Chapter 10, Section 10.5 |
| 4NF & MVDs | Chapter 11, Section 11.3 |
| 5NF & Join Dependencies | Chapter 11, Section 11.4 |
| Denormalization | Chapter 10, Section 10.3.2 |