# Code
==================================================================================
    JAVA REVISION HUB - Complete Guide with Comments
    Topics: Data Types, Access Modifiers, Command Line Args, Conversions,
            Input/Output, Control Flow (if-else, switch), Basic Functions
--================================================================================
```java
import java.util.Scanner;  // For taking user input
import java.io.PrintStream; // Easy Output

public class JavaRevisionHub {
    PrintStream out = System.out;
    out.println("With the help of PrintStream, Output became easy!");
    //========================================================================
    // SECTION 1: DATA TYPES & ACCESS MODIFIERS
    //========================================================================
    // Access Modifiers in Java:
    // public    -> accessible from ANYWHERE (any class, any package)
    // private   -> accessible ONLY within the same class
    // protected -> accessible within same package + subclasses (inheritance)
    // default   -> accessible ONLY within the same package (no keyword written)

    // PRIMITIVE DATA TYPES (store actual values, not references)
    // These are the 8 primitive types in Java:

    public byte    publicByte    = 127;          // 1 byte  (-128 to 127)
    private byte   privateByte   = -128;         // private: only this class
    protected byte protectedByte = 0;            // protected: package + subclasses
    byte           defaultByte   = 50;           // default (package-private)

    public short   publicShort   = 32000;        // 2 bytes (-32,768 to 32,767)
    public int     publicInt     = 2147483647;   // 4 bytes (whole numbers)
    public long    publicLong    = 9223372036854775807L; // 8 bytes, ends with 'L'

    public float   publicFloat   = 3.14f;        // 4 bytes (decimal), ends with 'f'
    public double  publicDouble  = 3.14159265359;// 8 bytes (more precision)

    public char    publicChar    = 'A';          // 2 bytes (single character, single quotes)
    public boolean publicBoolean = true;         // 1 bit (true or false only)

    // REFERENCE DATA TYPES (store memory addresses/references)
    public String  publicString  = "Hello";      // String is a class, not primitive
    public int[]   publicArray   = {1, 2, 3};    // Array is a reference type

    //========================================================================
    // SECTION 2: COMMAND LINE ARGUMENTS
    //========================================================================
    // Command line arguments are passed when running the program:
    // Syntax: java JavaRevisionHub arg1 arg2 arg3
    // They are received as a String array in the main method

    public static void main(String[] args) {
        // 'args' is a String array containing all command line arguments
        // Example: java JavaRevisionHub Alice 25 3.14
        //          args[0] = "Alice", args[1] = "25", args[2] = "3.14"

        System.out.println("=== COMMAND LINE ARGUMENTS ===");
        System.out.println("Number of arguments: " + args.length);

        // Check if arguments were provided
        if (args.length > 0) {
            System.out.println("First argument: " + args[0]);
        }
        if (args.length > 1) {
            System.out.println("Second argument: " + args[1]);
            // Convert String argument to int using parseInt()
            int age = Integer.parseInt(args[1]);
            System.out.println("Age as integer: " + age);
        }
        if (args.length > 2) {
            // Convert String argument to double using parseDouble()
            double value = Double.parseDouble(args[2]);
            System.out.println("Value as double: " + value);
        }

        // Print all arguments using a loop
        System.out.println("\nAll arguments:");
        for (int i = 0; i < args.length; i++) {
            System.out.println("  args[" + i + "] = " + args[i]);
        }

        //========================================================================
        // SECTION 3: EXPLICIT AND IMPLICIT CONVERSIONS (TYPE CASTING)
        //========================================================================
        // IMPLICIT CONVERSION (Widening): Java does it automatically
        // Happens when converting smaller type to larger type (no data loss)
        // byte -> short -> int -> long -> float -> double
        // char -> int

        System.out.println("\n=== IMPLICIT CONVERSIONS (Widening) ===");
        byte smallNum = 100;
        int biggerNum = smallNum;        // byte -> int (automatic)
        long biggestNum = biggerNum;     // int -> long (automatic)
        float decimalNum = biggestNum;   // long -> float (automatic)
        double preciseNum = decimalNum;  // float -> double (automatic)

        System.out.println("byte " + smallNum + " -> int " + biggerNum);
        System.out.println("int " + biggerNum + " -> long " + biggestNum);
        System.out.println("long " + biggestNum + " -> float " + decimalNum);
        System.out.println("float " + decimalNum + " -> double " + preciseNum);

        char letter = 'A';               // 'A' has ASCII value 65
        int asciiValue = letter;         // char -> int (automatic, gives 65)
        System.out.println("char '" + letter + "' -> int " + asciiValue);

        // EXPLICIT CONVERSION (Narrowing): Programmer must do it manually
        // Happens when converting larger type to smaller type (possible data loss)
        // double -> float -> long -> int -> short -> byte
        // Requires casting syntax: (targetType) value

        System.out.println("\n=== EXPLICIT CONVERSIONS (Narrowing) ===");
        double largeDecimal = 123.987;
        int truncated = (int) largeDecimal;  // double -> int (decimal part LOST)
        System.out.println("double " + largeDecimal + " -> int " + truncated + " (decimal lost)");

        int bigInteger = 130;
        byte overflowByte = (byte) bigInteger; // int -> byte (overflow! wraps around)
        System.out.println("int " + bigInteger + " -> byte " + overflowByte + " (overflow, wraps)");

        double preciseValue = 65.7;
        char convertedChar = (char) preciseValue; // double -> char (decimal lost)
        System.out.println("double " + preciseValue + " -> char '" + convertedChar + "' (decimal lost)");

        // String to primitive conversions (Parsing)
        System.out.println("\n=== STRING TO PRIMITIVE CONVERSIONS ===");
        String numberStr = "42";
        int parsedInt = Integer.parseInt(numberStr);           // String -> int
        double parsedDouble = Double.parseDouble("3.14");      // String -> double
        boolean parsedBool = Boolean.parseBoolean("true");     // String -> boolean
        System.out.println("String \"42\" -> int: " + parsedInt);
        System.out.println("String \"3.14\" -> double: " + parsedDouble);
        System.out.println("String \"true\" -> boolean: " + parsedBool);

        // Primitive to String conversions
        System.out.println("\n=== PRIMITIVE TO STRING CONVERSIONS ===");
        String fromInt = String.valueOf(100);        // int -> String
        String fromDouble = String.valueOf(9.8);     // double -> String
        String fromBool = String.valueOf(false);     // boolean -> String
        String fromChar = String.valueOf('Z');       // char -> String
        // OR use concatenation with empty string
        String quickConvert = "" + 500;              // int -> String (shortcut)
        System.out.println("int 100 -> String: \"" + fromInt + "\"");
        System.out.println("double 9.8 -> String: \"" + fromDouble + "\"");
        System.out.println("boolean false -> String: \"" + fromBool + "\"");
        System.out.println("char 'Z' -> String: \"" + fromChar + "\"");
        System.out.println("int 500 -> String (quick): \"" + quickConvert + "\"");

        //========================================================================
        // SECTION 4: INPUT HANDLING (Scanner class)
        //========================================================================
        // Scanner is used to read input from keyboard (System.in)
        // Must import java.util.Scanner at the top

        System.out.println("\n=== INPUT HANDLING ===");
        Scanner scanner = new Scanner(System.in);  // Create Scanner object

        // Reading different data types from user
        System.out.print("Enter an integer: ");
        int userInt = scanner.nextInt();           // Reads an integer
        System.out.println("You entered int: " + userInt);

        System.out.print("Enter a float: ");
        float userFloat = scanner.nextFloat();     // Reads a float
        System.out.println("You entered float: " + userFloat);

        System.out.print("Enter a double: ");
        double userDouble = scanner.nextDouble();  // Reads a double
        System.out.println("You entered double: " + userDouble);

        // nextLine() issue: after nextInt/nextDouble, newline remains in buffer
        scanner.nextLine();  // Consume the leftover newline character!

        System.out.print("Enter a string (nextLine): ");
        String userString = scanner.nextLine();    // Reads entire line including spaces
        System.out.println("You entered string: \"" + userString + "\"");

        System.out.print("Enter a word (next): ");
        String userWord = scanner.next();          // Reads single word (stops at space)
        System.out.println("You entered word: \"" + userWord + "\"");

        System.out.print("Enter a character: ");
        char userChar = scanner.next().charAt(0);  // Read string, take first char
        System.out.println("You entered char: '" + userChar + "'");

        System.out.print("Enter a boolean (true/false): ");
        boolean userBool = scanner.nextBoolean();  // Reads true or false
        System.out.println("You entered boolean: " + userBool);

        //========================================================================
        // SECTION 5: OUTPUT METHODS (printf, print, println)
        //========================================================================
        // System.out.print()   -> prints text, cursor stays on same line
        // System.out.println() -> prints text, moves cursor to next line
        // System.out.printf()  -> prints formatted text using format specifiers

        System.out.println("\n=== OUTPUT METHODS ===");

        // --- print() vs println() ---
        System.out.print("This is print() - ");
        System.out.print("same line ");
        System.out.println("now println() moves to next line");
        System.out.println("This is a new line");

        // --- println() with string concatenation using + ---
        String name = "Alice";
        int age = 20;
        System.out.println("\n--- String Concatenation with + ---");
        System.out.println("Name: " + name);                    // String + String
        System.out.println("Age: " + age);                      // String + int (auto convert)
        System.out.println("Next year age: " + (age + 1));      // Parentheses for math first
        System.out.println("Name is " + name + ", Age is " + age); // Multiple concatenations

        // --- printf() with format specifiers ---
        // %d -> integer (byte, short, int, long)
        // %f -> floating point (float, double)
        // %c -> character
        // %s -> string
        // %b -> boolean
        // %n or \n -> newline
        // %.2f -> float with 2 decimal places
        // %10d -> integer with width 10 (right-aligned)
        // %-10s -> string with width 10 (left-aligned)
        // %08d -> integer padded with zeros to width 8

        System.out.println("\n--- printf() Format Specifiers ---");
        int num = 42;
        double pi = 3.14159;
        char grade = 'A';
        String subject = "Math";
        boolean passed = true;

        System.out.printf("Integer: %d%n", num);               // %d for integers
        System.out.printf("Float: %f%n", pi);                   // %f for floats
        System.out.printf("Char: %c%n", grade);                // %c for characters
        System.out.printf("String: %s%n", subject);            // %s for strings
        System.out.printf("Boolean: %b%n", passed);            // %b for boolean

        // Precision and width
        System.out.printf("\nPi to 2 decimals: %.2f%n", pi);   // %.2f = 2 decimal places
        System.out.printf("Pi to 4 decimals: %.4f%n", pi);     // %.4f = 4 decimal places
        System.out.printf("Right-aligned (width 10): [%10d]%n", num);   // width 10, right
        System.out.printf("Left-aligned (width 10): [%-10d]%n", num);    // width 10, left
        System.out.printf("Zero-padded (width 8): %08d%n", num);        // pad with zeros
        System.out.printf("String width 15: [%15s]%n", subject);        // string width
        System.out.printf("Multiple values: %s scored %d, grade %c%n", name, 95, 'A');

        //========================================================================
        // SECTION 6: CONTROL FLOW - if-else STATEMENTS
        //========================================================================
        // if      -> executes block if condition is true
        // else if -> checks another condition if previous was false
        // else    -> executes if all above conditions are false

        System.out.println("\n=== IF-ELSE STATEMENTS ===");

        int score = 85;

        // Simple if
        if (score >= 60) {
            System.out.println("You passed!");
        }

        // if-else
        if (score >= 90) {
            System.out.println("Grade: A");
        } else {
            System.out.println("Grade: Not A");
        }

        // if-else if-else ladder
        System.out.println("\n--- Grade Calculator ---");
        if (score >= 90) {
            System.out.println("Grade: A (Excellent)");
        } else if (score >= 80) {
            System.out.println("Grade: B (Good)");
        } else if (score >= 70) {
            System.out.println("Grade: C (Average)");
        } else if (score >= 60) {
            System.out.println("Grade: D (Pass)");
        } else {
            System.out.println("Grade: F (Fail)");
        }

        // Nested if-else
        System.out.println("\n--- Nested If-Else ---");
        int age2 = 25;
        boolean hasLicense = true;

        if (age2 >= 18) {
            System.out.println("You are an adult.");
            if (hasLicense) {
                System.out.println("You can drive.");
            } else {
                System.out.println("Get a license to drive.");
            }
        } else {
            System.out.println("You are a minor. Cannot drive.");
        }

        // Ternary operator (shorthand if-else)
        // syntax: condition ? value_if_true : value_if_false
        int a = 10, b = 20;
        int max = (a > b) ? a : b;   // if a>b, max=a, else max=b
        System.out.println("\nTernary: max of " + a + " and " + b + " is " + max);
        String result = (score >= 60) ? "Pass" : "Fail";
        System.out.println("Result: " + result);

        // Logical operators in conditions
        // && -> AND (both must be true)
        // || -> OR (at least one must be true)
        // !  -> NOT (reverses the boolean)
        System.out.println("\n--- Logical Operators ---");
        int x = 15;
        if (x > 10 && x < 20) {      // AND: both conditions true
            System.out.println(x + " is between 10 and 20");
        }
        if (x < 5 || x > 10) {       // OR: at least one true
            System.out.println(x + " is either < 5 or > 10");
        }
        if (!(x == 10)) {            // NOT: reverses the condition
            System.out.println(x + " is NOT equal to 10");
        }

        //========================================================================
        // SECTION 7: CONTROL FLOW - switch STATEMENT
        //========================================================================
        // switch compares a variable against multiple constant values
        // break prevents "fall-through" to next case
        // default executes if no case matches

        System.out.println("\n=== SWITCH STATEMENTS ===");

        // switch with int
        int dayNum = 3;
        System.out.println("--- Switch with int ---");
        switch (dayNum) {
            case 1:
                System.out.println("Monday");
                break;  // Exit switch, without break it "falls through"
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Invalid day number");
        }

        // switch with char
        char grade2 = 'B';
        System.out.println("\n--- Switch with char ---");
        switch (grade2) {
            case 'A':
                System.out.println("Excellent!");
                break;
            case 'B':
                System.out.println("Good job!");
                break;
            case 'C':
                System.out.println("Average");
                break;
            case 'D':
                System.out.println("Need improvement");
                break;
            case 'F':
                System.out.println("Failed");
                break;
            default:
                System.out.println("Invalid grade");
        }

        // switch with String (Java 7+)
        String color = "red";
        System.out.println("\n--- Switch with String ---");
        switch (color.toLowerCase()) {
            case "red":
                System.out.println("Stop!");
                break;
            case "yellow":
                System.out.println("Caution!");
                break;
            case "green":
                System.out.println("Go!");
                break;
            default:
                System.out.println("Invalid color");
        }

        // switch without break (fall-through behavior)
        System.out.println("\n--- Fall-through Example ---");
        int month = 3;
        switch (month) {
            case 12: case 1: case 2:
                System.out.println("Winter");
                break;
            case 3: case 4: case 5:
                System.out.println("Spring");
                break;
            case 6: case 7: case 8:
                System.out.println("Summer");
                break;
            case 9: case 10: case 11:
                System.out.println("Autumn");
                break;
            default:
                System.out.println("Invalid month");
        }

        // Enhanced switch (Java 14+) - arrow syntax, no break needed
        // Note: This requires Java 14 or higher
        /*
        System.out.println("\n--- Enhanced Switch (Java 14+) ---");
        switch (dayNum) {
            case 1 -> System.out.println("Monday");
            case 2 -> System.out.println("Tuesday");
            case 3 -> System.out.println("Wednesday");
            case 4 -> System.out.println("Thursday");
            case 5 -> System.out.println("Friday");
            case 6, 7 -> System.out.println("Weekend");
            default -> System.out.println("Invalid");
        }
        */

        //========================================================================
        // SECTION 8: BASIC FUNCTIONS (METHODS)
        //========================================================================
        // Methods are reusable blocks of code
        // Syntax: accessModifier returnType methodName(parameters) { body }
        // void means the method returns nothing

        System.out.println("\n=== BASIC FUNCTIONS (METHODS) ===");

        // Create an instance (object) to call non-static methods
        JavaRevisionHub obj = new JavaRevisionHub();

        // Calling methods with no parameters and no return value
        obj.sayHello();

        // Calling method with parameters
        obj.greet("Bob");

        // Calling method with return value
        int sum = obj.add(10, 20);
        System.out.println("Sum returned: " + sum);

        // Calling method with multiple parameters of different types
        obj.displayInfo("Charlie", 22, 85.5);

        // Calling method that returns a boolean
        boolean isEven = obj.checkEven(14);
        System.out.println("Is 14 even? " + isEven);

        // Calling method with array parameter
        int[] numbers = {5, 10, 15, 20};
        int total = obj.sumArray(numbers);
        System.out.println("Sum of array: " + total);

        // Calling overloaded methods (same name, different parameters)
        System.out.println("\n--- Method Overloading ---");
        System.out.println("multiply(int, int): " + obj.multiply(5, 3));
        System.out.println("multiply(int, int, int): " + obj.multiply(2, 3, 4));
        System.out.println("multiply(double, double): " + obj.multiply(2.5, 4.0));

        // Calling static method (no object needed)
        System.out.println("\n--- Static Method ---");
        double circleArea = JavaRevisionHub.calculateCircleArea(5.0);
        /* Alternatively:
        double circleArea = CalculateCircleArea(5.0);

        However, the first approach is safer because it explicitly specifies 
        the class name. This is especially useful when multiple classes 
        contain methods with the same name. */

        System.out.println("Area of circle (r=5): " + circleArea);

        // Calling method that uses varargs (variable arguments)
        System.out.println("\n--- Varargs Method ---");
        int varargsSum = obj.sumAll(1, 2, 3, 4, 5);
        System.out.println("Sum of 1,2,3,4,5: " + varargsSum);

        // Recursion example
        System.out.println("\n--- Recursion ---");
        int factorial = obj.factorial(5);
        System.out.println("5! = " + factorial);

        //========================================================================
        // SECTION 9: PASS BY VALUE DEMONSTRATION
        //========================================================================
        // Java is ALWAYS pass-by-value (copies are passed, not references)
        // For primitives: copy of value is passed
        // For objects: copy of reference is passed (but object can be modified)

        System.out.println("\n=== PASS BY VALUE ===");
        int original = 100;
        System.out.println("Before method: " + original);
        obj.modifyValue(original);
        System.out.println("After method: " + original + " (unchanged!)");

        //========================================================================
        // Clean up scanner
        scanner.close();

        System.out.println("\n========================================");
        System.out.println("  REVISION COMPLETE! Read the comments!");
        System.out.println("========================================");
    }

    //========================================================================
    // METHOD DEFINITIONS (outside main, inside class)
    //========================================================================

    // Method with NO parameters, NO return value (void)
    public void sayHello() {
        System.out.println("\nHello from sayHello() method!");
    }

    // Method with ONE parameter, NO return value
    public void greet(String name) {
        System.out.println("Hello, " + name + "! Welcome!");
    }

    // Method with parameters, RETURNS an int
    public int add(int num1, int num2) {
        int result = num1 + num2;
        return result;  // return statement sends value back to caller
    }

    // Method with multiple parameters of different types
    public void displayInfo(String name, int age, double marks) {
        System.out.println("Name: " + name + ", Age: " + age + ", Marks: " + marks);
    }

    // Method that returns boolean
    public boolean checkEven(int number) {
        return (number % 2 == 0);  // returns true if even, false if odd
    }

    // Method that takes an array as parameter
    public int sumArray(int[] arr) {
        int sum = 0;
        for (int num : arr) {      // enhanced for-each loop
            sum += num;
        }
        return sum;
    }

    // METHOD OVERLOADING: Same method name, different parameter lists
    // Compiler decides which to call based on arguments

    // Overload 1: two int parameters
    public int multiply(int a, int b) {
        return a * b;
    }

    // Overload 2: three int parameters
    public int multiply(int a, int b, int c) {
        return a * b * c;
    }

    // Overload 3: two double parameters
    public double multiply(double a, double b) {
        return a * b;
    }

    // STATIC METHOD: Belongs to class, not object. Called using ClassName.method()
    public static double calculateCircleArea(double radius) {
        return Math.PI * radius * radius;  // Math.PI is a constant (3.14159...)
    }

    // VARARGS: Variable number of arguments (treated as array inside method)
    public int sumAll(int... numbers) {  // int... means 0 or more int arguments
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        return sum;
    }

    // RECURSIVE METHOD: Method that calls itself
    public int factorial(int n) {
        // Base case: stops recursion
        if (n <= 1) {
            return 1;
        }
        // Recursive case: calls itself with smaller value
        return n * factorial(n - 1);
    }

    // Demonstrates pass-by-value (primitive won't change outside)
    public void modifyValue(int value) {
        value = 999;  // Only local copy changes
        System.out.println("Inside method: " + value);
    }
}```