# MongoDB Complete Study Plan
## Part 1: MongoDB Fundamentals & Insertion
- MongoDB basics and terminology
- Database → Collection → Document → Field
- `mongosh` basics
- `use`, `show dbs`, `show collections`
- `insertOne()`
- `insertMany()`
- `_id` and `ObjectId`

**Lab coverage:** Assessment 4a, Questions 1–2.

## Part 2: Basic Read Operations
- `find()`
- `findOne()`
- Query filters
- Retrieving all documents
- Retrieving documents using a specific field

**Lab coverage:** Assessment 4a, Questions 3–4.

## Part 3: Comparison Operators
- `$eq`
- `$ne`
- `$gt`
- `$gte`
- `$lt`
- `$lte`

**Lab coverage:** Assessment 4a, Questions 5–10.

## Part 4: Logical Operators
- `$and`
- `$or`
- `$not`
- `$nor`

**Lab coverage:** Assessment 4a, Questions 11–14.

## Part 5: Membership Operators
- `$in`
- `$nin`

**Lab coverage:** Assessment 4a, Questions 15–16.

## Part 6: Array Queries
- Querying array elements directly
- `$all`
- `$size`
- `$elemMatch`

**Lab coverage:** Assessment 4a, Questions 17–20.

## Part 7: Pattern Matching
- `$regex`
- `$options`
- Case-insensitive searches
- Searching nested document fields, such as `instructor.name`

**Lab coverage:** Assessment 4a, Questions 21–22.

## Part 8: Projection, Sorting & Pagination
- Projection: selecting specific fields
- Including and excluding fields
- The `_id` field in projections
- `sort()`
- `skip()`
- `limit()`

**Lab coverage:** Assessment 4a, Questions 23–25.

## Part 9: Counting Documents
- `countDocuments()`
- Counting all documents
- Counting documents matching a filter

**Lab coverage:** Assessment 4a, Question 26.

## Part 10: Basic Update Operations
- `updateOne()`
- `updateMany()`
- Understanding filters and update documents
- `$set`
- `$inc`

**Lab coverage:** Assessment 4b, Questions 1–2.

## Part 11: Field Update Operators
- `$rename`
- `$min`
- `$max`

We'll understand exactly how these operators behave and how to construct update queries using them.

**Lab coverage:** Assessment 4b, Questions 3–4.

## Part 12: Document Replacement & Upsert
- `replaceOne()`
- Difference between `replaceOne()` and `updateOne()`
- `upsert: true`
- What happens when a matching document exists or doesn't exist

**Lab coverage:** Assessment 4b, Question 5, plus the upsert material in PPT 2.

## Part 13: Array Update Operators: Adding Elements
- `$push`
- `$addToSet`
- `$each`
- `$position`
- Controlling duplicates

**Lab coverage:** Assessment 4b, Questions 6–8.

## Part 14: Array Update Operators: Removing & Limiting Elements
- `$pop`
- `$slice`
- Combining `$each`, `$position`, and `$slice`

**Lab coverage:** Assessment 4b, Questions 9–10.

## Part 15: Delete Operations
- `deleteOne()`
- `deleteMany()`
- Deleting documents using conditions
- Difference between deleting documents and dropping a collection
- `drop()`
- Understanding deletion results

**Lab coverage:** Assessment 4b, Questions 11–12, plus the deletion material in PPT 2.

## Part 16: Final Revision & Lab Practice
We'll bring everything together.

- Practice questions from all previous parts
- Write queries without looking at notes
- Debug incorrect queries
- Identify the correct operator for each problem
- Complete a mock lab assessment covering CRUD operations

We'll also revisit any topic where your understanding is weak.
# MongoDB Notes: Parts 1–5
# PART 1: MongoDB Fundamentals & Insertion

## 1.1 What is MongoDB?

MongoDB is a NoSQL database that stores data as documents rather than traditional relational tables.

A MongoDB document resembles a JSON object and is stored internally in BSON.

Example:

```javascript
{
    courseId: 101,
    courseName: "Database Systems",
    department: "CSE",
    credits: 4
}
```

### MongoDB terminology

| Term | Meaning | Example |
|---|---|---|
| Database | Contains collections | `college` |
| Collection | Contains documents | `courses` |
| Document | A single record | One course |
| Field | A property in a document | `courseName` |
| Value | Data stored in a field | `"Database Systems"` |
| `_id` | Unique document identifier | `ObjectId("...")` |

The hierarchy is:

**Database → Collection → Document → Field**

### Example: Course document

```javascript
{
    courseId: 101,
    courseName: "Database Systems",
    department: "CSE",
    credits: 4,
    studentsEnrolled: 120,
    topics: ["MongoDB", "NoSQL", "Indexing"]
}
```

Notice that `topics` is an array. MongoDB supports arrays and nested documents, so a document can represent more complex data than a simple collection of flat fields.

---

## 1.2 Basic MongoDB shell commands

These commands are useful when working in `mongosh`.

| Command | Purpose |
|---|---|
| `show dbs` | Displays available databases |
| `use college` | Switches to the `college` database |
| `show collections` | Displays collections in the current database |
| `db` | Displays the current database name |

### Example

```javascript
show dbs
```

Displays available databases.

```javascript
use college
```

Switches the current database context to `college`.

```javascript
show collections
```

Displays collections in the current database.

**Important:** Switching to a database doesn't necessarily create it immediately. A database is normally persisted when data is first stored in it.

---

## 1.3 `insertOne()`

### Definition

`insertOne()` inserts one document into a collection.

### Syntax

```javascript
db.collectionName.insertOne({
    field1: value1,
    field2: value2
});
```

### Example

```javascript
db.courses_25BCE1087.insertOne({
    courseId: 101,
    courseName: "Database Systems",
    department: "CSE",
    credits: 4,
    studentsEnrolled: 120
});
```

### Explanation

- `db` refers to the current database.
- `courses_25BCE1087` is the collection.
- `insertOne()` inserts a single document.
- The object contains the fields and their values.

### What happens to `_id`?

If you omit `_id`, MongoDB generates an identifier automatically.

For example, the stored document will have this general structure:

```javascript
{
    _id: ObjectId("generated_value"),
    courseId: 101,
    courseName: "Database Systems",
    department: "CSE",
    credits: 4,
    studentsEnrolled: 120
}
```

The actual `ObjectId` will differ.

**Remember:** `courseId` is a field defined by your application. `_id` is MongoDB's document identifier. They are not automatically the same.

### Expected result

A successful insertion returns an acknowledgement and the inserted document's ID.

---

## 1.4 `insertMany()`

### Definition

`insertMany()` inserts multiple documents into a collection.

### Syntax

```javascript
db.collectionName.insertMany([
    { field1: value1, field2: value2 },
    { field1: value3, field2: value4 },
    { field1: value5, field2: value6 }
]);
```

Notice the square brackets. They contain an array of documents.

### Example

```javascript
db.courses_25BCE1087.insertMany([
    {
        courseId: 102,
        courseName: "Data Structures",
        department: "CSE",
        credits: 3,
        studentsEnrolled: 95
    },
    {
        courseId: 103,
        courseName: "Digital Electronics",
        department: "ECE",
        credits: 4,
        studentsEnrolled: 110
    },
    {
        courseId: 104,
        courseName: "Data Analytics",
        department: "IT",
        credits: 3,
        studentsEnrolled: 150
    }
]);
```

### Difference between `insertOne()` and `insertMany()`

| Feature | `insertOne()` | `insertMany()` |
|---|---|---|
| Documents supplied | One | Multiple |
| Input structure | Document object | Array of documents |
| Main use | Insert a single record | Insert a batch of records |
| Automatic `_id` | Yes, if omitted | Yes, for each document if omitted |

### Case-based Question 1

**Scenario:** A college application needs to add a new course with these details:

- Course ID: 201
- Course name: Artificial Intelligence
- Department: CSE
- Credits: 4
- Students enrolled: 80
- Topics: AI, Machine Learning, Neural Networks

**Question:** Write the MongoDB query to insert this course.

**Answer:**

```javascript
db.courses_25BCE1087.insertOne({
    courseId: 201,
    courseName: "Artificial Intelligence",
    department: "CSE",
    credits: 4,
    studentsEnrolled: 80,
    topics: ["AI", "Machine Learning", "Neural Networks"]
});
```

### Case-based Question 2

**Scenario:** A college wants to add three new courses in one operation:

| Course ID | Course name | Department |
|---:|---|---|
| 202 | Operating Systems | CSE |
| 203 | Signal Processing | ECE |
| 204 | Web Development | IT |

**Question:** Write a query using `insertMany()`.

**Answer:**

```javascript
db.courses_25BCE1087.insertMany([
    {
        courseId: 202,
        courseName: "Operating Systems",
        department: "CSE"
    },
    {
        courseId: 203,
        courseName: "Signal Processing",
        department: "ECE"
    },
    {
        courseId: 204,
        courseName: "Web Development",
        department: "IT"
    }
]);
```

MongoDB permits documents in the same collection to have different fields. However, your application should enforce appropriate data validation where consistency matters.

### Case-based Question 3

**Scenario:** A course document must include an instructor with a name, experience, and specialization.

**Question:** Insert a course with a nested `instructor` document.

**Answer:**

```javascript
db.courses_25BCE1087.insertOne({
    courseId: 205,
    courseName: "Database Management",
    department: "CSE",
    credits: 4,
    instructor: {
        name: "Dr. Kumar",
        experience: 12,
        specialization: "Database Systems"
    }
});
```

Here, `instructor` is a field containing a nested document.

You can later query nested fields using dot notation, such as `"instructor.name"`.

### Part 1: Quick revision

- `insertOne()` inserts one document.
- `insertMany()` inserts multiple documents.
- MongoDB generates `_id` if it is omitted.
- Arrays use square brackets `[]`.
- Nested documents use curly braces `{}`.
- `use databaseName` switches the current database context.

---
# PART 2: Basic Read Operations

The **Read** operation retrieves documents from a MongoDB collection.

Your PPT introduces two fundamental methods:

- `find()`
- `findOne()`

Both retrieve data, but they differ in what they return.

## 2.1 `find()`

### Definition

`find()` retrieves all documents that match a specified filter.

### Syntax

```javascript
db.collectionName.find(filter);
```

The `filter` specifies which documents should be returned.

### Example 1: Retrieve all courses

```javascript
db.courses_25BCE1087.find({});
```

**Explanation:** An empty filter `{}` matches all documents in the collection.

### Example 2: Retrieve all CSE courses

```javascript
db.courses_25BCE1087.find({
    department: "CSE"
});
```

Only documents whose `department` field equals `"CSE"` match this query.

### Example 3: Retrieve courses with four credits

```javascript
db.courses_25BCE1087.find({
    credits: 4
});
```

This retrieves all courses whose `credits` value is `4`.

Notice that you don't need `$eq` for a simple equality query. Both of these are valid:

```javascript
db.courses_25BCE1087.find({
    credits: 4
});
```

```javascript
db.courses_25BCE1087.find({
    credits: { $eq: 4 }
});
```

We'll examine comparison operators in Part 3.

---

## 2.2 `findOne()`

### Definition

`findOne()` returns a single document matching the specified filter.

### Syntax

```javascript
db.collectionName.findOne(filter);
```

### Example

```javascript
db.courses_25BCE1087.findOne({
    courseId: 101
});
```

This retrieves one course matching `courseId: 101`.

### Important distinction

`findOne()` returns one matching document, not every matching document.

If multiple documents satisfy the filter, it returns one of them. Without an explicit sort, you should not rely on which matching document it returns.

If no document matches, the result is `null`.

---

## 2.3 `find()` vs `findOne()`

| Feature | `find()` | `findOne()` |
|---|---|---|
| Purpose | Retrieve matching documents | Retrieve one matching document |
| Result | Cursor over matching documents | One document or `null` |
| Multiple matches | Can retrieve all matches | Returns only one match |
| Empty filter | Matches all documents | Returns one document |

### Case-based Question 1

**Scenario:** A college administrator wants to display every course offered by the CSE department.

**Question:** Write the query.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    department: "CSE"
});
```

**Why?** Multiple courses may belong to CSE, so `find()` is appropriate.

### Case-based Question 2

**Scenario:** A student enters course ID `103`. The application must retrieve one matching course.

**Answer:**

```javascript
db.courses_25BCE1087.findOne({
    courseId: 103
});
```

**Why?** The query asks for one course matching a specific identifier.

### Case-based Question 3

**Scenario:** A teacher wants to display all documents in a collection without filtering any records.

**Answer:**

```javascript
db.courses_25BCE1087.find({});
```

**Why?** An empty filter matches all documents.

### Case-based Question 4

**Scenario:** The college wants to retrieve all courses offered by the IT department that have exactly three credits.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    department: "IT",
    credits: 3
});
```

**Why?** Multiple ordinary field conditions in the same filter are combined using logical AND.

A document must satisfy both conditions.

### Common mistakes

**Mistake 1:**

```javascript
db.courses_25BCE1087.find();
```

This is valid in `mongosh` and behaves like an empty filter.

However, explicitly writing `find({})` makes the intended behavior clearer in exams.

**Mistake 2:**

```javascript
db.courses_25BCE1087.findOne({
    department: "CSE"
});
```

Using `findOne()` here is not inherently incorrect. But if the question asks for *all* CSE courses, use `find()`.

**Mistake 3:** Confusing the collection name with a field name.

```javascript
db.courses_25BCE1087.find({
    courses: "CSE"
});
```

This searches for a field named `courses`, not `department`. The query must use the field specified in the document structure.

### Part 2: Quick revision

- `find({})`: retrieve all matching documents, which means all documents when the filter is empty.
- `find({ field: value })`: retrieve all documents matching a field value.
- `findOne({ field: value })`: retrieve one matching document.
- Multiple ordinary conditions in one filter are combined using AND.

---
# PART 3: Comparison Operators

Comparison operators help retrieve documents based on numerical values or other comparable values.

Your PPT covers six operators:

| Operator | Meaning | Example condition |
|---|---|---|
| `$eq` | Equal to | Credits equal to 4 |
| `$ne` | Not equal to | Credits not equal to 3 |
| `$gt` | Greater than | Enrollment greater than 100 |
| `$gte` | Greater than or equal to | Enrollment at least 100 |
| `$lt` | Less than | Enrollment below 100 |
| `$lte` | Less than or equal to | Enrollment at most 100 |

## 3.1 `$eq`: Equal to

### Syntax

```javascript
db.collectionName.find({
    field: { $eq: value }
});
```

### Example

Find courses with exactly four credits.

```javascript
db.courses_25BCE1087.find({
    credits: { $eq: 4 }
});
```

Equivalent shorthand:

```javascript
db.courses_25BCE1087.find({
    credits: 4
});
```

Both perform equality matching.

### Case-based Question

**Scenario:** The examination department wants a list of all courses carrying exactly two credits.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    credits: { $eq: 2 }
});
```

---

## 3.2 `$ne`: Not equal to

### Syntax

```javascript
db.collectionName.find({
    field: { $ne: value }
});
```

### Example

Find courses whose credits are not equal to three.

```javascript
db.courses_25BCE1087.find({
    credits: { $ne: 3 }
});
```

This matches documents where `credits` is not `3`. MongoDB's `$ne` can also match documents where the field is missing, so be careful when dealing with incomplete documents.

### Case-based Question

**Scenario:** The college wants all courses that do not have four credits.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    credits: { $ne: 4 }
});
```

---

## 3.3 `$gt`: Greater than

### Syntax

```javascript
db.collectionName.find({
    field: { $gt: value }
});
```

### Example

Find courses with more than 100 enrolled students.

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $gt: 100 }
});
```

If enrollment is exactly `100`, the document does **not** match.

### Case-based Question

**Scenario:** A department wants to identify courses with more than 150 enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $gt: 150 }
});
```

---

## 3.4 `$gte`: Greater than or equal to

### Syntax

```javascript
db.collectionName.find({
    field: { $gte: value }
});
```

### Example

Find courses with at least 100 enrolled students.

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $gte: 100 }
});
```

This includes courses with exactly 100 students.

### Case-based Question

**Scenario:** A course is considered eligible for a special facility if it has 120 or more enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $gte: 120 }
});
```

---

## 3.5 `$lt`: Less than

### Syntax

```javascript
db.collectionName.find({
    field: { $lt: value }
});
```

### Example

Find courses with fewer than 100 enrolled students.

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $lt: 100 }
});
```

Exactly 100 students does not satisfy the condition.

### Case-based Question

**Scenario:** Find courses with fewer than 50 enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $lt: 50 }
});
```

---

## 3.6 `$lte`: Less than or equal to

### Syntax

```javascript
db.collectionName.find({
    field: { $lte: value }
});
```

### Example

Find courses with 100 or fewer enrolled students.

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $lte: 100 }
});
```

This includes courses with exactly 100 students.

### Case-based Question

**Scenario:** The college wants courses with no more than 80 enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: { $lte: 80 }
});
```

---

## 3.7 Combining comparison operators

You can place multiple comparison operators on the same field.

### Example

Find courses with enrollment between 50 and 120, inclusive.

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: {
        $gte: 50,
        $lte: 120
    }
});
```

The document must satisfy both conditions:

- Enrollment is at least 50.
- Enrollment is at most 120.

### Case-based Question

**Scenario:** Find courses with at least 3 credits but no more than 4 credits.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    credits: {
        $gte: 3,
        $lte: 4
    }
});
```

---

## 3.8 Boundary-value comparison

Suppose a course has exactly 100 enrolled students.

| Query condition | Does it match? |
|---|---|
| `{ studentsEnrolled: { $gt: 100 } }` | No |
| `{ studentsEnrolled: { $gte: 100 } }` | Yes |
| `{ studentsEnrolled: { $lt: 100 } }` | No |
| `{ studentsEnrolled: { $lte: 100 } }` | Yes |
| `{ studentsEnrolled: { $eq: 100 } }` | Yes |
| `{ studentsEnrolled: { $ne: 100 } }` | No |

This distinction is a common source of mistakes in lab examinations.

### Part 3: Quick revision

- `$gt` and `$lt` exclude the boundary value.
- `$gte` and `$lte` include the boundary value.
- `$eq` matches equality.
- `$ne` matches inequality and may also match documents where the field is missing.
- Multiple operators on the same field can express a range.

---
# PART 4: Logical Operators

Logical operators combine or negate query conditions.

Your PPT covers four operators:

1. `$and`
2. `$or`
3. `$not`
4. `$nor`

The important distinction is whether **all conditions must match, at least one must match, or matching conditions must be excluded**.

## 4.1 `$and`: All conditions must be true

### Definition

`$and` returns documents that satisfy every condition in the specified array.

### Syntax

```javascript
db.collectionName.find({
    $and: [
        { condition1 },
        { condition2 }
    ]
});
```

### Example

Find courses that belong to CSE and have more than three credits.

```javascript
db.courses_25BCE1087.find({
    $and: [
        { department: "CSE" },
        { credits: { $gt: 3 } }
    ]
});
```

A course must satisfy both conditions:

- Its department is CSE.
- Its credits are greater than 3.

### Simpler alternative

MongoDB implicitly combines different field conditions with AND when they're placed in the same filter.

Therefore, the previous query can be written as:

```javascript
db.courses_25BCE1087.find({
    department: "CSE",
    credits: { $gt: 3 }
});
```

Both queries express the same logical requirement.

**Exam tip:** If a question explicitly asks you to use `$and`, write the version containing `$and`.

### Case-based Question 1

**Scenario:** The college wants courses from the IT department that have at least 100 enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    $and: [
        { department: "IT" },
        { studentsEnrolled: { $gte: 100 } }
    ]
});
```

### Case-based Question 2

**Scenario:** Find CSE courses having exactly four credits and more than 100 enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    $and: [
        { department: "CSE" },
        { credits: { $eq: 4 } },
        { studentsEnrolled: { $gt: 100 } }
    ]
});
```

All three conditions must be satisfied.

---

## 4.2 `$or`: At least one condition must be true

### Definition

`$or` returns documents that satisfy at least one of the specified conditions.

### Syntax

```javascript
db.collectionName.find({
    $or: [
        { condition1 },
        { condition2 }
    ]
});
```

### Example

Find courses offered by either CSE or IT.

```javascript
db.courses_25BCE1087.find({
    $or: [
        { department: "CSE" },
        { department: "IT" }
    ]
});
```

A course matches if its department is CSE **or** IT.

It doesn't need to satisfy both conditions.

### Case-based Question 1

**Scenario:** A college offers a special program to students enrolled in courses with either more than 150 students or exactly two credits.

Find the eligible courses.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    $or: [
        { studentsEnrolled: { $gt: 150 } },
        { credits: { $eq: 2 } }
    ]
});
```

### Case-based Question 2

**Scenario:** Retrieve courses from either ECE or MECH.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    $or: [
        { department: "ECE" },
        { department: "MECH" }
    ]
});
```

---

## 4.3 `$not`: Negate a condition

### Definition

`$not` inverts the effect of a query expression. It is commonly applied to an individual field condition.

### Syntax

```javascript
db.collectionName.find({
    field: {
        $not: {
            operator: value
        }
    }
});
```

### Example

Find courses whose department is not ECE.

```javascript
db.courses_25BCE1087.find({
    department: {
        $not: {
            $eq: "ECE"
        }
    }
});
```

This expresses the negation of the equality condition.

For a simple inequality, you can also write:

```javascript
db.courses_25BCE1087.find({
    department: { $ne: "ECE" }
});
```

**Important:** `$not` is a field-level operator, not a general-purpose operator that you can place anywhere in a query. It can also match documents where the field is missing.

### Case-based Question 1

**Scenario:** Retrieve courses whose enrollment is not greater than 100.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    studentsEnrolled: {
        $not: {
            $gt: 100
        }
    }
});
```

For numeric enrollment values, this matches values that are not greater than 100, including 100 and values below 100.

### Case-based Question 2

**Scenario:** Find courses whose credits are not equal to four, explicitly using `$not`.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    credits: {
        $not: {
            $eq: 4
        }
    }
});
```

If the question doesn't require `$not` specifically, `$ne: 4` is the more direct expression.

---

## 4.4 `$nor`: None of the conditions may match

### Definition

`$nor` returns documents that fail to match all the specified conditions.

In other words, **none of the listed conditions may be true**.

### Syntax

```javascript
db.collectionName.find({
    $nor: [
        { condition1 },
        { condition2 }
    ]
});
```

### Example

Find courses that are neither from CSE nor IT.

```javascript
db.courses_25BCE1087.find({
    $nor: [
        { department: "CSE" },
        { department: "IT" }
    ]
});
```

A course is returned only if neither condition matches.

### Case-based Question 1

**Scenario:** Retrieve courses that are neither from ECE nor from MECH.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    $nor: [
        { department: "ECE" },
        { department: "MECH" }
    ]
});
```

### Case-based Question 2

**Scenario:** Find courses that have neither four credits nor more than 100 enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    $nor: [
        { credits: 4 },
        { studentsEnrolled: { $gt: 100 } }
    ]
});
```

Both conditions must fail for a document to match.

A course with four credits is excluded. A course with more than 100 students is also excluded.

---

## 4.5 Compare the four logical operators

Assume these course documents exist:

| Course | Department | Credits | Students |
|---|---|---:|---:|
| A | CSE | 4 | 120 |
| B | IT | 3 | 80 |
| C | ECE | 4 | 150 |
| D | MECH | 2 | 40 |

Consider the following queries.

| Operator | Conditions | Matching courses |
|---|---|---|
| `$and` | CSE AND credits = 4 | A |
| `$or` | CSE OR IT | A, B |
| `$not` | Department is not ECE | A, B, D |
| `$nor` | Neither CSE nor IT | C, D |

These results assume all four documents have the stated fields.

### Memory trick

- **AND:** Every condition must match.
- **OR:** At least one condition must match.
- **NOT:** Negates a field condition.
- **NOR:** None of the listed conditions may match.

### Part 4: Quick revision

Your Lab Assessment 4a tests `$and`, `$or`, `$not`, and `$nor` in Questions 11–14. LA4_25BCE1087

When a question explicitly names an operator, use that operator in your answer rather than relying on an equivalent shorthand.

---
# PART 5: Membership Operators

Membership operators check whether a field's value belongs to a specified list.

The two operators in your syllabus are:

- `$in`
- `$nin`

They are particularly useful when filtering records by categories such as department, status, course type, or location.

## 5.1 `$in`: Matches any listed value

### Definition

`$in` matches documents where the specified field equals at least one value in the given array.

### Syntax

```javascript
db.collectionName.find({
    field: {
        $in: [value1, value2, value3]
    }
});
```

### Example

Find courses belonging to CSE, IT, or ECE.

```javascript
db.courses_25BCE1087.find({
    department: {
        $in: ["CSE", "IT", "ECE"]
    }
});
```

A course matches if its department is any one of these three values.

### Equivalent query using `$or`

```javascript
db.courses_25BCE1087.find({
    $or: [
        { department: "CSE" },
        { department: "IT" },
        { department: "ECE" }
    ]
});
```

Both queries express the same condition.

**Practical rule:** When checking whether one field matches any of several possible values, `$in` is usually simpler.

### Case-based Question 1

**Scenario:** A university wants to retrieve courses offered by CSE, ECE, and MECH.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    department: {
        $in: ["CSE", "ECE", "MECH"]
    }
});
```

### Case-based Question 2

**Scenario:** Find courses whose credit count is either two or four.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    credits: {
        $in: [2, 4]
    }
});
```

### Case-based Question 3

**Scenario:** The college is preparing a report on courses named either `Database Systems`, `Data Structures`, or `Data Analytics`.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    courseName: {
        $in: [
            "Database Systems",
            "Data Structures",
            "Data Analytics"
        ]
    }
});
```

The values must match the stored strings.

---

## 5.2 `$nin`: Matches none of the listed values

### Definition

`$nin` matches documents where the field's value does not equal any value in the specified array.

### Syntax

```javascript
db.collectionName.find({
    field: {
        $nin: [value1, value2, value3]
    }
});
```

### Example

Find courses that are not from CSE or IT.

```javascript
db.courses_25BCE1087.find({
    department: {
        $nin: ["CSE", "IT"]
    }
});
```

Documents with departments other than CSE and IT can match.

**Important:** `$nin` can also match documents where the specified field is missing. If the question requires the field to exist, add an appropriate existence condition.

### Case-based Question 1

**Scenario:** A college wants courses that do not belong to ECE or MECH.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    department: {
        $nin: ["ECE", "MECH"]
    }
});
```

### Case-based Question 2

**Scenario:** Retrieve courses whose credit count is neither two nor three.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    credits: {
        $nin: [2, 3]
    }
});
```

### Case-based Question 3

**Scenario:** A report must exclude courses from CSE, IT, and ECE.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    department: {
        $nin: ["CSE", "IT", "ECE"]
    }
});
```

---

## 5.3 `$in` vs `$nin`

| Feature | `$in` | `$nin` |
|---|---|---|
| Purpose | Match any listed value | Exclude all listed values |
| Example | `$in: ["CSE", "IT"]` | `$nin: ["CSE", "IT"]` |
| CSE document | Matches | Does not match |
| IT document | Matches | Does not match |
| ECE document | Does not match | Matches |
| Missing field | Doesn't match solely because the field is missing | Can match because the field is missing |

### Part 5: Quick revision

Your Lab Assessment 4a tests `$in` and `$nin` in Questions 15–16. LA4_25BCE1087

Remember:

- `$in` means **any value from this list**.
- `$nin` means **none of these values**.
- Both use square brackets to specify the list of values.
- For a single field with multiple possible values, `$in` is often cleaner than a long `$or` query.

# MongoDB Notes: Parts 6, 7, 8 & 9
# PART 6: Array Queries

MongoDB supports arrays as field values. An array can contain strings, numbers, nested documents or other supported BSON values.

Consider this course document:

```javascript
{
    courseId: 101,
    courseName: "Database Systems",
    department: "CSE",
    topics: ["MongoDB", "NoSQL", "Indexing"]
}
```

Here, `topics` is an array containing three strings.

Your PPT covers these array-query concepts:

1. Matching an array element directly
2. `$all`
3. `$size`
4. `$elemMatch`

## 6.1 Matching an array element directly

MongoDB can match an array field by specifying a value contained in that array.

### Syntax

```javascript
db.collectionName.find({
    arrayField: value
});
```

### Example

Find courses containing `"MongoDB"` in their `topics` array.

```javascript
db.courses_25BCE1087.find({
    topics: "MongoDB"
});
```

This matches documents whose `topics` array contains `"MongoDB"`.

You don't need to write an explicit loop or access the array by its index.

### Case-based Question 1

**Scenario:** A university wants to identify every course that includes Python in its topics.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    topics: "Python"
});
```

### Case-based Question 2

**Scenario:** Find all courses that cover Indexing.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    topics: "Indexing"
});
```

**Remember:** When you query an array field using a single value, MongoDB can match documents containing that value as an array element.

---

## 6.2 The `$all` operator

### Definition

`$all` matches documents whose array contains **every specified element**.

The array may contain additional elements beyond those specified in the query.

### Syntax

```javascript
db.collectionName.find({
    arrayField: {
        $all: [value1, value2]
    }
});
```

### Example

Find courses containing both `"MongoDB"` and `"NoSQL"`.

```javascript
db.courses_25BCE1087.find({
    topics: {
        $all: ["MongoDB", "NoSQL"]
    }
});
```

A document matches only if its `topics` array contains both elements.

Consider these documents:

```javascript
{
    courseName: "Course A",
    topics: ["MongoDB", "NoSQL", "Indexing"]
}
```

```javascript
{
    courseName: "Course B",
    topics: ["MongoDB", "Python"]
}
```

```javascript
{
    courseName: "Course C",
    topics: ["NoSQL", "Indexing"]
}
```

For the `$all` query above:

| Course | Contains MongoDB? | Contains NoSQL? | Matches? |
|---|---|---|---|
| Course A | Yes | Yes | Yes |
| Course B | Yes | No | No |
| Course C | No | Yes | No |

### Case-based Question 3

**Scenario:** The college wants to identify courses covering both `"Python"` and `"Machine Learning"`.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    topics: {
        $all: ["Python", "Machine Learning"]
    }
});
```

### Case-based Question 4

**Scenario:** Find courses containing all three topics: `"MongoDB"`, `"NoSQL"` and `"Indexing"`.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    topics: {
        $all: ["MongoDB", "NoSQL", "Indexing"]
    }
});
```

All three elements must be present in the array.

---

## 6.3 The `$size` operator

### Definition

`$size` matches arrays containing exactly the specified number of elements.

### Syntax

```javascript
db.collectionName.find({
    arrayField: {
        $size: number
    }
});
```

### Example

Find courses having exactly three topics.

```javascript
db.courses_25BCE1087.find({
    topics: {
        $size: 3
    }
});
```

Consider these arrays:

```javascript
topics: ["MongoDB", "NoSQL", "Indexing"]
```

This array contains three elements and matches `$size: 3`.

```javascript
topics: ["Python", "AI"]
```

This array contains two elements and does not match `$size: 3`.

### Case-based Question 5

**Scenario:** Find courses containing exactly four topics.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    topics: {
        $size: 4
    }
});
```

### Case-based Question 6

**Scenario:** A college wants courses that have exactly two topics in their syllabus.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    topics: {
        $size: 2
    }
});
```

**Important limitation:** `$size` checks the exact array length. It does not directly support a range such as "at least three elements" or "fewer than five elements." Such requirements need a different approach.

---

## 6.4 The `$elemMatch` operator

### Definition

`$elemMatch` matches documents where **at least one array element satisfies all the specified conditions**.

It is particularly useful when an array contains nested documents.

### Example data

Suppose a course stores topics as documents:

```javascript
{
    courseName: "Advanced Databases",
    topics: [
        {
            name: "MongoDB",
            level: 3
        },
        {
            name: "SQL",
            level: 1
        }
    ]
}
```

Each element of `topics` is a document with two fields: `name` and `level`.

### Syntax

```javascript
db.collectionName.find({
    arrayField: {
        $elemMatch: {
            condition1,
            condition2
        }
    }
});
```

### Example

Find courses with at least one topic whose name is `"MongoDB"` and whose level is at least 2.

```javascript
db.courses_25BCE1087.find({
    topics: {
        $elemMatch: {
            name: "MongoDB",
            level: { $gte: 2 }
        }
    }
});
```

The same array element must satisfy both conditions.

In the example document:

- The MongoDB element has level 3, so it satisfies both conditions.
- The SQL element has level 1 and a different name, so it does not.

Therefore, the document matches.

### Why is `$elemMatch` important?

Consider this array:

```javascript
topics: [
    { name: "MongoDB", level: 1 },
    { name: "SQL", level: 3 }
]
```

No single element has both `name: "MongoDB"` and `level >= 2`.

Consequently, the document does **not** match the `$elemMatch` query above.

This is the key concept: `$elemMatch` ensures that the specified conditions apply to the same array element.

### Case-based Question 7

**Scenario:** Find courses with at least one topic whose name is `"Python"` and whose level is greater than 2.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    topics: {
        $elemMatch: {
            name: "Python",
            level: { $gt: 2 }
        }
    }
});
```

### Case-based Question 8

**Scenario:** Find courses where at least one assessment has a score of at least 80 and a maximum score of 100.

Assume the documents contain an array like this:

```javascript
assessments: [
    { score: 85, maxScore: 100 },
    { score: 65, maxScore: 80 }
]
```

**Answer:**

```javascript
db.courses_25BCE1087.find({
    assessments: {
        $elemMatch: {
            score: { $gte: 80 },
            maxScore: 100
        }
    }
});
```

This query assumes the collection uses the `assessments` array structure shown above.

---

## 6.5 Comparison of array operators

| Operator | Purpose | Example |
|---|---|---|
| Direct matching | Array contains a particular value | `{ topics: "MongoDB" }` |
| `$all` | Array contains every specified value | `{ topics: { $all: ["MongoDB", "NoSQL"] } }` |
| `$size` | Array has exactly N elements | `{ topics: { $size: 3 } }` |
| `$elemMatch` | At least one element satisfies multiple conditions | `{ topics: { $elemMatch: { name: "MongoDB", level: { $gte: 2 } } } }` |

### Part 6: Quick revision

Your Lab Assessment 4a tests direct array matching, `$all`, `$size` and `$elemMatch` in Questions 17–20. LA4_25BCE1087

---
# PART 7: Pattern Matching Using Regular Expressions

Sometimes you don't know the complete value of a field. You only know part of the text, the starting characters, or a pattern.

For example, you may need to find:
- Course names beginning with `D`.
- Instructor names containing `Data`.
- Names beginning with `Al`, regardless of capitalization.

MongoDB supports regular-expression queries using `$regex`.

## 7.1 The `$regex` operator

### Definition

`$regex` matches strings against a specified regular-expression pattern.

### Syntax

```javascript
db.collectionName.find({
    fieldName: {
        $regex: "pattern"
    }
});
```

### Example 1: Find course names starting with D

```javascript
db.courses_25BCE1087.find({
    courseName: {
        $regex: "^D"
    }
});
```

The pattern `^D` means the string must begin with `D`.

For example:

| Course name | Matches? |
|---|---|
| Database Systems | Yes |
| Data Structures | Yes |
| Digital Electronics | Yes |
| Computer Networks | No |
| Web Development | No |

### Understanding regex symbols

| Symbol | Meaning | Example |
|---|---|---|
| `^` | Beginning of the string | `/^D/` |
| `$` | End of the string | `/s$/` |
| `.` | Any single character except line terminators, by default | `/D.ta/` |
| `*` | Zero or more repetitions of the preceding expression | `/Da*/` |
| `+` | One or more repetitions of the preceding expression | `/Da+/` |

For your lab, the most important pattern to understand is `^`, because the assessment asks you to find course names starting with D.

---

## 7.2 Case-insensitive matching

Normally, a regex pattern containing `D` is case-sensitive.

To ignore capitalization, use the `i` option.

### Syntax

```javascript
db.collectionName.find({
    fieldName: {
        $regex: "pattern",
        $options: "i"
    }
});
```

### Example

Find course names starting with D, regardless of capitalization.

```javascript
db.courses_25BCE1087.find({
    courseName: {
        $regex: "^D",
        $options: "i"
    }
});
```

The `i` option makes the match case-insensitive.

An equivalent form uses JavaScript regex notation:

```javascript
db.courses_25BCE1087.find({
    courseName: /^D/i
});
```

Both forms express the same pattern.

---

## 7.3 Finding text anywhere in a string

Suppose the college wants to find instructor names containing the word `Data`.

Assume an instructor is stored as a nested document:

```javascript
{
    courseName: "Database Systems",
    instructor: {
        name: "Dr. Data Sharma",
        experience: 12
    }
}
```

Use dot notation to query the nested field.

### Query

```javascript
db.courses_25BCE1087.find({
    "instructor.name": {
        $regex: "Data",
        $options: "i"
    }
});
```

### Explanation

- `"instructor.name"` accesses the nested name field.
- `$regex: "Data"` searches for the specified text pattern.
- `$options: "i"` ignores capitalization.

Because the pattern doesn't contain `^` or `$`, it can match `Data` anywhere within the field's value.

For example, `Dr. Data Sharma` matches.

### Case-based Question 1

**Scenario:** The college wants all courses whose names start with the letter D.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    courseName: {
        $regex: "^D"
    }
});
```

### Case-based Question 2

**Scenario:** Find instructor names containing `Kumar`, regardless of capitalization.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    "instructor.name": {
        $regex: "Kumar",
        $options: "i"
    }
});
```

### Case-based Question 3

**Scenario:** Find course names ending with `Systems`.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    courseName: {
        $regex: "Systems$"
    }
});
```

The `$` symbol anchors the pattern to the end of the string.

### Case-based Question 4

**Scenario:** Find course names containing `data`, regardless of capitalization.

**Answer:**

```javascript
db.courses_25BCE1087.find({
    courseName: {
        $regex: "data",
        $options: "i"
    }
});
```

### Part 7: Quick revision

Your Lab Assessment 4a tests regex-based course-name searching and case-insensitive instructor-name searching in Questions 21–22. LA4_25BCE1087

Remember:

- `$regex` searches using a pattern.
- `^D` means starts with D.
- `Systems$` means ends with Systems.
- `$options: "i"` makes the search case-insensitive.
- `"instructor.name"` accesses a nested field.

---
# PART 8: Projection, Sorting & Pagination

This part covers three operations that are commonly combined when retrieving data:

1. Projection: choose which fields to display.
2. Sorting: arrange documents in a particular order.
3. Pagination: skip documents and limit the number returned.

Your PPT covers projection, `sort()` and `limit()`. Your Lab Assessment 4a also tests `skip()`. NOSQL-CRUD1 LA4_25BCE1087

## 8.1 Projection

### Definition

Projection specifies which fields should be included in or excluded from the returned documents.

Suppose a course document contains:

```javascript
{
    courseId: 101,
    courseName: "Database Systems",
    department: "CSE",
    credits: 4,
    studentsEnrolled: 120
}
```

If you only need the course name, department and credits, you can use projection rather than returning every field.

### Syntax

```javascript
db.collectionName.find(
    filter,
    projection
);
```

The first argument is the filter. The second argument is the projection.

### Example: Include selected fields

```javascript
db.courses_25BCE1087.find(
    {},
    {
        courseName: 1,
        department: 1,
        credits: 1
    }
);
```

This includes the three specified fields.

However, MongoDB includes `_id` by default unless you explicitly exclude it.

To return only the three requested fields:

```javascript
db.courses_25BCE1087.find(
    {},
    {
        _id: 0,
        courseName: 1,
        department: 1,
        credits: 1
    }
);
```

### Understanding `1` and `0`

| Value | Meaning |
|---|---|
| `1` | Include the field |
| `0` | Exclude the field |

### Example: Exclude a field

Suppose you want every field except `studentsEnrolled`.

```javascript
db.courses_25BCE1087.find(
    {},
    {
        studentsEnrolled: 0
    }
);
```

All other fields are returned, except `studentsEnrolled`.

### Important projection rule

In a normal projection, you generally cannot mix inclusion and exclusion specifications, except for excluding `_id` while including other fields.

**Valid:**

```javascript
{
    _id: 0,
    courseName: 1,
    credits: 1
}
```

**Invalid as a standard inclusion/exclusion projection:**

```javascript
{
    courseName: 1,
    credits: 0
}
```

Choose an inclusion projection or an exclusion projection.

---

## 8.2 Sorting with `sort()`

### Definition

`sort()` arranges query results according to one or more fields.

### Syntax

```javascript
db.collectionName.find(filter).sort({
    fieldName: direction
});
```

### Sorting directions

| Value | Meaning |
|---|---|
| `1` | Ascending order |
| `-1` | Descending order |

### Example 1: Ascending order

Sort courses by enrollment from lowest to highest.

```javascript
db.courses_25BCE1087.find({})
    .sort({
        studentsEnrolled: 1
    });
```

### Example 2: Descending order

Sort courses by enrollment from highest to lowest.

```javascript
db.courses_25BCE1087.find({})
    .sort({
        studentsEnrolled: -1
    });
```

### Example 3: Sort by multiple fields

Suppose you want to sort by department alphabetically and then by credits in descending order.

```javascript
db.courses_25BCE1087.find({})
    .sort({
        department: 1,
        credits: -1
    });
```

MongoDB sorts by `department` first. When documents have the same department value, `credits` determines their relative order.

If multiple documents also have the same credits, their relative order is not guaranteed unless you specify another sort key.

---

## 8.3 Limiting results with `limit()`

### Definition

`limit()` restricts the maximum number of documents returned by a query.

### Syntax

```javascript
db.collectionName.find(filter).limit(number);
```

### Example

Retrieve at most five courses.

```javascript
db.courses_25BCE1087.find({})
    .limit(5);
```

This returns no more than five documents. If the collection contains fewer than five documents, it returns the available matches.

### Case-based Question 1

**Scenario:** The college wants to display the three courses with the highest enrollment, showing only course name and enrollment count.

**Answer:**

```javascript
db.courses_25BCE1087.find(
    {},
    {
        _id: 0,
        courseName: 1,
        studentsEnrolled: 1
    }
)
.sort({
    studentsEnrolled: -1
})
.limit(3);
```

### What does this query do?

1. `find({}, projection)` selects the required fields.
2. `sort({ studentsEnrolled: -1 })` orders the courses from highest enrollment to lowest.
3. `limit(3)` returns at most three results.

**Important:** Sorting before limiting ensures that the selected results are the top three by enrollment.

---

## 8.4 Skipping results with `skip()`

### Definition

`skip()` skips a specified number of documents before returning results.

### Syntax

```javascript
db.collectionName.find(filter).skip(number);
```

### Example

Skip the first three documents.

```javascript
db.courses_25BCE1087.find({})
    .skip(3);
```

This excludes the first three documents from the returned result set.

If you want predictable pagination, apply a consistent sort before using `skip()`.

### Case-based Question 2

**Scenario:** A college has sorted its course list and wants to skip the first three courses, then display the next three.

**Answer:**

```javascript
db.courses_25BCE1087.find({})
    .sort({ courseId: 1 })
    .skip(3)
    .limit(3);
```

This query:

1. Sorts courses by `courseId` in ascending order.
2. Skips the first three.
3. Returns at most the next three.

The sort ensures the pagination order is predictable, assuming `courseId` uniquely identifies each course.

### Understanding pagination

Suppose the sorted course IDs are:

```text
101, 102, 103, 104, 105, 106, 107, 108
```

For this query:

```javascript
db.courses_25BCE1087.find({})
    .sort({ courseId: 1 })
    .skip(3)
    .limit(3);
```

The returned course IDs are:

```text
104, 105, 106
```

The first three IDs were skipped.

---

## 8.5 Combining projection, sorting and pagination

These operations can be chained together.

### General syntax

```javascript
db.collectionName.find(
    filter,
    projection
)
.sort({
    fieldName: direction
})
.skip(number)
.limit(number);
```

### Case-based Question 3

**Scenario:** Display the second page of courses, with five courses per page, ordered by `courseId`. Show only the course name and department.

Assume page numbering starts at 1.

**Answer:**

```javascript
db.courses_25BCE1087.find(
    {},
    {
        _id: 0,
        courseName: 1,
        department: 1
    }
)
.sort({
    courseId: 1
})
.skip(5)
.limit(5);
```

The offset for page 2 is:

`(page number - 1) × page size`

Therefore:

`(2 - 1) × 5 = 5`

The query skips five documents and returns the next five.

### Case-based Question 4

**Scenario:** Find the four courses with the lowest enrollment and display only their names and enrollment counts.

**Answer:**

```javascript
db.courses_25BCE1087.find(
    {},
    {
        _id: 0,
        courseName: 1,
        studentsEnrolled: 1
    }
)
.sort({
    studentsEnrolled: 1
})
.limit(4);
```

### Part 8: Quick revision

| Method | Purpose |
|---|---|
| Projection | Controls which fields are returned |
| `sort({ field: 1 })` | Ascending order |
| `sort({ field: -1 })` | Descending order |
| `skip(n)` | Skips the first `n` results |
| `limit(n)` | Returns at most `n` results |

---
# PART 9: Counting Documents

MongoDB provides `countDocuments()` to count documents matching a specified filter.

This is useful when a college wants to know:
- The total number of courses.
- How many courses belong to CSE.
- How many courses have more than 100 enrolled students.
- How many courses have exactly four credits.

Your Lab Assessment 4a explicitly asks you to count the total number of courses and the number of CSE courses. LA4_25BCE1087

## 9.1 `countDocuments()`

### Definition

`countDocuments()` returns the number of documents matching the specified query filter.

### Syntax

```javascript
db.collectionName.countDocuments(filter);
```

The filter determines which documents are counted.

---

## 9.2 Counting all documents

### Example

Count every course in the collection.

```javascript
db.courses_25BCE1087.countDocuments({});
```

**Explanation:** The empty filter `{}` matches all documents.

Suppose the collection contains seven course documents.

The result is:

```text
7
```

The number shown here is an example, not a claim about the current contents of your database.

### Case-based Question 1

**Scenario:** A college administrator wants to know the total number of courses stored in the `courses_25BCE1087` collection.

**Answer:**

```javascript
db.courses_25BCE1087.countDocuments({});
```

---

## 9.3 Counting documents with a condition

You can provide a filter to count only documents that satisfy a specific condition.

### Example: Count CSE courses

```javascript
db.courses_25BCE1087.countDocuments({
    department: "CSE"
});
```

Only documents whose department is CSE are counted.

### Example: Count courses with four credits

```javascript
db.courses_25BCE1087.countDocuments({
    credits: 4
});
```

### Case-based Question 2

**Scenario:** Find the number of courses offered by the IT department.

**Answer:**

```javascript
db.courses_25BCE1087.countDocuments({
    department: "IT"
});
```

### Case-based Question 3

**Scenario:** Find the number of courses having more than 100 enrolled students.

**Answer:**

```javascript
db.courses_25BCE1087.countDocuments({
    studentsEnrolled: {
        $gt: 100
    }
});
```

### Case-based Question 4

**Scenario:** Count courses with at least four credits.

**Answer:**

```javascript
db.courses_25BCE1087.countDocuments({
    credits: {
        $gte: 4
    }
});
```

---

## 9.4 Combining conditions while counting

You can use comparison and logical operators inside `countDocuments()`.

### Example

Count CSE courses having more than three credits.

```javascript
db.courses_25BCE1087.countDocuments({
    $and: [
        { department: "CSE" },
        { credits: { $gt: 3 } }
    ]
});
```

Both conditions must be satisfied for a course to be counted.

### Case-based Question 5

**Scenario:** Count courses belonging to either CSE or IT.

**Answer:**

```javascript
db.courses_25BCE1087.countDocuments({
    department: {
        $in: ["CSE", "IT"]
    }
});
```

The query counts courses whose department matches either listed value.

### Case-based Question 6

**Scenario:** Count courses with enrollment between 50 and 150, inclusive.

**Answer:**

```javascript
db.courses_25BCE1087.countDocuments({
    studentsEnrolled: {
        $gte: 50,
        $lte: 150
    }
});
```

Both boundaries are included.

---

## 9.5 `countDocuments()` vs `find()`

| Feature | `find()` | `countDocuments()` |
|---|---|---|
| Purpose | Retrieve matching documents | Count matching documents |
| Result | Cursor over documents | Number |
| Example | `db.courses.find({})` | `db.courses.countDocuments({})` |
| Use case | Display course details | Calculate the number of courses |

### Case-based Question 7

**Scenario:** A college needs to display all CSE course records and separately calculate how many CSE courses exist.

**Answer:**

```javascript
// Retrieve all CSE course documents
db.courses_25BCE1087.find({
    department: "CSE"
});

// Count all CSE course documents
db.courses_25BCE1087.countDocuments({
    department: "CSE"
});
```

The first query retrieves the matching records. The second returns their count.

### Part 9: Quick revision

Remember these three patterns:

**Count everything:**
```javascript
db.courses_25BCE1087.countDocuments({});
```

**Count documents matching a field value:**
```javascript
db.courses_25BCE1087.countDocuments({
    department: "CSE"
});
```

**Count documents matching a comparison condition:**
```javascript
db.courses_25BCE1087.countDocuments({
    studentsEnrolled: { $gt: 100 }
});
```

---

# MongoDB DBMS Lab Notes: Parts 10–15
# Part 10: Basic Update Operations

## 10.1 `updateOne()`

Updates **the first document that matches** the filter.

### Syntax

```javascript
db.collectionName.updateOne(
    { condition },
    { $set: { field: value } }
)
```

### Example

Change the credits of course `C101` to `4`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $set: { credits: 4 } }
)
```

**Explanation:**
- `{ courseId: "C101" }` identifies the document.
- `$set` changes the specified field.
- If `credits` doesn't exist, `$set` creates it.
- Other fields remain unchanged.

## 10.2 `updateMany()`

Updates **all documents that match** the filter.

### Example

Set the department to `"CSE"` for every course with fewer than 3 credits.

```javascript
db.courses_25BCE1087.updateMany(
    { credits: { $lt: 3 } },
    { $set: { department: "CSE" } }
)
```

Every matching document is updated, not just the first one.

## 10.3 `$set`

Sets a field to a specified value. If the field doesn't exist, MongoDB creates it.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $set: { courseName: "Advanced DBMS" } }
)
```

Only `courseName` changes. The rest of the document stays intact.

## 10.4 `$inc`

Increases or decreases a numeric field by a specified amount.

### Increase credits by 1

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $inc: { credits: 1 } }
)
```

### Decrease credits by 1

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $inc: { credits: -1 } }
)
```

If `credits` is `3`, incrementing by `1` makes it `4`. Decrementing by `1` makes it `2`.

**Remember:** `$inc` is for numeric fields. It is not a substitute for `$set`.

### Difference between `$set` and `$inc`

| Operator | Purpose | Example | Result if current value is `3` |
|---|---|---|---|
| `$set` | Assign a value | `{ $set: { credits: 5 } }` | `5` |
| `$inc` | Add an amount | `{ $inc: { credits: 2 } }` | `5` |

## 10.5 Case-based questions

**Q1.** Update the course named `"DBMS"` and set its credits to `4`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseName: "DBMS" },
    { $set: { credits: 4 } }
)
```

**Q2.** Increase `studentsEnrolled` by 10 for every course in the `"CSE"` department.

```javascript
db.courses_25BCE1087.updateMany(
    { department: "CSE" },
    { $inc: { studentsEnrolled: 10 } }
)
```

**Q3.** Change the department to `"IT"` for course `C102`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C102" },
    { $set: { department: "IT" } }
)
```

**Common mistake:** Forgetting `$set` or `$inc` inside the update document. For ordinary field updates, this is incorrect:

```javascript
// Incorrect
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { credits: 4 }
)
```

Use `$set` when changing only selected fields.

---
# Part 11: Field Update Operators

This section covers `$rename`, `$min`, and `$max`.

## 11.1 `$rename`

Changes a field's name.

### Syntax

```javascript
db.collectionName.updateOne(
    { condition },
    { $rename: { "oldField": "newField" } }
)
```

### Example

Rename `courseName` to `title` for course `C101`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $rename: { "courseName": "title" } }
)
```

If the document contains:

```javascript
{
    courseId: "C101",
    courseName: "DBMS",
    credits: 3
}
```

It becomes:

```javascript
{
    courseId: "C101",
    title: "DBMS",
    credits: 3
}
```

The old field name is removed and replaced by the new one. If the old field doesn't exist, nothing is renamed.

## 11.2 `$min`

Updates a field **only if the specified value is less than its current value**. If the field doesn't exist, MongoDB sets it to the specified value.

### Example

Ensure that `credits` is no greater than `3` by applying `$min` with `3`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $min: { credits: 3 } }
)
```

If the existing credits are:
- `5` → changed to `3`
- `3` → remains `3`
- `2` → remains `2`

Think of `$min` as: **keep the smaller value**.

## 11.3 `$max`

Updates a field **only if the specified value is greater than its current value**. If the field doesn't exist, MongoDB sets it to the specified value.

### Example

Ensure that `credits` is at least `4`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $max: { credits: 4 } }
)
```

If the existing credits are:
- `2` → changed to `4`
- `4` → remains `4`
- `5` → remains `5`

Think of `$max` as: **keep the larger value**.

## 11.4 Quick comparison

| Operator | Function | If current value is `5` and supplied value is `3` |
|---|---|---|
| `$rename` | Changes a field name | Renames the field |
| `$min` | Keeps the smaller value | Changes `5` to `3` |
| `$max` | Keeps the larger value | Leaves `5` unchanged |

## 11.5 Case-based questions

**Q1.** Rename `department` to `branch` for course `C102`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C102" },
    { $rename: { "department": "branch" } }
)
```

**Q2.** Update `studentsEnrolled` to `100` only if the current value is greater than `100`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $min: { studentsEnrolled: 100 } }
)
```

**Q3.** Ensure that `studentsEnrolled` is at least `50`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $max: { studentsEnrolled: 50 } }
)
```

**Assessment trap:** `$min` does not mean "set to the minimum value no matter what." It compares the existing value with the supplied value.

---
# Part 12: Replacement and Upsert

This part covers `replaceOne()` and the `upsert` option.

## 12.1 `replaceOne()`

Replaces the **entire document, except for its `_id` value**, with a new document.

### Syntax

```javascript
db.collectionName.replaceOne(
    { condition },
    { newDocument }
)
```

### Example

Suppose the existing document is:

```javascript
{
    _id: 1,
    courseId: "C101",
    courseName: "DBMS",
    department: "CSE",
    credits: 3
}
```

Run:

```javascript
db.courses_25BCE1087.replaceOne(
    { courseId: "C101" },
    {
        courseId: "C101",
        courseName: "Advanced DBMS",
        credits: 4
    }
)
```

The resulting document is:

```javascript
{
    _id: 1,
    courseId: "C101",
    courseName: "Advanced DBMS",
    credits: 4
}
```

Notice that `department` is gone because it wasn't included in the replacement document.

**Key point:** `replaceOne()` replaces the document's contents. It does not merely change selected fields.

### `updateOne()` vs `replaceOne()`

| Feature | `updateOne()` | `replaceOne()` |
|---|---|---|
| Purpose | Modify selected fields | Replace the document's contents |
| Uses update operators such as `$set` | Yes | No |
| Unspecified existing fields | Usually remain unchanged | Are removed |
| Can create a missing document with `upsert` | Yes | Yes |

## 12.2 Upsert

**Upsert = Update + Insert.**

When `upsert: true` is specified:
- If a document matches the filter, MongoDB updates it.
- If no document matches, MongoDB inserts a new document based on the filter and update operation.

### Syntax

```javascript
db.collectionName.updateOne(
    { condition },
    { $set: { field: value } },
    { upsert: true }
)
```

### Example

Update course `C999`, or insert a new document if it doesn't exist.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C999" },
    {
        $set: {
            courseName: "Cloud Computing",
            department: "CSE",
            credits: 4
        }
    },
    { upsert: true }
)
```

**Case 1: `C999` exists**

The matching document is updated.

**Case 2: `C999` doesn't exist**

MongoDB inserts a document containing the equality filter field and the fields specified by `$set`. MongoDB also generates an `_id` if one isn't supplied.

## 12.3 Upsert with `replaceOne()`

```javascript
db.courses_25BCE1087.replaceOne(
    { courseId: "C200" },
    {
        courseId: "C200",
        courseName: "Operating Systems",
        department: "CSE",
        credits: 4
    },
    { upsert: true }
)
```

If `C200` exists, its document is replaced. Otherwise, a new document is inserted.

## 12.4 Case-based questions

**Q1.** Replace the document for course `C101` with a document containing `courseId`, `courseName`, and `credits`.

```javascript
db.courses_25BCE1087.replaceOne(
    { courseId: "C101" },
    {
        courseId: "C101",
        courseName: "DBMS",
        credits: 4
    }
)
```

**Q2.** Update course `C500` with the name `"AI"` and credits `3`. Insert it if it doesn't exist.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C500" },
    {
        $set: {
            courseName: "AI",
            credits: 3
        }
    },
    { upsert: true }
)
```

**Q3.** What happens if `upsert` is omitted?

By default, `upsert` is `false`. If no document matches, the operation does not insert a new document.

**Assessment trap:** Don't confuse `replaceOne()` with `updateOne()`. Accidentally replacing a document when you meant to update one field can remove data.

---
# Part 13: Array Updates: `$push`, `$addToSet`, `$each`, `$position`

Assume a course document contains this array:

```javascript
{
    courseId: "C101",
    topics: ["SQL", "Normalization"]
}
```

MongoDB provides operators for modifying array fields.

## 13.1 `$push`

Adds an element to the end of an array.

### Syntax

```javascript
db.collectionName.updateOne(
    { condition },
    { $push: { arrayField: value } }
)
```

### Example

Add `"Indexing"` to the `topics` array.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $push: { topics: "Indexing" } }
)
```

Result:

```javascript
{
    courseId: "C101",
    topics: ["SQL", "Normalization", "Indexing"]
}
```

**Important:** `$push` allows duplicate values.

If you push `"SQL"` again, the array becomes:

```javascript
["SQL", "Normalization", "Indexing", "SQL"]
```

MongoDB does not object to your repeated enthusiasm for SQL.

## 13.2 `$addToSet`

Adds an element only if that value isn't already present in the array.

### Example

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $addToSet: { topics: "SQL" } }
)
```

If `"SQL"` already exists, no new copy is added.

If `"Transactions"` doesn't exist, it is added:

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $addToSet: { topics: "Transactions" } }
)
```

**Important distinction:** `$addToSet` prevents adding an existing equal value. It does not remove duplicates that were already present.

## 13.3 `$push` vs `$addToSet`

| Feature | `$push` | `$addToSet` |
|---|---|---|
| Adds a new value | Yes | Yes |
| Allows duplicates | Yes | No, for an equal existing value |
| Removes existing duplicates | No | No |
| Typical use | Maintain a list where repeats are allowed | Maintain unique values |

## 13.4 `$each`

Adds multiple values to an array in a single update.

### Example using `$push`

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["Transactions", "Indexing", "Joins"]
            }
        }
    }
)
```

All three values are appended to `topics`.

### Example using `$addToSet`

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $addToSet: {
            topics: {
                $each: ["SQL", "Transactions", "Indexing"]
            }
        }
    }
)
```

Only values not already present are added.

**Remember:**
- `$each` lets you supply multiple values.
- `$push` with `$each` can add duplicates.
- `$addToSet` with `$each` avoids adding values already present.

## 13.5 `$position`

Specifies the index at which values should be inserted when using `$push` with `$each`.

Array indexes start at `0`.

Suppose:

```javascript
topics: ["SQL", "Normalization", "Indexing"]
```

Insert `"ER Model"` at index `1`:

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["ER Model"],
                $position: 1
            }
        }
    }
)
```

Result:

```javascript
topics: ["SQL", "ER Model", "Normalization", "Indexing"]
```

### Add multiple values at the beginning

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["Introduction", "Data Models"],
                $position: 0
            }
        }
    }
)
```

The new values are inserted at the beginning, in the order specified.

## 13.6 Case-based questions

**Q1.** Add `"Transactions"` to the topics of course `C101`.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $push: { topics: "Transactions" } }
)
```

**Q2.** Add `"SQL"` to the topics only if it isn't already present.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $addToSet: { topics: "SQL" } }
)
```

**Q3.** Add `"Joins"`, `"Views"`, and `"Indexes"` to the topics array.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["Joins", "Views", "Indexes"]
            }
        }
    }
)
```

**Q4.** Insert `"ER Model"` at index `0` in the topics array.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["ER Model"],
                $position: 0
            }
        }
    }
)
```

**Assessment trap:** `$position` is used with `$push` and `$each`, not as a standalone update operator.

---
# Part 14: Array Updates: `$pop` and `$slice`

This part covers removing elements from arrays and limiting the size of an array during a `$push` operation.

## 14.1 `$pop`

Removes one element from an array.

It accepts two values:

- `$pop: { arrayField: 1 }` removes the **last** element.
- `$pop: { arrayField: -1 }` removes the **first** element.

### Remove the last topic

Suppose:

```javascript
topics: ["SQL", "Normalization", "Indexing"]
```

Run:

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $pop: { topics: 1 } }
)
```

Result:

```javascript
topics: ["SQL", "Normalization"]
```

### Remove the first topic

Starting with:

```javascript
topics: ["SQL", "Normalization", "Indexing"]
```

Run:

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $pop: { topics: -1 } }
)
```

Result:

```javascript
topics: ["Normalization", "Indexing"]
```

### Quick reference

| Expression | Action |
|---|---|
| `{ $pop: { topics: 1 } }` | Remove the last element |
| `{ $pop: { topics: -1 } }` | Remove the first element |

`$pop` removes one element per specified array field, not every matching value.

## 14.2 `$slice`

When used inside `$push`, `$slice` limits the number of elements retained in an array after the new values are added.

It is commonly used with `$each`.

### Keep only the first three elements

Suppose:

```javascript
topics: ["SQL", "Normalization", "Indexing"]
```

Run:

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["Transactions"],
                $slice: 3
            }
        }
    }
)
```

The operation appends `"Transactions"` and then retains only the first three elements.

Result:

```javascript
topics: ["SQL", "Normalization", "Indexing"]
```

The new element was appended, but it fell outside the first three elements and was discarded by `$slice`.

### Keep only the last three elements

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["Transactions"],
                $slice: -3
            }
        }
    }
)
```

If the original array is:

```javascript
topics: ["SQL", "Normalization", "Indexing"]
```

After appending `"Transactions"` and retaining the last three elements, the result is:

```javascript
topics: ["Normalization", "Indexing", "Transactions"]
```

### `$slice` values

| `$slice` value | Effect after `$push` |
|---|---|
| `$slice: 3` | Keep the first 3 elements |
| `$slice: -3` | Keep the last 3 elements |
| `$slice: 0` | Keep no elements |
| `$slice: -1` | Keep only the last element |

**Important:** `$slice` here is an option inside `$push`, not a standalone update operator.

## 14.3 Case-based questions

**Q1.** Remove the last element from the topics array.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $pop: { topics: 1 } }
)
```

**Q2.** Remove the first element from the topics array.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    { $pop: { topics: -1 } }
)
```

**Q3.** Append `"Transactions"` and retain only the last two topics.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["Transactions"],
                $slice: -2
            }
        }
    }
)
```

**Q4.** Append `"Views"` and retain only the first four elements.

```javascript
db.courses_25BCE1087.updateOne(
    { courseId: "C101" },
    {
        $push: {
            topics: {
                $each: ["Views"],
                $slice: 4
            }
        }
    }
)
```

**Assessment trap:** A positive `$slice` retains elements from the beginning; a negative `$slice` retains elements from the end.

---
# Part 15: Delete Operations

MongoDB provides operations for deleting individual documents, multiple documents, and entire collections.

## 15.1 `deleteOne()`

Deletes the **first document matching the filter**.

### Syntax

```javascript
db.collectionName.deleteOne(
    { condition }
)
```

### Example

Delete the document for course `C101`.

```javascript
db.courses_25BCE1087.deleteOne(
    { courseId: "C101" }
)
```

Only one matching document is deleted.

If multiple documents match the filter, `deleteOne()` deletes just one of them. Don't assume it selects a particular document unless your filter uniquely identifies it.

## 15.2 `deleteMany()`

Deletes **all documents matching the filter**.

### Example

Delete every course in the `"ECE"` department.

```javascript
db.courses_25BCE1087.deleteMany(
    { department: "ECE" }
)
```

Every matching document is removed.

### Delete all documents in a collection

```javascript
db.courses_25BCE1087.deleteMany({})
```

An empty filter `{}` matches every document, so this deletes all documents in the collection.

**Warning:** The collection itself remains, but its documents are removed.

## 15.3 `drop()`

Deletes the entire collection, including its documents and collection-level structures such as indexes.

### Syntax

```javascript
db.collectionName.drop()
```

### Example

```javascript
db.courses_25BCE1087.drop()
```

The collection is removed from the database.

### `deleteMany({})` vs `drop()`

| Feature | `deleteMany({})` | `drop()` |
|---|---|---|
| Deletes all documents | Yes | Yes |
| Removes the collection itself | No | Yes |
| Removes collection indexes | No, indexes remain | Yes, collection indexes are removed |
| Collection remains available | Yes | No |

These operations are not interchangeable. One empties a collection; the other removes it.

## 15.4 Case-based questions

**Q1.** Delete the course whose ID is `C101`.

```javascript
db.courses_25BCE1087.deleteOne(
    { courseId: "C101" }
)
```

**Q2.** Delete all courses having fewer than 2 credits.

```javascript
db.courses_25BCE1087.deleteMany(
    { credits: { $lt: 2 } }
)
```

**Q3.** Delete all documents from the collection but keep the collection itself.

```javascript
db.courses_25BCE1087.deleteMany({})
```

**Q4.** Delete the entire collection.

```javascript
db.courses_25BCE1087.drop()
```

**Q5.** What is the difference between `deleteOne()` and `deleteMany()`?

- `deleteOne()` deletes one matching document.
- `deleteMany()` deletes all matching documents.

**Q6.** What is the difference between `deleteMany({})` and `drop()`?

- `deleteMany({})` removes all documents but leaves the collection.
- `drop()` removes the collection itself.
