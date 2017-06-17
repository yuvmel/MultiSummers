
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yuval M.
 */
public class SynchronizedCollection {

    private final LinkedList<Integer> collection;
    private int pendingAddCount = 0;

    public SynchronizedCollection() {
        collection = new LinkedList<>();
    }

    public SynchronizedCollection(List list) {
        collection = new LinkedList<>(list);
    }

    public synchronized int getInt() {
        return collection.remove();
    }

    public synchronized void addInt(int i) {
        collection.add(i);
    }

    public synchronized int getCount() {
        return collection.size();
    }

    public synchronized int getPendingAddCount() {
        return pendingAddCount;
    }

    public synchronized void incPendingAddCount() {
        pendingAddCount++;
    }

    public synchronized void decPendingAddCount() {
        pendingAddCount--;
    }

    @Override
    public synchronized String toString() {
        return collection.toString();
    }
}
