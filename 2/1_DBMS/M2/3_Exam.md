## D1(Nomenclature)
### 📌 Row
- **Tuple**  
- **Record**  
- **Entity instance** 

---

### 📌 Column
- **Attribute**  
- **Field**  
- **Property**  

---

### 📌 Table
- **Relation** (formal DBMS term, especially in relational algebra)  
- **Entity set**  

### Note
    - Entity Type = Definition (schema)
    - Entity Set = Collection of instances following that schema


## D2(Data models and types, ER Vs Logical design,aka data model mapping, conceptual design is given as QNS)
### 🧩 Data Model Mapping vs. ER Diagrams
- **ER Diagrams**  
  - Visual representation of entities (tables), attributes (columns), and relationships (links between tables).  
  - Used to design or understand the logical structure of a database.  

- **Data Model Mapping**  
  - Defines how fields in one data model (say, a CRM system) correspond to fields in another (say, a data warehouse).  
  - Goes beyond just structure — it includes **transformations, conversions, and business rules**.  

---

### 🔗 How They Relate
Think of ER diagrams as the **blueprint of a single system’s data model**, while data model mapping is the **bridge between two different blueprints**.  

Example:  
- ER Diagram shows `Customer` entity with attributes: `CustomerID`, `Name`, `Email`.  
- Another system has `Client` entity with attributes: `ID`, `FullName`, `ContactEmail`.  
- **Mapping** defines:  
  - `CustomerID → ID`  
  - `Name → FullName`  
  - `Email → ContactEmail`  
## D3(Conceptual Vs Logical(ER) Vs Physical design)
### 🧩 What Conceptual Design Really Is
- **Purpose:** Capture the *essence* of the data requirements in a way that’s easy for both technical and non-technical stakeholders to understand.  
- **Representation:** Often expressed in **Entity–Relationship (ER) diagrams** or in plain language descriptions of entities and their relationships.  
- **Focus:**  
  - Entities (things of interest, like *Customer*, *Order*, *Product*)  
  - Relationships (how those entities connect, like *Customer places Order*)  
  - Attributes (basic properties, like *Customer Name*, *Order Date*)  

---

### 🏗️ Levels of Data Modeling
| Level | What It Describes | Audience |
|-------|-------------------|----------|
| **Conceptual** | High-level entities and relationships, often in plain language or simple diagrams | Business stakeholders, analysts |
| **Logical** | More detailed structure: attributes, primary keys, foreign keys, normalization | Database designers, system architects |
| **Physical** | Actual implementation: tables, columns, indexes, constraints in a specific DBMS | Developers, DBAs |

---

### 📌 Example
- **Conceptual (English-like):**  
  - A *Customer* places an *Order*.  
  - An *Order* contains one or more *Products*.  

- **Logical (ER diagram):**  
  - Entities: Customer, Order, Product  
  - Relationships: Customer → Order (1:N), Order → Product (M:N)  

- **Physical (SQL schema):**  
  ```sql
  CREATE TABLE Customer (
    CustomerID INT PRIMARY KEY,
    Name VARCHAR(100)
  );
  CREATE TABLE Order (
    OrderID INT PRIMARY KEY,
    CustomerID INT FOREIGN KEY REFERENCES Customer(CustomerID),
    OrderDate DATE
  );
  CREATE TABLE Product (
    ProductID INT PRIMARY KEY,
    Name VARCHAR(100),
    Price DECIMAL(10,2)
  );
  CREATE TABLE OrderProduct (
    OrderID INT FOREIGN KEY REFERENCES Order(OrderID),
    ProductID INT FOREIGN KEY REFERENCES Product(ProductID)
  );
  ```

## D4(ER Diagram Steps & About relationships, ER To relational schema mapping look into Notes)
### 🏗️ Steps in Database Design
1. **Conceptual Design**  
   - High-level view of the data requirements.  
   - Focuses on *what* data needs to be stored, not *how*.  
   - Usually represented with an **ER diagram** (entities, attributes, relationships).

2. **Logical Design (ER/EER)**  
   - More detailed, structured representation.  
   - Defines entities, attributes, primary keys, and relationships.  
   - Enhanced ER (EER) adds concepts like specialization/generalization, categories, etc.

3. **Physical Design**  
   - Conversion of ER/EER into actual database tables.  
   - Includes data types, constraints, indexes, and storage details.  
   - Optimized for performance and implementation in a specific DBMS.

---

### 📌 Entities and Relationships(These are used to define some connection between the entity types)
- **Entity Types**: Defined separately first (e.g., *Student*, *Course*, *Professor*).  
- **Relationships**: Then connect them (e.g., *Student enrolls in Course*).  
- After defining, you may refine or modify based on requirements.

---

### 🔑 Do Relationships Become Tables?
This depends on the **cardinality** and **type of relationship**:

| Relationship Type | Implementation in Physical DB |
|-------------------|--------------------------------|
| **1:1** | Often merged into one table, or FK in one table referencing the other. |
| **1:N** | FK in the "many" side table referencing the "one" side. |
| **M:N** | Must be converted into a **separate table** (called a *junction/bridge table*) with FKs referencing both entities. |

👉 So, relationships **can become tables** (especially M:N), but in other cases they are represented as foreign keys.

---

### ⚡ Example
- Entities: `Student`, `Course`  
- Relationship: `Enrolls` (M:N)  
- Physical design:  
  - `Student(student_id, name, …)`  
  - `Course(course_id, title, …)`  
  - `Enrollment(student_id FK, course_id FK, date_enrolled, …)` ← relationship becomes a table here.
## D5(Schema Vs Data Model)
    - Data model = grammar of a language
    - Schema = a sentence written using that grammar
## D6(Keys)
    - Super key is set of keys that can identify entire Relation, but candidate key is minimal super key.
    - A developer must choose one candidate key as primary key


## D7(EER Look into notes, Even Design guidelines - Anomalies, Decomposition properties & verifying whether fd is preserved after decomposition in notes. Even close of X & FDs, Minimal cover, extraneous Attr, Candidate key in notes, Normalization in notes)

## D8
    Full FD(A attribute depends on entire Candidate key)
    Partial
## D9(Database models means types but data model means conceptual, logical and physical level design, 3 tier arch mean physical level logical level and view level) --> Data model means framework or design but database model is implementationd! 

## D10
### 📘 Relational Model
- **Definition**: A theoretical framework proposed by E.F. Codd.  
- **Purpose**: Describes how data should be structured and manipulated using relations (tables).  
- **Key Idea**: Data is represented as a set of relations, each relation being a table with rows (tuples) and columns (attributes).  
- **Abstract**: It’s a *conceptual* model, not tied to any specific database implementation.  

---

### 📗 Relational Schema
- **Definition**: A concrete description of how a database is organized under the relational model.  
- **Purpose**: Specifies the structure of relations (tables), including table names, column names, data types, and constraints.  
- **Key Idea**: It’s the *blueprint* of the database.  
- **Practical**: Directly implemented in a DBMS (like SQL).  

---

### ⚖️ Difference at a Glance

| Aspect              | Relational Model | Relational Schema |
|---------------------|------------------|------------------|
| **Nature**          | Abstract theory  | Concrete design   |
| **Focus**           | Concept of relations | Structure of specific tables |
| **Level**           | Conceptual       | Logical/Implementation |
| **Example**         | "Data is stored in relations (tables)" | `Student(ID, Name, Age)` |

---

### 🖼️ Simple Diagram

```
Relational Model (Conceptual)
        |
        v
Relational Schema (Blueprint)
        |
        v
Database (Actual Tables with Data)
```

Or visually:

```
[ Relational Model ]
       ↓
[ Relational Schema ]
       ↓
[ Database Instance ]
```

- **Relational Model** → Theory of relations  
- **Relational Schema** → Structure of tables  
- **Database Instance** → Actual data stored in those tables  
