
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuval.melamed
 */
public class SummingThread implements Runnable {

    private final SynchronizedCollection synchronizedCollection;

    public SummingThread(SynchronizedCollection synchronizedCollection) {
        this.synchronizedCollection = synchronizedCollection;
    }

    @Override
    public void run() {
        int i, j;

        while (synchronizedCollection.getCount() > 1
                || synchronizedCollection.getPendingAddCount() > 0) {
            try {
                synchronizedCollection.incPendingAddCount();
                i = synchronizedCollection.getInt();
            } catch (NoSuchElementException e) {
                synchronizedCollection.decPendingAddCount();
                continue;
            }
            try {
                j = synchronizedCollection.getInt();
            } catch (NoSuchElementException e) {
                synchronizedCollection.addInt(i);
                synchronizedCollection.decPendingAddCount();
                continue;
            }
            synchronizedCollection.addInt(i + j);
            synchronizedCollection.decPendingAddCount();
        }
    }
}
