
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int MAX_RAND = 100;

    private static int getPositiveInt(String message) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (i == 0) {
            try {
                System.out.println(message);
                i = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int n = getPositiveInt("Enter array size, n>0:");
        int m = getPositiveInt("Enter thread count, m>0:");

        ArrayList<Integer> arrayList = new ArrayList<>(n);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arrayList.add(random.nextInt(MAX_RAND) + 1);
        }

        SynchronizedCollection synchronizedCollection
                = new SynchronizedCollection(arrayList);
        System.out.println(synchronizedCollection);

        ExecutorService executorService = Executors.newFixedThreadPool(m);
        for (int i = 0; i < m; i++) {
            executorService.execute(new SummingThread(synchronizedCollection));
        }
        executorService.shutdown();

        System.out.println(synchronizedCollection);
    }
}
