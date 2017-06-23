/*
 * Maman 15 course 20554 question 1 by Yuval Melamed, ID 035870864
 */

import java.util.NoSuchElementException;

/**
 * @author yuval.melamed
 */
public class SummingThread implements Runnable {

    // The collection with which to work upon
    private final SynchronizedCollection synchronizedCollection;

    // Setup link to collection
    public SummingThread(SynchronizedCollection synchronizedCollection) {
        this.synchronizedCollection = synchronizedCollection;
    }

    @Override
    public void run() {
        int i, j;

        // Loop while collection is not empty or about to be added to
        while (synchronizedCollection.getCount() > 1 || synchronizedCollection.getPendingAddCount() > 0) {

            // Only if collection is not currently empty
            if (synchronizedCollection.getCount() > 1) {
                try {
                    // Signal we're going to add 1 back before we take 1 integer
                    synchronizedCollection.incPendingAddCount();
                    i = synchronizedCollection.getInt();
                } catch (NoSuchElementException e) {
                    // We won't be adding anything if we couldn't get anything
                    synchronizedCollection.decPendingAddCount();
                    continue;
                }
                try {
                    // Try getting the 2nd integer
                    j = synchronizedCollection.getInt();
                } catch (NoSuchElementException e) {
                    // Add the 1st integer back if we failed reading the 2nd
                    synchronizedCollection.addInt(i);
                    synchronizedCollection.decPendingAddCount();
                    continue;
                }
                // Add back just the sum of the 2 integers
                synchronizedCollection.addInt(i + j);
                synchronizedCollection.decPendingAddCount();
            }
        }
    }
}
