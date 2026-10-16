# MongoDB Lab Assessment 4: Detailed Notes

Question 4(a): MongoDB queries and operators

Database: `UniversityDB`

Collection: `courses_25BCE1087`

Question 4(b): Employee updates and deletion

Database: `companyDB`

Collection: `employees_25BCE1087`

## Before you start

Run these commands in `mongosh`, not directly in Windows PowerShell.

- `use databaseName` switches to a database, creating it when data is first written.
- `db.createCollection()` creates a collection.
- `insertOne()` inserts one document.
- `insertMany()` inserts multiple documents.
- `find()` retrieves matching documents.
- `updateOne()` updates the first matching document.
- `updateMany()` updates all matching documents.
- `replaceOne()` replaces an entire document, except for its existing `_id`.
- `deleteOne()` deletes one matching document.
- `deleteMany()` deletes all matching documents.

A MongoDB document is similar to a JSON object. Documents can contain nested documents and arrays, so the database can represent more than just flat rows and columns.

# 4(a). University course database

## 1. Create the database and collection

```
use UniversityDB

db.createCollection("courses_25BCE1087")
```

Verify the collection:

```
show collections
```

Expected collection name:

```
courses_25BCE1087
```

## 2. Insert one document using `insertOne()`

```
db.courses_25BCE1087.insertOne({
  courseId: 101,
  courseName: "Database Management Systems",
  department: "CSE",
  credits: 4,
  studentsEnrolled: 120,
  instructor: {
    name: "Data Kumar",
    email: "data.kumar@university.edu"
  },
  topics: ["MongoDB", "NoSQL", "Indexing"]
})
```

### Understand the document

| Field              | Type              | Example                         |
| ------------------ | ----------------- | ------------------------------- |
| `courseId`         | Number            | `101`                           |
| `courseName`       | String            | `"Database Management Systems"` |
| `department`       | String            | `"CSE"`                         |
| `credits`          | Number            | `4`                             |
| `studentsEnrolled` | Number            | `120`                           |
| `instructor`       | Embedded document | `{name: "...", email: "..."}`   |
| `topics`           | Array             | `["MongoDB", "NoSQL"]`          |

The `instructor` field is a nested document. The `topics` field is an array.

## 3. Insert multiple documents using `insertMany()`

```
db.courses_25BCE1087.insertMany([
  {
    courseId: 102,
    courseName: "Data Structures",
    department: "CSE",
    credits: 3,
    studentsEnrolled: 90,
    instructor: {
      name: "Anita Rao",
      email: "anita@university.edu"
    },
    topics: ["Arrays", "Trees", "Graphs"]
  },
  {
    courseId: 103,
    courseName: "Digital Electronics",
    department: "ECE",
    credits: 4,
    studentsEnrolled: 75,
    instructor: {
      name: "Ravi Sharma",
      email: "ravi@university.edu"
    },
    topics: ["Logic Gates", "Circuits", "Flip-Flops"]
  },
  {
    courseId: 104,
    courseName: "Operating Systems",
    department: "CSE",
    credits: 4,
    studentsEnrolled: 140,
    instructor: {
      name: "Data Priya",
      email: "priya@university.edu"
    },
    topics: ["Processes", "Threads", "Scheduling"]
  },
  {
    courseId: 105,
    courseName: "Data Analytics",
    department: "IT",
    credits: 3,
    studentsEnrolled: 110,
    instructor: {
      name: "Data Mehta",
      email: "mehta@university.edu"
    },
    topics: ["Python", "MongoDB", "Visualization"]
  },
  {
    courseId: 106,
    courseName: "Computer Networks",
    department: "IT",
    credits: 3,
    studentsEnrolled: 95,
    instructor: {
      name: "Kiran Das",
      email: "kiran@university.edu"
    },
    topics: ["TCP/IP", "Routing", "Security"]
  },
  {
    courseId: 107,
    courseName: "Design and Analysis of Algorithms",
    department: "CSE",
    credits: 4,
    studentsEnrolled: 130,
    instructor: {
      name: "Neha Singh",
      email: "neha@university.edu"
    },
    topics: ["Sorting", "Greedy", "Dynamic Programming"]
  }
])
```

You now have seven documents in total: one from `insertOne()` and six from `insertMany()`.

Verify:

```
db.courses_25BCE1087.find()
```

## 4. Retrieve documents using `find()` and `findOne()`

### Display all documents

```
db.courses_25BCE1087.find()
```

### Find one document

```
db.courses_25BCE1087.findOne({
  courseId: 104
})
```

Difference:

- `find()` returns a cursor that can contain multiple matching documents.
- `findOne()` returns one matching document, or `null` if nothing matches.

## 5. Comparison operators

Comparison operators compare a field against a value.

| Operator | Meaning                  | Example                  |
| -------- | ------------------------ | ------------------------ |
| `$eq`    | Equal to                 | `credits = 4`            |
| `$ne`    | Not equal to             | `credits != 3`           |
| `$gt`    | Greater than             | `studentsEnrolled > 100` |
| `$gte`   | Greater than or equal to | `credits >= 4`           |
| `$lt`    | Less than                | `studentsEnrolled < 100` |
| `$lte`   | Less than or equal to    | `credits <= 3`           |

### `$eq`: Equal to

```
db.courses_25BCE1087.find({
  credits: { $eq: 4 }
})
```

Returns courses with exactly four credits.

### `$ne`: Not equal to

```
db.courses_25BCE1087.find({
  credits: { $ne: 3 }
})
```

Returns documents whose `credits` value is not 3. Fields that are missing can also match `$ne`, so remember that distinction if your data is inconsistent.

### `$gt`: Greater than

```
db.courses_25BCE1087.find({
  studentsEnrolled: { $gt: 100 }
})
```

Returns courses with more than 100 students.

### `$gte`: Greater than or equal to

```
db.courses_25BCE1087.find({
  credits: { $gte: 4 }
})
```

Returns courses with at least four credits.

### `$lt`: Less than

```
db.courses_25BCE1087.find({
  studentsEnrolled: { $lt: 100 }
})
```

Returns courses with fewer than 100 students.

### `$lte`: Less than or equal to

```
db.courses_25BCE1087.find({
  credits: { $lte: 3 }
})
```

Returns courses with three or fewer credits.

Exam memory trick: `gt` means greater than, `lt` means less than, and `e` means equal is included.

## 6. Logical operators

Logical operators combine or negate conditions.

| Operator | Meaning                             |
| -------- | ----------------------------------- |
| `$and`   | All conditions must be true         |
| `$or`    | At least one condition must be true |
| `$not`   | Negates a condition                 |
| `$nor`   | None of the conditions may be true  |

### `$and`: Both conditions must match

Find CSE courses with more than three credits.

```
db.courses_25BCE1087.find({
  $and: [
    { department: "CSE" },
    { credits: { $gt: 3 } }
  ]
})
```

MongoDB also allows the shorter form for different fields:

```
db.courses_25BCE1087.find({
  department: "CSE",
  credits: { $gt: 3 }
})
```

Both conditions must be satisfied.

### `$or`: Either condition can match

Find courses from CSE or IT.

```
db.courses_25BCE1087.find({
  $or: [
    { department: "CSE" },
    { department: "IT" }
  ]
})
```

### `$not`: Negate a condition

Find courses whose department is not ECE.

```
db.courses_25BCE1087.find({
  department: { $not: { $eq: "ECE" } }
})
```

For this simple case, the more direct query is:

```
db.courses_25BCE1087.find({
  department: { $ne: "ECE" }
})
```

`$not` is commonly used to negate a field-level condition, including comparisons and regular expressions.

### `$nor`: None of the conditions should match

Find courses that are neither CSE nor IT.

```
db.courses_25BCE1087.find({
  $nor: [
    { department: "CSE" },
    { department: "IT" }
  ]
})
```

With the current sample documents, this returns the ECE course.

Important distinction: `$or` needs at least one condition to match. `$nor` requires every listed condition to fail.

## 7. Set operators: `$in` and `$nin`

These operators check whether a field's value belongs to a list.

### `$in`: Value exists in the specified list

Find CSE, IT, or ECE courses.

```
db.courses_25BCE1087.find({
  department: {
    $in: ["CSE", "IT", "ECE"]
  }
})
```

### `$nin`: Value is not in the specified list

Find courses that are neither CSE nor IT.

```
db.courses_25BCE1087.find({
  department: {
    $nin: ["CSE", "IT"]
  }
})
```

Real-world use: `$in` is useful when filtering orders by several statuses, employees by several departments, or courses by a list of department codes.

`$nin` can also match documents where the field is missing.

## 8. Array operators

MongoDB arrays store multiple values inside a single document.

Example:

```
topics: ["MongoDB", "NoSQL", "Indexing"]
```

### Find an array containing a particular value

Find courses whose `topics` array contains `"MongoDB"`.

```
db.courses_25BCE1087.find({
  topics: "MongoDB"
})
```

MongoDB checks array elements when a scalar value is compared with an array field.

### `$all`: Array contains every specified value

```
db.courses_25BCE1087.find({
  topics: {
    $all: ["MongoDB", "NoSQL"]
  }
})
```

The array must contain both values, but they do not need to be adjacent or in a particular order.

### `$size`: Array has an exact number of elements

```
db.courses_25BCE1087.find({
  topics: { $size: 3 }
})
```

This matches arrays containing exactly three elements.

Limitation: `$size` checks an exact length. It does not directly support queries such as “at least three elements.”

### `$elemMatch`: One array element must satisfy multiple conditions

This is most useful for arrays of embedded documents. Add a practice document:

```
db.courses_25BCE1087.insertOne({
  courseId: 108,
  courseName: "Data Engineering",
  department: "CSE",
  credits: 4,
  studentsEnrolled: 80,
  instructor: {
    name: "Arun Rao",
    email: "arun@university.edu"
  },
  topics: [
    { name: "MongoDB", level: "Advanced" },
    { name: "SQL", level: "Beginner" },
    { name: "Docker", level: "Intermediate" }
  ]
})
```

Now query for a topic named MongoDB whose level is Advanced:

```
db.courses_25BCE1087.find({
  topics: {
    $elemMatch: {
      name: "MongoDB",
      level: "Advanced"
    }
  }
})
```

`$elemMatch` ensures that the same array element satisfies both conditions.

Without it, separate conditions on array subfields could be satisfied by different elements. That is the sort of subtle bug that makes database practicals entertaining for absolutely nobody.

Note: this additional practice document makes the collection contain eight documents. Skip it if your assessment requires exactly seven documents.

## 9. Regular expressions using `$regex`

Regular expressions search for text patterns.

### Course names beginning with D

```
db.courses_25BCE1087.find({
  courseName: { $regex: "^D" }
})
```

The `^` symbol means the start of the string.

Examples that match include:

- Data Structures
- Data Analytics
- Design and Analysis of Algorithms

### Instructor names containing `Data`, ignoring case

```
db.courses_25BCE1087.find({
  "instructor.name": {
    $regex: "Data",
    $options: "i"
  }
})
```

Here:

- `"instructor.name"` accesses a nested field.
- `$regex: "Data"` searches for the text `Data`.
- `$options: "i"` makes the search case-insensitive.

For example, `Data Kumar`, `data kumar`, and `DATA KUMAR` can all match.

## 10. Projection

Projection selects which fields to show in query results.

### Display only course name, department, and credits

```
db.courses_25BCE1087.find(
  {},
  {
    _id: 0,
    courseName: 1,
    department: 1,
    credits: 1
  }
)
```

Meaning:

- `{}` means no filter, so all documents are considered.
- `1` includes a field.
- `0` excludes a field.
- `_id: 0` removes MongoDB's default `_id` field from the displayed results.

Generally, inclusion and exclusion cannot be mixed in one projection, except for excluding `_id` in an inclusion projection.

Remember: Projection changes what is returned, not what is stored in the database.

## 11. Sorting

### Sort by students enrolled in descending order

```
db.courses_25BCE1087.find().sort({
  studentsEnrolled: -1
})
```

- `1` means ascending order.
- `-1` means descending order.

For ascending order:

```
db.courses_25BCE1087.find().sort({
  studentsEnrolled: 1
})
```

## 12. Pagination using `skip()` and `limit()`

Pagination retrieves results in smaller groups.

```
db.courses_25BCE1087.find()
  .sort({ courseId: 1 })
  .skip(3)
  .limit(3)
```

What happens:

1. Sort documents by `courseId` in ascending order.
2. Skip the first three documents.
3. Return the next three documents.

For a page size of three:

| Page | Skip | Limit |
| ---- | ---- | ----- |
| 1    | 0    | 3     |
| 2    | 3    | 3     |
| 3    | 6    | 3     |

Sorting first makes pagination more predictable. Without a defined sort order, the database does not promise a particular order of results.

## 13. Count documents

### Count all courses

```
db.courses_25BCE1087.countDocuments({})
```

### Count CSE courses

```
db.courses_25BCE1087.countDocuments({
  department: "CSE"
})
```

`countDocuments()` counts documents satisfying the supplied filter.

### Question 4(a) revision table

| Requirement                        | Command or operator |
| ---------------------------------- | ------------------- |
| Insert one document                | `insertOne()`       |
| Insert several documents           | `insertMany()`      |
| Retrieve documents                 | `find()`            |
| Retrieve one document              | `findOne()`         |
| Equal to                           | `$eq`               |
| Not equal to                       | `$ne`               |
| Greater than                       | `$gt`               |
| Greater than or equal              | `$gte`              |
| Less than                          | `$lt`               |
| Less than or equal                 | `$lte`              |
| Combine conditions                 | `$and`, `$or`       |
| Negate conditions                  | `$not`, `$nor`      |
| Match values in a list             | `$in`, `$nin`       |
| Match array values                 | `$all`              |
| Match exact array length           | `$size`             |
| Match one qualifying array element | `$elemMatch`        |
| Search text patterns               | `$regex`            |
| Select output fields               | Projection          |
| Order results                      | `sort()`            |
| Paginate                           | `skip()`, `limit()` |
| Count matching documents           | `countDocuments()`  |

# 4(b). Company employee database

## 1. Create the database and collection

```
use companyDB

db.createCollection("employees_25BCE1087")
```

Verify:

```
show collections
```

## 2. Insert six employee documents

```
db.employees_25BCE1087.insertMany([
  {
    empId: 140,
    name: "Aarav",
    department: "CSE",
    designation: "Junior Developer",
    salary: 42000,
    experience: 4,
    skills: ["Java", "SQL", "Git"],
    projects: ["Library App", "Student Portal"],
    performanceScore: 88,
    contactNo: "9000000140"
  },
  {
    empId: 141,
    name: "Diya",
    department: "CSE",
    designation: "Software Engineer",
    salary: 50000,
    experience: 5,
    skills: ["Python", "SQL"],
    projects: ["Analytics Platform"],
    performanceScore: 94,
    contactNo: "9000000141"
  },
  {
    empId: 142,
    name: "Kabir",
    department: "HR",
    designation: "HR Executive",
    salary: 34000,
    experience: 2,
    skills: ["Communication", "Recruitment"],
    projects: ["Hiring Portal"],
    performanceScore: 76,
    contactNo: "9000000142"
  },
  {
    empId: 143,
    name: "Meera",
    department: "Mechanical",
    designation: "Design Engineer",
    salary: 39000,
    experience: 3,
    skills: ["CAD", "Simulation"],
    projects: ["Engine Design"],
    performanceScore: 68,
    contactNo: "9000000143"
  },
  {
    empId: 144,
    name: "Rohan",
    department: "ECE",
    designation: "Test Engineer",
    salary: 31000,
    experience: 1,
    skills: ["Testing", "Electronics"],
    projects: ["Circuit Testing"],
    performanceScore: 35,
    contactNo: "9000000144"
  },
  {
    empId: 145,
    name: "Sara",
    department: "HR",
    designation: "HR Manager",
    salary: 62000,
    experience: 7,
    skills: ["Leadership", "Recruitment"],
    projects: ["Workforce Planning"],
    performanceScore: 97,
    contactNo: "9000000145"
  }
])
```

Verify the data:

```
db.employees_25BCE1087.find()
```

## 3. Update one employee's designation and salary

Requirement: Change employee 140's designation and increase salary by ₹10,000.

```
db.employees_25BCE1087.updateOne(
  { empId: 140 },
  {
    $set: {
      designation: "Backend Developer"
    },
    $inc: {
      salary: 10000
    }
  }
)
```

Verify:

```
db.employees_25BCE1087.findOne({
  empId: 140
})
```

### Operators used

- `$set` assigns a new value to a field.
- `$inc` increases a numeric value by the specified amount.

Employee 140's salary changes from ₹42,000 to ₹52,000.

`updateOne()` modifies only the first matching document.

## 4. Increase salary for experienced CSE employees

Requirement: Increase salary by ₹5,000 for CSE employees with more than three years of experience.

```
db.employees_25BCE1087.updateMany(
  {
    department: "CSE",
    experience: { $gt: 3 }
  },
  {
    $inc: {
      salary: 5000
    }
  }
)
```

Verify:

```
db.employees_25BCE1087.find({
  department: "CSE",
  experience: { $gt: 3 }
})
```

Both conditions must be true:

- Department is CSE.
- Experience is greater than three years.

In this sample, Aarav and Diya match. Since Aarav was updated in the previous step, his salary increases from ₹52,000 to ₹57,000.

## 5. Rename `contactNo` to `phoneNumber` for HR employees

```
db.employees_25BCE1087.updateMany(
  {
    department: "HR"
  },
  {
    $rename: {
      contactNo: "phoneNumber"
    }
  }
)
```

Verify:

```
db.employees_25BCE1087.find({
  department: "HR"
})
```

`$rename` changes the field name. It does not simply create another field containing the same value.

For HR employees, `contactNo` is replaced by `phoneNumber`.

## 6. Ensure salary is at least ₹30,000 and performance score is at most 100

```
db.employees_25BCE1087.updateMany(
  {},
  {
    $max: {
      salary: 30000
    },
    $min: {
      performanceScore: 100
    }
  }
)
```

### How `$max` and `$min` work

- `$max` changes a field only when its existing value is less than the supplied value.
- `$min` changes a field only when its existing value is greater than the supplied value.

Therefore:

- Salary below ₹30,000 becomes ₹30,000.
- Salary already above ₹30,000 remains unchanged.
- Performance score above 100 becomes 100.
- Performance score already below 100 remains unchanged.

These operators enforce bounds. They do not set every employee's salary to ₹30,000 or every score to 100.

## 7. Replace an entire employee document using `replaceOne()`

Requirement: Replace employee 143's document with only `empId`, `name`, `department`, `designation`, and `salary`.

```
db.employees_25BCE1087.replaceOne(
  {
    empId: 143
  },
  {
    empId: 143,
    name: "Meera",
    department: "Mechanical",
    designation: "Design Engineer",
    salary: 39000
  }
)
```

Verify:

```
db.employees_25BCE1087.findOne({
  empId: 143
})
```

### Important

`replaceOne()` replaces the document's existing fields with the replacement document. It is not the same as `$set`.

After replacement, employee 143 no longer has `experience`, `skills`, `projects`, `performanceScore`, or `contactNo`, unless you explicitly include those fields in the replacement.

MongoDB preserves the existing `_id` when you omit it from the replacement.

## 8. Add a skill using `$push`

Requirement: Add `"MongoDB"` to employee 140's skills.

```
db.employees_25BCE1087.updateOne(
  {
    empId: 140
  },
  {
    $push: {
      skills: "MongoDB"
    }
  }
)
```

Verify:

```
db.employees_25BCE1087.findOne({
  empId: 140
})
```

`$push` appends a value to an array. It allows duplicates.

For example, pushing `"MongoDB"` twice can produce:

```
skills: ["Java", "SQL", "Git", "MongoDB", "MongoDB"]
```

## 9. Add multiple skills without duplicates using `$addToSet`

Requirement: Add MongoDB, Docker, and Kubernetes without adding duplicate values.

```
db.employees_25BCE1087.updateOne(
  {
    empId: 140
  },
  {
    $addToSet: {
      skills: {
        $each: ["MongoDB", "Docker", "Kubernetes"]
      }
    }
  }
)
```

Verify:

```
db.employees_25BCE1087.findOne({
  empId: 140
})
```

### Difference between `$push` and `$addToSet`

| Feature                                   | `$push` | `$addToSet`          |
| ----------------------------------------- | ------- | -------------------- |
| Adds new values                           | Yes     | Yes                  |
| Allows duplicates                         | Yes     | No, for equal values |
| Supports `$each`                          | Yes     | Yes                  |
| Automatically removes existing duplicates | No      | No                   |

`$addToSet` prevents adding a value that already exists. It does not clean up duplicates that were already in the array.

## 10. Prepend three projects to the beginning of an array

Requirement: Add three projects at the start of employee 140's `projects` array.

```
db.employees_25BCE1087.updateOne(
  {
    empId: 140
  },
  {
    $push: {
      projects: {
        $each: [
          "AI Assistant",
          "Cloud Migration",
          "Data Portal"
        ],
        $position: 0
      }
    }
  }
)
```

Verify:

```
db.employees_25BCE1087.findOne({
  empId: 140
})
```

### Explanation

- `$each` inserts multiple values.
- `$position: 0` inserts the new values at the beginning.

The array begins with:

```
[
  "AI Assistant",
  "Cloud Migration",
  "Data Portal",
  ...
]
```

## 11. Remove the last skill using `$pop`

```
db.employees_25BCE1087.updateOne(
  {
    empId: 140
  },
  {
    $pop: {
      skills: 1
    }
  }
)
```

`$pop` removes one element from an array:

| Value | Action                   |
| ----- | ------------------------ |
| `1`   | Remove the last element  |
| `-1`  | Remove the first element |

To remove the first skill instead:

```
db.employees_25BCE1087.updateOne(
  { empId: 140 },
  { $pop: { skills: -1 } }
)
```

## 12. Prepend three skills and retain only the latest five

Requirement: For employee 140, prepend AI, Cloud, and Docker, then retain the first five skills.

```
db.employees_25BCE1087.updateOne(
  {
    empId: 140
  },
  {
    $push: {
      skills: {
        $each: ["AI", "Cloud", "Docker"],
        $position: 0,
        $slice: 5
      }
    }
  }
)
```

Verify:

```
db.employees_25BCE1087.findOne({
  empId: 140
})
```

### Explanation

- `$each` adds three values.
- `$position: 0` inserts them at the start.
- `$slice: 5` limits the resulting array to five elements.

The new skills appear first, followed by the earliest remaining skills, with everything after the fifth element removed.

Important: `$slice: 5` limits the array to five elements. It does not mean “keep the five most recently added elements” in every possible insertion arrangement. Here, the new elements are prepended, so they are retained first.

## 13. Delete one Mechanical employee

First inspect matching employees:

```
db.employees_25BCE1087.find({
  department: "Mechanical"
})
```

Then delete one matching employee:

```
db.employees_25BCE1087.deleteOne({
  department: "Mechanical"
})
```

`deleteOne()` removes just one matching document.

Because the sample contains only one Mechanical employee, Meera is deleted if the earlier commands have been executed as shown.

## 14. Delete all employees with performance scores below 40

First inspect the records:

```
db.employees_25BCE1087.find({
  performanceScore: {
    $lt: 40
  }
})
```

Then delete all matching documents:

```
db.employees_25BCE1087.deleteMany({
  performanceScore: {
    $lt: 40
  }
})
```

`deleteMany()` deletes every document satisfying the filter.

In the original sample data, Rohan has a performance score of 35, so he matches unless he has already been deleted or updated.

Practical exam habit: Run the matching `find()` before a delete query. It is a lot cheaper than discovering you deleted the wrong half of your collection.

# Final revision sheet

## Update operators

| Operator    | Purpose                                             | Example                               |
| ----------- | --------------------------------------------------- | ------------------------------------- |
| `$set`      | Assign a field value                                | `$set: {designation: "Developer"}`    |
| `$inc`      | Increase or decrease a number                       | `$inc: {salary: 5000}`                |
| `$rename`   | Rename a field                                      | `$rename: {contactNo: "phoneNumber"}` |
| `$max`      | Raise a value only if it is lower                   | `$max: {salary: 30000}`               |
| `$min`      | Lower a value only if it is higher                  | `$min: {performanceScore: 100}`       |
| `$push`     | Append to an array, duplicates allowed              | `$push: {skills: "MongoDB"}`          |
| `$addToSet` | Add to an array without adding an equal value twice | `$addToSet: {skills: "MongoDB"}`      |
| `$each`     | Specify multiple array values                       | `$each: ["AI", "Cloud"]`              |
| `$position` | Specify the insertion position                      | `$position: 0`                        |
| `$slice`    | Limit array length during `$push`                   | `$slice: 5`                           |
| `$pop`      | Remove first or last array element                  | `$pop: {skills: 1}`                   |

## CRUD operations

CRUD stands for Create, Read, Update, Delete.

| Operation | MongoDB methods                               |
| --------- | --------------------------------------------- |
| Create    | `insertOne()`, `insertMany()`                 |
| Read      | `find()`, `findOne()`                         |
| Update    | `updateOne()`, `updateMany()`, `replaceOne()` |
| Delete    | `deleteOne()`, `deleteMany()`                 |

## Verification commands

Use these whenever you want to inspect the current state of your lab database:

```
// Select the database
use UniversityDB

// Inspect course documents
db.courses_25BCE1087.find()

// Count course documents
db.courses_25BCE1087.countDocuments({})

// Switch to the company database
use companyDB

// Inspect employee documents
db.employees_25BCE1087.find()

// Count employee documents
db.employees_25BCE1087.countDocuments({})
```

## Three things to remember during the practical

1. Check your filter. `updateMany()` and `deleteMany()` affect every matching document.
2. Know the difference between `$set` and `replaceOne()`. `$set` changes selected fields; `replaceOne()` replaces the document's other fields too.
3. Array operators behave differently. `$push` allows duplicates, `$addToSet` avoids adding duplicate values, `$pop` removes one end element, and `$slice` limits the array length when used with `$push`.
