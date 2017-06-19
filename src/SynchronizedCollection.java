/*
 * Maman 15 course 20554 question 1 by Yuval Melamed, ID 035870864
 */

import java.util.LinkedList;

/**
 * Created by Yuval M.
 */
public class SynchronizedCollection {

    private final LinkedList<Integer> collection;
    private int pendingAddCount = 0;

    public SynchronizedCollection(int[] array) {
        collection = new LinkedList<>();
        for (int i : array) {
            collection.add(i);
        }
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
}
