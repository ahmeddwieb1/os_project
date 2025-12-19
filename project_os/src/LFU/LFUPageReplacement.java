package LFU;

import utils.InputHandler;

import java.util.*;

public class LFUPageReplacement {
    public static void main(String[] args) {
        DisplayManager display = new DisplayManager();
        InputHandler input = new InputHandler();

        // Display header
        display.printHeader();

        // Get user input
//        int frames = input.getFrameCount();
//        int[] pages = input.getPageReferenceString();
//        int[] pages = {1,2,3,4, 7, 0,1,2,0,3,0, 4, 2, 3,0, 3,0, 3,2,
//                1, 2,0,1, 7,0, 1};
        int[] pages ={ 1, 2, 3, 4, 2, 1, 5 };
        int frames = 3;
        display.printPageReferenceString(pages);

        int pagefaults = pageFaults(pages.length - 1, frames, pages);

        System.out.println("Page Faults = "
                + pagefaults);
        System.out.println("Page Hits = "
                + (pages.length - pagefaults));
        double faultRate = (pagefaults * 100.0) / pages.length;
        System.out.println("Fault Rate = " + faultRate + "%");

    }

    /* Counts no. of page faults */
    static int pageFaults(int n, int c, int[] pages) {
        // Initialise count to 0
        int count = 0;

        // To store elements in memory of size c
        List<Integer> memory = new ArrayList<Integer>();
        // To store frequency of pages
        Map<Integer, Integer> freq
                = new HashMap<Integer, Integer>();

        for (int i = 0; i <= n ; i++) {
            // Find if element is present in memory or not
            int idx = memory.indexOf(pages[i]);

            // If element is not present
            if (idx == -1) {
                // If memory is full
                if (memory.size() == c) {
                    // Decrease the frequency
                    int leastFreqPage = memory.get(0);
                    freq.put(leastFreqPage,
                            freq.get(leastFreqPage) - 1);

                    // Remove the first element as
                    // It is least frequently used
                    memory .remove(0);
                }

                // Add the element at the end of memory
                memory .add(pages[i]);
//               for (int j = 0 ; j <= c; j++) {
//                   System.out.print(memory .get(j)+" ");
//               }
                // Increase its frequency
                freq.put(pages[i],
                        freq.getOrDefault(pages[i], 0) + 1);

                // Increment the count
                count++;
            } else {
// If element is present Remove the element And add it at the end
// Increase its frequency
                int page = memory .remove(idx);
                memory .add(page);
                freq.put(page, freq.get(page) + 1);
            }

            // Compare frequency with other pages
            // starting from the 2nd last page
            int k = memory .size() - 2;

            // Sort the pages based on their frequency
            // And time at which they arrive
            // if frequency is same
            // then, the page arriving first must be placed
            // first
            while (k >= 0
                    && freq.get(memory .get(k))
                    > freq.get(memory .get(k + 1))) {
                Collections.swap(memory , k, k + 1);
                k--;
            }
        }

        // Return total page faults
        return count;
    }

    /* Driver program to test pageFaults function*/

}
