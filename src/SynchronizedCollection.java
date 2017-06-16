import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yuval M.
 */
public class SynchronizedCollection {
    private LinkedList<Integer> collection;

    public SynchronizedCollection() {
        collection = new LinkedList<>();
    }

    public SynchronizedCollection(List list) {
        collection = new LinkedList<Integer>(list);
    }

    synchronized int getInt() {
        return collection.remove();
    }

    synchronized void addInt(int i) {
        collection.add(i);
    }

    @Override
    public String toString() {
        return collection.toString();
    }
}
