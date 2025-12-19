package LRU;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class LRUCache {
    private int capacity;
    private LinkedHashMap<Integer, Integer> cache;
    private Statistics stats;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.stats = new Statistics();
        // LinkedHashMap with access order (true parameter)
        this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public void accessPage(int page) {
        if (cache.containsKey(page)) {
            handlePageHit(page);
        } else {
            handlePageFault(page);
        }
        displayCurrentState();
    }

    private void handlePageHit(int page) {
        stats.incrementPageHits();
        cache.get(page); // Updates access order
        System.out.println("LRU.Page " + page + " → HIT ✓");
    }

    private void handlePageFault(int page) {
        stats.incrementPageFaults();

        if (cache.size() >= capacity) {
            int lruPage = cache.keySet().iterator().next();
            System.out.println("LRU.Page " + page + " → MISS ✗ (Replaced: " + lruPage + ")");
        } else {
            System.out.println("LRU.Page " + page + " → MISS ✗ (Empty frame)");
        }

        cache.put(page, page);
    }

    private void displayCurrentState() {
        System.out.print("Frames: [");
        List<Integer> pages = new ArrayList<>(cache.keySet());
        for (int i = 0; i < pages.size(); i++) {
            System.out.print(pages.get(i));
            if (i < pages.size() - 1) System.out.print(", ");
        }
        System.out.println("]\n");
    }

    public Statistics getStatistics() {
        return stats;
    }
}