# MODULE 2 (First Half): ER Model, EER Model, Relational Mapping & Functional Dependencies

---

## Table of Contents

1. [Database Design Process](#1-database-design-process)
2. [ER Model Concepts](#2-er-model-concepts)
   - [Entities & Attributes](#21-entities--attributes)
   - [Types of Attributes](#22-types-of-attributes)
   - [Entity Types & Key Attributes](#23-entity-types--key-attributes)
   - [Relationships & Relationship Types](#24-relationships--relationship-types)
   - [Roles in Relationships](#25-roles-in-relationships)
   - [Structural Constraints](#26-structural-constraints)
   - [Weak Entity Types](#27-weak-entity-types)
3. [ER Diagram Notation Summary](#3-er-diagram-notation-summary)
4. [Mapping ER Model to Relational Schema](#4-mapping-er-model-to-relational-schema)
5. [Extended ER (EER) Model](#5-extended-eer-model)
   - [Subclasses & Superclasses](#51-subclasses--superclasses)
   - [Specialization & Generalization](#52-specialization--generalization)
   - [Constraints on Specialization/Generalization](#53-constraints-on-specializationgeneralization)
   - [Hierarchies, Lattices & Shared Subclasses](#54-hierarchies-lattices--shared-subclasses)
   - [Aggregation](#55-aggregation)
6. [Mapping EER to Relational Schema](#6-mapping-eer-to-relational-schema)
7. [Guidelines for Relational Schema Design](#7-guidelines-for-relational-schema-design)
8. [Functional Dependencies (FDs)](#8-functional-dependencies-fds)
   - [Definition](#81-definition)
   - [Inference Rules (Armstrong's Axioms)](#82-inference-rules-armstrongs-axioms)
   - [Closure of FDs](#83-closure-of-fds)
   - [Equivalence of Sets of FDs](#84-equivalence-of-sets-of-fds)
   - [Minimal Cover](#85-minimal-cover)

---

## 1. Database Design Process

### Four Main Phases

| Phase | Description | Output |
|-------|-------------|--------|
| **Requirements Collection & Analysis** | Interview users; document data & functional requirements | User requirements specification |
| **Conceptual Design** | Create high-level schema using ER/EER model | Conceptual schema (no implementation details) |
| **Logical Design** | Transform conceptual schema to implementation data model | Database schema in DBMS data model |
| **Physical Design** | Specify storage structures, indexes, access paths | Physical database design |

### Two Main Activities
1. **Database Design** — Focus of this module
2. **Applications Design** — Programs and interfaces (software engineering)

---

## 2. ER Model Concepts

### 2.1 Entities & Attributes

| Term | Definition | Example |
|------|------------|---------|
| **Entity** | Specific object/thing in the mini-world | EMPLOYEE John Smith |
| **Entity Type** | Group of entities with same basic attributes | EMPLOYEE, PROJECT |
| **Entity Set** | Collection of entities of a type stored in DB | All employee records |
| **Attribute** | Property describing an entity | Name, SSN, Address |
| **Value Set/Domain** | Data type of attribute | integer, string, date |

**Example:**
- EMPLOYEE entity: `Name='John Smith'`, `SSN='123456789'`, `Address='731 Fondren, Houston, TX'`, `Gender='M'`, `BirthDate='09-JAN-55'`

---

### 2.2 Types of Attributes

| Classification | Description | Example |
|----------------|-------------|---------|
| **Simple (Atomic)** | Single indivisible value | SSN, Gender |
| **Composite** | Composed of multiple components | Address (Street, City, State, Zip) |
| **Single-valued** | One value per entity | EmployeeID |
| **Multi-valued** | Multiple values per entity | {Color} of CAR, {PreviousDegrees} of STUDENT |
| **Stored** | Directly stored in database | BirthDate |
| **Derived** | Computed from other attributes | Age (from BirthDate) |

**Composite Attribute Example:**
```
Name(FirstName, MiddleName, LastName)
Address(Apt#, House#, Street, City, State, ZipCode, Country)
```

**Nested Multi-valued Attribute:**
```
{PreviousDegrees(College, Year, Degree, Field)}
```

**SQL for Composite Attribute (Oracle):**
```sql
CREATE TYPE AddressType AS OBJECT (
    street VARCHAR2(15),
    city   VARCHAR2(15),
    state  CHAR(2),
    zip    VARCHAR2(5)
);

CREATE TABLE Person (
    id NUMBER,
    first_name VARCHAR2(10),
    last_name  VARCHAR2(10),
    dob        DATE,
    phone      VARCHAR2(12),
    address    AddressType
);
```

---

### 2.3 Entity Types & Key Attributes

| Term | Definition |
|------|------------|
| **Key Attribute** | Attribute with unique value for each entity |
| **Composite Key** | Key composed of multiple attributes |
| **Candidate Key** | Multiple possible keys for an entity type |
| **Primary Key** | Chosen candidate key (underlined in diagrams) |

**Example — CAR with Two Keys:**
- Key 1: `VehicleIdentificationNumber (VIN)`
- Key 2: `VehicleTagNumber (Number, State)` — composite key

---

### 2.4 Relationships & Relationship Types

| Term | Definition |
|------|------------|
| **Relationship** | Association between two or more entities |
| **Relationship Type** | Schema description; name + participating entity types + constraints |
| **Relationship Set** | Current set of relationship instances |
| **Degree** | Number of participating entity types |

**COMPANY Database — Six Relationship Types:**

| Relationship | Participating Entities | Type |
|--------------|------------------------|------|
| **WORKS_FOR** | EMPLOYEE, DEPARTMENT | N:1 |
| **MANAGES** | EMPLOYEE, DEPARTMENT | 1:1 |
| **CONTROLS** | DEPARTMENT, PROJECT | 1:N |
| **WORKS_ON** | EMPLOYEE, PROJECT | M:N |
| **SUPERVISION** | EMPLOYEE, EMPLOYEE | Recursive |
| **DEPENDENTS_OF** | EMPLOYEE, DEPENDENT | 1:N |

**Relationship Attributes:**
- `HoursPerWeek` of **WORKS_ON** — depends on (employee, project) combination
- Mostly used with **M:N** relationships
- In **1:N**, can be transferred to N-side entity

---

### 2.5 Roles in Relationships

> **Recursive Relationship**: Same entity type participates in **distinct roles**

**Example — SUPERVISION:**
- Role 1: **Supervisor** (boss)
- Role 2: **Supervisee** (subordinate/worker)
- Each instance relates **two distinct EMPLOYEE entities**

**In ER Diagram:**
- Display **role names** to distinguish participations
- Label first role as **1**, second as **2**

---

### 2.6 Structural Constraints

#### A. Cardinality Ratio (Maximum Participation)

| Ratio | Description |
|-------|-------------|
| **1:1 (One-to-One)** | One entity in A relates to at most one in B |
| **1:N (One-to-Many)** | One in A relates to many in B |
| **N:1 (Many-to-One)** | Many in A relate to one in B |
| **M:N (Many-to-Many)** | Many in A relate to many in B |

#### B. Participation Constraint (Minimum Cardinality)

| Type | Description | Notation |
|------|-------------|----------|
| **Total** | Every entity MUST participate in at least one relationship | **Double line** |
| **Partial** | Some entities MAY NOT participate | **Single line** |

**Examples:**
- EMPLOYEE in **WORKS_FOR** → **Total** (every employee works for a dept)
- EMPLOYEE in **MANAGES** → **Partial** (not every employee manages)

> **Total participation** = **Existence Dependency**

---

### 2.7 Weak Entity Types

> **Weak Entity**: Entity that does **NOT** have a key attribute of its own.

**Characteristics:**
- Must participate in an **identifying relationship** with an **owner entity**
- Identified by combination of:
  1. **Partial key** (discriminator) of weak entity
  2. **Primary key** of owner entity

**Example — DEPENDENT:**
- **Partial key**: `Dependent_name`
- **Owner**: EMPLOYEE (via **DEPENDENTS_OF**)
- **Primary key of DEPENDENT**: `{ESSN, Dependent_name}`

**ER Diagram Notation:**
- Weak entity: **Double rectangle**
- Partial key: **Dashed underline**
- Identifying relationship: **Double diamond**

**Another Example — PAYMENT:**
- `payment_number` = discriminator
- PK = `(loan_number, payment_number)`

---

## 3. ER Diagram Notation Summary

| Symbol | Meaning |
|--------|---------|
| **Rectangle** | Entity type |
| **Double Rectangle** | Weak entity type |
| **Diamond** | Relationship type |
| **Double Diamond** | Identifying relationship |
| **Oval** | Attribute |
| **Double Oval** | Multi-valued attribute |
| **Dashed Oval** | Derived attribute |
| **Underline** | Key attribute |
| **Dashed Underline** | Partial key (discriminator) |
| **Line** | Connection |
| **Double Line** | Total participation |
| **Single Line** | Partial participation |
| **1, N, M** | Cardinality ratio |

---

## 4. Mapping ER Model to Relational Schema

### Step 1: Mapping Regular (Strong) Entity Types
- Create a **relation R** for each regular entity type E
- Include **all simple attributes** of E
- Choose one **key attribute** as **Primary Key**
- If composite key → all simple attributes form PK

**Example:**
```
EMPLOYEE(SSN, Fname, Minit, Lname, Bdate, Address, Salary, Sex)
DEPARTMENT(Dnumber, Dname)
PROJECT(Pnumber, Pname, Plocation)
```

---

### Step 2: Mapping Weak Entity Types
- Create relation R for weak entity W
- Include **all attributes** of W
- Include **PK of owner entity** as **Foreign Key**
- **PK of R** = FK + partial key of W

**Example:**
```
DEPENDENT(ESSN, Dependent_name, Sex, Bdate, Relationship)
PK: {ESSN, Dependent_name}
FK: ESSN → EMPLOYEE(SSN)
```

---

### Step 3: Mapping Binary 1:1 Relationship Types
**Three approaches:**

| Approach | When to Use |
|----------|-------------|
| **(1) Foreign Key** | Include T's PK in S where S has **total participation** |
| **(2) Merged Relation** | Both participations are **total** |
| **(3) Cross-reference** | Third relation W(T.PK, S.PK) |

**Example — MANAGES:**
- DEPARTMENT has **total** participation
- Include `Mgr_ssn` (EMPLOYEE.PK) in DEPARTMENT relation

---

### Step 4: Mapping Binary 1:N Relationship Types
- Identify relation on **N-side** (S)
- Include **PK of 1-side** (T) as **FK in S**
- Include any **simple attributes** of relationship in S

**Examples:**
| Relationship | FK Added | In Relation |
|--------------|----------|-------------|
| WORKS_FOR | DNO (from DEPARTMENT) | EMPLOYEE |
| CONTROLS | DNUM (from DEPARTMENT) | PROJECT |
| SUPERVISION | SUPERSSN (from EMPLOYEE) | EMPLOYEE |

---

### Step 5: Mapping Binary M:N Relationship Types
- Create **new relation S** for the relationship
- Include **PKs of both participating entities** as FKs
- Their **combination = PK of S**
- Include any **simple attributes** of relationship

**Example — WORKS_ON:**
```
WORKS_ON(ESSN, PNO, Hours)
PK: {ESSN, PNO}
FK: ESSN → EMPLOYEE(SSN)
FK: PNO → PROJECT(Pnumber)
```

---

### Step 6: Mapping Multi-valued Attributes
- Create **new relation R**
- Include attribute corresponding to A
- Include **PK of entity type** as **FK**
- **PK of R** = combination of A + K

**Example — DEPT_LOCATIONS:**
```
DEPT_LOCATIONS(DNUMBER, DLOCATION)
PK: {DNUMBER, DLOCATION}
FK: DNUMBER → DEPARTMENT(Dnumber)
```

---

### Step 7: Mapping N-ary Relationship Types (n > 2)
- Create **new relation S**
- Include **PKs of ALL participating entities** as FKs
- Include any **simple attributes**
- **PK** = combination of all FKs

**Example — SUPPLY (ternary):**
```
SUPPLY(SNAME, PARTNUM, JOBNAME, Quantity)
PK: {SNAME, PARTNUM, JOBNAME}
FKs: SNAME → Supplier, PARTNUM → Part, JOBNAME → Job
```

---

### ER-to-Relational Mapping Summary Table

| ER Construct | Relational Mapping |
|--------------|-------------------|
| Entity type | Entity relation |
| 1:1 or 1:N relationship | Foreign key (or relationship relation) |
| M:N relationship | Relationship relation + two FKs |
| n-ary relationship | Relationship relation + n FKs |
| Simple attribute | Attribute |
| Composite attribute | Set of simple component attributes |
| Multi-valued attribute | Relation + FK |
| Value set | Domain |
| Key attribute | Primary/secondary key |

---

## 5. Extended ER (EER) Model

### 5.1 Subclasses & Superclasses

> **Subclass**: Meaningful subgrouping of entities within an entity type.

**Example — EMPLOYEE subclasses:**
- `SECRETARY`, `ENGINEER`, `TECHNICIAN` — based on Job
- `MANAGER` — employees who are managers
- `SALARIED_EMPLOYEE`, `HOURLY_EMPLOYEE` — based on pay method

**IS-A Relationships:**
- `SECRETARY IS-A EMPLOYEE`
- `TECHNICIAN IS-A EMPLOYEE`

**Key Properties:**
- Subclass member = **same real-world entity** as superclass member (distinct role)
- Entity **cannot exist** merely as subclass member; must be superclass member too
- Superclass member can **optionally** belong to any number of subclasses

**Examples:**
- Salaried engineer → belongs to `ENGINEER` + `SALARIED_EMPLOYEE`
- Engineering manager → belongs to `MANAGER` + `ENGINEER` + `SALARIED_EMPLOYEE`
- **Not necessary** that every superclass entity be in a subclass

---

### 5.2 Specialization & Generalization

| Process | Direction | Description |
|---------|-----------|-------------|
| **Specialization** | Top-Down | Define subclasses from superclass based on distinguishing characteristics |
| **Generalization** | Bottom-Up | Combine classes with common features into superclass |

**Specialization Example:**
```
EMPLOYEE → {SECRETARY, ENGINEER, TECHNICIAN}  [based on JobType]
EMPLOYEE → {SALARIED_EMPLOYEE, HOURLY_EMPLOYEE}  [based on PayMethod]
```

**Generalization Example:**
```
CAR + TRUCK → VEHICLE
```
- CAR and TRUCK become subclasses of VEHICLE

**Specific/Local Attributes:**
- Attributes of subclass only
- Example: `TypingSpeed` of SECRETARY

**Specific Relationships:**
- Subclass can participate in specific relationships
- Example: `BELONGS_TO` of HOURLY_EMPLOYEE

---

### 5.3 Constraints on Specialization/Generalization

#### A. Membership Constraints

| Type | Description | Example |
|------|-------------|---------|
| **Predicate-defined/Condition-defined** | Condition determines subclass membership | `JobType = 'Secretary'` |
| **Attribute-defined** | All subclasses based on same attribute | `JobType` defines {SECRETARY, TECHNICIAN, ENGINEER} |
| **User-defined** | User manually assigns membership | No automatic condition |

#### B. Disjointness Constraint

| Type | Symbol | Description |
|------|--------|-------------|
| **Disjoint (d)** | **d** | Entity in **at most one** subclass |
| **Overlapping (o)** | **o** | Entity in **more than one** subclass |

#### C. Completeness Constraint

| Type | Notation | Description |
|------|----------|-------------|
| **Total** | **Double line** | Every superclass entity **must** be in some subclass |
| **Partial** | **Single line** | Entity **may not** belong to any subclass |

#### Four Combinations:
1. **Disjoint, Total**
2. **Disjoint, Partial**
3. **Overlapping, Total**
4. **Overlapping, Partial**

> **Note**: Generalization is usually **total** (superclass derived from subclasses).

---

### 5.4 Hierarchies, Lattices & Shared Subclasses

| Structure | Description | Inheritance |
|-----------|-------------|-------------|
| **Hierarchy** | Tree structure; each subclass has **one superclass** (single inheritance) | From direct superclass only |
| **Lattice** | Subclass can have **multiple superclasses** (multiple inheritance) | From **all predecessor superclasses** |

**Shared Subclass:**
- Subclass of **several classes**
- Example: `STUDENT_ASSISTANT` — subclass of both `STUDENT` and `EMPLOYEE`
- All superclasses must have **same key attribute**

**Processes:**
| Process | Approach | Called |
|---------|----------|--------|
| Specialization | Top-down refinement | Conceptual refinement |
| Generalization | Bottom-up synthesis | Conceptual synthesis |
| **Practice** | Combination of both | — |

---

### 5.5 Aggregation

> **Problem**: ER diagram cannot represent relationship between an **entity** and a **relationship**.

> **Solution**: **Aggregation** — treat relationship + its entities as a **higher-level entity**.

**Example:**
- EMPLOYEE **WORKS_FOR** PROJECT (relationship)
- This work requires **MACHINERY** (entity)
- Need **REQUIRE** relationship between WORKS_FOR and MACHINERY

**Aggregation:**
- Aggregate `EMPLOYEE + PROJECT + WORKS_FOR` into single entity
- Create `REQUIRE` relationship between aggregated entity and MACHINERY

---

## 6. Mapping EER to Relational Schema

### Step 8: Four Options for Mapping Specialization/Generalization

Given: Superclass C with key k, attributes {a₁...aₙ}; subclasses {S₁...Sₘ}

---

#### **Option 8A: Multiple Relations — Superclass + Subclasses**

```
L(k, a₁, ..., aₙ)        PK = k          ← Superclass
L₁(k, s₁₁, ..., s₁ₖ)    PK = k          ← Subclass 1
L₂(k, s₂₁, ..., s₂ₖ)    PK = k          ← Subclass 2
...
```

- **Works for**: Any specialization (total/partial, disjoint/overlapping)
- Each subclass relation has **PK = superclass key** (also FK)

---

#### **Option 8B: Multiple Relations — Subclasses Only**

```
L₁(k, a₁, ..., aₙ, s₁₁, ..., s₁ₖ)    PK = k    ← Subclass 1 (includes all attributes)
L₂(k, a₁, ..., aₙ, s₂₁, ..., s₂ₖ)    PK = k    ← Subclass 2
...
```

- **Works for**: **Total specialization only**
- Each subclass relation includes **all superclass attributes**

---

#### **Option 8C: Single Relation — One Type Attribute**

```
L(k, a₁, ..., aₙ, S₁_attrs, ..., Sₘ_attrs, t)
PK = k
```

- **t** = type/discriminating attribute (indicates which subclass)
- **NULL values** for attributes not applicable to subclass

---

#### **Option 8D: Single Relation — Multiple Boolean Type Attributes**

```
L(k, a₁, ..., aₙ, S₁_attrs, ..., Sₘ_attrs, t₁, t₂, ..., tₘ)
PK = k
```

- Each **tᵢ** = Boolean flag indicating membership in subclass Sᵢ
- For **overlapping** specializations

---

### Step 9: Mapping Union Types (Categories)

- Category = subclass of **union** of multiple superclasses
- Similar mapping options apply

---

### Mapping Options Comparison

| Option | # Relations | When to Use | Pros | Cons |
|--------|-------------|-------------|------|------|
| **8A** | 1 + m | Any specialization | Clean separation; no NULLs | More joins needed |
| **8B** | m | Total specialization only | No superclass table | Redundancy if overlapping |
| **8C** | 1 | Disjoint; few subclass-specific attrs | Simple; no joins | Many NULLs |
| **8D** | 1 | Overlapping | Handles overlapping | Many NULLs; complex |

---

## 7. Guidelines for Relational Schema Design

### Four Informal Guidelines

| Guideline | Principle |
|-----------|-----------|
| **Guideline 1** | Each tuple = one entity/relationship instance. Don't mix attributes of different entities. |
| **Guideline 2** | Avoid insertion, deletion, update anomalies. Note any anomalies if unavoidable. |
| **Guideline 3** | Minimize NULL values. Place frequently NULL attributes in separate relations. |
| **Guideline 4** | Ensure **lossless join**. No spurious tuples from natural joins. |

### Update Anomalies Example

**Bad Design:** `EMP_PROJ(Emp#, Proj#, Ename, Pname, No_hours)`

| Anomaly | Problem |
|---------|---------|
| **Modification** | Change P1's name → update 100 employee records |
| **Insertion** | Can't insert project without employee; can't insert employee without project |
| **Deletion** | Delete project → delete all its employees; delete sole employee → delete project |

---

## 8. Functional Dependencies (FDs)

### 8.1 Definition

> **X → Y**: X **functionally determines** Y. The value of X determines a unique value for Y.

**Formal Definition:**
For any two tuples t₁, t₂ in any relation instance r(R):
```
If t₁[X] = t₂[X], then t₁[Y] = t₂[Y]
```

**Examples:**
| FD | Meaning |
|----|---------|
| `SSN → ENAME` | SSN determines employee name |
| `PNUMBER → {PNAME, PLOCATION}` | Project number determines name & location |
| `{SSN, PNUMBER} → HOURS` | Employee+Project determines hours worked |

**Key Property:**
If K is a key of R, then **K → all attributes in R**

---

### 8.2 Inference Rules (Armstrong's Axioms)

| Rule | Name | Statement |
|------|------|-----------|
| **IR1** | **Reflexive** | If Y ⊆ X, then X → Y |
| **IR2** | **Augmentation** | If X → Y, then XZ → YZ |
| **IR3** | **Transitive** | If X → Y and Y → Z, then X → Z |

**Additional Useful Rules (derived from IR1-IR3):**

| Rule | Statement |
|------|-----------|
| **Decomposition** | If X → YZ, then X → Y and X → Z |
| **Union** | If X → Y and X → Z, then X → YZ |
| **Pseudotransitivity** | If X → Y and WY → Z, then WX → Z |

> IR1, IR2, IR3 are **sound and complete** — all other rules can be deduced from these.

---

### 8.3 Closure of FDs

| Term | Definition |
|------|------------|
| **F⁺ (Closure of F)** | Set of **all FDs** that can be inferred from F |
| **X⁺ (Closure of X)** | Set of **all attributes** functionally determined by X |

**Computing X⁺:** Repeatedly apply IR1, IR2, IR3 using FDs in F

---

### 8.4 Equivalence of Sets of FDs

**F and G are equivalent if:**
1. Every FD in F can be inferred from G
2. Every FD in G can be inferred from F

**i.e., F⁺ = G⁺**

| Term | Definition |
|------|------------|
| **F covers G** | Every FD in G can be inferred from F (G⁺ ⊆ F⁺) |
| **Equivalent** | F covers G AND G covers F |

---

### 8.5 Minimal Cover (Minimal Set of FDs)

A set F is **minimal** if:

| Condition | Description |
|-----------|-------------|
| **1** | Every dependency has a **single attribute** on RHS |
| **2** | Cannot **remove any dependency** from F and still be equivalent |
| **3** | Cannot **replace X → A** with **Y → A** (where Y ⊂ X) and still be equivalent |

**Properties:**
- Every set of FDs has an **equivalent minimal set**
- **Multiple** equivalent minimal sets may exist
- No simple algorithm; used in synthesis approach

---

## Summary Table: Quick Reference

| Concept | Key Point |
|---------|-----------|
| **Entity** | Object in mini-world |
| **Attribute types** | Simple, Composite, Single/Multi-valued, Stored/Derived |
| **Key** | Unique identifier; underlined in ER |
| **Weak entity** | No own key; double rectangle; partial key dashed |
| **Total participation** | Double line; existence dependency |
| **1:1 mapping** | FK approach, merge, or cross-reference |
| **1:N mapping** | FK on N-side |
| **M:N mapping** | New relation with both PKs |
| **Multi-valued attr** | New relation with PK + attr |
| **Specialization** | Top-down; define subclasses |
| **Generalization** | Bottom-up; combine into superclass |
| **Disjoint** | Entity in at most one subclass (d) |
| **Overlapping** | Entity in multiple subclasses (o) |
| **Total** | Every superclass entity in some subclass |
| **Partial** | Some superclass entities not in any subclass |
| **Aggregation** | Relationship + entities → higher-level entity |
| **8A mapping** | Superclass + subclass relations |
| **8B mapping** | Subclass relations only (total only) |
| **8C mapping** | Single relation + type attribute |
| **8D mapping** | Single relation + Boolean flags |
| **FD** | X → Y: X determines Y |
| **Armstrong's Axioms** | Reflexive, Augmentation, Transitive |
| **Minimal cover** | Single RHS, irreducible, no extraneous attributes |

---

## Textbook References

| Topic | Elmasri & Navathe |
|-------|-------------------|
| ER Model | Chapter 3 |
| EER Model | Chapter 4 |
| ER-to-Relational Mapping | Chapter 7, Section 7.1 |
| EER-to-Relational Mapping | Chapter 7, Section 7.2 |
| Functional Dependencies | Chapter 10, Section 10.2 |
| Armstrong's Axioms | Chapter 10, Section 10.2.2 |
| Minimal Cover | Chapter 10, Section 10.2.4 |
