import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Arrays;

public class BasicMinHeapPractice {

    // === Min Heap 以 ArrayList 實作 ===
    private final List<Integer> heap = new ArrayList<>();

    public void insert(int val) {
        heap.add(val);                 // 放到最後
        heapifyUp(heap.size() - 1);    // 往上調整
    }

    public int extractMin() {
        if (heap.isEmpty()) throw new NoSuchElementException("Heap is empty");
        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);         // 將最後一個移到根
            heapifyDown(0);            // 往下調整
        }
        return min;
    }

    public int getMin() {
        if (heap.isEmpty()) throw new NoSuchElementException("Heap is empty");
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // === 內部輔助 ===
    private void heapifyUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(i) < heap.get(parent)) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }

    private void heapifyDown(int i) {
        int n = heap.size();
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < n && heap.get(left) < heap.get(smallest)) smallest = left;
            if (right < n && heap.get(right) < heap.get(smallest)) smallest = right;

            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else break;
        }
    }

    private void swap(int i, int j) {
        int tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    // === 測試 ===
    public static void main(String[] args) {
        BasicMinHeapPractice h = new BasicMinHeapPractice();
        int[] inserts = {15, 10, 20, 8, 25, 5};
        for (int v : inserts) h.insert(v);

        System.out.println("當前最小值 (peek): " + h.getMin()); // 應為 5
        System.out.println("大小: " + h.size());               // 應為 6

        int[] extracted = new int[h.size()];
        for (int i = 0; i < extracted.length; i++) {
            extracted[i] = h.extractMin();
        }
        System.out.println("extractMin 順序: " + Arrays.toString(extracted));
        // 期望：[5, 8, 10, 15, 20, 25]

        System.out.println("是否為空: " + h.isEmpty()); // true
    }
}