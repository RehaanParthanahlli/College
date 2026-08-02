Which is bigger float or double? ---> Read Wrapper class, Why Static outside the class?
Bitwise operations!(Determine whether a number is even using bitwise operators) --> ( ! & << >> | XOR NXOR )
Island Qns!(Jst Remebr)

2D_Array & nD_Array(Read class bk notes)
    You can print(Array_name), you have to print(Array_name.toString())

String Operations & StringBuffer(Read class bk notes)
 - "AEIOUaeiou".indexOf(ch[i])==1

# Label
label_name:
    Code....
    continue label_name;
```
startBlock: {
    System.out.println("Inside block");
    if (true) {
        break startBlock; // exits the labeled block
    }
    System.out.println("This won't be printed");
}
System.out.println("Outside block");
```

# WrapperClass & Conversions(In Wrapper clss nts below)
    INT
        - String/char to int
        - Double/float to int
        - Boolean to int
    CHAR
    STRING
    BOOLEAN
    DOUBLE/FLOAT
    - Anything to string, int, double/float, char & Boolean.
# Wrapper class notes:
**In Java, wrapper classes let you treat primitive types (`int`, `float`, etc.) as objects, and type conversion rules (widening and narrowing) control how values move between different numeric types. Together, they make Java flexible when mixing primitives and objects, but you must understand automatic vs. explicit conversions to avoid errors or data loss.**

---
## 🔹 Wrapper Classes in Java
Wrapper classes are object representations of primitive types. They live in the `java.lang` package.

| Primitive Type | Wrapper Class |
|----------------|---------------|
| `byte`         | `Byte`        |
| `short`        | `Short`       |
| `int`          | `Integer`     |
| `long`         | `Long`        |
| `float`        | `Float`       |
| `double`       | `Double`      |
| `char`         | `Character`   |
| `boolean`      | `Boolean`     |

### Why Wrapper Classes?
- **Collections & Generics**: `ArrayList<Integer>` works, but `ArrayList<int>` does not.  
- **Null handling**: Wrappers can be `null`, primitives cannot.  
- **Utility methods**: e.g., `Integer.parseInt("123")`, `Double.toString(3.14)`.  
- **Object-oriented features**: Needed for reflection, synchronization, serialization.  [GeeksForGeeks](https://www.geeksforgeeks.org/java/wrapper-classes-java/)  [javathinking.com](https://www.javathinking.com/blog/wrapper-classes-in-java/)  

### Autoboxing & Unboxing
- **Autoboxing**: Automatic conversion of primitive → wrapper.  
  ```java
  int x = 10;
  Integer obj = x; // autoboxing
  ```
- **Unboxing**: Automatic conversion of wrapper → primitive.  
  ```java
  Integer obj = 20;
  int y = obj; // unboxing
  ```

---

## 🔹 Type Conversion in Java
Java supports two main kinds of type conversion between primitives:

### 1. Widening (Implicit Conversion)
- Smaller type → larger type (safe, no data loss).  
- Examples:
  ```java
  int i = 100;
  long l = i;       // int → long
  float f = l;      // long → float
  double d = f;     // float → double
  ```
- Order: `byte → short → int → long → float → double`  
- `char` can widen to `int`, `long`, `float`, `double`.  [GeeksForGeeks](https://www.geeksforgeeks.org/java/type-conversion-java-examples/)  [ZetCode](https://zetcode.com/java/type-conversions/)  

### 2. Narrowing (Explicit Conversion)
- Larger type → smaller type (risk of data loss).  
- Requires cast operator `(type)`.  
- Examples:
  ```java
  double d = 99.99;
  int i = (int) d;   // fractional part lost → 99
  byte b = (byte) 257; // overflow → 1 (257 % 256)
  ```
- Order: `double → float → long → int → short → char → byte`  

### Type Promotion in Expressions
- If one operand is `double`, all others become `double`.  
- If one operand is `float`, all others become `float`.  
- If one operand is `long`, all others become `long`.  
- Otherwise, smaller types (`byte`, `short`, `char`) are promoted to `int`.  [ZetCode](https://zetcode.com/java/type-conversions/)  

---

## ⚠️ Key Risks & Best Practices
- **Performance**: Wrappers are heavier than primitives; avoid unnecessary autoboxing in performance-critical code.  
- **Null safety**: Be careful when unboxing a `null` wrapper (throws `NullPointerException`).  
- **Data loss**: Narrowing conversions can truncate or overflow values. Always check ranges before casting.  

---

✅ **Takeaway:** Use **wrapper classes** when working with collections, generics, or APIs that require objects. Rely on **widening conversions** for safe automatic type changes, and use **explicit narrowing casts** carefully to avoid data loss.  

## 🔹 Converting `String` to Numbers in Java

### 1. Using `parseXxx()` (returns primitive)
- `Integer.parseInt("123")` → `int`
- `Float.parseFloat("3.14")` → `float`
- `Double.parseDouble("2.718")` → `double`
- `Long.parseLong("999999")` → `long`
- `Boolean.parseBoolean("true")` → `boolean`

```java
String s1 = "123";
int i = Integer.parseInt(s1);   // i = 123

String s2 = "3.14";
double d = Double.parseDouble(s2); // d = 3.14
```

---

### 2. Using `valueOf()` (returns wrapper object)
- `Integer.valueOf("123")` → `Integer`
- `Float.valueOf("3.14")` → `Float`
- `Double.valueOf("2.718")` → `Double`

```java
String s3 = "456";
Integer obj = Integer.valueOf(s3); // obj is an Integer object
```

---

### 3. Common Pitfalls
- If the string isn’t a valid number, you’ll get a `NumberFormatException`:
  ```java
  int x = Integer.parseInt("abc"); // ERROR
  ```
- Always validate or wrap in `try-catch`.

---

## 🔹 Converting Numbers Back to Strings
- `String.valueOf(123)` → `"123"`
- `Integer.toString(123)` → `"123"`
- `Double.toString(3.14)` → `"3.14"`

```java
int num = 789;
String str = String.valueOf(num); // "789"
```

---

✅ **Takeaway:**  
- Primitive ↔ Wrapper conversions happen automatically (autoboxing/unboxing).  
- String ↔ Number conversions require explicit methods (`parseXxx`, `valueOf`, `toString`).  


# Constructor(Just copy constructor is like ```class obj1 = obj2```) & Objects basic