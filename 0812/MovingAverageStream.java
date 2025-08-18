import java.util.*;

public class MovingAverageStream {
    private final int size;
    private final Queue<Integer> window = new LinkedList<>();
    private final PriorityQueue<Integer> lo = new PriorityQueue<>(Comparator.reverseOrder());
    private final PriorityQueue<Integer> hi = new PriorityQueue<>();
    private final Deque<Integer> minQ = new LinkedList<>();
    private final Deque<Integer> maxQ = new LinkedList<>();
    private double sum = 0.0;

    public MovingAverageStream(int size) {
        this.size = size;
    }

    public double next(int val) {
        window.offer(val);
        sum += val;
        // min/max
        while (!minQ.isEmpty() && minQ.getLast() > val) minQ.removeLast();
        minQ.addLast(val);
        while (!maxQ.isEmpty() && maxQ.getLast() < val) maxQ.removeLast();
        maxQ.addLast(val);
        // median
        if (lo.isEmpty() || val <= lo.peek()) lo.offer(val);
        else hi.offer(val);
        balanceHeaps();

        if (window.size() > size) {
            int removed = window.poll();
            sum -= removed;
            if (removed == minQ.peekFirst()) minQ.removeFirst();
            if (removed == maxQ.peekFirst()) maxQ.removeFirst();
            if (removed <= lo.peek()) lo.remove(removed);
            else hi.remove(removed);
            balanceHeaps();
        }
        return sum / window.size();
    }

    private void balanceHeaps() {
        while (lo.size() > hi.size() + 1) hi.offer(lo.poll());
        while (hi.size() > lo.size()) lo.offer(hi.poll());
    }

    public double getMedian() {
        if (lo.size() > hi.size()) return lo.peek();
        return ((double) lo.peek() + hi.peek()) / 2.0;
    }

    public int getMin() { return minQ.peekFirst(); }
    public int getMax() { return maxQ.peekFirst(); }

    public static void main(String[] args) {
        MovingAverageStream ma = new MovingAverageStream(3);
        System.out.println(ma.next(1));   // 1.0
        System.out.println(ma.next(10));  // 5.5
        System.out.println(ma.next(3));   // 4.67
        System.out.println(ma.next(5));   // 6.0
        System.out.println(ma.getMedian()); // 5.0
        System.out.println(ma.getMin());    // 3
        System.out.println(ma.getMax());    // 10
    }
}