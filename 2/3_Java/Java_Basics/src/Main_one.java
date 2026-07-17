import java.util.Arrays;  // For utility methods

public class Main_one {

    public static void main(String[] args) {
        System.out.println("=== ARRAYS REVISION HUB ===");

        //========================================================================
        // SECTION 1: DECLARATION & INITIALIZATION
        //========================================================================
        int[] numbers = new int[5];   // array of size 5 (default values = 0)
        numbers[0] = 10;
        numbers[1] = 20;
        numbers[2] = 30;

        int[] marks = {85, 90, 75, 88, 92}; // shortcut initialization

        System.out.println("First mark: " + marks[0]);
        System.out.println("Last mark: " + marks[marks.length - 1]);
        System.out.println("Array length: " + marks.length);

        //========================================================================
        // SECTION 2: LOOPING THROUGH ARRAYS
        //========================================================================
        System.out.println("\n--- For Loop ---");
        for (int i = 0; i < marks.length; i++) {
            System.out.println("marks[" + i + "] = " + marks[i]);
        }

        System.out.println("\n--- For-Each Loop ---");
        for (int m : marks) {
            System.out.println(m);
        }

        //========================================================================
        // SECTION 3: MULTI-DIMENSIONAL ARRAYS
        //========================================================================
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        System.out.println("\n--- 2D Array ---");
        System.out.println("matrix[0][0] = " + matrix[0][0]);
        System.out.println("matrix[2][2] = " + matrix[2][2]);

        System.out.println("\nMatrix elements:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        //========================================================================
        // SECTION 4: COMMON OPERATIONS
        //========================================================================
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        System.out.println("\nSum of marks: " + sum);

        int max = marks[0];
        for (int m : marks) {
            if (m > max) max = m;
        }
        System.out.println("Maximum mark: " + max);

        //========================================================================
        // SECTION 5: ARRAYS UTILITY CLASS
        //========================================================================
        System.out.println("\n--- Arrays Utility Methods ---");
        System.out.println("Original: " + Arrays.toString(marks));

        Arrays.sort(marks);
        System.out.println("Sorted: " + Arrays.toString(marks));

        int index = Arrays.binarySearch(marks, 90);
        System.out.println("Index of 90: " + index);

        int[] copy = Arrays.copyOf(marks, marks.length);
        System.out.println("Copied array: " + Arrays.toString(copy));

        System.out.println("\n=== ARRAYS REVISION COMPLETE ===");
    }
}
