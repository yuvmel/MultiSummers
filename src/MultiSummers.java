/*
 * Maman 15 course 20554 question 1 by Yuval Melamed, ID 035870864
 */

import java.text.NumberFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class
 */
public class MultiSummers {

    // Upper limit for random integer (lower is 1)
    private static final int MAX_RAND = 100;

    // Utility function for getting a valid number from user
    private static int getPositiveInt(String message) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (i <= 0) {
            try {
                System.out.print(message);
                i = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid number, please try again!");
                scanner.nextLine();
            }
        }
        return i;
    }

    public static void main(String[] args) {
        // User input
        int n = getPositiveInt("Enter array size, n > 0, e.g. 1,000: ");
        int m = getPositiveInt("Enter thread count, m > 0, e.g. 100: ");

        // Creating array
        System.out.println("Creating array of " + NumberFormat.getIntegerInstance().format(n)
                + " random (1-" + NumberFormat.getIntegerInstance().format(MAX_RAND) + ") integers...");
        int array[] = null;
        try {
            array = new int[n];
        } catch (OutOfMemoryError e) {
            System.out.println("Error allocating memory for array - too big?");
            System.exit(0);
        }
        long sum = 0;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(MAX_RAND) + 1;
            sum += array[i];
            if (sum > Integer.MAX_VALUE) {
                System.out.println("Array size too big to hold its own sum as integer!");
                System.exit(0);
            }
        }
        System.out.println("Single-thread calculation of sum (during creation) = "
                + NumberFormat.getIntegerInstance().format(sum));

        // Creating collection
        System.out.println("Creating a synchronized collection out of the array...");
        SynchronizedCollection synchronizedCollection = new SynchronizedCollection(array);

        // Creating threads
        System.out.println("Creating " + NumberFormat.getIntegerInstance().format(m)
                + " threads to sum the integers...");
        ExecutorService executorService = Executors.newFixedThreadPool(m);
        for (int i = 0; i < m; i++) {
            executorService.execute(new SummingThread(synchronizedCollection));
        }
        executorService.shutdown();

        System.out.println("Waiting for all the threads to finish...");
        while (true) {
            try {
                if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
            }
        }
        int mt_sum = synchronizedCollection.getInt();
        System.out.print("Multi-thread sum = "
                + NumberFormat.getIntegerInstance().format(mt_sum));
        if (mt_sum == sum) {
            System.out.println(" (as expected)");
        } else {
            System.out.printf(" (hmm...)");
        }
    }
}
