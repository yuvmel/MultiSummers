/*
 * Maman 15 course 20554 question 1 by Yuval Melamed, ID 035870864
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int MAX_RAND = 100;

    private static int getPositiveInt(String message, int limit) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (i == 0) {
            try {
                System.out.print(message);
                i = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
            }
            if (i > limit) {
                System.out.printf("Try something less than %d please...%n",
                        limit);
                i = 0;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int n = getPositiveInt("Enter valid array size, n: ", 20000000);
        int m = getPositiveInt("Enter valid thread count, m: ", 50000);

        System.out.printf("Creating array of %d random (1-%d) integers...",
                n, MAX_RAND);
        int array[] = new int[n];
        int sum = 0;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(MAX_RAND) + 1;
            sum += array[i];
        }
        System.out.printf("sum: %d%n", sum);

        System.out.println("Creating synchronized collection out of array...");
        SynchronizedCollection synchronizedCollection
                = new SynchronizedCollection(array);

        System.out.printf("Creating %d threads to sum the integers...%n", m);
        ExecutorService executorService = Executors.newFixedThreadPool(m);
        for (int i = 0; i < m; i++) {
            executorService.execute(new SummingThread(synchronizedCollection));
        }
        executorService.shutdown();

        int seconds = 0;
        System.out.println("Waiting for all threads to finish...");
        while (true) {
            try {
                if (executorService.awaitTermination(1, TimeUnit.NANOSECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
            }
            System.out.printf("Active threads: %8d, Integers left: %d + %d%n",
                    Thread.activeCount(), synchronizedCollection.getCount(),
                    synchronizedCollection.getPendingAddCount());
        }
        System.out.println(synchronizedCollection.getInt());
    }
}
