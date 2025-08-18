import java.util.*;

/**
 * 題目 3：使用 Heap 實作優先級佇列
 * 任務名稱唯一；數字越大優先級越高；同優先級則先到先服務。
 */
public class PriorityQueueWithHeap {

    // ========== 內部型別 ==========
    private static class Task {
        final String name;
        int priority;
        final long ts; // 越小代表越早加入（同優先級時用）

        Task(String name, int priority, long ts) {
            this.name = name;
            this.priority = priority;
            this.ts = ts;
        }
        @Override public String toString() { return name + "(" + priority + ")"; }
    }

    // Max-heap：用 ArrayList 當底層；indexMap 加速定位（O(1)）
    private final List<Task> heap = new ArrayList<>();
    private final Map<String, Integer> indexMap = new HashMap<>();
    private long clock = 0; // 全域時間戳

    // 比較：回傳正值代表 a 的優先權高於 b（要往上浮）
    private int compare(Task a, Task b) {
        if (a.priority != b.priority) return a.priority - b.priority;  // 大者優先
        // 同優先級：先加入者優先（ts 小者優先）
        // 為了讓「a 更早加入」時回傳正值：b.ts - a.ts
        return (a.ts == b.ts) ? 0 : (b.ts > a.ts ? 1 : -1);
    }

    // ========== 對外 API ==========
    public void addTask(String name, int priority) {
        if (indexMap.containsKey(name))
            throw new IllegalArgumentException("Task name duplicated: " + name);
        Task t = new Task(name, priority, clock++);
        heap.add(t);
        int i = heap.size() - 1;
        indexMap.put(name, i);
        siftUp(i);
    }

    /** 執行（移除並回傳）優先級最高的任務；若無任務回傳 null */
    public String executeNext() {
        if (heap.isEmpty()) return null;
        String topName = heap.get(0).name;
        removeAt(0);
        return topName;
    }

    /** 查看下一個要執行的任務名稱（不移除）；若無任務回傳 null */
    public String peek() {
        return heap.isEmpty() ? null : heap.get(0).name;
    }

    /** 修改既有任務優先級；若任務不存在則忽略 */
    public void changePriority(String name, int newPriority) {
        Integer i = indexMap.get(name);
        if (i == null) return;
        Task t = heap.get(i);
        int old = t.priority;
        t.priority = newPriority;
        if (newPriority > old) siftUp(i);
        else if (newPriority < old) siftDown(i);
        // 相等就不動
    }

    // ========== Heap 輔助 ==========
    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (compare(heap.get(i), heap.get(p)) > 0) {
                swap(i, p); i = p;
            } else break;
        }
    }

    private void siftDown(int i) {
        int n = heap.size();
        while (true) {
            int l = 2 * i + 1, r = 2 * i + 2, best = i;
            if (l < n && compare(heap.get(l), heap.get(best)) > 0) best = l;
            if (r < n && compare(heap.get(r), heap.get(best)) > 0) best = r;
            if (best != i) { swap(i, best); i = best; } else break;
        }
    }

    private void removeAt(int i) {
        int n = heap.size();
        swap(i, n - 1);
        Task removed = heap.remove(n - 1);
        indexMap.remove(removed.name);
        if (i < heap.size()) {   // 被換上來的節點需要重新定位
            siftUp(i);
            siftDown(i);
        }
    }

    private void swap(int i, int j) {
        if (i == j) return;
        Task a = heap.get(i), b = heap.get(j);
        heap.set(i, b); heap.set(j, a);
        indexMap.put(b.name, i);
        indexMap.put(a.name, j);
    }

    // ========== Demo ==========
    public static void main(String[] args) {
        PriorityQueueWithHeap q = new PriorityQueueWithHeap();
        q.addTask("備份", 1);
        q.addTask("緊急修復", 5);
        q.addTask("更新", 3);

        // 期望：緊急修復 → 更新 → 備份
        System.out.println("peek: " + q.peek());        // 緊急修復
        System.out.println(q.executeNext());            // 緊急修復
        System.out.println(q.executeNext());            // 更新
        System.out.println("變更備份優先級為 10");
        q.changePriority("備份", 10);
        q.addTask("巡檢", 2);
        System.out.println(q.executeNext());            // 備份
        System.out.println(q.executeNext());            // 巡檢
        System.out.println(q.executeNext());            // null
    }
}