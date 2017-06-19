/*
 * Maman 15 course 20554 question 1 by Yuval Melamed, ID 035870864
 */

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

        while (synchronizedCollection.getPendingAddCount() > 0
                || synchronizedCollection.getCount() > 1) {
            while (synchronizedCollection.getCount() > 1) {
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
                    continue;
                } finally {
                    synchronizedCollection.decPendingAddCount();
                }
                synchronizedCollection.addInt(i + j);
            }
        }
    }
}
