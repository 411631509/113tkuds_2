import java.util.*;

public class MultiLevelCacheSystem {

    private static class CacheEntry {
        final int key;
        String value;
        int freq;
        long ts;
        CacheEntry(int k, String v, long t) { key=k; value=v; ts=t; freq=1; }
    }

    private static class Level {
        final int capacity;
        final int cost;
        final Map<Integer, CacheEntry> map;
        final PriorityQueue<CacheEntry> pq; // freq 小 + ts 舊的優先移出

        Level(int cap, int cost) {
            this.capacity = cap;
            this.cost = cost;
            this.map = new LinkedHashMap<>();
            this.pq = new PriorityQueue<>((a,b)->{
                if (a.freq != b.freq) return a.freq - b.freq;
                return Long.compare(a.ts, b.ts);
            });
        }
    }

    private final Level L1 = new Level(2, 1);
    private final Level L2 = new Level(5, 3);
    private final Level L3 = new Level(10, 10);
    private long clock = 0;

    public void put(int key, String value) {
        clock++;
        CacheEntry e = new CacheEntry(key, value, clock);
        insert(L1, e);
    }

    public String get(int key) {
        clock++;
        for (Level L : Arrays.asList(L1,L2,L3)) {
            if (L.map.containsKey(key)) {
                CacheEntry e = L.map.get(key);
                e.freq++; e.ts = clock;
                promoteIfNeeded(L, e);
                return e.value;
            }
        }
        return null;
    }

    private void insert(Level L, CacheEntry e) {
        if (L.map.size() >= L.capacity) evict(L);
        L.map.put(e.key, e); L.pq.offer(e);
    }

    private void evict(Level L) {
        CacheEntry evicted = L.pq.poll();
        if (evicted != null) {
            L.map.remove(evicted.key);
            if (L == L1) insert(L2, evicted);
            else if (L == L2) insert(L3, evicted);
        }
    }

    private void promoteIfNeeded(Level from, CacheEntry e) {
        if (from != L1 && e.freq > 1) {
            // 從 L2/L3 提升
            from.map.remove(e.key); from.pq.remove(e);
            if (from == L2) insert(L1, e);
            else if (from == L3) insert(L2, e);
        }
    }

    public void printStatus() {
        System.out.println("L1: " + L1.map.keySet());
        System.out.println("L2: " + L2.map.keySet());
        System.out.println("L3: " + L3.map.keySet());
    }

    public static void main(String[] args) {
        MultiLevelCacheSystem cache = new MultiLevelCacheSystem();
        cache.put(1, "A"); cache.put(2, "B"); cache.put(3, "C");
        cache.printStatus();
        cache.get(1); cache.get(1); cache.get(2);
        cache.printStatus();
        cache.put(4, "D"); cache.put(5, "E"); cache.put(6, "F");
        cache.printStatus();
    }
}