import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 2, 3));
        SynchronizedCollection synchronizedCollection = new SynchronizedCollection(a);

        System.out.println(synchronizedCollection);
    }
}
