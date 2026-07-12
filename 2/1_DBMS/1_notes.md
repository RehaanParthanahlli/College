# Module 1/2: Introduction to Database Systems

---

## Table of Contents

1. [Basic Definitions](#1-basic-definitions)
   - [Data](#11-data)
   - [Information](#12-information)
   - [Database](#13-database)
   - [Properties of Database](#14-properties-of-database)
2. [Database Management System (DBMS)](#2-database-management-system-dbms)
   - [DBMS Functionalities](#21-dbms-functionalities)
   - [Popular DBMS Software](#22-popular-dbms-software)
3. [Need for Database Systems](#3-need-for-database-systems)
   - [University Database Example](#31-university-database-example)
   - [Problems with File Systems](#32-problems-with-file-systems)
   - [Data Retrieval](#33-data-retrieval)
   - [Data Redundancy](#34-data-redundancy)
   - [Data Integrity](#35-data-integrity)
   - [Data Security](#36-data-security)
   - [Data Indexing](#37-data-indexing)
4. [Characteristics of Database Approach](#4-characteristics-of-database-approach)
   - [Self-Describing Nature](#41-self-describing-nature-of-a-database-system)
   - [Insulation Between Programs and Data](#42-insulation-between-programs-and-data)
   - [Data Abstraction](#43-data-abstraction)
   - [Support of Multiple Views](#44-support-of-multiple-views-of-the-data)
   - [Sharing of Data and Multi-User Transaction Processing](#45-sharing-of-data-and-multi-user-transaction-processing)
5. [Advantages of Using the DBMS Approach](#5-advantages-of-using-the-dbms-approach)
6. [Database Applications Examples](#6-database-applications-examples)
7. [Actors on the Database Management Scene](#7-actors-on-the-database-management-scene)
   - [Actors on the Screen](#71-actors-on-the-screen)
   - [Database Administrators (DBA)](#72-database-administrators-dba)
   - [Database Designers](#73-database-designers)
   - [End Users](#74-end-users)
   - [System Analysts and Application Programmers](#75-system-analysts-and-application-programmers)
   - [Workers Behind the Screen](#76-workers-behind-the-screen)
8. [Types of DBMS](#8-types-of-dbms)
   - [Classification of DBMS based on Data Model](#81-classification-of-dbms-based-on-data-model)
   - [Hierarchical DBMS](#82-hierarchical-dbms)
   - [Network DBMS](#83-network-dbms)
   - [Relational DBMS](#84-relational-dbms)
   - [Object-Oriented DBMS](#85-object-oriented-dbms)
9. [Categories of Data Models](#9-categories-of-data-models)
   - [High-Level or Conceptual Data Models](#91-high-level-or-conceptual-data-models)
   - [Representational (or Implementation) Data Models](#92-representational-or-implementation-data-models)
   - [Low-Level or Physical Data Models](#93-low-level-or-physical-data-models)
   - [Self-Describing Data Models](#94-self-describing-data-models)
   - [History of Data Models](#95-history-of-data-models)
10. [Three-Schema Architecture](#10-three-schema-architecture)
    - [The Internal Level](#101-the-internal-level)
    - [The Conceptual Level](#102-the-conceptual-level)
    - [The External or View Level](#103-the-external-or-view-level)
    - [Mapping](#104-mapping)
    - [Data Independence](#105-data-independence)
11. [Data Abstraction](#11-data-abstraction)
    - [Physical Level](#111-physical-level)
    - [Logical Level](#112-logical-level)
    - [View Level](#113-view-level)
12. [Instances and Schema](#12-instances-and-schema)
    - [Schema](#121-schema)
    - [Instance](#122-instance)
    - [Database State](#123-database-state)
    - [Schema vs State](#124-schema-vs-state)
13. [The Database System Environment](#13-the-database-system-environment)
    - [DBMS Component Modules](#131-dbms-component-modules)
    - [Database System Utilities](#132-database-system-utilities)
14. [Centralized and Client-Server Architectures](#14-centralized-and-client-server-architectures)
    - [Centralized DBMS](#141-centralized-dbms)
    - [Client-Server DBMS](#142-client-server-dbms)
    - [Basic Client/Server Architecture](#143-basic-clientserver-architecture)
    - [Two-Tier Client-Server Architecture](#144-two-tier-client-server-architecture)
    - [Three-Tier Client-Server Architecture](#145-three-tier-client-server-architecture)
    - [Difference Between File System and DBMS](#146-difference-between-file-system-and-dbms)
15. [DBMS Languages](#15-dbms-languages)
    - [Data Definition Language (DDL)](#151-data-definition-language-ddl)
    - [Data Manipulation Language (DML)](#152-data-manipulation-language-dml)
    - [Data Control Language (DCL)](#153-data-control-language-dcl)
    - [Transaction Control Language (TCL)](#154-transaction-control-language-tcl)
    - [Types of DML](#155-types-of-dml)
16. [Introduction to Modern Databases](#16-introduction-to-modern-databases)
    - [Relational Database](#161-relational-database)
    - [Semi-Structured Database](#162-semi-structured-database)
    - [Graph Database](#163-graph-database)
    - [Vector Database](#164-vector-database)
    - [Time-Series Database](#165-time-series-database)
    - [Distributed Database](#166-distributed-database)
    - [Unstructured Database](#167-unstructured-database)
    - [Advantages Comparison](#168-advantages-comparison)
17. [Introduction to Relational Model](#17-introduction-to-relational-model)
    - [What is a Relation?](#171-what-is-a-relation)
    - [Why Study the Relational Model?](#172-why-study-the-relational-model)
    - [Terminology](#173-terminology)
    - [Characteristics of Relations](#174-characteristics-of-relations)
    - [Relational Database Definitions](#175-relational-database-definitions)
    - [Relational Data Model](#176-relational-data-model)
    - [Example Instance of Students Relation](#177-example-instance-of-students-relation)
    - [Relational Database Schema](#178-relational-database-schema)
    - [Schema vs Instances](#179-schema-vs-instances)
    - [Creating Relations in SQL](#1710-creating-relations-in-sql)
18. [Integrity Constraints (ICs)](#18-integrity-constraints-ics)
    - [Domain Constraints](#181-domain-constraints)
    - [Entity Integrity Constraints](#182-entity-integrity-constraints)
    - [Referential Integrity Constraints](#183-referential-integrity-constraints)
    - [Key Constraints](#184-key-constraints)
    - [Primary Key Constraints](#185-primary-key-constraints)
    - [Primary and Candidate Keys in SQL](#186-primary-and-candidate-keys-in-sql)
    - [Foreign Keys and Referential Integrity](#187-foreign-keys-and-referential-integrity)
    - [Foreign Keys in SQL](#188-foreign-keys-in-sql)
    - [Constraints on NULL](#189-constraints-on-null)
    - [Enforcing Referential Integrity](#1810-enforcing-referential-integrity)
    - [Referential Integrity in SQL](#1811-referential-integrity-in-sql)
    - [Constraint Violations](#1812-constraint-violations)
    - [Types of Constraint Violations](#1813-types-of-constraint-violations)

---

## 1. Basic Definitions

### 1.1 Data

**Definition**: Data is a raw material or fact.

**Examples**:
- Registration Number
- Name

> **Note**: In DBMS terms, data is the actual values stored inside the table — the contents of each cell at the intersection of a row (tuple) and a column (attribute).

**Example Table (STUDENTS)**:

| StudentID | Name    | Age |
|-----------|---------|-----|
| 1         | Alice   | 20  |
| 2         | Bob     | 22  |
| 3         | Charlie | 21  |

- The **table** itself (`STUDENTS`) is a **relation**.
- Each **row** (like `1, Alice, 20`) is a **tuple/record**.
- Each **column** (`StudentID`, `Name`, `Age`) is an **attribute/field**.
- Each **cell value** (`Alice`, `22`, etc.) is the **data**.

> **Correction**: If you think "data = row," that's slightly off. A **row** is a collection of data values grouped together as one record. The **individual values inside the row** are the actual data.

---

### 1.2 Information

**Definition**: Information is data that has some meaning — knowledge, intelligence, a particular piece of data with a special meaning or function. Information is often the result of combining, comparing, and performing calculations on data.

- **Data** = raw facts (e.g., `Alice`, `20`, `Bob`).
- **Information** = processed, organized, or meaningful data.
  - Example: "Average age of students is 21" → that's **information**, derived from raw data.

**Raw Data Table**:

| StudentID | Name    | Age |
|-----------|---------|-----|
| 1         | Alice   | 20  |
| 2         | Bob     | 22  |
| 3         | Charlie | 21  |

**Derived Information (query result)**:

| Average StudentAge |
|-------------------|
| 21                |

> **Correction**: If you say "information = data," that's incomplete. **Data** is the input. **Information** is the meaningful output after processing.

**Information Types**:

| Type | Description | SQL Example |
|------|-------------|-------------|
| **Descriptive** | Shows what exists in the database | `SELECT StudentID, Name, Age FROM Students;` |
| **Comparative** | Highlights differences between values | `SELECT Name, Age FROM Students WHERE Age = (SELECT MAX(Age) FROM Students);` |
| **Aggregated** | Summarizes data using aggregate functions | `SELECT AVG(Age) AS AvgAge FROM Students;` |
| **Derived/Analytical** | Transforms data to add meaning | `SELECT Name, CASE WHEN Age >= 21 THEN 'Senior' ELSE 'Junior' END AS Category FROM Students;` |
| **Predictive** | Forecasts based on historical data | `SELECT AVG(EnrollmentCount) + 10 AS PredictedNextSemester FROM PastEnrollments;` |

---

### 1.3 Database

**Definition**: A database is a collection of interrelated data.

**Extended Definition**: A database is an organized collection of data, generally stored and accessed electronically from a computer system. It is controlled by Database Management System (DBMS).

- A database application is simply a program that interacts with the database at some point in its execution.
- Database system is a collection of application programs that interact with the database along with the DBMS and the database itself.

**Key Points**:
- Modeled in rows and columns in a series of tables.
- The data can be accessed / arranged / managed / modified / organized.

**Examples**:
- Department
- Courses
- Grades

> **In short**: A database is a structured, meaningful, and purpose-driven collection of data, managed by a DBMS, and used by applications to represent and interact with real-world information.

---

### 1.4 Properties of Database

1. **Database represents some aspect of real world**
2. **Database is logically coherent collection of data with some inherent meaning**
3. **Database is designed, built with data for specific purpose**
4. **Database can be any size and complexity**
5. **Database may be generated and maintained manually or it may be computerized**

---

## 2. Database Management System (DBMS)

**Definition**: A Database Management System is software designed to store , retrieve , define and manage in a Database.

**Examples**: MS-Access, MySQL, SQL Server, Oracle, RDBMS, etc.

**Key Points**:
- DBMS is the software layer that **controls, manages, and provides access to the database**.
- It acts as the bridge between:
  - The **database** (organized collection of data), and
  - The **users/applications** that want to interact with that data.
- DBMS ensures **data consistency, integrity, and security**.
- It provides **query languages** (like SQL) to access and manipulate data.
- It handles **storage, indexing, transactions, and concurrency control**.

> **Correction**: If you say "DBMS = database," that's wrong.
> - **Database** = the data itself.
> - **DBMS** = the system that manages the data.

---

### 2.1 DBMS Functionalities

| Functionality | Description |
|---------------|-------------|
| **Data Definition** | Create, alter, drop tables (DDL) |
| **Data Manipulation** | Insert, update, delete, query (DML) |
| **Data Security** | User access control |
| **Transaction Management** | Ensures ACID properties (Atomicity, Consistency, Isolation, Durability) |
| **Concurrency Control** | Multiple users can safely access data |
| **Backup & Recovery** | Protects against data loss |

**Detailed Functionalities**:

1. **Define a particular database** in terms of its data types, structures, and constraints.
2. **Construct or Load** the initial database contents on a secondary storage medium.
3. **Manipulating the database**:
   - **Retrieval**: Querying, generating reports
   - **Modification**: Insertions, deletions and updates to its content
   - **Accessing the database through Web applications**
4. **Protection or Security measures** to prevent unauthorized access.
5. **Presentation and Visualization of Data**.
6. **Maintaining the Database and associated Programs** over the life time of Database Application.

---

### 2.2 Popular DBMS Software

**List of Popular DBMS Software**:
- MySQL
- Microsoft Access
- Oracle
- PostgreSQL
- dBASE
- FoxPro
- SQLite
- IBM DB2
- LibreOffice Base
- MariaDB
- Microsoft SQL Server

---

## 3. Need for Database Systems

### 3.1 University Database Example

In this text we will be using a university database to illustrate all the concepts.

**Data consists of information about**:
- Students
- Instructors
- Classes

**Application program examples**:
- Add new students, instructors, and courses
- Register students for courses, and generate class rosters
- Assign grades to students, compute grade point averages (GPA) and generate transcripts

---

### 3.2 Problems with File Systems

In the early days, database applications were built directly on top of file systems, which leads to:

| Problem | Description |
|---------|-------------|
| **Data redundancy and inconsistency** | Data is stored in multiple file formats resulting in duplication of information in different files |
| **Difficulty in accessing data** | Need to write a new program to carry out each new task |
| **Data isolation** | Multiple files and formats |
| **Integrity problems** | Data integrity is the overall accuracy, completeness, and consistency of data. Integrity constraints (e.g., account balance > 0) become "buried" in program code rather than being stated explicitly. Hard to add new constraints or change existing ones. |
| **Atomicity of updates** | Failures may leave database in an inconsistent state with partial updates carried out. Example: Transfer of funds from one account to another should either complete or not happen at all. |
| **Concurrent access by multiple users** | Concurrent access needed for performance. Uncontrolled concurrent accesses can lead to inconsistencies. Example: Two people reading a balance (say 100) and updating it by withdrawing money (say 50 each) at the same time. |
| **Security problems** | Hard to provide user access to some, but not all, data |

---

### 3.3 Data Retrieval

If you want to retrieve data from the flat file then we must develop application programs in a high-level language, so that data can be stored and retrieved fastly and securely within the time bound.

**Example**: SQL – Structured Query Language

```sql
SELECT Name, Age FROM Students WHERE Age > 20;
```

---

### 3.4 Data Redundancy

In any storage, we need to make copies of data for backup but in traditional file management systems once we update data in one location sometimes it fails to get updated in the copy of the data, so that it may create problems of inconsistency. This rate is called duplicate data or redundant data.

- The database automatically maintains consistent data through a transaction using certain rules and procedures.
- Each transaction internally follows four properties known as **ACID properties**:
  - **Atomicity**
  - **Consistency**
  - **Durability**
  - **Isolation**
- The database is capable of eliminating all problems of insertion-deletion-updation of data through levels of the **normalization process**.

---

### 3.5 Data Integrity

Data integrity ensures that only required data is stored in the database. Data is validated before entered into the database using integrity constraints such as primary key, foreign key, etc.

```sql
CREATE TABLE Students (
    StudentID NUMBER PRIMARY KEY,
    Name VARCHAR2(50),
    Age NUMBER CHECK (Age > 0)
);
```

---

### 3.6 Data Security

In traditional file management, there is no authentication mechanism at high-end whereas DBMS provides levels of security authentication which can be done at user level, admin level, etc.

```sql
GRANT SELECT, INSERT ON Students TO rehaan;
```

---

### 3.7 Data Indexing

If you want to retrieve data very fastly from the database we are using indexing mechanism whereas Flat files don't support indexing and solely depend upon secondary storage devices.

- **Indexing** is a mechanism where data is uniquely identified and stored using some computational techniques so that data is retrieved very fastly.

```sql
CREATE INDEX idx_student_name ON Students(Name);
```

---

## 4. Characteristics of Database Approach

### 4.1 Self-Describing Nature of a Database System

A catalog stores the description of a particular DB (Data structures / types / constraints): **Meta-data**.

- Allows DBMS to work with different DB applications.
- DBMS stores **metadata** (data about data) in a catalog.
- This allows DBMS to work with different applications without rewriting everything.

---

### 4.2 Insulation Between Programs and Data

Called **program-data independence**.

- Allows changing data structures and storage organization without having to change the DBMS access programs.
- You can change storage structures without changing application code.

---

### 4.3 Data Abstraction

A data model is used to hide storage details and present the users with a **conceptual view** of the database.

- DBMS hides low-level storage details.
- Users see a conceptual view (tables, rows, columns) instead of raw files.

---

### 4.4 Support of Multiple Views of the Data

Each user may see a different view of the database, which describes only the data of interest to that user.

- Different users can see different slices of the same database.
- Example: A student sees their grades; an instructor sees the whole class roster.

---

### 4.5 Sharing of Data and Multi-User Transaction Processing

A set of concurrent users to retrieve from and to update the database.

**Concurrency control**: Each transaction is correctly executed or aborted.

**Recovery**: Each completed transaction has its effect permanently recorded in the database.

**OLTP (Online Transaction Processing)**: Allows hundreds of concurrent transactions to execute per second.

---

## 5. Advantages of Using the DBMS Approach

| Advantage | Description |
|-----------|-------------|
| **Controlling Redundancy** | Normalization and transactions keep data consistent across copies |
| **Restricting Unauthorized Access** | Authentication and authorization at user/admin levels |
| **Providing Persistent Storage for Program Objects** | Data survives program execution |
| **Providing Storage Structures and Search Techniques for Efficient Query Processing** | SQL queries and indexing make access fast |
| **Providing Backup and Recovery** | Automated mechanisms protect against crashes |
| **Providing Multiple User Interfaces** | SQL, GUIs, APIs, reporting tools |
| **Representing Complex Relationships among Data** | Relationships via keys (PK, FK) |
| **Enforcing Integrity Constraints** | PK, FK, CHECK constraints |
| **Permitting Inferencing and Actions Using Rules and Triggers** | Automated actions on data changes |
| **Potential for Enforcing Standards** | Consistent naming, formats across organization |
| **Reduced Application Development Time** | Reusable schema and queries |
| **Flexibility** | Schema changes without breaking apps |
| **Availability of Up-to-Date Information** | Real-time data access |
| **Economies of Scale** | Centralized management reduces costs |

---

## 6. Database Applications Examples

### Enterprise Information
- **Sales**: customers, products, purchases
- **Accounting**: payments, receipts, assets
- **Human Resources**: Information about employees, salaries, payroll taxes

### Manufacturing
- Management of production, inventory, orders, supply chain

### Banking and Finance
- Customer information, accounts, loans, and banking transactions
- Credit card transactions
- Finance: sales and purchases of financial instruments (e.g., stocks and bonds; storing real-time market data)

### Universities
- Registration, grades

### Airlines
- Reservations, schedules

### Telecommunication
- Records of calls, texts, and data usage
- Generating monthly bills
- Maintaining balances on prepaid calling cards

### Web-based Services
- **Online retailers**: order tracking, customized recommendations
- **Online advertisements**
- **Document databases**

### Navigation Systems
- For maintaining the locations of various places of interest along with the exact routes of roads, train systems, buses, etc.

### Users Day-to-Day Interaction to DB – Direct Access to Data
- ATM, APPS, ONLINE PURCHASE

### Major Areas Where DB is Used
- Online transaction processing
- Data analytics

---

## 7. Actors on the Database Management Scene

### 7.1 Actors on the Screen

These are the people who **use and control the database directly** through applications.

| Role | Description |
|------|-------------|
| **End Users** | They query, update, and generate reports |
| **System Analysts & Application Programmers** | Design, develop, and maintain database applications |

---

### 7.2 Database Administrators (DBA)

Responsible for:
- Authorizing access to the database
- Coordinating and monitoring its use
- Acquiring software and hardware resources

**DBA Responsibilities**:
- Schema definition
- Storage structure and access-method definition
- Schema and physical-organization modification
- Granting of authorization for data access
- Routine maintenance

---

### 7.3 Database Designers

Responsible to define:
- The content
- The structure
- The constraints and functions or transactions

They must communicate with the end-users and understand their needs.

---

### 7.4 End Users

Use the data for queries, reports and some of them update the database content.

**Categorized into**:

| Type | Description | Examples |
|------|-------------|----------|
| **Casual users** | Access database when needed | Instructors checking rosters |
| **Naïve or Parametric users** | They make up a large section of the end-user population. Perform repetitive tasks. | Bank-tellers, reservation clerks (do the same for entire shift) |
| **Sophisticated users** | Business analysts, scientists, engineers | Analysts writing complex queries |
| **Stand-alone users** | Personal databases | Tax records, address books |

---

### 7.5 System Analysts and Application Programmers (Software Engineers)

Design, Develop and Maintain DB Applications.

---

### 7.6 Workers Behind the Screen

These are the people who **design, develop, and maintain the DBMS itself** or manage the database system.

| Role | Description |
|------|-------------|
| **DBMS Software Developers** | Build the DBMS engine and related tools |
| **Computer System Operators** | Ensure hardware and OS resources are available for DBMS |

---

## 8. Types of DBMS

### 8.1 Classification of DBMS based on Data Model

| Data Model | Description |
|------------|-------------|
| Relational data model | Data stored in tables (rows & columns) |
| Object data model | Data stored as objects |
| Big data systems | Key-value data model, document data model [JSON], etc. |
| XML model | Data stored in XML format |
| Network model | Graph-like structure |
| Hierarchical model | Tree-like structure |

---

### 8.2 Hierarchical DBMS

In a Hierarchical database, model data is organized in a **tree-like structure**. Data is stored hierarchically (top down or bottom up) format.

- Data is represented using a **parent-child relationship**.
- In Hierarchical DBMS parent may have many children, but children have only one parent.

**Example**: Information Management System (IMS), developed by IBM.

#### Merits
- The design of the hierarchical model is simple.
- Provides Data Integrity since it is based on parent/child relationship.
- Data sharing is feasible since the data is stored in a single database.
- Even for large volumes of data, this model works perfectly.

#### De-Merits
- Implementation is complex.
- This model has to deal with anomalies like Insert, Update and Delete.
- Maintenance is difficult since changes done in the database may want you to do changes in the entire database structure.

---

### 8.3 Network DBMS

Network Model is same as hierarchical model except that it has **graph-like structure** rather than a tree-based structure. Unlike hierarchical model, this model allows each record to have more than one parent record.

- The relationship can be defined in the form of links and it handles **many-to-many relations**.
- This itself states that a record can have more than one parent.

#### Merits
- Easy to design the Network Model.
- The model can handle one-one, one-to-many, many-to-many relationships.
- It isolates the program from other details.
- Based on standards and conventions.

#### De-Merits
- Pointers bring complexity since the records are based on pointers and graphs.
- Changes in the database isn't easy that makes it hard to achieve structural independence.

---

### 8.4 Relational DBMS

Relational DBMS is the most widely used DBMS model because it is one of the easiest.

- In relational model, the data and relationships are represented by collection of inter-related tables.
- Each table is a group of column and rows, where column represents attribute of an entity and rows represents records.
- Tables are related to each other using common records.

**Example**: Let us see an example of two relations **Employee** and **Department** linked to each other, with DepartmentID, which is Foreign Key of **Employee** table and Primary key of **Department** table.

#### Merits
- It does not have any issues that we saw in the previous two models i.e. update, insert and delete anomalies have nothing to do in this model.
- Changes in the database do not require you to affect the complete database.
- Implementation of a Relational Model is easy.
- To maintain a Relational Model is not a tiresome task.

#### De-Merits
- Database inefficiencies hide and arise when the model has large volumes of data.
- The overheads of using relational data model come with the cost of using powerful hardware and devices.

```sql
-- Example in Oracle
CREATE TABLE Students (
    StudentID NUMBER PRIMARY KEY,
    Name VARCHAR2(50),
    Age NUMBER
);
```

---

### 8.5 Object-Oriented DBMS

In Object-oriented Model data stored in the form of **objects**. The structure which is called **classes** which display data within it.

- It defines a database as a collection of objects which stores both data members values and operations.

```sql
-- Oracle supports object types
CREATE TYPE Address AS OBJECT (
    Street VARCHAR2(50),
    City   VARCHAR2(50)
);
```

---

## 9. Categories of Data Models

A **data model** is a framework that defines:
- The **structure** of the database (tables, objects, trees, graphs)
- The **relationships** among data
- The **constraints** on data
- The **operations** allowed on data

> **In short**: It's the blueprint that helps us translate real-world requirements into a database design.

### 9.1 High-Level or Conceptual Data Models

Provide concepts that are close to the way many users perceive data.

- Use concepts such as **entities, attributes, and relationships**.
- A typical example is the **Entity-Relationship (ER) model**.
- Used in database design to capture requirements.
- Shows entities (Students, Courses) and relationships (Enrolls, Teaches).

---

### 9.2 Representational (or Implementation) Data Models

It is used to represent the logical part of the database and does not represent the physical structure of the databases. It allows us to focus primarily on the design part of the database.

- Between the extremes high level and low level data models.
- Easily understandable by user yet technicalities are not completely removed.
- Examples: Relational data model, network and hierarchical models, object data model.
- Sometimes called **record-based data models**.

**Popular representational model is Relational model.**

---

### 9.3 Low-Level or Physical Data Models

Details of how data is stored on the computer storage media by representing information such as:
- Record formats
- Record orderings
- Access paths

**Access path**: Makes the search for particular database records efficient, such as **indexing** or **hashing**.

---

### 9.4 Self-Describing Data Models

Combines the description of the data with the data values themselves.

- Examples: **XML**, **NoSQL systems** (JSON documents).
- Useful for flexible, schema-less storage.

---

### 9.5 History of Data Models

| Model | History | Examples |
|-------|---------|----------|
| **Relational Model** | Proposed in 1970 by E.F. Codd (IBM), first commercial system in 1981-82 | DB2, Oracle, SQL Server, Sybase, Informix |
| **Network Model** | First implemented by Honeywell in 1964-65 (IDS System). Adopted heavily due to support by CODASYL (CODASYL-DBTG report of 1971) | IDMS, DMS 1100, IMAGE, VAX-DBMS |
| **Hierarchical Model** | Implemented in joint effort by IBM and North American Rockwell around 1965. Resulted in IMS family of systems | IMS, System 2k (SAS inc.) |
| **Object-Oriented Models** | Several models proposed for implementing in database system. Persistent OOP languages like C++ (ObjectStore, Versant), Smalltalk (GemStone) | O2, ORION, IRIS |
| **Object-Relational Models** | Most recent trend. Started with Informix Universal Server | Oracle 10i, DB2, SQL Server |
| **Self-Describing Models** | XML databases, NoSQL systems | Oracle XML DB, BaseX, MongoDB, Redis, Cassandra, Neo4j |

---

## 10. Three-Schema Architecture

Proposed to support DBMS characteristics of:
- Use of catalog to store description (metadata)
- Program-data independence
- Support of multiple views of the data

> **The goal**: Separate **user applications** from the **physical database**.

### 10.1 The Internal Level

- Has an **internal schema**.
- Describes the **physical storage structure** of the database.
- Uses a **physical data model**.
- Includes complete details of data storage and access paths (e.g., indexes, file structures).

---

### 10.2 The Conceptual Level

- Has a **conceptual schema**.
- Describes the **structure of the whole database** for a community of users.
- Hides details of physical storage.
- Defines **entities, data types, relationships, user operations, and constraints**.
- Uses a **representational data model** (relational, network, hierarchical).

---

### 10.3 The External or View Level

- Has multiple **external schemas (user views)**.
- Each describes the **part of the database relevant to a particular user group**.
- Implemented using the same representational model as the conceptual schema.
- Example: A student sees their grades; an instructor sees class rosters.

---

### 10.4 Mapping

- Mappings among schema levels are needed to **transform requests and data**.
- Programs refer to an **external schema**, and the DBMS maps them to the **internal schema** for execution.
- Data extracted from the internal level is reformatted to match the user's external view (e.g., SQL query results displayed on a web page).

---

### 10.5 Data Independence

| Type | Definition | Example |
|------|------------|---------|
| **Logical Data Independence** | Capacity to change the **conceptual schema** without changing external schemas or application programs | Adding a new attribute to a table doesn't break existing user views |
| **Physical Data Independence** | Capacity to change the **internal schema** without having to change the conceptual schema | Reorganizing files or creating new indexes for performance doesn't affect user queries |

> When a schema at a lower level is changed, **only the mappings between this schema and higher-level schemas need to be changed** in a DBMS that fully supports data independence.
> - The **higher-level schemas themselves remain unchanged**.
> - Hence, **application programs need not be changed**, since they refer to the external schemas.

> **Note**: Not explicitly implemented in commercial DBMS products, but has been useful in explaining database system organization.

---

## 11. Data Abstraction

### 11.1 Physical Level

The lowest level of abstraction describes how the data are actually stored. The physical level describes complex low-level data structures in detail.

---

### 11.2 Logical Level

The next-higher level of abstraction describes what data are stored in the database, and what relationships exist among those data. Database administrators, who must decide what information to keep in the database, use the logical level of abstraction.

- The logical level thus describes the entire database in terms of a small number of relatively simple structures.
- **Physical data independence** exists at this level.

---

### 11.3 View Level

The highest level of abstraction describes only part of the entire database. The view level of abstraction exists to simplify their interaction with the system. The system may provide many views for the same database.

---

## 12. Instances and Schema

### 12.1 Schema

- The **overall design** of the database.
- Describes structure, constraints, and organization.
- Rarely changes (only during **schema evolution**).
- Sometimes called the **intension** of the database.
- Example: Tables `STUDENT`, `COURSE`, relationships, constraints.
- A displayed schema is called a **schema diagram**.
- Each object in the schema (like STUDENT or COURSE) is a **schema construct**.

---

### 12.2 Instance

- The **collection of information stored at a particular moment**.
- Also called a **database state** or **snapshot**.
- Changes frequently as data is inserted, updated, or deleted.
- Sometimes called the **extension** of the schema.

---

### 12.3 Database State

| State | Description |
|-------|-------------|
| **Empty State** | When a new database is defined, only the schema exists. No data yet → database state is empty. |
| **Initial State** | When the database is first populated with initial data. |
| **Current State** | The state after updates (insert, delete, modify). At any point in time, the DB has a current state. |
| **Valid State** | A state that satisfies all **constraints** defined in the schema. |

---

### 12.4 Schema vs State

| Aspect | Schema | State |
|--------|--------|-------|
| Changes | Very infrequently | Every time the database is updated |
| Also called | **Intension** | **Extension** |

> **Schema evolution**: When schema changes (adding new tables, attributes, constraints). Rare compared to frequent state changes.

---

## 13. The Database System Environment

### 13.1 DBMS Component Modules

A DBMS is a complex software system.

**Two major parts**:

1. **Top Part → Users and Interfaces**
   - End users, application programmers, DBAs, analysts
   - Interfaces: SQL, GUIs, APIs, reporting tools

2. **Lower Part → Internal DBMS Modules**
   - Responsible for **storage of data** and **transaction processing**
   - Includes:
     - **Storage Manager** (handles files, indexes, buffers)
     - **Query Processor** (parses, optimizes, executes SQL)
     - **Transaction Manager** (ensures ACID properties)
     - **Concurrency Control Manager** (handles multi-user access)
     - **Recovery Manager** (backup, restore, crash recovery)

---

### 13.2 Database System Utilities

- **Loading data** (import/export tools)
- **Backing up** (automatic or manual)
- **Reorganizing** (defragmenting, restructuring)
- **Performance monitoring** (query optimization, tuning)
- **Data dictionary tools** (metadata catalog)
- **Application environments** (e.g., JBuilder, IDEs)
- **Communication facilities** (networking, client-server connections)

---

## 14. Centralized and Client-Server Architectures

### 14.1 Centralized DBMS

A centralized database is stored at a **single location** such as a mainframe computer.
- It is maintained and modified from that location only and usually accessed using an internet connection such as a LAN or WAN.
- Combines everything into a **single system**: DBMS software, hardware, application programs, and user interface processing software.
- Used by organizations such as **colleges, companies, banks**.

#### Advantages
| Advantage | Description |
|-----------|-------------|
| **Data Integrity** | One location ensures accuracy and consistency |
| **Minimal Redundancy** | No duplicate data scattered across systems |
| **Strong Security** | Easier to secure one central location |
| **Portability** | Data stored in one place |
| **Cost-effective** | Less power and maintenance |
| **Easy Access** | All information available at the same location |

#### Disadvantages
| Disadvantage | Description |
|--------------|-------------|
| **Slower Access** | Heavy load on one system, network delays |
| **Traffic Bottlenecks** | Multiple users cause congestion |
| **Concurrency Issues** | Simultaneous access reduces efficiency |
| **Single Point of Failure** | If system crashes, all data is lost |

---

### 14.2 Client-Server DBMS

A client does not share any of its resources, but requests a server's content or service function. Clients therefore initiate communication sessions with servers which await incoming requests.

**Examples of computer applications that use the client-server model**: Email, network printing, and the World Wide Web.

#### Advantages
| Advantage | Description |
|-----------|-------------|
| **Centralization** | Access, Resources, and Data Security are controlled through server |
| **Scalability** | Any element can be upgraded when needed |
| **Flexibility** | New Technology can be easily integrated into the system |
| **Interoperability** | All components work together |

#### Disadvantages
| Disadvantage | Description |
|--------------|-------------|
| **Dependability** | When Servers goes down, operations will cease |
| **Lack of Mature Tools** | To administrate |
| **Lack of Scalability** | Network OS are not very scalable |
| **Higher Costs** | Infrastructure and maintenance |
| **Network Congestion** | Heavy traffic slows performance |

---

### 14.3 Basic Client/Server Architecture

**Server types**:
- File Servers
- Printer Servers
- Web Servers
- E-mail Servers

---

### 14.4 Two-Tier Client-Server Architecture

- **Client side** → User Interface Programs and Application Programs run on the client side.
- **Server side** → DBMS software.
- Communication via **ODBC (Open Database Connectivity)** or **JDBC**.
- Interface called ODBC provides an Application program interface (API) allow client side programs to call the DBMS.
- Most DBMS vendors provide ODBC drivers.

---

### 14.5 Three-Tier Client-Server Architecture

- Common for **Web applications**.
- Adds an **Application Server / Web Server** as an intermediate layer.
- **Responsibilities**:
  - Stores the web connectivity software.
  - Implements **business logic and rules**.
  - Acts as a conduit for sending partially processed data between the database server and the client.
- **Additional Features - Security**:
  - Encrypt data at server before transmission.
  - Decrypt data at client.

---

### 14.6 Difference Between File System and DBMS

| Aspect | File System | DBMS |
|--------|-------------|------|
| **Data Storage** | Stores raw files (text, binary) | Stores structured data in tables, objects, or documents |
| **Data Redundancy** | High (duplicate copies across files) | Minimal (normalization + transactions) |
| **Data Consistency** | Hard to maintain (manual updates) | Ensured via ACID properties |
| **Data Access** | Requires custom programs | Standardized queries (SQL) |
| **Security** | Limited (basic file permissions) | Strong authentication & authorization |
| **Concurrency** | Poor (conflicts when multiple users access) | Controlled via locks & transactions |
| **Backup & Recovery** | Manual, error-prone | Automated, reliable recovery mechanisms |
| **Indexing** | Not supported | Indexes for fast retrieval |
| **Data Integrity** | Constraints buried in code | Enforced via schema constraints (PK, FK, CHECK) |
| **Scalability** | Difficult for large data | Designed for large-scale applications |

---

## 15. DBMS Languages

### 15.1 Data Definition Language (DDL)

DDL is used for specifying the database schema. It is used for creating tables, schema, indexes, constraints etc. in database.

| Command | Purpose |
|---------|---------|
| `CREATE` | To create the database instance |
| `ALTER` | To alter the structure of database |
| `DROP` | To drop database instances |
| `TRUNCATE` | To delete tables in a database instance |
| `RENAME` | To rename database instances |

**Example**:
```sql
CREATE TABLE Student (
    SID INT PRIMARY KEY,
    Name VARCHAR(50),
    Dept VARCHAR(20),
    CGPA DECIMAL(3,2)
);
```

---

### 15.2 Data Manipulation Language (DML)

DML is used for accessing and manipulating data in a database.

| Command | Purpose |
|---------|---------|
| `SELECT` | To read records from table(s) |
| `INSERT` | To insert record(s) into the table(s) |
| `UPDATE` | To update the data in table(s) |
| `DELETE` | To delete all the records from the table |

---

### 15.3 Data Control Language (DCL)

DCL is used for granting and revoking user access on a database.

| Command | Purpose |
|---------|---------|
| `GRANT` | To grant access to user |
| `REVOKE` | To revoke access from user |

---

### 15.4 Transaction Control Language (TCL)

The changes in the database that we made using DML commands are either performed or rollbacked using TCL.

| Command | Purpose |
|---------|---------|
| `COMMIT` | To persist the changes made by DML commands in database |
| `ROLLBACK` | To rollback the changes made to the database |

---

### 15.5 Types of DML

| Type | Description | Examples |
|------|-------------|----------|
| **Low-Level (Procedural)** | Allow user to tell system exactly how to manipulate data | Network and hierarchical DMLs |
| **High-Level (Non-procedural / Declarative)** | Allow user to state what data is needed rather than how it is to be retrieved | SQL, QBE |

---

## 16. Introduction to Modern Databases

### 16.1 Relational Database

- Data stored in the form of **Tables (Relations)**.
- **Components**:
  - Rows (Tuples)
  - Columns (Attributes)
  - Primary Keys
  - Foreign Keys
- **Advantages**:
  - High consistency
  - ACID transactions
  - SQL support
- **Examples**: MySQL, PostgreSQL, Oracle, SQL Server

**Example**: STUDENT

| SID | Name | Dept |
|-----|------|------|
| 101 | Anu  | CSE  |

---

### 16.2 Semi-Structured Database

- Stores data **without fixed schema**.
- **Uses**: JSON, XML, YAML, BSON
- **Advantages**:
  - Flexible
  - Easy to modify
  - Suitable for web applications
- **Examples**: MongoDB, CouchDB, Firebase Firestore

**JSON Example**:
```json
{
    "StudentID": 101,
    "Name": "Anu",
    "Department": "CSE",
    "Skills": ["Python", "Java"]
}
```

---

### 16.3 Graph Database

- Stores **Relationships as first-class objects**.
- **Components**:
  - Node
  - Edge
  - Properties
- **Applications**:
  - Social Networks (Facebook, LinkedIn)
  - Recommendation Systems
  - Fraud Detection
  - Knowledge Graphs
  - Road Navigation
- **Examples**: Neo4j, Amazon Neptune, TigerGraph

---

### 16.4 Vector Database

- **Newest generation database**.
- Stores **Embeddings (Vectors)** Instead of Names.
- **What is an Embedding?**
  - AI converts the Image, Text, Audio into numerical vectors.
  - Similar vectors = Similar meaning.
- **Examples**: Pinecone, Milvus, Weaviate, ChromaDB, FAISS
- **Applications**:
  - AI Chatbots
  - Recommendation Systems
  - Image Search, Voice Search
  - Large Language Models (LLMs)
  - RAG Systems

---

### 16.5 Time-Series Database

- Stores **Time-stamped records**.
- **Applications**:
  - IoT, Weather, Stock Market, Server Monitoring
  - Smart Cities, Industrial Automation
- **Examples**: InfluxDB, OpenTSDB, TimescaleDB

> Every record contains Timestamp.

| Time  | Temperature |
|-------|-------------|
| 10:00 | 30°C        |
| 10:01 | 31°C        |
| 10:02 | 32°C        |

---

### 16.6 Distributed Database

- Database spread across **multiple computers**.
- **Advantages**:
  - High Availability
  - Scalability
  - Fault Tolerance
  - Fast Local Access
  - Load Balancing
- **Examples**: Google Spanner, Cassandra, CockroachDB

**Types of Distribution**:
- **Horizontal Fragmentation**: Rows split across sites
- **Vertical Fragmentation**: Columns split across sites

---

### 16.7 Unstructured Database

- Stores data **without predefined format**.
- **Examples**: Images, Videos, Audio, Emails, PDF, Medical Images, Satellite Images
- **Applications**:
  - Google Photos, Netflix, YouTube
  - Medical Imaging, Document Storage, Cloud Backup
- **Examples**: Elasticsearch, Hadoop HDFS, Amazon S3, Azure Blob Storage

---

### 16.8 Advantages Comparison

| Database | Strength |
|----------|----------|
| Relational | Consistency |
| Semi-Structured | Flexibility |
| Graph | Relationship Analysis |
| Vector | Similarity Search |
| Time-Series | Trend Analysis |
| Distributed | Scalability |
| Unstructured | Large File Storage |

---

## 17. Introduction to Relational Model

- Developed by **Dr. Edgar F. Codd (1970)**.
- Based on:
  - Mathematical Relations
  - Set Theory
  - Predicate Logic
- Information is stored as **Tables (Relations)**.

**Advantages**:
- Simple
- Easy to understand
- Data independence
- High integrity
- Reduced redundancy

---

### 17.1 What is a Relation?

A Relation is a two-dimensional table consisting of rows and columns.

**Example**: STUDENT

| SID | Name | Dept | CGPA |
|-----|------|------|------|
| 101 | Anu  | CSE  | 8.5  |
| 102 | Raj  | ECE  | 8.9  |
| 103 | Sara | IT   | 9.1  |

---

### 17.2 Why Study the Relational Model?

- Most widely used model.
- **Vendors**: IBM, Informix, Microsoft, Oracle, Sybase, etc.
- "Legacy systems" in older models:
  - E.g., IBM's IMS (hierarchical model)
- **Recent competitor**: object-oriented model
  - ObjectStore, Versant, Ontos, O2
- **A synthesis emerging**: object-relational model
  - Informix UDS, UniSQL, Oracle, DB2

---

### 17.3 Terminology

| Relational Term | Meaning |
|-----------------|---------|
| Relation | Table |
| Tuple | Row |
| Attribute | Column |
| Degree | No. of columns |
| Cardinality | No. of rows |
| Domain | Allowed values |
| Schema | Table structure |

---

### 17.4 Characteristics of Relations

A relation has the following properties:
- Unique table name
- Unique attribute names
- Atomic values
- No duplicate rows
- Order of rows not important
- Order of columns not important
- Every column has same data type

---

### 17.5 Relational Database Definitions

**Relational database**: a set of relations

**Relation**: made up of 2 parts:
- **Schema**: specifies name of relation, plus name and type of each column.
  - e.g. `Students(sid: string, name: string, login: string, age: integer, gpa: real)`
- **Instance**: a table, with rows and columns.
  - #Rows = cardinality, #fields = degree / arity.
  - Can think of a relation as a set of rows or tuples (i.e., all rows are distinct).
  - Columns (attributes) are single-valued.

---

### 17.6 Relational Data Model

- Relational model can represent as a table with columns and rows.
- A table in relational model is known as **Relation**.
- Each row is known as a **tuple**.
- Each column is known as an **attribute** or **field**.
- A finite set of tuples in the relational database system represents relational instance. Relation instances do not have duplicate tuples.
- A relation schema describes the relation name (table name), attributes, and their names.
- Each row has one or more attributes, known as **relation key**, which can identify the row in the relation (table) uniquely.

---

### 17.7 Example Instance of Students Relation

Cardinality = 3, degree = 5, all rows distinct

| sid | name  | login | age | gpa |
|-----|-------|-------|-----|-----|
| 53666 | Jones | jones@cs | 18 | 3.4 |
| 53688 | Smith | smith@ee | 18 | 3.2 |
| 53650 | Smith | smith@math | 19 | 3.8 |

---

### 17.8 Relational Database Schema

**Schema**: Structure of the database.

**Contains**:
- Table names
- Attributes
- Data types
- Constraints

**Example**: `STUDENT (SID, Name, Dept, CGPA)`

---

### 17.9 Schema vs Instances

| Term | Definition |
|------|------------|
| **Database Schema** | The description of a database. Includes descriptions of the database structure, data types, and the constraints on the database. |
| **Schema Diagram** | An illustrative display of (most aspects of) a database schema. |
| **Schema Construct** | A component of the schema or an object within the schema, e.g., STUDENT, COURSE. |
| **Database State** | The actual data stored in a database at a particular moment in time. This includes the collection of all the data in the database. Also called database instance (or occurrence or snapshot). |
| **Initial Database State** | Refers to the database state when it is initially loaded into the system. |
| **Valid State** | A state that satisfies the structure and constraints of the database. |

**Distinction**:
- The database schema changes very infrequently.
- The database state changes every time the database is updated.
- Schema is also called **intension**.
- State is also called **extension**.

---

### 17.10 Creating Relations in SQL

```sql
CREATE TABLE Students
(sid CHAR(20),
 name CHAR(20),
 login CHAR(10),
 age INTEGER,
 gpa REAL);

CREATE TABLE Enrolled
(sid CHAR(20),
 cid CHAR(20),
 grade CHAR(2));
```

> Creates the Student relation. Observe that the type (domain) of each field is specified, and enforced by the DBMS whenever tuples are added or modified.

> As another example, the Enrolled table holds information about courses that students take.

---

## 18. Integrity Constraints (ICs)

Integrity constraints are a set of rules. It is used to maintain the **quality of information**.

- Integrity constraints ensure that the data insertion, updating, and other processes have to be performed in such a way that data integrity is not affected.

**Types of Integrity Constraints**:
1. Domain constraints
2. Entity Integrity constraints
3. Referential Integrity constraints
4. Key Constraints

---

### 18.1 Domain Constraints

- Domain constraints can be defined as the definition of a **valid set of values** for an attribute.
- The data type of domain includes string, character, integer, time, date, currency, etc.
- The value of the attribute must be available in the corresponding domain.

**Example**: Age must be an integer > 0.

**Example Table**:

| ID | NAME | SEMESTER | AGE |
|----|------|----------|-----|
| 1000 | Tom | 1st | 17 |
| 1001 | Johnson | 2nd | 24 |
| 1002 | Leonardo | 5th | 21 |
| 1003 | Kate | 3rd | 19 |
| 1004 | Morgan | 8th | A |

> A is not allowed, because AGE is an integer attribute.

---

### 18.2 Entity Integrity Constraints

The entity integrity constraint states that **primary key value can't be null**.

- This is because the primary key value is used to identify individual rows in relation and if the primary key has a null value, then we can't identify those rows.

**Example Table (EMPLOYEE)**:

| EMP_ID | EMP_NAME | SALARY |
|--------|----------|--------|
| 123 | Jack | 30000 |
| 142 | Harry | 60000 |
| 164 | John | 20000 |
| NULL | Jackson | 27000 |

> Not allowed as primary key can't contain a NULL value.

---

### 18.3 Referential Integrity Constraints

In the Referential integrity constraints, if a **foreign key in Table 1 refers to the Primary Key of Table 2**, then every value of the Foreign Key in Table 1 must be null or be available in Table 2.

**Example**:

**Table 1 (Employee)**:

| EMP_NAME | NAME | AGE | D_No |
|----------|------|-----|------|
| 1 | Jack | 20 | 11 |
| 2 | Harry | 40 | 24 |
| 3 | John | 27 | 18 |
| 4 | Devil | 38 | 13 |

**Table 2 (Department)**:

| D_No | D_Location |
|------|------------|
| 11 | Mumbai |
| 24 | Delhi |
| 13 | Noida |

> Foreign key: D_No. Not allowed, as D_No = 18 is not defined as a Primary Key in Table 2, and in Table 1, D_No is a foreign key.

---

### 18.4 Key Constraints

- Keys are the entity set that is used to identify an entity within its entity set uniquely.
- An entity set can have multiple keys, but out of which one key will be the **primary key**.
- A primary key cannot contain a unique and null value in the relational table.

**Example Table**:

| ID | NAME | SEMESTER | AGE |
|----|------|----------|-----|
| 1000 | Tom | 1st | 17 |
| 1001 | Johnson | 2nd | 24 |
| 1002 | Leonardo | 5th | 21 |
| 1003 | Kate | 3rd | 19 |
| 1002 | Morgan | 8th | 22 |

> Duplicate ID 1002 violates key constraint.

---

### 18.5 Primary Key Constraints

Suppose sids are unique and logins are unique.

- A set of fields is a **superkey** for a relation if: No two distinct tuples have the same values in all fields of the superkey.
- A superkey is a **(candidate) key** if: No proper subset of it is a superkey.
- If there's >1 candidate key for a relation, one of them is chosen (by DBA) to be the **primary key**.

---

### 18.6 Primary and Candidate Keys in SQL

```sql
CREATE TABLE Students
(sid CHAR(20),
 login CHAR(10),
 gpa REAL,
 ...,
 PRIMARY KEY (sid),
 UNIQUE (login))
```

> "For a given student and course, there is a single grade."
> "No two students have the same sid and no two students have the same login. Furthermore, any other table wishing to reference a student should reference the sid field if possible."

```sql
CREATE TABLE Enrolled
(sid CHAR(20),
 cid CHAR(20),
 grade CHAR(2),
 PRIMARY KEY (sid,cid))
```

> Possibly many candidate keys (specified using UNIQUE), one of which is chosen as the primary key.

---

### 18.7 Foreign Keys and Referential Integrity

**Foreign key**: Set of fields in one relation that is used to 'refer' to a tuple in another (or the same) relation. (Must correspond to primary key of the second relation.) Like a 'logical pointer'.

- E.g. `sid` is a foreign key referring to Students:
  - `Enrolled(sid: string, cid: string, grade: string)`

If all foreign key constraints are enforced, **referential integrity** is achieved, i.e., no dangling references.

> Can you name a data model w/o referential integrity? **Links in HTML!**

---

### 18.8 Foreign Keys in SQL

Only students listed in the Students relation should be allowed to enroll for courses.

```sql
CREATE TABLE Enrolled
(sid CHAR(20), cid CHAR(20), grade CHAR(2),
 PRIMARY KEY (sid,cid),
 FOREIGN KEY (sid) REFERENCES Students (sid))
```

---

### 18.9 Constraints on NULL

- **Primary Key**: Cannot contain NULL
- **Foreign Key**: Can contain NULL (unless specified NOT NULL)

**Example**:
```sql
Name VARCHAR(50) NOT NULL
```

---

### 18.10 Enforcing Referential Integrity

Consider Students and Enrolled; sid in Enrolled is a foreign key that references Students.

| Scenario | Action |
|----------|--------|
| Enrolled tuple with non-existent student id inserted | **Reject it!** |
| Students tuple is deleted | Options: (1) Also delete all Enrolled tuples that refer to it, (2) Disallow deletion, (3) Set sid to default, (4) Set sid to NULL |
| Primary key of Students tuple is updated | Similar options |

---

### 18.11 Referential Integrity in SQL

SQL supports all 4 options on deletes and updates.

| Option | Description |
|--------|-------------|
| **NO ACTION** (default) | Delete/update is rejected |
| **CASCADE** | Also delete all tuples that refer to deleted tuple |
| **SET NULL** | Sets foreign key value of referencing tuple to NULL |
| **SET DEFAULT** | Sets foreign key value to a default value |

```sql
CREATE TABLE Enrolled
(sid CHAR(20),
 cid CHAR(20),
 grade CHAR(2),
 PRIMARY KEY (sid,cid),
 FOREIGN KEY (sid)
   REFERENCES Students
   ON DELETE CASCADE
   ON UPDATE SET DEFAULT)
```

---

### 18.12 Constraint Violations

A **constraint violation** occurs when an operation (INSERT, UPDATE, or DELETE) breaks one or more rules (constraints) defined in the database.

**Constraints ensure that**:
- Data is accurate.
- Data is valid.
- Relationships remain consistent.
- Duplicate or invalid records are prevented.

**Common SQL Operations That May Cause Violations**:
- INSERT
- UPDATE
- DELETE

---

### 18.13 Types of Constraint Violations

| Constraint | Violation Example |
|------------|-------------------|
| Domain Constraint | Age = -5 |
| Key Constraint | Duplicate Primary Key |
| Entity Integrity | NULL Primary Key |
| Referential Integrity | Invalid Foreign Key |
| NOT NULL Constraint | Name = NULL |
| CHECK Constraint | Salary < 0 |

#### INSERT Constraint Violations

**Example 1 – Duplicate Primary Key**:
```sql
INSERT INTO Student VALUES (101,'Kumar','D01');
```
> Error: PRIMARY KEY constraint violated. Reason: Student ID 101 already exists.

**Example 2 – Invalid Foreign Key**:
```sql
INSERT INTO Student VALUES (103,'Priya','D10');
```
> Error: FOREIGN KEY constraint violated. Reason: Department D10 does not exist.

#### UPDATE Constraint Violations

**Example 1**:
```sql
UPDATE Student SET DeptID='D20' WHERE SID=101;
```
> Error: Foreign Key Constraint Violated. Reason: Department D20 is not available in the Department table.

**Example 2**:
```sql
UPDATE Student SET SID=NULL WHERE SID=101;
```
> Error: Primary Key Cannot Be NULL

#### DELETE Constraint Violations

Suppose:

**Department Table**:

| DeptID |
|--------|
| D01 |

**Student Table**:

| SID | DeptID |
|-----|--------|
| 101 | D01 |

Now execute:
```sql
DELETE FROM Department WHERE DeptID='D01';
```

> **Result**: Cannot delete!
> **Reason**: Students are still referring to Department D01. This is called **Referential Integrity Violation**.

---

## Synonyms Recap

| DBMS Term | Synonyms |
|-----------|----------|
| Table | Relation |
| Row | Tuple, Record |
| Column | Attribute, Field |
| Cell Value | Data |
| Schema | Intension |
| Instance/State | Extension |

---

## Summary

| Topic | Key Points |
|-------|------------|
| **Data vs Information** | Data = raw facts; Information = processed, meaningful data |
| **Database** | Organized collection of interrelated data |
| **DBMS** | Software that manages and controls access to the database |
| **Need for DBMS** | Solves redundancy, inconsistency, security, concurrency issues |
| **Characteristics** | Self-describing, program-data independence, data abstraction, multiple views, multi-user processing |
| **Data Models** | Conceptual, Representational, Physical, Self-describing |
| **Three-Schema Architecture** | Internal, Conceptual, External levels with mappings |
| **Data Independence** | Logical and Physical |
| **Schema vs Instance** | Schema = design (stable); Instance = data at a moment (changes) |
| **DBMS Languages** | DDL, DML, DCL, TCL |
| **Modern Databases** | Relational, Semi-structured, Graph, Vector, Time-series, Distributed, Unstructured |
| **Relational Model** | Tables, tuples, attributes, keys, constraints |
| **Integrity Constraints** | Domain, Entity, Referential, Key constraints |
